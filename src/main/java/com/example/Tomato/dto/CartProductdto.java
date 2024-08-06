package com.example.Tomato.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartProductdto {

    @NotNull
    private String id;

    @NotNull
    private String productId;

    @NotNull
    private int quantity;

    @NotNull
    private String cartId;
}
