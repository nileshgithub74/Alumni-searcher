package com.alumni_searcher.alumni;

import com.alumni_searcher.alumni.common.apiResponse.ApiResponse;
import com.alumni_searcher.alumni.common.exception.GlobalExpection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlumniTest {

    @InjectMocks
    private GlobalExpection globalExpection;

    @Mock
    private BindingResult bindingResult;

    // Test 1: Validation exception
    @Test
    void handleValidationShouldReturnBadRequest() {

        // Arrange
        FieldError fieldError = new FieldError(
                "requestDTO",
                "university",
                "University name is required"
        );

        when(bindingResult.getFieldError())
                .thenReturn(fieldError);

        MethodArgumentNotValidException exception =
                mock(MethodArgumentNotValidException.class);

        when(exception.getBindingResult())
                .thenReturn(bindingResult);

        // Act
        ResponseEntity<ApiResponse<String>> response =
                globalExpection.handleValidation(exception);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        assertNotNull(response.getBody());

        assertEquals(
                "University name is required",
                response.getBody().getMessage()
        );

        assertEquals("error", response.getBody().getStatus());

        assertNull(response.getBody().getData());

        verify(bindingResult).getFieldError();
    }

    // Test 2: General exception
    @Test
    void handleGeneralExceptionShouldReturnInternalServerError() {

        // Arrange
        Exception exception =
                new RuntimeException("Something went wrong");

        // Act
        ResponseEntity<ApiResponse<String>> response =
                globalExpection.handleGeneralException(exception);

        // Assert
        assertEquals(
                HttpStatus.INTERNAL_SERVER_ERROR,
                response.getStatusCode()
        );

        assertNotNull(response.getBody());

        assertEquals(
                "Something went wrong",
                response.getBody().getMessage()
        );

        assertEquals("error", response.getBody().getStatus());

        assertNull(response.getBody().getData());
    }
}
