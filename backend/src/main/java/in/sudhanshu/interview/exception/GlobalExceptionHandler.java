package in.sudhanshu.interview.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(EmailAlreadyExistsException.class)
        public ResponseEntity<String> handleEmailExists(
                        EmailAlreadyExistsException ex) {
                return ResponseEntity
                                .status(HttpStatus.CONFLICT)
                                .body(ex.getMessage());
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<String> handleException(
                        Exception ex) {
                ex.printStackTrace();

                return ResponseEntity
                                .badRequest()
                                .body(ex.getMessage());
        }
}