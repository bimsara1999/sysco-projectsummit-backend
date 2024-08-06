package com.example.Tomato.repo;

import com.example.Tomato.model.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartProductRepo extends JpaRepository<CartProduct,String> {



    @Query(value = "SELECT * FROM cart_product WHERE cart_id = :id AND product_id = :productId", nativeQuery = true)
    CartProduct findUserByCartIDItemID(@Param("id") String id, @Param("productId") String productId);


}
