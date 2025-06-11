package au.com.telstra.simcardactivator.services;

import java.util.Collections;
import java.util.Map;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import au.com.telstra.simcardactivator.dto.ActivateResponse;


@Service
public class activateService {
    private final RestTemplate restTemplate = new RestTemplate();

    public boolean actuatorSuccess(String iccid){
        String activationURL = "http://localhost:8444/actuate";
        Map<String,String> payload = Collections.singletonMap("iccid", iccid);
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(payload, header);
        try {
            ResponseEntity<ActivateResponse> response = restTemplate.postForEntity(activationURL, request, ActivateResponse.class);
            return response.getStatusCode().is2xxSuccessful() &&
                   response.getBody() != null &&
                   response.getBody().isSuccess();
        } catch (Exception e) {
            System.out.println("Error contacting distributor: " + e.getMessage());
            return false;
        }
    }
}
