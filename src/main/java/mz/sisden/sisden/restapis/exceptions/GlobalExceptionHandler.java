package mz.sisden.sisden.restapis.exceptions;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import mz.sisden.sisden.restapis.helpers.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleJsonDeserializationError(HttpMessageNotReadableException ex) {
        Throwable cause = ex.getCause();
        if (cause instanceof InvalidFormatException invalidEx) {
            // Pega o campo problemático
            String field = invalidEx.getPath().isEmpty() ? "desconhecido" : invalidEx.getPath().get(0).getFieldName();
            String valor = String.valueOf(invalidEx.getValue());
            String mensagem = String.format("Campo '%s' possui valor inválido: %s", field, valor);
            return ResponseEntity.badRequest().body(Map.of("error", mensagem));
        }

        return ResponseEntity.badRequest().body(Map.of("error", "Erro ao ler o JSON: verifique os tipos e valores enviados."));
    }
}
