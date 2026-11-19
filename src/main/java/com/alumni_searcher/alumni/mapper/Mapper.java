
package com.alumni_searcher.alumni.mapper;

import ch.qos.logback.classic.pattern.ClassOfCallerConverter;
import com.alumni_searcher.alumni.dto.RequestDTO;
import com.alumni_searcher.alumni.dto.ResponseDTO;
import com.alumni_searcher.alumni.entity.Alumni;
import tools.jackson.databind.JsonNode;

import java.time.LocalDateTime;

public class Mapper {


    public static ResponseDTO entityToRes(Alumni alumni) {

        ResponseDTO response = new ResponseDTO();

        response.setId(alumni.getId());
        response.setName(alumni.getName());
        response.setCurrentRole(alumni.getCurrentRole());
        response.setLinkedinUrl(alumni.getLinkedinUrl());
        response.setLocation(alumni.getLocation());
        response.setPassoutYear(alumni.getPassoutYear());
        response.setUniversity(alumni.getUniversity());
        response.setCreatedAt(alumni.getCreatedAt());
        response.setUpdatedAt(alumni.getUpdatedAt());

        return response;
    }

    public static Alumni resToEntity(JsonNode item, RequestDTO request) {

        Alumni alumni = new Alumni();

        alumni.setName(getText(item, "fullName"));
        alumni.setCurrentRole(getText(item, "jobTitle"));
        alumni.setUniversity(request.getUniversity());
        alumni.setLocation(getText(item, "location"));
        alumni.setLinkedinUrl(getText(item, "profileUrl"));
        alumni.setPassoutYear(request.getPassoutYear());
        alumni.setCreatedAt(LocalDateTime.now());
        alumni.setUpdatedAt(LocalDateTime.now());

        return alumni;
    }


    private static String getText(JsonNode item, String fieldName) {
        JsonNode value = item.get(fieldName);

        if (value == null || value.isNull()) {
            return null;
        }

        return value.asString();
    }
}
