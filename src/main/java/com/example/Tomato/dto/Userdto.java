package com.example.Tomato.dto;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class Userdto {
    @Valid


    private String id; // No @Id or @UuidGenerator annotations

    @Lob
    @Column(length =255)
    @NotNull
    private String email;

    @Lob
    @Column(length =50000)
    @NotNull
    private String password;

    @Lob
    @Column(length =255)
    @NotNull
    private String name;

}
