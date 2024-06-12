package com.rayanbourse.ers.model.result;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RestOutputModel {
    private Object result;
    private String errorMessage;
    private Integer errorCode;
}
