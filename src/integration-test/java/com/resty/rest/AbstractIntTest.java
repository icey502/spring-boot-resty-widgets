package com.resty.rest;

import org.apache.commons.codec.binary.Base64;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.net.URI;

/**
 * Base class which provides functionality needed by all restful integration tests
 *
 * Created by damianhagge on 5/19/17.
 */
public class AbstractIntTest {

    protected URI testUri;
    protected TestRestTemplate testRestTemplate = new TestRestTemplate();
    protected RestTemplate template;

    protected JdbcTemplate jdbcTemplate;

    @Value("${local.server.port}")
    protected int port;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Before
    public void before() throws Exception {
        testUri = new URI("http://localhost:" + port);

        // prevent 401 auth errors from being retried (default behavior)
        template = testRestTemplate.getRestTemplate();
        if (template.getRequestFactory() instanceof SimpleClientHttpRequestFactory) {
            ((SimpleClientHttpRequestFactory) template.getRequestFactory()).setOutputStreaming(false);
        }

        setup();
    }

    /**
     * Method that child classes can override which will be called at at setup time
     */
    protected void setup() throws Exception {
        // overridden by children
    }

    protected static HttpHeaders getRequestHeaders() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/json");

        byte[] base64CredsBytes = Base64.encodeBase64("system:resty".getBytes("UTF-8"));
        headers.add(HttpHeaders.AUTHORIZATION, "Basic " + new String(base64CredsBytes, "UTF-8"));

        return headers;
    }
}
