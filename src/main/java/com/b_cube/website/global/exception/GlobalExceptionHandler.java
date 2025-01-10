package com.b_cube.website.global.exception;

import com.b_cube.website.domain.designton.exception.DesigntonNotFoundException;
import com.b_cube.website.domain.etc.exception.EtcNotFoundException;
import com.b_cube.website.domain.executives.exception.ExecutivesNotFoundException;
import com.b_cube.website.domain.sexyit.exception.SexyItNotFoundException;
import com.b_cube.website.global.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler({ExecutivesNotFoundException.class, DesigntonNotFoundException.class, SexyItNotFoundException.class, EtcNotFoundException.class})
        public ResponseEntity<ErrorResponse> handleExecutivesNotFoundException(RuntimeException e) {
            return new ResponseEntity<>(ErrorResponse.builder()
                    .message(e.getMessage())
                    .build(), HttpStatus.NOT_FOUND);
        }


}
