package com.alumni_searcher.alumni.dto;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@JsonPropertyOrder({
        "id",
        "name",
        "location",
        "currentRole",
        "university",
        "passoutYear",
        "linkedinUrl",
        "createdAt",
        "updatedAt"
})
public class ResponseDTO {


    private Long id;
    private String name;
    private String currentRole;
    private String university;
    private String location;
    private Integer passoutYear;
    private String linkedinUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
