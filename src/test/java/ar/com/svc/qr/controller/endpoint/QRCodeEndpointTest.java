package ar.com.svc.qr.controller.endpoint;

import ar.com.svc.qr.controller.dto.QRCodeDTO;
import org.junit.Test;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

public class QRCodeEndpointTest extends BaseTest {

    @Test
    public void generateQRTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("clientCode", "cctest1");
        headers.add("token", "userToken1");
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<QRCodeDTO> response = this.restTemplate.exchange("/api/qr/generate", HttpMethod.POST, entity, QRCodeDTO.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

    }
}
