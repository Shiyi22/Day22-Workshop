package ibfbatch2paf.day22workshop.model;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.MultiValueMap;

import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RSVP {

    @Id
    private Integer id;

    @NotNull
    @Size(min= 10, max = 150, message = "Full name must be between 10 and 150 characters")
    private String fullName;

    @Email(message = "Email must not be blank and must be in a correct format")
    // @Size(max = 150, message = "Email only accepts a max of 150 characters")
    private String email;
    
    private String phone;

    private Date confirmationDate;

    private String comments;

    public RSVP() { }

    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}
    public String getFullName() {return fullName;}
    public void setFullName(String fullName) {this.fullName = fullName;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    public String getPhone() {return phone;}
    public void setPhone(String phone) {this.phone = phone;}
    public Date getConfirmationDate() {return confirmationDate;}
    public void setConfirmationDate(Date confirmationDate) {this.confirmationDate = confirmationDate;}
    public String getComments() {return comments;}
    public void setComments(String comments) {this.comments = comments;} 

    // method to convert form to RSVP
    public static RSVP toRSVP(MultiValueMap<String, String> form) throws ParseException {
        RSVP rsvp = new RSVP(); 

        if (null != form.getFirst("id")) {
            rsvp.setId(Integer.parseInt(form.getFirst("id")));
        }
        rsvp.setFullName(form.getFirst("fullName"));
        rsvp.setEmail(form.getFirst("email"));
        rsvp.setPhone(form.getFirst("phone"));
        rsvp.setConfirmationDate(toDate(form.getFirst("confirmationDate")));
        rsvp.setComments(form.getFirst("comments"));
        return rsvp; 
    }

    // method to convert string to date
    public static Date toDate(String dateString) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
        java.util.Date date = dateFormat.parse(dateString); 

        return new java.sql.Date(date.getTime());
    }

    // convert JsonArray to List<RSVP>
    public static List<RSVP> toList(JsonArray arr) throws ParseException {

        List<RSVP> rsvps = new ArrayList<>(); 

        for (int i = 0; i < arr.size(); i++) {
            JsonObject json = arr.getJsonObject(i); 
            RSVP rsvp = new RSVP(); 
            // rsvp.setId(json.getInt("id"));
            rsvp.setFullName(json.getString("fullName"));
            rsvp.setEmail(json.getString("email"));
            rsvp.setPhone(json.getString("phone"));
            rsvp.setConfirmationDate(toDate(json.getString("confirmationDate")));
            rsvp.setComments(json.getString("comments"));

            rsvps.add(rsvp); 
        }
        return rsvps; 
    }
    
    
}
