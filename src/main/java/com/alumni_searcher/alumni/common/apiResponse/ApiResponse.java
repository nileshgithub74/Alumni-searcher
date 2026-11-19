package com.alumni_searcher.alumni.common.apiResponse;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApiResponse<T> {

    private String message;
    private String status;
    private T data;
}
