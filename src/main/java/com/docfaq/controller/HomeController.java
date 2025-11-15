package com.docfaq.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Home controller for handling the main landing page requests.
 */
@Controller
public class HomeController {

    /**
     * Handles requests to the root path and returns the home page.
     * 
     * @param model the model to pass data to the view
     * @return the name of the template to render
     */
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("appName", "Doc2FAQ");
        model.addAttribute("appDescription", 
            "Transform your documents into comprehensive FAQ formats with ease. " +
            "Upload documents and let our intelligent system generate relevant questions and answers.");
        model.addAttribute("version", "1.0.0-SNAPSHOT");
        return "index";
    }
}