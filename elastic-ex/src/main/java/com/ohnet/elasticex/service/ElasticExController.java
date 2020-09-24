package com.ohnet.elasticex.service;

import com.ohnet.elasticex.api.ErrorResponse;
import com.ohnet.elasticex.data_load.CarService;
import com.ohnet.elasticex.entity.Car;
import com.ohnet.elasticex.repository.CarElasticRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping(value = "/api")
@Slf4j
public class ElasticExController implements ElasticExApi {

    @Autowired
    private CarService carService;

    @Autowired
    private CarElasticRepository elasticRepository;

    @Override
    public ResponseEntity<String> getInfo() {
        log.info("we reach the app");
        //return new ResponseEntity<>("welcome to elastic", HttpStatus.OK);
        return ResponseEntity.ok().body("welcome to elastic");
    }

    @Override
    public ResponseEntity<Car> random() {
        return new ResponseEntity<>(carService.generateCar(), HttpStatus.OK);
    }

    @Override
    public List<Car> findCarsByBrandAndColor(Car car) {
        return elasticRepository.findByBrandAndColor(car.getBrand(), car.getColor());
    }

    @Override
    public List<Car> findCarsByBrandAndColorPageable(Car car, int page, int size) {
        if(car == null && car.getColor().isEmpty() || StringUtils.isNumeric(car.getColor())) {
            throw new IllegalArgumentException("no valid color: " + car.getColor());
        }
        var pageable = PageRequest.of(page, size);
        return elasticRepository.findByBrandAndColor(car.getBrand(), car.getColor(), pageable).getContent();
    }

    @Override
    public List<Car> findCarsReleasedAfter(LocalDate firstReleaseDate) {
        return elasticRepository.findByFirstReleaseDateAfter(firstReleaseDate);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    private  ResponseEntity<ErrorResponse> handleInvalidArgumentException(IllegalArgumentException iax) {
        var message = "Exception, " + iax.getMessage();
        log.warn(message);

        var response = new ErrorResponse(message, LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/random-error")
    public ResponseEntity<String> randomError() {
        int remainder = new Random().nextInt() % 5;
        var body = "Kibana";

        switch (remainder) {
            case 0:
                return ResponseEntity.ok().body(body);
            case 1:
            case 2:
                return ResponseEntity.badRequest().body(body);
            default:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
        }
    }

}
