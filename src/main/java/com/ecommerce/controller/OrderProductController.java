package com.ecommerce.controller;

import com.ecommerce.DTO.OrderDTO;
import com.ecommerce.service.OrderProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orderProduct")
public class OrderProductController {

    @Autowired
    OrderProductService orderProductService;

    @PutMapping("/update")
    public void updateOrder(@RequestBody OrderDTO orderDTO) {
        orderProductService.updateOrderProducts(orderDTO);
    }

    @DeleteMapping("/delete/{orderId}/{orderProductId}")
    public void deleteOrderProduct(@PathVariable("orderId") Long orderId,
                                   @PathVariable("orderProductId") Long orderProductId) {
        orderProductService.deleteOrderProduct(orderId, orderProductId);
    }
}
