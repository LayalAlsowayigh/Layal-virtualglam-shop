package com.virtualglamshop.config;

import com.virtualglamshop.entity.Product;
import com.virtualglamshop.repository.ProductRepository;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;

import java.math.BigDecimal;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {
  private final ProductRepository repo;
  public DataLoader(ProductRepository repo){ this.repo = repo; }

  @Override public void run(String... args){
    if (repo.count() > 0) return;
    List<Product> seed = List.of(
      new Product("Velvet Lipstick","GlamCo","Lips", new BigDecimal("12.99"),50,"/frontend/images/lipstick-velvet.jpg"),
      new Product("Glow Foundation","PureSkin","Face", new BigDecimal("24.99"),30,"/frontend/images/foundation-glow.jpg"),
      new Product("Skyline Mascara","GlamCo","Eyes", new BigDecimal("14.99"),40,"/frontend/images/mascara-skyline.jpg"),
      new Product("Rose Blush","BlushBuddy","Cheeks", new BigDecimal("11.49"),35,"/frontend/images/blush-rose.jpg"),
      new Product("Nude Palette","ShadeLab","Eyes", new BigDecimal("29.99"),20,"/frontend/images/palette-nude.jpg")
    );
    repo.saveAll(seed);
  }
}
