package com.virtualglamshop.service;

import com.virtualglamshop.dto.ProductDto;
import com.virtualglamshop.entity.Product;
import com.virtualglamshop.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ProductService {
  private final ProductRepository repo;
  public ProductService(ProductRepository repo){ this.repo = repo; }

  public List<Product> listAll(String category,String brand){
    if (category!=null && !category.isBlank()) return repo.findByCategoryIgnoreCase(category);
    if (brand!=null && !brand.isBlank()) return repo.findByBrandIgnoreCase(brand);
    return repo.findAll();
  }

  public Product getById(Long id){
    return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Product not found"));
  }

  @Transactional public Product create(ProductDto d){ return repo.save(toEntity(new Product(), d)); }

  @Transactional public Product update(Long id, ProductDto d){
    Product e = getById(id); return repo.save(toEntity(e, d));
  }

  @Transactional public void delete(Long id){
    if (!repo.existsById(id)) throw new IllegalArgumentException("Product not found");
    repo.deleteById(id);
  }

  private Product toEntity(Product p, ProductDto d){
    p.setName(d.name); p.setBrand(d.brand); p.setCategory(d.category);
    p.setPrice(d.price); p.setStockQty(d.stockQty); p.setImageURL(d.imageURL);
    return p;
  }
}
