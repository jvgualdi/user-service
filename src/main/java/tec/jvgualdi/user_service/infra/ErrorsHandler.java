package tec.jvgualdi.user_service.infra;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorsHandler {

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity error404Handler () {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity error400Handler (MethodArgumentNotValidException validationException) {
        var validationErrors = validationException.getFieldErrors();
        return ResponseEntity.badRequest().body(validationErrors.stream().map(ValidationError::new).toList());
    }

    private record ValidationError (String field, String message) {
        public ValidationError (FieldError error){
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
