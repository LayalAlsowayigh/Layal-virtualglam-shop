package com.virtualglamshop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Entity @Table(name="products")
public class Product {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  @NotBlank @Size(max=120) private String name;
  @NotBlank @Size(max=80)  private String brand;
  @NotBlank @Size(max=60)  private String category;
  @NotNull @DecimalMin("0.00") @Column(precision=10,scale=2) private BigDecimal price;
  @NotNull @Min(0) private Integer stockQty;
  @Size(max=255) private String imageURL;

  public Product() {}
  public Product(String n,String b,String c,BigDecimal p,Integer s,String u){
    this.name=n; this.brand=b; this.category=c; this.price=p; this.stockQty=s; this.imageURL=u;
  }
  public Long getId(){return id;} public void setId(Long id){this.id=id;}
  public String getName(){return name;} public void setName(String name){this.name=name;}
  public String getBrand(){return brand;} public void setBrand(String brand){this.brand=brand;}
  public String getCategory(){return category;} public void setCategory(String c){this.category=c;}
  public BigDecimal getPrice(){return price;} public void setPrice(BigDecimal p){this.price=p;}
  public Integer getStockQty(){return stockQty;} public void setStockQty(Integer s){this.stockQty=s;}
  public String getImageURL(){return imageURL;} public void setImageURL(String u){this.imageURL=u;}
}
