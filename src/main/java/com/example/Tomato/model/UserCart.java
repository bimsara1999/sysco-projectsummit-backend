package com.example.Tomato.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.HashSet;
import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(NON_DEFAULT)

public class UserCart {
    @Id
    @UuidGenerator
    @Column(name= "id", unique = true, updatable = false)
    private String id;

    @NotNull
    private String userId;

    @JoinColumn(name="cartId",referencedColumnName = "id")
    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true)
    private Set<CartProduct> cartItems = new HashSet<>();
}
