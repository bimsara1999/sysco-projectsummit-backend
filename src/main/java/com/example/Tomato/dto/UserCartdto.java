package com.example.Tomato.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserCartdto {
    @Id
    @UuidGenerator
    @Column(name= "id", unique = true, updatable = false)
    private String id;

    @NotNull
    private String userId;
}
