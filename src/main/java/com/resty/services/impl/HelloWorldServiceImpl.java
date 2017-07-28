package com.resty.services.impl;

import com.resty.services.HelloWorldService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created by damianhagge on 5/19/17.
 */
@Slf4j
@Component
public class HelloWorldServiceImpl implements HelloWorldService {

    @Override
    public String getHello() {
        return "Hello World";
    }
}
