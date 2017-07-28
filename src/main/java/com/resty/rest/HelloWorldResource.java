package com.resty.rest;

import com.google.common.collect.ImmutableMap;
import com.resty.services.HelloWorldService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by damianhagge on 5/16/17.
 */
@RestController
@RequestMapping("/hello")
@Slf4j
public class HelloWorldResource {

    @Autowired
    HelloWorldService helloWorldService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getHello() throws Exception {
        String message =  helloWorldService.getHello();
        Map<String, String> payload = ImmutableMap.of("message", message);
        return new ResponseEntity<>(payload, HttpStatus.OK);
    }
}
