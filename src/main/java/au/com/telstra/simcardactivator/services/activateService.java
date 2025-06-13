package au.com.telstra.simcardactivator.services;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import au.com.telstra.simcardactivator.dto.ActivateResponse;
import au.com.telstra.simcardactivator.entity.SimCard;
import au.com.telstra.simcardactivator.repository.SimCardRepository;


@Service
public class activateService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final SimCardRepository simCardRepository;

    public activateService(SimCardRepository simCardRepository){
        this.simCardRepository = simCardRepository;
    }

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

    public SimCard registerSimCard(String iccid, String customerEmail, boolean active){
        SimCard simCard = new SimCard();
        simCard.setIccid(iccid);
        simCard.setCustomerEmail(customerEmail);
        simCard.setActive(active);
        return simCardRepository.save(simCard);
    }

    public Optional<SimCard> getSimCardById(Long id){
        System.out.println("Looking for SimCard with ID: " + id);
        return simCardRepository.findById(id);
    }

    public List<SimCard> getAllSimCards() {
        return simCardRepository.findAll();
    }
}
