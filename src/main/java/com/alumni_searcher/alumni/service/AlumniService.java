package com.alumni_searcher.alumni.service;

import java.util.*;

import com.alumni_searcher.alumni.dto.RequestDTO;
import com.alumni_searcher.alumni.dto.ResponseDTO;
import org.springframework.stereotype.Service;


public interface AlumniService {

    List<ResponseDTO> findAllusers();

    List<ResponseDTO> search(RequestDTO request);

    void deleteAlumni(Long id);

    void deleteAllAlumni();

}
