package org.example.o13_auth_backend.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class RedirectController {

    @GetMapping("/")
    public String redirectToSignup() {
        return "redirect:/signup.html";
    }

    @GetMapping("/signin")
    public String signin() {
        return "redirect:/signin.html";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
            boolean hasAdminRole = authentication.getAuthorities().stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
            if (hasAdminRole) {
                return "redirect:/dashboard.html";
            }
            return "redirect:/access-denied.html";
        }
        return "redirect:/signin.html";
    }

    @GetMapping("/auth/signup-redirect")
    public RedirectView afterSignup() {
        return new RedirectView("/signin.html");
    }

    @GetMapping("/auth/login-redirect") // Changed from PostMapping to GetMapping
    public RedirectView afterLogin() {
        return new RedirectView("/dashboard.html");
    }
}