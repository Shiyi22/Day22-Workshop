package ibfbatch2paf.day22workshop.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ibfbatch2paf.day22workshop.model.RSVP;
import ibfbatch2paf.day22workshop.service.RsvpService;
import jakarta.json.JsonArray;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class RsvpRestController {

    @Autowired
    private RsvpService rsvpSvc; 

    @GetMapping("/rsvps")
    public ResponseEntity<List<RSVP>> getAllRsvp() {

        List<RSVP> rsvps = new ArrayList<>(); 
        rsvps = rsvpSvc.findAll();
        if (rsvps.isEmpty()) {
            return ResponseEntity.noContent().build(); 
        } 
        return ResponseEntity.ok().body(rsvps); 
    }

    @GetMapping("/rsvp")
    public ResponseEntity<List<RSVP>> getByName(@RequestParam String fullName) {

        List<RSVP> rsvps = rsvpSvc.findByName(fullName);
        if (rsvps.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(rsvps); 
    }

    // create another thymeleaf controller to display form and input 
    @PostMapping("/rsvp")
    public ResponseEntity<Boolean> save(@RequestBody RSVP rsvp) throws ParseException {
        
        Boolean result; 
        
        if (rsvp.getId() != null) {
            RSVP existingRsvp = rsvpSvc.findById(rsvp.getId());
            if (existingRsvp != null) {
                // update existing rsvp details 
                result = rsvpSvc.update(rsvp);
                return new ResponseEntity<>(result, HttpStatus.CREATED); 
            }
        }
        // create new rsvp details 
        result = rsvpSvc.save(rsvp); 
        return new ResponseEntity<Boolean>(result,HttpStatus.CREATED); 
    }

    // to change 
    @PutMapping("/rsvp/{email}")
    public ResponseEntity<Boolean> update(@PathVariable("email") String email, @RequestBody RSVP rsvp) {
        Boolean result;

        Optional<RSVP> opt = rsvpSvc.findByEmail(email); 
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build(); 
        }

        result = rsvpSvc.update(rsvp); 
        return new ResponseEntity<Boolean>(result, HttpStatus.CREATED); 
    }

    @GetMapping("/rsvps/count")
    public ResponseEntity<Integer> count() {
        return new ResponseEntity<>(rsvpSvc.countAll(), HttpStatus.CREATED); 
    }
    
    // batchUpdate mapping 
    // enter raw json data using JsonObject and Array 
    @PutMapping("/rsvps/batch")
    public ResponseEntity<int[]> batchUpdate(@RequestBody String payload) throws ParseException {

        // convert payload to Json 
        // convert Json to a list of RSVP 
        JsonArray arr = rsvpSvc.toJArray(payload); 
        List<RSVP> rsvps = RSVP.toList(arr); 

        int[] intArr = rsvpSvc.batchUpdate(rsvps);
        return new ResponseEntity<>(intArr, HttpStatus.CREATED); 
    }
}
    
