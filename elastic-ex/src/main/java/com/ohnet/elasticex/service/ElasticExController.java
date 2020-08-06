package com.ohnet.elasticex.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping(value = "/api")
@Slf4j
public class ElasticExController implements ElasticExApi {

    @Override
    public ResponseEntity<String> getInfo() {
        log.info("we reach the app");
        return new ResponseEntity<>("welcome to elastic", HttpStatus.OK);
    }
}
