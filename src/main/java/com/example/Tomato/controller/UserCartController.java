package com.example.Tomato.controller;

import com.example.Tomato.dto.CartResponsedto;
import com.example.Tomato.dto.UserCartdto;
import com.example.Tomato.exception.deleteprohibited.DeleteProhibited;
import com.example.Tomato.service.UserCartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value="/api/v1/user/cart")
public class UserCartController {
    @Autowired
    private UserCartService userCartService;


    @PostMapping
    public ResponseEntity<UserCartdto> addCart(@Valid @RequestBody UserCartdto userCartdto) {
        UserCartdto addedCart= userCartService.addCart(userCartdto);
        return ResponseEntity.created(URI.create("")).body(addedCart);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserCartdto> deleteCartById(@PathVariable(value = "id") String id) {
        ResponseEntity<UserCartdto> userCart = null;
        try{
            userCart = ResponseEntity.ok().body(userCartService.deleteCartByID(id));
        }catch (Exception e)
        {
            throw  new DeleteProhibited();
        }
        return userCart;
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<UserCartdto>> getCartUserById(@PathVariable(value = "id") String id) {
        return ResponseEntity.ok().body(userCartService.getCartBYUserId(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<CartResponsedto>> getCartById(@PathVariable(value = "id") String id){

        return ResponseEntity.ok().body(userCartService.getCartById(id));

    }



}
