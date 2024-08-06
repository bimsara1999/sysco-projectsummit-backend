package com.example.Tomato.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(NON_DEFAULT)
@Table(name= "Product")
public class Product {

    @Id
    @UuidGenerator
    @Column(name= "id", unique = true, updatable = false)
    private String id;



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
    @NotNull
    private String categoryId;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDate.now();
    }

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<CartProduct> cartProducts;

}