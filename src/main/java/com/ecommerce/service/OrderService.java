package com.ecommerce.service;

import com.ecommerce.DTO.OrderDTO;
import com.ecommerce.DTO.OrderProductDTO;
import com.ecommerce.model.Order;
import com.ecommerce.model.OrderProduct;
import com.ecommerce.model.OrderProductPK;
import com.ecommerce.model.Product;
import com.ecommerce.repository.OrderProductRepository;
import com.ecommerce.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Set;

@Service
@Transactional
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderProductRepository orderProductRepository;

    //Save an order in the database
    public Order createOrder() {
        Order order = new Order();
        order.setStatus(false);
        order.setDateCreated(LocalDate.now());
        return orderRepository.save(order);
    }

    public void updateOrder(Order order) {
        orderRepository.save(order);
    }

    //return order by id
    public OrderDTO findOrderDTOById(Long id) {
        return orderRepository.findOrderDTOById(id);
    }

    //return all orderProducts for an Order
    public Set<OrderProductDTO> findOrderProductsByOrderId(Long id) {
        return orderProductRepository.findOrderProductByOrderId(id);
    }

    //Delete order by orderId
    public void deleteOrderById(Long orderId){
        orderProductRepository.deleteAllByOrderId(orderId);
        orderRepository.deleteById(orderId);
    }
}
