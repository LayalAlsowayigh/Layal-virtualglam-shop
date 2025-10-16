package com.virtualglamshop.controller;

import com.virtualglamshop.entity.Order;
import com.virtualglamshop.entity.OrderStatus;
import com.virtualglamshop.service.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
  private final OrderService service;

  public OrderController(OrderService service) {
    this.service = service;
  }

  // Allow both GET (for browser) and POST (for API tools) to create a demo order
  @GetMapping("/demo")
  public Order createDemoGet() {
    return service.createDemo(1L, 1L);
  }

  @PostMapping("/demo")
  public Order createDemoPost() {
    return service.createDemo(1L, 1L);
  }

  @GetMapping("/{id}")
  public Order get(@PathVariable Long id) {
    return service.get(id);
  }

  @PatchMapping("/{id}/status")
  public Order update(@PathVariable Long id, @RequestParam OrderStatus status) {
    return service.updateStatus(id, status);
  }
}

