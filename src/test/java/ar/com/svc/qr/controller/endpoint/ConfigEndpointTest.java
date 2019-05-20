package ar.com.svc.qr.controller.endpoint;

import org.junit.Test;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ConfigEndpointTest extends BaseTest{

    @Test
    public void getAllConfigTest() {
        ResponseEntity<List> response = this.restTemplate.getForEntity("/api/configs", List.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        response.getBody().stream().forEach(config -> {
            assertThat(((HashMap)config).get("clientCode")).isEqualTo("cctest1");
            assertThat(((HashMap)config).get("authURL")).isEqualTo("http://0.0.0.0:8090/api/dummy/token");
            assertThat(((HashMap)config).get("validateURL")).isEqualTo("http://0.0.0.0:8090/api/dummy/validate");
            assertThat(((HashMap)config).get("size")).isEqualTo(50);
            assertThat(((HashMap)config).get("ttl")).isEqualTo(3600);
        });
    }

}
