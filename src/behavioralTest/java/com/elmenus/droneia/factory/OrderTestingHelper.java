package com.elmenus.droneia.factory;

import com.elmenus.droneia.domain.common.model.BasicResponse;
import com.elmenus.droneia.domain.order.model.MedicationLoad;
import com.elmenus.droneia.domain.order.model.OrderEntity;
import com.elmenus.droneia.domain.order.model.OrderLoadingRequest;
import com.elmenus.droneia.domain.order.model.OrderResponse;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

public class OrderTestingHelper {

    // lets hardcode single medication item for simplicity
    public static OrderLoadingRequest getMockOrderLoadingRequest(String droneId, String medicationId, int quantity) {
        List<MedicationLoad> medicationLoads = List.of(MedicationLoad.builder()
                .medicationId(medicationId)
                .medicationQuantity(quantity)
                .build());

        return OrderLoadingRequest.builder()
                .droneId(droneId)
                .medicationItems(medicationLoads)
                .build();
    }


    public static class OrderResponseType extends TypeReference<BasicResponse<OrderResponse>> {
    }

    public static class OrderEntityType extends TypeReference<BasicResponse<OrderEntity>> {
    }

}
