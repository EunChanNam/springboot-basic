package org.prgrms.assignment.voucher.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalCustomException.class)
    public ResponseEntity<ErrorResponse> handleGlobalCustomException(final GlobalCustomException e) {
        return ResponseEntity
            .status(e.getErrorCode().getStatus().value())
            .body(new ErrorResponse(e.getErrorCode()));
    }
    
}
