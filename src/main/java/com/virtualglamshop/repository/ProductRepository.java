package com.virtualglamshop.repository;

import com.virtualglamshop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
  List<Product> findByCategoryIgnoreCase(String category);
  List<Product> findByBrandIgnoreCase(String brand);
}
