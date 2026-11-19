package com.alumni_searcher.alumni.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestDTO {


    @NotBlank(message = " University name is required")
    private String university;

    @NotBlank(message = "Designation is required")
    @Size(max = 300, message = "Designation do not excedd  more than 300 character")
    private String designation;


    @Min(value = 2001, message = "passout year must be greator than 2015")
    @Max(value = 2100, message = "Passout year must be less than 2026" )
    private Integer passoutYear;
}
