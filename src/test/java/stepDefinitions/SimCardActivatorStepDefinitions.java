package stepDefinitions;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import au.com.telstra.simcardactivator.SimCardActivator;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = SimCardActivator.class, loader = SpringBootContextLoader.class)
public class SimCardActivatorStepDefinitions {
    @Autowired
    private TestRestTemplate restTemplate;
    private ResponseEntity<String> response;

    @Given("the ICCID {string} with the email {string}")
    public void cardActivation(String iccid, String email){
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("iccid", iccid);
        requestBody.put("customerEmail", email);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String,String>> request = new HttpEntity<>(requestBody, header);
        restTemplate.postForEntity("http://localhost:8080/activate", request, String.class);


    }

    @When("I query the simcard with ID {int}")
    public void queryByID(int id){
        response = restTemplate.getForEntity("http://localhost:8080/simCard?id="+id, String.class);
    }

    @Then("the SIM card is {string}")
    public void checksIfActive(String expectedAnswer) throws JsonMappingException, JsonProcessingException{
        if(response.getStatusCode()==HttpStatus.OK){
            ObjectMapper mapper = new ObjectMapper();
            JsonNode json = mapper.readTree(response.getBody());
            boolean isActive = json.get("active").asBoolean();

            if ("active".equals(expectedAnswer)) {
                assertEquals(true, isActive);
            } else if ("not active".equals(expectedAnswer)) {
                assertEquals(false, isActive);
            } else {
                throw new IllegalArgumentException("Invalid expectedAnswer: " + expectedAnswer);
            }
        }
        else{
            assertEquals("not active", expectedAnswer);
        }
    }
}