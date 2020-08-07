package com.ohnet.elasticex.service;

import com.ohnet.elasticex.entity.Car;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.time.LocalDate;
import java.util.List;

public interface ElasticExApi {

    @ApiOperation(value = "Finds stations near by Office.", nickname = "getNearByStations", notes = "Finds stations near by Office.", response = String.class, responseContainer = "List", tags = {
            "info", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = String.class ),
            @ApiResponse(code = 400, message = "Invalid tag value") })
    @RequestMapping(value = "/v1/info", produces = {
            "application/json" }, method = RequestMethod.GET)
    ResponseEntity<String> getInfo();

    @RequestMapping(value = "/random", produces = {
            "application/json" }, method = RequestMethod.GET)
    ResponseEntity<Car> random();

    @PostMapping(value = "/find-json", consumes= MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    List<Car> findCarsByBrandAndColor(@RequestBody Car car);

    @PostMapping(value = "/find-json-pageable", consumes= MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    List<Car> findCarsByBrandAndColorPageable(@RequestBody Car car, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size);

    @GetMapping(value = "/cars/date", produces = MediaType.APPLICATION_JSON_VALUE)
    List<Car> findCarsReleasedAfter(@RequestParam(name="first_release_date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate firstReleaseDate);
}
