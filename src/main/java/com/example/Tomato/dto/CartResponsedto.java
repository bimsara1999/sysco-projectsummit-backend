package com.example.Tomato.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartResponsedto {
    private String cartId;
    private int qunatity;
    private String name;
    private float price;
    private String photoUrl;


}
