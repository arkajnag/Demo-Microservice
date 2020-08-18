package io.car.microservices;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BinaryOperator;
import java.util.stream.IntStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.car.microservices.model.Car;
import io.car.microservices.repository.CarRepository;

@SpringBootApplication
public class IoCarMicroservicesApplication implements CommandLineRunner {

	private Logger logger = LoggerFactory.getLogger(IoCarMicroservicesApplication.class);

	@Autowired
	private CarRepository carRepository;

	public static void main(String[] args) {
		SpringApplication.run(IoCarMicroservicesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ExecutorService createCarIOExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		IntStream.rangeClosed(0, 20).forEach(i -> {
			CompletableFuture.supplyAsync(this::createNewCarInformation, createCarIOExecutor).join();
		});

	}

	private Car createNewCarInformation() {
		try {
			logger.info("Started creating new Car Info in Thread:" + Thread.currentThread().getName());
			Car requestedCar = new Car();
			requestedCar.setCarID(UUID.randomUUID().toString());
			requestedCar.setCarModelName("ModelName-" + new Random().nextInt(99999));
			requestedCar.setYearESTD(getRandomWithinRange.apply(1950, 1999));
			return carRepository.save(requestedCar);
		} finally {
			logger.info("Finished creating new Car Info in Thread:" + Thread.currentThread().getName());
		}
	}

	private BinaryOperator<Integer> getRandomWithinRange = (minRange, maxRange) -> {
		return (int) ((Math.random() * (maxRange - minRange)) + minRange);
	};

}
