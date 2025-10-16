package com.virtualglamshop.service;

import com.virtualglamshop.entity.Order;
import com.virtualglamshop.entity.OrderItem;
import com.virtualglamshop.entity.OrderStatus;
import com.virtualglamshop.entity.Product;
import com.virtualglamshop.repository.OrderRepository;
import com.virtualglamshop.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class OrderService {
  private final OrderRepository orders;
  private final ProductRepository products;

  public OrderService(OrderRepository orders, ProductRepository products) {
    this.orders = orders;
    this.products = products;
  }

  public Order get(Long id) {
    return orders.findById(id).orElseThrow(() -> new IllegalArgumentException("Order not found"));
  }

  @Transactional
  public Order updateStatus(Long id, OrderStatus status) {
    Order o = get(id);
    o.setStatus(status);
    return orders.save(o);
  }

  // Demo flow: create an order for userId with 1 unit of productId
  @Transactional
  public Order createDemo(Long userId, Long productId) {
    Product p = products.findById(productId)
        .orElseThrow(() -> new IllegalArgumentException("Product not found"));
    Order o = new Order();
    o.setUserId(userId);

    OrderItem item = new OrderItem();
    item.setOrder(o);
    item.setProductId(p.getId());
    item.setQuantity(1);
    item.setUnitPrice(p.getPrice());

    o.getItems().add(item);
    o.setTotalAmount(p.getPrice().multiply(new BigDecimal(item.getQuantity())));
    return orders.save(o);
  }
}
