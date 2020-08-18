# Demo-Microservice
Demo Microservice to host in Heroku to practise CRUD operations with H2 embedded DB

- Microservice using Spring Boot Web (Not Reactive) and H2 embedded DB.
- Unit Test Cases with TestNG integration (Includes DataProvider for Test Data and MockMVC-WebApplicationContext for triggering async test cases)
- Includes Eureka Service Discovery with Eureka Server and Eureka Client
     - @EnableEurekaServer
     - @EnableEurekaClient
     - @LoadBalancer (for RestTemplate or WebClient @Bean)
- Includes Spring Cloud Config Server to host Global properties and Spring cloud config client to embrace those properties using @Value
- Includes Netflix Zuul for Api Gateway and proper routing.
