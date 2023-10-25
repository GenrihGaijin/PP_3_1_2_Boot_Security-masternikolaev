package ru.kata.spring.boot_security.demo.rest;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/restAdmin")
public class AdminRestController {
    private final UserService userService;

    @Autowired
    public AdminRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> restFindAll(){
        List<User> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK );
    }
    @PostMapping
    public ResponseEntity<User> restCreateUser(@RequestBody User user){
        userService.createUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK );
    }
    @PutMapping
    public ResponseEntity<User> restUpdate(@RequestBody User user){
        userService.update(user, user.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping
    public ResponseEntity<User> restDeleteUserById(@RequestBody User user){
        userService.deleteUserById(user.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> restFindUserById(@PathVariable(name = "id") Long id) {
        User user = userService.findUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
