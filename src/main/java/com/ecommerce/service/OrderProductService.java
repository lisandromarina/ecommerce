package com.ecommerce.service;

import com.ecommerce.DTO.OrderDTO;
import com.ecommerce.DTO.OrderProductDTO;
import com.ecommerce.model.Order;
import com.ecommerce.model.OrderProduct;
import com.ecommerce.model.OrderProductPK;
import com.ecommerce.model.Product;
import com.ecommerce.repository.OrderProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class OrderProductService {

    @Autowired
    OrderProductRepository orderProductRepository;

    //Create an Order from OrderDTO
    private Order createOrderFromDTO(OrderDTO orderDTO) {
        Order order = new Order();
        order.setId(orderDTO.getId());
        return order;
    }

    //Create an OrderProductPk
    private OrderProductPK createOrderProductPk(Order order, Product product) {
        OrderProductPK orderProductPK = new OrderProductPK();
        orderProductPK.setOrder(order);
        orderProductPK.setProduct(product);
        return orderProductPK;
    }

    //Create OrderProduct from a orderProductDTO
    private OrderProduct createOrderProduct(OrderProductPK orderProductPK, OrderProductDTO orderProductDTO) {
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setPk(orderProductPK);
        orderProduct.setQuantity(orderProductDTO.getQuantity());
        orderProduct.setSellPrice(orderProductDTO.getProduct().getPrice());
        return orderProduct;
    }

    //update orderProducts for a specific order
    public void updateOrderProducts(OrderDTO orderDTO) {
        Order order = createOrderFromDTO(orderDTO);
        for (OrderProductDTO orderProductDTO : orderDTO.getOrderProducts()) {

            OrderProductPK orderProductPK = createOrderProductPk(order, orderProductDTO.getProduct());

            OrderProduct orderProduct = createOrderProduct(orderProductPK, orderProductDTO);

            orderProductRepository.save(orderProduct);
        }
    }

    //delete one OrderProduct for an Order
    public void deleteOrderProduct(Long orderId, Long orderProductId) {
        orderProductRepository.deleteByOrderIdAndOrderProductId(orderId, orderProductId);

    }
}
