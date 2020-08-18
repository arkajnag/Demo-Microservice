package io.car.microservices;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.AssertJUnit;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Random;
import java.util.function.BinaryOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.car.microservices.model.Car;
import io.car.microservices.model.CarDTO;

@SpringBootTest(classes = IoCarMicroservicesApplication.class)
class IoCarMicroservicesApplicationTests extends AbstractTestNGSpringContextTests {

	private MockMvc mockMVC;
	private Logger logger = LoggerFactory.getLogger(IoCarMicroservicesApplicationTests.class);
	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private WebApplicationContext webApplicationContext;

	@DataProvider
	public Object[][] getCarDetails() {
		return new Object[][] {
			{"CarModel-123",getRandomWithinRange.apply(1950, 2020)},
			{"CarModel-" + new Random().nextInt(99999),getRandomWithinRange.apply(1950, 2020)}
		};
	}
	@BeforeClass
	public void setUpMVC() {
		mockMVC = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test(priority = 1, description = "Verify New Car Information is added in backend Repo as expected.", dataProvider="getCarDetails")
	public void tcAddNewCarInformation(String carModelName, Integer manufacturedYear) throws Exception {
		try {
			logger.info("Started Execution of " + Thread.currentThread().getStackTrace()[2].getMethodName()
					+ " in Thread:" + Thread.currentThread().getName());
			String jsonPayload = "";
			Car carObj = new Car();
			carObj.setCarModelName(carModelName);
			carObj.setYearESTD(manufacturedYear);
			try {
				jsonPayload = mapper.writeValueAsString(carObj);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			MvcResult mvcResult = mockMVC
					.perform(post("/rest/car/new").content(jsonPayload).contentType(MediaType.APPLICATION_JSON))
					.andExpect(request().asyncStarted()).andReturn();
			String responseJSONString = mockMVC.perform(asyncDispatch(mvcResult)).andExpect(status().isCreated())
					.andExpect(content().contentType("application/json")).andReturn().getResponse()
					.getContentAsString();
			Car responseJSONObj = mapper.readValue(responseJSONString, Car.class);
			logger.info("" + responseJSONObj);
		} finally {
			logger.info("Finished Execution of " + Thread.currentThread().getStackTrace()[2].getMethodName()
					+ " in Thread:" + Thread.currentThread().getName());
		}
	}

	@Test(priority = 2, description = "Verify All Car Records are retrieved from backend", dependsOnMethods = "tcAddNewCarInformation")
	public void tcGetAllCarInformation() throws Exception {
		try {
			logger.info("Started Execution of " + Thread.currentThread().getStackTrace()[2].getMethodName()
					+ " in Thread:" + Thread.currentThread().getName());
			MvcResult mvcResult = mockMVC.perform(get("/rest/car/all")).andExpect(request().asyncStarted()).andReturn();
			String responseJSONString=mockMVC.perform(asyncDispatch(mvcResult))
			.andExpect(status().isOk())
			.andReturn()
			.getResponse()
			.getContentAsString();
			CarDTO carDTOJSONObject=mapper.readValue(responseJSONString, CarDTO.class);
			int countOfCars=carDTOJSONObject.getCars().size();
			AssertJUnit.assertEquals(countOfCars, 23);
		} finally {
			logger.info("Finished Execution of " + Thread.currentThread().getStackTrace()[2].getMethodName()
					+ " in Thread:" + Thread.currentThread().getName());
		}
	}
	
	@Test(priority=3, description="Verify Car Record retrieved when queried with Model name", dependsOnMethods="tcAddNewCarInformation")
	public void tcGetCarInformationByModelName() throws Exception {
		try {
			logger.info("Started Execution of " + Thread.currentThread().getStackTrace()[2].getMethodName()
					+ " in Thread:" + Thread.currentThread().getName());
			final String modelName="CarModel-123";
			MvcResult mvcResult=mockMVC.perform(get("/rest/car/model/"+modelName))
			.andExpect(request().asyncStarted())
			.andReturn();
			String jsonPayloadResponseString=mockMVC.perform(asyncDispatch(mvcResult))
			.andExpect(status().isOk())
			.andReturn()
			.getResponse()
			.getContentAsString();
			Car responseJSONObject=mapper.readValue(jsonPayloadResponseString, Car.class);
			Assert.assertTrue((responseJSONObject.getCarID()!=null?true:false), "Car Plate Number is not populated as expected.");
			Assert.assertEquals(responseJSONObject.getCarModelName(), modelName, "Expected Model Name is not retrieved.");
		}finally {
			logger.info("Finished Execution of " + Thread.currentThread().getStackTrace()[2].getMethodName()
					+ " in Thread:" + Thread.currentThread().getName());
		}
	}

	private BinaryOperator<Integer> getRandomWithinRange = (minRange, maxRange) -> {
		return (int) ((Math.random() * (maxRange - minRange)) + minRange);
	};

}
