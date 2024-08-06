package com.example.Tomato.repo;

import com.example.Tomato.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepo extends JpaRepository<Product,String> {

    @Query("SELECT p FROM Product p WHERE p.categoryId = ?1")
    Page<Product> findProductsByCategory(String categoryId, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.name = ?1")
    Page<Product> findProductsByName(String Name, Pageable pageable);
}
