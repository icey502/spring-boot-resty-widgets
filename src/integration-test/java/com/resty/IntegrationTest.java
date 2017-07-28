package com.resty;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.*;

/**
 * Meta annotations for integration tests
 *
 * Created by damianhagge on 5/12/17.
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@SpringBootTest(
        properties = {"server.port=0", "management.port=0"},
        classes = ServiceApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles(resolver = IntTestActiveProfileResolver.class)
public @interface IntegrationTest {
}
