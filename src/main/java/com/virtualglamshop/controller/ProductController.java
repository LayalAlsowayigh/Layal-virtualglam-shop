package com.virtualglamshop.controller;

import com.virtualglamshop.dto.ProductDto;
import com.virtualglamshop.entity.Product;
import com.virtualglamshop.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
  private final ProductService service;
  public ProductController(ProductService s){ this.service = s; }

  @GetMapping
  public List<Product> list(@RequestParam(required=false) String category,
                            @RequestParam(required=false) String brand){
    return service.listAll(category, brand);
  }

  @GetMapping("/{id}")
  public Product get(@PathVariable Long id){ return service.getById(id); }

  @PostMapping
  public ResponseEntity<Product> create(@Valid @RequestBody ProductDto dto){
    Product saved = service.create(dto);
    return ResponseEntity.created(URI.create("/api/products/"+saved.getId())).body(saved);
  }

  @PutMapping("/{id}")
  public Product update(@PathVariable Long id, @Valid @RequestBody ProductDto dto){
    return service.update(id, dto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id){
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
