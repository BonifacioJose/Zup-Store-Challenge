package br.com.zup.api.advisor;

import br.com.zup.api.v1.dto.ErrorDTO;
import br.com.zup.exception.ApiErrorException;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = Logger.getLogger(RestResponseEntityExceptionHandler.class);

    @ExceptionHandler(value = ApiErrorException.class)
    protected ResponseEntity<Object> handleApiErrorException(ApiErrorException ex) {
        logger.error(String.format("Caught ApiErrorException: %s", ex.getMessage()));
        return ResponseEntity.status(ex.getCode()).body(new ErrorDTO(ex.getCode(), ex.getMessage()));
    }

}
