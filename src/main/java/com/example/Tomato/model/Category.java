package com.example.Tomato.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(NON_DEFAULT)
@Table(name= "Category")
public class Category {
    @Id
    @UuidGenerator
    @Column(name= "id", unique = true,updatable = false)
    private String id;

    @Lob
    @Column(length =255)
    @NotNull
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="categoryId",referencedColumnName = "id")
    private List<Product> products;

    @Lob
    @Column(length = 500) // Length is optional for CLOBs
    private String photoUrl;
}
