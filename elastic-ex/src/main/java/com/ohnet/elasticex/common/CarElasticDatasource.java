package com.ohnet.elasticex.common;

import com.ohnet.elasticex.data_load.CarService;
import com.ohnet.elasticex.entity.Car;
import com.ohnet.elasticex.repository.CarElasticRepository;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;

@Slf4j
@Component
public class CarElasticDatasource {

    @Autowired
    private CarElasticRepository repository;

    @Autowired
    @Qualifier("randomCarService")
    private CarService carService;

    @Autowired
    @Qualifier("webClientElasticsearch")
    private WebClient webClient;

    @EventListener(ApplicationReadyEvent.class)
    public void populateData() {
        var response = webClient.delete().uri("elastic-ex").retrieve().bodyToMono(String.class).block();
        log.info("deleted car index {}", response);

        var myCars = new ArrayList<Car>();

        for(int i=0; i < 10_000; i++) {
            myCars.add(carService.generateCar());
        }

        repository.saveAll(myCars);

        log.info("add cars to repo: {}", repository.count());
    }
}
