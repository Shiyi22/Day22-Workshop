package ibfbatch2paf.day22workshop.service;

import java.io.StringReader;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ibfbatch2paf.day22workshop.model.RSVP;
import ibfbatch2paf.day22workshop.repository.RsvpRepository;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonReader;

@Service
public class RsvpService {

    @Autowired
    private RsvpRepository rsvpRepo; 

    public int countAll() {
        return rsvpRepo.countAll(); 
    }

    public List<RSVP> findAll() {
        return rsvpRepo.findAll(); 
    }

    public RSVP findById(int id) {
        return rsvpRepo.findById(id); 
    }

    public List<RSVP> findByName(String fullName) {
        return rsvpRepo.findByName(fullName); 
    }

    public Optional<RSVP> findByEmail(String email) {
        return rsvpRepo.findByEmail(email);
    }

    public Boolean save (RSVP rsvp) {
        return rsvpRepo.save(rsvp); 
    }

    public Boolean update(RSVP rsvp) {
        return rsvpRepo.update(rsvp); 
    }

    public int[] batchUpdate(List<RSVP> rsvps) {
        return rsvpRepo.batchUpdate(rsvps); 
    }

    // convert String payload to JsonArray
    public JsonArray toJArray(String payload) {
        JsonReader reader = Json.createReader(new StringReader(payload)); 
        JsonArray arr = reader.readArray(); 
        return arr; 
    }
    
}
