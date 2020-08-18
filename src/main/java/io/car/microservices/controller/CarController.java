package io.car.microservices.controller;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.car.microservices.exceptionhandler.DataNotAllowedException;
import io.car.microservices.exceptionhandler.DataNotFoundException;
import io.car.microservices.model.Car;
import io.car.microservices.model.CarDTO;
import io.car.microservices.service.CarService;

@RestController
@RequestMapping(value = "/rest/car", produces = "application/json")
public class CarController {

	@Autowired
	private CarService carService;

	@PostMapping("/new")
	public CompletableFuture<ResponseEntity<Car>> addNewCarDetails(@RequestBody Car carObj) throws DataNotAllowedException {
		carObj.setCarID(UUID.randomUUID().toString());
		return carService.addNewCar(carObj).thenApplyAsync(carResObj -> ResponseEntity.status(HttpStatus.CREATED)
				.contentType(MediaType.APPLICATION_JSON).body(carResObj));
	}

	@GetMapping("/all")
	public CompletableFuture<ResponseEntity<CarDTO>> getAllCarInformation() throws DataNotFoundException {
		return carService.getAllCarsInformation().thenApplyAsync(carResObj -> ResponseEntity.status(HttpStatus.OK)
				.contentType(MediaType.APPLICATION_JSON).body(carResObj));
	}

	@GetMapping("/model/{modelName}")
	public CompletableFuture<ResponseEntity<Car>> getCarInfoByModelName(@PathVariable(value = "modelName", required = true) String modelName)
			throws DataNotAllowedException, DataNotFoundException {
		return carService.getCarInfoByModelName(modelName).thenApplyAsync(carResObj -> ResponseEntity.status(HttpStatus.OK)
				.contentType(MediaType.APPLICATION_JSON).body(carResObj));
	}

	@PutMapping(value = "/put/{id}")
	public CompletableFuture<ResponseEntity<Car>> UpdateExistingCarInformation(@PathVariable(value = "id", required = true) String id,
			@RequestBody Car carRequestObj) throws DataNotAllowedException, DataNotFoundException {
		return carService.updateExistingCarInformation(id, carRequestObj).thenApplyAsync(carResObj -> ResponseEntity
				.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(carResObj));
	}

	@PatchMapping(value = "/patch/{id}")
	public CompletableFuture<ResponseEntity<Car>> PatchExistingCarInformation(@PathVariable(value = "id", required = true) String id,
			@RequestBody Car carObj) throws DataNotAllowedException, DataNotFoundException {
		return carService.patchExistingCarInformation(id, carObj).thenApplyAsync(carResObj -> ResponseEntity
				.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(carResObj));
	}
}
