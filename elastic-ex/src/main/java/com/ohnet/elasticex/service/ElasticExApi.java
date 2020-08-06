package com.ohnet.elasticex.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

public interface ElasticExApi {

    @ApiOperation(value = "Finds stations near by Office.", nickname = "getNearByStations", notes = "Finds stations near by Office.", response = String.class, responseContainer = "List", tags = {
            "info", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = String.class ),
            @ApiResponse(code = 400, message = "Invalid tag value") })
    @RequestMapping(value = "/v1/info", produces = {
            "application/json" }, method = RequestMethod.GET)
    ResponseEntity<String> getInfo();
}
