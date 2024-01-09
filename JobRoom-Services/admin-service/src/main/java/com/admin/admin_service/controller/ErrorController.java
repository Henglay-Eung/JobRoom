package com.admin.admin_service.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {
    @GetMapping("/403")
    public String errorPage403() {
        return "/error/403";
    }

    @GetMapping("/404")
    public String errorPage404() {
        return "/error/404";
    }

    @GetMapping("/500")
    public String errorPage405() {
        return "/error/500";
    }
}
