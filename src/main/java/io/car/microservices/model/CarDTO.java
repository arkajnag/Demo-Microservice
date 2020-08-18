package io.car.microservices.model;

import java.util.List;

/**
 * Data Transfer Object created as a wrapper to Car Object.
 * Holding List or Flux of Car Objects.
 */
public class CarDTO {
	
	private List<Car> cars;

	public List<Car> getCars() {
		return cars;
	}

	public void setCars(List<Car> cars) {
		this.cars = cars;
	}

	public CarDTO(List<Car> cars) {
		this.cars = cars;
	}

	public CarDTO() {
		
	}

	@Override
	public String toString() {
		return "CarDTO [cars=" + cars + "]";
	}
	
}
