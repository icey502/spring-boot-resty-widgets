package com.resty.rest;

import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by damianhagge on 7/27/17.
 */
@RestController
@RequestMapping("/ping")
@Slf4j
public class PingResource {

    @Autowired
    private Environment environment;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getHello() throws Exception {
        String[] activeProfiles = environment.getActiveProfiles();
        Map<String, Object> payload = ImmutableMap.of("activeProfiles", activeProfiles);

        return new ResponseEntity<>(payload, HttpStatus.OK);
    }
}
