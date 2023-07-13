package org.promgrammers.springbootbasic.global.error.response;

import lombok.Builder;
import lombok.Getter;
import org.promgrammers.springbootbasic.global.error.exception.ErrorCode;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ErrorResponse {

    private int status;
    private String message;
    private Map<String, String> errorMap;

    @Builder
    public ErrorResponse(int status, String message, Map<String, String> valid) {
        this.status = status;
        this.message = message;
        this.errorMap = valid != null ? valid : new HashMap<>();
    }

    public static ErrorResponse from(String errorMsg) {
        return ErrorResponse.builder()
                .message(errorMsg)
                .build();
    }

    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getStatus())
                .body(ErrorResponse.builder()
                        .status(errorCode.getStatus())
                        .message(errorCode.getMessage())
                        .build());
    }

    public void addValidation(String fieldName, String errorMsg) {
        this.errorMap.put(fieldName, errorMsg);
    }
}

