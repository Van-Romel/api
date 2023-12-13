package com.cronoseuropa.api.configs;

import com.cronoseuropa.api.exceptions.CronosApiException;
import com.cronoseuropa.api.exceptions.CronosBadRequestException;
import com.cronoseuropa.api.exceptions.CronosInternalErrorException;
import com.cronoseuropa.api.exceptions.UserNotFoundException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = {UserNotFoundException.class})
    public CustomError handleNotFoundException(CronosApiException ex, HttpServletRequest request) {
        return new CustomError(request,
                String.format("description=%s", ex.getDescription()),
                String.format("internalCode=%d", ex.getCode()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {CronosBadRequestException.class})
    public CustomError handleBadRequestException(CronosApiException ex, HttpServletRequest request) {
        return new CustomError(request,
                String.format("description=%s", ex.getDescription()),
                String.format("internalCode=%d", ex.getCode()));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = CronosInternalErrorException.class)
    public CustomError handleThorthulRuntimeException(CronosApiException ex, HttpServletRequest request) {
        return new CustomError(request,
                String.format("description=%s", ex.getDescription()),
                String.format("internalCode=%d", ex.getCode()));
    }
    @Getter
    @Setter
    public static class CustomError {

        private String timestamp;
        private String path;
        private Map<String, String> error;

        public CustomError(HttpServletRequest req, String... args) {
            this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            this.path = req.getRequestURI();
            if (args.length == 0)
                this.error = null;
            this.error = Arrays.stream(args)
                    .map(s -> s.split("="))
                    .collect(Collectors.toMap(
                            s -> s[0],
                            s -> s[1]
                    ));
        }
    }
}
