package com.example.first_spring_boot.controller;

import com.example.first_spring_boot.model.User;
import com.example.first_spring_boot.repository.UserRepository;
import com.example.first_spring_boot.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.UsesSunHttpServer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("")
public class UserController {

    private final
    UserService userService;
    private final
    UserRepository userRepository;

    private final RestTemplate restTemplate;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository, RestTemplate restTemplate) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/hello")
    public String printWelcome(ModelMap model) {
        List<String> messages = new ArrayList<>();
        messages.add("Hello!");
        messages.add("I'm Spring MVC-SECURITY application");
        messages.add("5.2.0 version by sep'19 ");
        model.addAttribute("messages", messages);
        return "hello";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @GetMapping("/admin")
    public String UsersList(Model model, Authentication authentication, @ModelAttribute("newUser") User user) throws JsonProcessingException {
        ResponseEntity<List<User>> response = restTemplate.exchange("http://localhost:8080/allUsers", HttpMethod.GET,
                null, new ParameterizedTypeReference<List<User>>() {});
        model.addAttribute("users", response.getBody());
        model.addAttribute("rolesList", userRepository.getRolesList());
        model.addAttribute("email", userRepository.getUserByEmail(authentication.getName()).getEmail());
        model.addAttribute("usersRoles", userRepository.getUserByEmail(authentication.getName()).getRoles());
        return "index";
    }

    @GetMapping( "/user/{id}")
    public String getUser(@PathVariable Long id, Model model, Authentication authentication){
        ResponseEntity<User> response = restTemplate.exchange("http://localhost:8080/User/" + id, HttpMethod.GET,
                null, new ParameterizedTypeReference<User>() {});
        model.addAttribute("user", response.getBody());
        model.addAttribute("email", userRepository.getUserByEmail(authentication.getName()).getEmail());
        model.addAttribute("usersRoles", userRepository.getUserByEmail(authentication.getName()).getRoles());

        return "user";
    }

    /*@GetMapping("/admin/new")
    public String newUser(@ModelAttribute("user") User user){
        return "new";
    }

    @PostMapping("/admin")
    public String create(@ModelAttribute("user") User user){
        HttpEntity<User> request = new HttpEntity<>(user);
        restTemplate.exchange("http://localhost:8080/newUser", HttpMethod.POST, request, User.class);
        return "redirect:/admin";
    }
    @GetMapping("admin/user/{id}/edit")
    public String edit(Model model, @PathVariable Long id){
        model.addAttribute("newUser", userService.findById(id));
        return "edit";
    }

    @PutMapping ("/admin/edit")
    public String update(@ModelAttribute("user") User user){
        HttpEntity<User> request = new HttpEntity<>(user);
        System.out.println(user);
        restTemplate.exchange("http://localhost:8080/Edit", HttpMethod.PUT, request, User.class);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/user/{id}/delete")
    public String delete(@PathVariable Long id){
       restTemplate.delete("http://localhost:8080/Delete/"+id);
        return "redirect:/admin";
    }
*/
}
