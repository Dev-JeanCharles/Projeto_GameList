package com.devsuperior.dslist.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SwaggerController {

    @GetMapping("/docs")
    public String swaggerRedirect() {
        return "redirect:/swagger-ui.html";
    }
}
