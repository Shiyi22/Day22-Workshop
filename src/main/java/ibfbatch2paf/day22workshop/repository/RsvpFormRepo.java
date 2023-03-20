package ibfbatch2paf.day22workshop.repository;

import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import ibfbatch2paf.day22workshop.model.RSVP;

@Repository
public class RsvpFormRepo {

    
    RestTemplate restTemplate = new RestTemplate();

    private String CREATE_RSVP_ENDPOINT_URL = "http://localhost:8080/api/rsvp"; 
    private String UPDATE_RSVP_ENDPOINT_URL = "http://localhost:8080/api/rsvp/{email}";

    // POST 
    public Boolean save(RSVP rsvp) {
        return restTemplate.postForObject(CREATE_RSVP_ENDPOINT_URL, rsvp, Boolean.class);
    }

    // POST
    public Integer update(RSVP rsvp) {
        restTemplate.put(UPDATE_RSVP_ENDPOINT_URL, rsvp, rsvp.getEmail());
        return 1;
    }
    
}
