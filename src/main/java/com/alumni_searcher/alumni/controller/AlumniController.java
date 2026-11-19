package com.alumni_searcher.alumni.controller;

import com.alumni_searcher.alumni.common.apiResponse.ApiResponse;
import com.alumni_searcher.alumni.dto.RequestDTO;
import com.alumni_searcher.alumni.dto.ResponseDTO;
import com.alumni_searcher.alumni.service.AlumniService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alumni")
public class AlumniController {
    private final AlumniService alumniService;

    public AlumniController(AlumniService alumniService) {
        this.alumniService = alumniService;
    }


    //get all the users;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<ResponseDTO>>> getAllUsers() {
        List<ResponseDTO> alluserResponse = alumniService.findAllusers();

        String message = alluserResponse.isEmpty()
                ? "No alumni records found."
                : "All alumni records fetched successfully.";

        ApiResponse<List<ResponseDTO>> res = new ApiResponse<>(
                message,
                "success",
                alluserResponse

        );

        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    // search the alumni;

    @PostMapping("/search")
    public ResponseEntity<ApiResponse<List<ResponseDTO>>> search(@Valid @RequestBody RequestDTO request) {
        List<ResponseDTO> alumniList = alumniService.search(request);

        ApiResponse<List<ResponseDTO>> responseList = new ApiResponse<>(
                "All alumni list fetched successfully",
                "success",
                alumniList
        );

        return ResponseEntity.status(HttpStatus.OK).body(responseList);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAlumni(@PathVariable Long id) {

        alumniService.deleteAlumni(id);

        return ResponseEntity.ok(
                new ApiResponse<>("Alumni deleted successfully", "success", null)
        );
    }

    @DeleteMapping("/delete-all")
    public ResponseEntity<ApiResponse<Void>> deleteAllAlumni() {

        alumniService.deleteAllAlumni();

        return ResponseEntity.ok(
                new ApiResponse<>("All alumni deleted successfully", "success", null)
        );
    }


}
