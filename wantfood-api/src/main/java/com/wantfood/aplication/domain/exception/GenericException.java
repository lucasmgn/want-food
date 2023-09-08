package com.wantfood.aplication.domain.exception;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class GenericException extends RuntimeException{

    private final HttpStatus status;

    private final String consumerMessage;

    @Builder(access = AccessLevel.PRIVATE)
    private GenericException(HttpStatus status, String consumerMessage, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
        this.consumerMessage = consumerMessage;
    }

    public static GenericException internalServerError(String message, Throwable cause) {
        return GenericException.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .consumerMessage(message)
                .message(message)
                .cause(cause)
                .build();
    }

    public static GenericException badRequest(String message, Throwable cause) {
        return GenericException.builder()
                .status(HttpStatus.BAD_REQUEST)
                .consumerMessage(message)
                .message(message)
                .cause(cause)
                .build();
    }

    public static GenericException notFound(String message, Throwable cause) {
        return GenericException.builder()
                .status(HttpStatus.NOT_FOUND)
                .consumerMessage(message)
                .message(message)
                .cause(cause)
                .build();
    }

    public static GenericException notFound(String message) {
        return GenericException.builder()
                .status(HttpStatus.NOT_FOUND)
                .consumerMessage(message)
                .message(message)
                .build();
    }
}
