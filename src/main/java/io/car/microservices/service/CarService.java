package io.car.microservices.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.car.microservices.exceptionhandler.DataNotAllowedException;
import io.car.microservices.exceptionhandler.DataNotFoundException;
import io.car.microservices.model.Car;
import io.car.microservices.model.CarDTO;
import io.car.microservices.repository.CarRepository;

@Service
public class CarService {

	private Logger logger = LoggerFactory.getLogger(CarService.class);

	@Autowired
	private CarRepository carRepository;

	public CompletableFuture<Car> addNewCar(Car carObj) throws DataNotAllowedException {
		try {
			logger.info("Started Execution of " + Thread.currentThread().getStackTrace()[2].getMethodName()
					+ " in Thread:" + Thread.currentThread().getName());
			String carid = carObj.getCarID();
			String carmodelname = carObj.getCarModelName();
			Integer carManufactured = carObj.getYearESTD();
			if (carid == null || carid.equalsIgnoreCase(null) || carid.equalsIgnoreCase("")) {
				throw new DataNotAllowedException(
						"Car ID can't be a Null Object nor accepts null as value nor Blank Value");
			}
			if (carmodelname == null || carmodelname.equalsIgnoreCase(null) || carmodelname.equalsIgnoreCase("")) {
				throw new DataNotAllowedException(
						"Car Modelname can't be a Null Object nor accepts null as value nor Blank Value");
			}
			if (carManufactured == null || carManufactured.intValue() == 0) {
				throw new DataNotAllowedException("Car Manufactured year can't be a Null Object nor 0");
			}
			if(carRepository.findById(carid).isPresent()) {
				throw new DataNotAllowedException("Duplicate Car ID. Please enter different Car ID");
			}	
			if(carRepository.findByCarModelName(carmodelname)!=null)
				throw new DataNotAllowedException("Duplicate Car Model Name. Please enter different Car ID");
			return CompletableFuture.completedFuture(carRepository.save(carObj));
		} finally {
			logger.info("Finished Execution of " + Thread.currentThread().getStackTrace()[2].getMethodName()
					+ " in Thread:" + Thread.currentThread().getName());
		}

	}

	public CompletableFuture<Car> getCarInfoByModelName(String modelName)
			throws DataNotAllowedException, DataNotFoundException {
		try {
			logger.info("Started Execution of " + Thread.currentThread().getStackTrace()[2].getMethodName()
					+ " in Thread:" + Thread.currentThread().getName());
			if (modelName == null || modelName.equalsIgnoreCase(null) || modelName.equalsIgnoreCase("")) {
				throw new DataNotAllowedException(
						"Car Modelname can't be a Null Object nor accepts null as value nor Blank Value");
			}
			Car responseObj = carRepository.findByCarModelName(modelName);
			if (responseObj == null)
				throw new DataNotFoundException("No Car records found with Model Name:" + modelName);
			return CompletableFuture.completedFuture(responseObj);
		} finally {
			logger.info("Finished Execution of " + Thread.currentThread().getStackTrace()[2].getMethodName()
					+ " in Thread:" + Thread.currentThread().getName());
		}

	}

	public CompletableFuture<CarDTO> getAllCarsInformation() throws DataNotFoundException {
		try {
			logger.info("Started Execution of " + Thread.currentThread().getStackTrace()[2].getMethodName()
					+ " in Thread:" + Thread.currentThread().getName());
			List<Car> cars = carRepository.findAll();
			if (!(cars.size() > 0))
				throw new DataNotFoundException("No Car Records present currently. Please add new Car Record.");
			return CompletableFuture.completedFuture(new CarDTO(cars));
		} finally {
			logger.info("Finished Execution of " + Thread.currentThread().getStackTrace()[2].getMethodName()
					+ " in Thread:" + Thread.currentThread().getName());
		}

	}

	public CompletableFuture<Car> updateExistingCarInformation(String id, Car carRequestObj)
			throws DataNotAllowedException, DataNotFoundException {
		try {
			logger.info("Started Execution of " + Thread.currentThread().getStackTrace()[2].getMethodName()
					+ " in Thread:" + Thread.currentThread().getName());
			Car carResponseObj = null;
			String carmodelname = carRequestObj.getCarModelName();
			Integer carManufactured = carRequestObj.getYearESTD();
			if (id == null || id.equalsIgnoreCase(null) || id.equalsIgnoreCase("")) {
				throw new DataNotAllowedException(
						"Car ID can't be a Null Object nor accepts null as value nor Blank Value");
			}
			if (carmodelname == null || carmodelname.equalsIgnoreCase(null) || carmodelname.equalsIgnoreCase("")) {
				throw new DataNotAllowedException(
						"Car Modelname can't be a Null Object nor accepts null as value nor Blank Value");
			}
			if (carManufactured == null || carManufactured == 0) {
				throw new DataNotAllowedException("Car Manufactured year can't be a Null Object nor 0");
			}
			if (!carRepository.findById(id).isPresent())
				throw new DataNotFoundException("No Car records found with platenumber:" + id);
			carResponseObj = carRepository.save(carRequestObj);
			return CompletableFuture.completedFuture(carResponseObj);
		} finally {
			logger.info("Finished Execution of " + Thread.currentThread().getStackTrace()[2].getMethodName()
					+ " in Thread:" + Thread.currentThread().getName());
		}

	}

	public CompletableFuture<Car> patchExistingCarInformation(String id, Car carObj)
			throws DataNotAllowedException, DataNotFoundException {
		try {
			logger.info("Started Execution of " + Thread.currentThread().getStackTrace()[2].getMethodName()
					+ " in Thread:" + Thread.currentThread().getName());
			String carmodelname = carObj.getCarModelName();
			Integer carManufactured = carObj.getYearESTD();
			if (id == null || id.equalsIgnoreCase(null) || id.equalsIgnoreCase("")) {
				throw new DataNotAllowedException(
						"Car ID can't be a Null Object nor accepts null as value nor Blank Value");
			}
			if (!carRepository.findById(id).isPresent())
				throw new DataNotFoundException("No Car records found with platenumber:" + id);
			Car savedCarObj = carRepository.findById(id).get();
			if (carmodelname != null || carManufactured != null) {
				savedCarObj.setCarModelName(carmodelname);
				savedCarObj.setYearESTD(carManufactured);
			}
			return CompletableFuture.completedFuture(savedCarObj);
		} finally {
			logger.info("Finished Execution of " + Thread.currentThread().getStackTrace()[2].getMethodName()
					+ " in Thread:" + Thread.currentThread().getName());
		}

	}

}
