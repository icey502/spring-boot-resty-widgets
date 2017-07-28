package com.resty.rest;

import com.resty.IntegrationTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by damianhagge on 7/3/17.
 */
@RunWith(SpringRunner.class)
@IntegrationTest
public class HelloWorldResourceIntTest extends AbstractIntTest {

    @Test
    public void test_helloWorld() throws Exception {
        ResponseEntity<Map<String, String>> response = template.exchange(
                testUri + "/hello",
                HttpMethod.GET, new HttpEntity<>(getRequestHeaders()),
                new ParameterizedTypeReference<Map<String, String>>() {});
        assertThat(response.getStatusCode().value(), equalTo(200));

        assertThat(response.getBody().get("message"), equalTo("Hello World"));
    }
}
