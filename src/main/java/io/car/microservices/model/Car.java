package io.car.microservices.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = Include.NON_EMPTY)
public class Car {
	
	@Id
	@Column(name = "carID", length = 200, unique = true, nullable = false)
	private String carID;
	@Column(name = "carModelName", length = 200, unique = true, nullable = false)
	private String carModelName;
	@Column(name = "yearESTD", nullable = false)
	private Integer yearESTD;
	public String getCarID() {
		return carID;
	}
	public void setCarID(String carID) {
		this.carID = carID;
	}
	public String getCarModelName() {
		return carModelName;
	}
	public void setCarModelName(String carModelName) {
		this.carModelName = carModelName;
	}
	public Integer getYearESTD() {
		return yearESTD;
	}
	public void setYearESTD(Integer yearESTD) {
		this.yearESTD = yearESTD;
	}
	public Car(String carID, String carModelName, Integer yearESTD) {
		this.carID = carID;
		this.carModelName = carModelName;
		this.yearESTD = yearESTD;
	}
	public Car() {
	
	}
	@Override
	public String toString() {
		return "Car [carID=" + carID + ", carModelName=" + carModelName + ", yearESTD=" + yearESTD + "]";
	}
}
