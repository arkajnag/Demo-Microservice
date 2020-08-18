package io.car.microservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import io.car.microservices.model.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, String> {

	Car findByCarModelName(String modelName);

}
