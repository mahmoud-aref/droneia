package com.elmenus.droneia.domain.order.service;


import com.elmenus.droneia.domain.common.model.BasicResponse;
import com.elmenus.droneia.domain.order.model.OrderLoadingRequest;
import com.elmenus.droneia.domain.order.model.OrderEntity;
import com.elmenus.droneia.domain.order.validation.ValidLoad;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderService {

    String ORDER_CREATED_SUCCESSFULLY = "Order Created Successfully";
    String ORDER_RETRIEVED_SUCCESSFULLY = "Order Retrieved Successfully";
    String BATTERY_LOW_EXCEPTION_MESSAGE = "Drone Battery Low Less Than 25%";
    String DRONE_IS_BUSY_EXCEPTION = "Drone Is Busy with another order";

    Mono<BasicResponse<OrderEntity>> createOrder(@ValidLoad OrderLoadingRequest request);

    Mono<BasicResponse<OrderEntity>> getOrderById(String orderId);

    Mono<BasicResponse<OrderEntity>> getOrderOfDrone(String droneId);

    Flux<BasicResponse<OrderEntity>> getAllOrders();

    Flux<BasicResponse<OrderEntity>> getAllActiveOrders();
}
