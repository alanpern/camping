package com.campingconnecte.camping.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.campingconnecte.camping.service.MailService;

@Controller
public class ContactController {

    private final MailService mailService;

    public ContactController(MailService mailService) {
        this.mailService = mailService;
    }

    // Method to display the contact form
    @GetMapping("/contact")
    public String showContactForm() {
        return "contact"; // Assuming "contact.html" is your form template
    }

    // Method to handle form submission
    @PostMapping("/contact")
    @ResponseBody
    public String submitContactForm(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String message) {
        String content = "Name: " + name + "\nEmail: " + email + "\nMessage: " + message;
        mailService.sendEmail("alain@alainpernot.com", "New Contact Form Submission", content);
        return "Form submitted successfully!";
    }
}
