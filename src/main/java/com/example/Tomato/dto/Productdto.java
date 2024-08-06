package com.example.Tomato.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.PrePersist;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Productdto {

    @Valid

    @Id
    @UuidGenerator
    @Column(name= "id", unique = true, updatable = false)
    private String id;

    @NotNull
    private String categoryId;

    @Lob
    @Column(length =255)
    @NotNull
    private String name;

    @Lob
    @Column(length = 500)
    private String photoUrl;

    @Max(value = 1000000, message = "Quantity must be less than or equal to 100000")
    private int quantity;

    @Max(value = 100000, message = "Quantity must be less than or equal to 100000")
    private float price;

    @Column(updatable = false)
    private LocalDate createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDate.now();
    }
}
