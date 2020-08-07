package com.ohnet.elasticex.service;

import com.ohnet.elasticex.data_load.CarService;
import com.ohnet.elasticex.entity.Car;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.text.IsEqualIgnoringCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ElasticExApiTest {


    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    @Qualifier(
            value = "randomCarService"
    )
    private CarService carService;

    @Test
    void getInfoTest() {
        log.info("start testing");
        WebTestClient.BodySpec<String, ?> welcome_to_elastic = webTestClient.get().uri("/api/v1/info")
                                                                                  .exchange()
                                                                                  .expectStatus()
                                                                                  .is2xxSuccessful()
                                                                                  .expectBody(String.class)
                                                                                  .value(IsEqualIgnoringCase.equalToIgnoringCase("welcome to elastic"));
        log.info("result: {}", welcome_to_elastic.returnResult().getResponseBody());
        assertThat(welcome_to_elastic.returnResult().getResponseBody(), is(notNullValue()));
    }

    @Test
    void randomTest() {
       log.info("start testing");
       webTestClient.get().uri("/api/random").exchange().expectStatus().is2xxSuccessful().expectBody(Car.class).value(car -> {
           assertTrue(CarService.BRANDS.contains(car.getBrand()));
           assertTrue(CarService.COLORS.contains(car.getColor()));
       });
    }

    @Test
    void findCarsByBrandAndColor() {
      log.info("start testing");
      Car randomCar = carService.generateCar();

       EntityExchangeResult<List<Car>> listEntityExchangeResult = webTestClient.post().uri("/api/find-json").accept(MediaType.APPLICATION_JSON).bodyValue(randomCar).exchange().expectStatus().is2xxSuccessful().expectBodyList(Car.class).returnResult();

      assertNotNull(listEntityExchangeResult);

      listEntityExchangeResult.getResponseBody().iterator().forEachRemaining(car -> {
          assertNotNull(car);
          Car aCar = (Car) car;
          log.info("the car has: brand-> {}, color -> {}", aCar.getBrand(), aCar.getColor());
          assertThat(randomCar.getBrand().equals(car.getBrand()), is(true));
          assertThat(randomCar.getColor().equals(car.getColor()), is(true));
      });

      log.info("ending...");
    }
}
