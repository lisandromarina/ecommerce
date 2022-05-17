package com.ecommerce.controller;

import com.ecommerce.DTO.OrderDTO;
import com.ecommerce.DTO.OrderProductDTO;
import com.ecommerce.model.Order;
import com.ecommerce.model.OrderProduct;
import com.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/create")
    public void createOrder() {
        orderService.createOrder();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteOrder(@PathVariable("id") Long id) {
        orderService.deleteOrderById(id);
    }

    @GetMapping("/findById/{id}")
    public OrderDTO findOrderById(@PathVariable("id") Long id) {
        OrderDTO orderDTO = orderService.findOrderDTOById(id);
        Set<OrderProductDTO> orderProductDTO = orderService.findOrderProductsByOrderId(id);
        orderDTO.setOrderProductsDTO(orderProductDTO);
        return orderDTO;
    }
}
