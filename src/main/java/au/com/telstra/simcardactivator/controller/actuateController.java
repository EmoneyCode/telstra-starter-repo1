package au.com.telstra.simcardactivator.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import au.com.telstra.simcardactivator.dto.ActivateRequest;
import au.com.telstra.simcardactivator.entity.SimCard;
import au.com.telstra.simcardactivator.services.activateService;


;

@RestController
@RequestMapping()
public class actuateController {

    private final activateService service;

    public actuateController(activateService service){
        this.service = service;
    }

    @PostMapping("/activate")
    public ResponseEntity<String> requestActivation(@RequestBody ActivateRequest request) {
        boolean success = service.actuatorSuccess(request.getIccid());

        if(success){
            service.registerSimCard(request.getIccid(), request.getCustomerEmail(), true);
            return ResponseEntity.ok("Received activation request for ICCID: " + request.getIccid());
        }
        else{
            return ResponseEntity.status(502).body("Error your code sucks");
        }
    }

    @GetMapping("/simCard")
    public ResponseEntity<SimCard> getSimCard(@RequestParam Long id) {
        return service.getSimCardById(id)
                      .map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/allSimCards")
    public List<SimCard> getAllSimCards() {
        return service.getAllSimCards();
    }
    
    
    
}
