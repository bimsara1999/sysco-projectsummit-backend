package com.example.Tomato.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Categorydto {

    @Valid
    private String id;

    @Size(min=2,max=255)
    @NotNull(message="Name is mandotary")
    private String name;

    private String photoUrl;
}
