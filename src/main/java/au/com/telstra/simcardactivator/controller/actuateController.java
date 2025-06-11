package au.com.telstra.simcardactivator.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import au.com.telstra.simcardactivator.dto.ActivateRequest;
import au.com.telstra.simcardactivator.services.activateService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
            return ResponseEntity.ok("Received activation request for ICCID: ");
        }
        else{
            return ResponseEntity.status(502).body("");
        }
    }
    
    
}
