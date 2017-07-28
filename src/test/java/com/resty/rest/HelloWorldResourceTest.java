package com.resty.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.resty.services.HelloWorldService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Created by damianhagge on 5/16/17.
 */
public class HelloWorldResourceTest {

    @Mock
    HelloWorldService helloWorldService;

    private ObjectMapper mapper = new ObjectMapper();
    private HelloWorldResource target;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        target = new HelloWorldResource();

        ReflectionTestUtils.setField(target, "helloWorldService", helloWorldService);
        mockMvc = standaloneSetup(target).setControllerAdvice(new SharedExceptionHandler()).build();
    }

    @Test
    public void test_helloWorld() throws Exception {
        when(helloWorldService.getHello()).thenReturn("Hi there");

        RequestBuilder builder = get("/hello");
        MvcResult result =
                mockMvc.perform(builder).andExpect(status().is(HttpStatus.OK.value())).andReturn();
        Map<String, String> response = mapper.readValue(
                result.getResponse().getContentAsString(), new TypeReference<Map<String,Object>>() {});
        assertThat(response.get("message"), equalTo("Hi there"));

        verify(helloWorldService).getHello();
    }
}
