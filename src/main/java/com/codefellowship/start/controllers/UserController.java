package com.codefellowship.start.controllers;

import com.codefellowship.start.models.ApplicationUser;
import com.codefellowship.start.models.Post;
import com.codefellowship.start.repositories.CodeUserRepository;
import com.codefellowship.start.repositories.PostRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class UserController {
    @Autowired
    CodeUserRepository codeUserRepository;

    @Autowired
    PostRepository postRepository;

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

    @GetMapping("my-profile")
    String getMyProfile(Principal p, Model m){
        if (p != null){
            String userName = p.getName();
            ApplicationUser applicationUser = codeUserRepository.findByUserName(userName);

            m.addAttribute("userName", applicationUser.getUserName());
            m.addAttribute("firstName", applicationUser.getFirstName());
            m.addAttribute("lastName", applicationUser.getLastName());
            m.addAttribute("birthDate", applicationUser.getBirthDate().toString());
            m.addAttribute("bio", applicationUser.getBio());
            m.addAttribute("posts", applicationUser.getPosts());
        }

        return "verified.html";
    }

    @GetMapping("/verified/{id}")
    String getVerifiedPage(Principal p, Model m, @PathVariable Long id){
        if (p != null){
            String userName = p.getName();
            ApplicationUser applicationUser = codeUserRepository.findByUserName(userName);

            m.addAttribute("userName", applicationUser.getUserName());
            m.addAttribute("firstName", applicationUser.getFirstName());
            m.addAttribute("lastName", applicationUser.getLastName());

        }

        ApplicationUser pageApplicationUser = codeUserRepository.findById(id).orElseThrow();
        m.addAttribute("hostUserName", pageApplicationUser.getUserName());
        m.addAttribute("hostFistName", pageApplicationUser.getFirstName());
        m.addAttribute("hostId", pageApplicationUser.getId());
        m.addAttribute("bio", pageApplicationUser.getBio());
        m.addAttribute("pageApplicationUserPosts", pageApplicationUser.getPosts());
        m.addAttribute("guestPost", pageApplicationUser.getPosts());

        return "verified.html";
    }

    @GetMapping("/logout")

    public RedirectView logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();
        return new RedirectView("/");
    }

    @PostMapping("/signup")
    RedirectView signUpUser(String username, String password, String firstName, String lastName, String birthDate, String bio) throws ServletException, ParseException {
        HttpSession session = request.getSession();
        session.invalidate();

        String encryptedPassword = passwordEncoder.encode(password);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDateModified = dateFormat.parse(birthDate);

        ApplicationUser applicationUser = new ApplicationUser(username,encryptedPassword,firstName, lastName, birthDateModified,bio);
        codeUserRepository.save(applicationUser);
        authWithHttpServletRequest(username,password);
        return new RedirectView("/login");

    }

    public void authWithHttpServletRequest(String userName, String password) throws ServletException {
        try {
            request.login(userName,password);
        }catch (ServletException e){
            System.out.println("Error while logging in");
            e.printStackTrace();
        }
    }

    @PostMapping("/login")
    public RedirectView login(HttpServletRequest request, String userName, String password) throws ServletException {
        authWithHttpServletRequest(userName,password);
        return new RedirectView("/my-profile");
    }

    @PostMapping("/addPost")
    public RedirectView processPost(String postData, Principal p){
        if(p == null){
            return new RedirectView("/");
        }

        String userName = p.getName();
        ApplicationUser applicationUser = codeUserRepository.findByUserName(userName);

        Post newPost = new Post(postData,applicationUser);
        postRepository.save(newPost);

        return new RedirectView("/my-profile");

    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public class ResourceNotFoundException extends RuntimeException {
        ResourceNotFoundException(String message) {
            super(message);
        }
    }

}
