package com.elmenus.droneia.domain.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class BasicResponse<T> {
    private String message;
    private T data;
}
