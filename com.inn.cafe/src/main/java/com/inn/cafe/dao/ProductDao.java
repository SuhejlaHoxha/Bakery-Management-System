package com.inn.cafe.dao;

import com.inn.cafe.model.Product;
import com.inn.cafe.wrapper.ProductWrapper;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDao extends JpaRepository<Product, Integer> {

    @Query("SELECT new com.inn.cafe.wrapper.ProductWrapper(p.id, p.name, p.description, p.price, p.status, p.category.id, p.category.name) FROM Product p")
    List<ProductWrapper> getAllProduct();

    @Modifying
    @Transactional
    @Query("UPDATE Product p SET p.status = :status WHERE p.id = :id")
    Integer updateProductStatus(@Param("status") String status, @Param("id") Integer id);

    @Query("SELECT new com.inn.cafe.wrapper.ProductWrapper(p.id, p.name, p.description, p.price, p.status, p.category.id, p.category.name) FROM Product p WHERE p.category.id = :id")
    List<ProductWrapper> getProductByCategory(@Param("id") Integer id);

    @Query("SELECT new com.inn.cafe.wrapper.ProductWrapper(p.id, p.name, p.description, p.price, p.status, p.category.id, p.category.name) FROM Product p WHERE p.id = :id")
    ProductWrapper getProductById(@Param("id") Integer id);
}
