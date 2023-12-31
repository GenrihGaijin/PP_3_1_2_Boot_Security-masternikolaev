package ru.kata.spring.boot_security.demo.controller;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;



@Controller
@RequestMapping(value = "/admin")
public class SecurityAdminController {

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;


    public SecurityAdminController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }


    @GetMapping()
    public String showUsers( Model model, @AuthenticationPrincipal User principalUser) {
        model.addAttribute("principalUser", principalUser);
        model.addAttribute("userslist", userService.findAll());
        model.addAttribute("roles", roleService.findAll());
        return "admin";
    }

    @GetMapping("/user/{id}")
    public String findUser(@PathVariable("id") Long id, Model model){
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("roles", user.getRoles());
        return "user";
    }



    @GetMapping("/registration")
    public String createForm(Model model, User user) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.findAll());
        return "redirect:/admin";
    }


    @PostMapping("/registration")
    public String createUser(@ModelAttribute("user") User user) {
        userService.createUser(user);
        return "redirect:/admin";
    }




    @GetMapping("/edit/{id}")
    public String updateForm(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.findUserById(id));
        model.addAttribute("roles", roleService.findAll());
        return "redirect:/admin";
    }


    @PatchMapping("/edit/{id}")
    public String updateUser(@ModelAttribute("user") User updateUser,
                             @PathVariable("id") Long id) {
        userService.update(updateUser, id);
        return "redirect:/admin";
    }



    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }
}