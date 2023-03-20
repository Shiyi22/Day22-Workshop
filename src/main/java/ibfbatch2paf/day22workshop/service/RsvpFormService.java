package ibfbatch2paf.day22workshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ibfbatch2paf.day22workshop.model.RSVP;
import ibfbatch2paf.day22workshop.repository.RsvpFormRepo;

@Service
public class RsvpFormService {

    @Autowired
    private RsvpFormRepo rsvpFormRepo; 

    public Boolean save(RSVP rsvp) {
        return rsvpFormRepo.save(rsvp); 
    }

    public Integer update(RSVP rsvp) {
        return rsvpFormRepo.update(rsvp);
    }
    
}
