package ibfbatch2paf.day22workshop.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ibfbatch2paf.day22workshop.model.RSVP;
import ibfbatch2paf.day22workshop.service.RsvpFormService;
import ibfbatch2paf.day22workshop.service.RsvpService;

@Controller
@RequestMapping("/rsvp")
public class RsvpFormController {

    @Autowired
    private RsvpFormService rsvpFormSvc;

    @Autowired
    private RsvpService rsvpSvc;

    // FOR ADD NEW RSVP 
    @GetMapping("/addnew")
    public String createRSVP(Model model) {

        RSVP rsvp = new RSVP(); 
        model.addAttribute("rsvp", rsvp); 
        return "newrsvpform"; 
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("rsvp") RSVP rsvp, BindingResult result) {

        if (result.hasErrors()) {
            return "newrsvpform";
        }

        rsvpFormSvc.save(rsvp); 
        return "createsuccess"; 
    }

    // FOR UPDATE EXISTING RSVP 
    @GetMapping("/update/{email}")
    public String updateRsvp(@PathVariable("email") String email, Model model) {

        // RSVP rsvp = new RSVP(); 
        // get existing RSVP from email and check if rsvp exist via email
        Optional<RSVP> opt = rsvpSvc.findByEmail(email);
        if (opt.isEmpty()) {
            return "updateerror"; 
        }

        // rsvp.setEmail(email);
        model.addAttribute("rsvp", opt.get());
        return "updatersvpform"; 
    }

    @PostMapping("/saveUpdate")
    public String saveUpdate(@ModelAttribute("rsvp") RSVP rsvp, BindingResult result) {

        if (result.hasErrors()) {
            return "updatersvpform"; 
        }

        rsvpFormSvc.update(rsvp); 
        return "updatesuccess"; 
    }
    
}
