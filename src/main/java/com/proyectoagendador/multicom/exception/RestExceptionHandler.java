package com.proyectoagendador.multicom.exception;

import java.util.Optional;
import java.io.IOException;

import com.proyectoagendador.multicom.constants.Constants;
import com.proyectoagendador.multicom.model.response.ExceptionTypeResponse;
import lombok.RequiredArgsConstructor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
@RequiredArgsConstructor
public
class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private
    ExceptionTypeResponse setErrorResponse(String value){
        ExceptionTypeResponse error = new ExceptionTypeResponse();
        error.setError(Constants.GENERIC_CODE);
        error.setMessage(value);
        return error;
    }

    private ResponseEntity<ExceptionTypeResponse> setResponse(String value){
        return new ResponseEntity<>(this.setErrorResponse(value), INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Object> setResponseObject(HttpStatus status) {
        return new ResponseEntity<>(this.setErrorResponse(Constants.INCORRECT_FORMAT), status);
    }

    @ExceptionHandler(BusinessException.class)
    public final ResponseEntity<ExceptionTypeResponse> handleBusinessException(BusinessException ex) {
        return this.setResponse(ex.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        return this.setResponseObject(status);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex
            , HttpHeaders headers, HttpStatus status, WebRequest request) {

        Optional<ObjectError> opError = ex.getBindingResult().getAllErrors().stream().findFirst();
        ExceptionTypeResponse error = new ExceptionTypeResponse();

        error.setError(Constants.GENERIC_CODE);
        error.setMessage(opError.isPresent() ? opError.get().getDefaultMessage() : Constants.GENERIC_CODE);

        return new ResponseEntity<>(error, INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public final ResponseEntity<ExceptionTypeResponse> HttpServerErrorException(HttpServerErrorException ex) {
        ExceptionTypeResponse error = null;
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode node = mapper.readTree(ex.getResponseBodyAsString());
            error = new ExceptionTypeResponse(node.get("error").asText(), node.get("message").asText());
        } catch (IOException e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(error, ex.getStatusCode());
    }
}
