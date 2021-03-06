package com.Dyna.personalDiary.controller;

import com.Dyna.personalDiary.Service.EmailService;
import com.Dyna.personalDiary.login.User;
import com.Dyna.personalDiary.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.mail.MessagingException;

@Controller
public class AppController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;

    @GetMapping("/")
    public String viewHomePage() {
        return "index";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "signupForm";
    }

    @PostMapping("/process_register")
    public String processRegistrationSuccess(User user) throws MessagingException {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);
        emailService.prepareAndSendEmail();
        return "registrationSuccessPage";
    }

    @GetMapping("/loginSuccess")
    public String viewLoginSuccessPage(){
        return "loginSuccess";
    }


}
