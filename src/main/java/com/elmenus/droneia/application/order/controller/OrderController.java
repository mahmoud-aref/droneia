package com.elmenus.droneia.application.order.controller;

import com.elmenus.droneia.domain.common.model.BasicResponse;
import com.elmenus.droneia.domain.order.model.OrderEntity;
import com.elmenus.droneia.domain.order.model.OrderLoadingRequest;
import com.elmenus.droneia.domain.order.model.OrderResponse;
import com.elmenus.droneia.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(OrderController.ORDER_PATH)
@RequiredArgsConstructor
public class OrderController {

    public static final String ORDER_PATH = "/droneia/api/v1/order";
    public static final String ORDER_PATH_CREATE = "/create";
    public static final String ORDER_PATH_GET = "/get/{id}";
    public static final String ORDER_PATH_GET_ALL = "/get/all";
    public static final String ORDER_PATH_GET_ALL_ACTIVE = "/get/all/active";

    private final OrderService orderService;

    @PostMapping(ORDER_PATH_CREATE)
    public Mono<ResponseEntity<BasicResponse<OrderResponse>>> createOrder(
            @RequestBody Mono<OrderLoadingRequest> monoRequest
    ) {
        return monoRequest.flatMap(
                request -> orderService.createOrder(request)
                        .map(ResponseEntity::ok)
        );
    }

    @GetMapping(ORDER_PATH_GET)
    public Mono<ResponseEntity<BasicResponse<OrderEntity>>> getOrderById(@PathVariable String id) {
        return orderService.getOrderById(id)
                .map(ResponseEntity::ok);
    }

    @GetMapping(ORDER_PATH_GET_ALL)
    public Flux<ResponseEntity<BasicResponse<OrderEntity>>> getAllOrders() {
        return orderService.getAllOrders()
                .map(ResponseEntity::ok);
    }

    @GetMapping(ORDER_PATH_GET_ALL_ACTIVE)
    public Flux<ResponseEntity<BasicResponse<OrderEntity>>> getAllActiveOrders() {
        return orderService.getAllActiveOrders()
                .map(ResponseEntity::ok);
    }


}
