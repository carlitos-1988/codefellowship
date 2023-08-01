package com.codefellowship.start.controllers;

import com.codefellowship.start.models.ApplicationUser;
import com.codefellowship.start.repositories.CodeUserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class UserController {
    @Autowired
    CodeUserRepository codeUserRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    HttpServletRequest request;

    @GetMapping("/")
    String getHomePage(){
        return "splash.html";
    }

    @GetMapping("/login")
    String returnLoginPage(){
        return "login.html";
    }

    @GetMapping("/signup")
    String returnSignUpPage(){
        return "sighnup.html";
    }

    @GetMapping("/verified")
    String getVerifiedPage(HttpServletRequest request, Model m){
        HttpSession session = request.getSession();

        Object userNameAttribute = session.getAttribute("userName");

//        if(userNameAttribute == null){
//            System.out.println("person not verified userNameAttribute = ");
//            return "redirect:/";
//        }

//        String userName = userNameAttribute.toString();
//        m.addAttribute("userName", userName);

        return "verified.html";
    }

    @GetMapping("/logout")
    public RedirectView logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();
        return new RedirectView("/");
    }

    @PostMapping("/login")
    RedirectView logIn(HttpServletRequest request, String username, String password){
        ApplicationUser applicationUser = codeUserRepository.findByUserName(username);
        System.out.println("GOT HERE I GUESS");
        if(applicationUser == null){
            System.out.println("application user null");
            return new RedirectView("/");
        }

        if(!BCrypt.checkpw(password,applicationUser.getPassword())){
            System.out.println("password was incorrect");
            return new RedirectView("/");
        }

        //Store in session location
        HttpSession session = request.getSession();
        session.setAttribute("userName", username);

        return new RedirectView("/verified");

    }

    @PostMapping("/signup")
    RedirectView signUpUser(String username, String password, String firstName, String lastName, String birthDate, String bio) throws ServletException, ParseException {
        String encryptedPassword = passwordEncoder.encode(password);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDateModified = dateFormat.parse(birthDate);

        ApplicationUser applicationUser = new ApplicationUser(username,encryptedPassword,firstName, lastName, birthDateModified,bio);
        codeUserRepository.save(applicationUser);
        authWithHttpServletRequest(username,password);
        return new RedirectView("/verified");

    }

    public void authWithHttpServletRequest(String userName, String password) throws ServletException {
        try {
            request.login(userName,password);
        }catch (ServletException e){
            System.out.println("Error while logging in");
            e.printStackTrace();
        }
    }

//    @PostMapping("/login")
//    public RedirectView login(HttpServletRequest request, String userName, String password){
//
//        ApplicationUser siteUser = codeUserRepository.findByUserName(userName);
//        System.out.println(" Username from login PostMapping");
//
//        if(siteUser == null){
//            return new RedirectView("/");
//        }
//
//        if(!BCrypt.checkpw(password,siteUser.getPassword())){
//            return new RedirectView("/");
//        }
//
//        //Store in session location
//        HttpSession session = request.getSession();
//        session.setAttribute("userName", userName);
//
//        return new RedirectView("/verified");
//    }
}
