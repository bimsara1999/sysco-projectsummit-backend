package com.example.Tomato.controller;

import com.example.Tomato.dto.Userdto;
import com.example.Tomato.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/api/v1/admin/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public ResponseEntity<Page<Userdto>> getAllUsers(@RequestParam(value = "page", defaultValue = "0") int page,
                                                     @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok().body(userService.getAllUsers(page, size));
    }
//    @PostMapping
//    public ResponseEntity<Userdto> addUser(@Valid @RequestBody Userdto user) {
//
//        Userdto addedProduct = userService.addUser(user);
//
//        return ResponseEntity.created(URI.create("")).body(addedProduct);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<Userdto> getUserById(@PathVariable(value = "id") String id) {
        return ResponseEntity.ok().body(userService.getUserById(id));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Userdto> getUserByEmail(@PathVariable(value = "email") String id) {
        return ResponseEntity.ok().body(userService.getUserByEmail(id));
    }


}
