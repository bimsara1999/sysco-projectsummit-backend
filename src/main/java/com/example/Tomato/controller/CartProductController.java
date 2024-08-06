package com.example.Tomato.controller;

import com.example.Tomato.dto.CartProductdto;
import com.example.Tomato.service.CartProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


@RestController
@RequestMapping(value="/api/v1/user/cartproduct")
public class CartProductController {

    @Autowired
    private CartProductService cartProductService;

    @GetMapping("/{id}")
    public ResponseEntity<CartProductdto> getCartProductByID(@PathVariable(value = "id") String id){
        return ResponseEntity.ok().body(cartProductService.getCartProductsByID(id));
    }

    @PostMapping
    public ResponseEntity<CartProductdto> addCartProduct(@Valid @RequestBody CartProductdto cartProduct) {

        CartProductdto Cartproduct2 = cartProductService.addCartProduct(cartProduct);
        return ResponseEntity.created(URI.create("")).body(Cartproduct2 );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CartProductdto> deleteCartProductById(@PathVariable(value = "id") String id) {
        return ResponseEntity.ok().body(cartProductService.deleteCartProductByID(id));
    }
}
