package com.dinidu.jobms.controller;

import com.dinidu.jobms.dto.JobDTO;
import com.dinidu.jobms.exceptions.ResourceNotFoundException;
import com.dinidu.jobms.service.JobService;
import com.dinidu.jobms.utility.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @PostMapping
    public ResponseEntity<ApiResponse<String>> createJob(@RequestBody JobDTO jobDTO) {
        jobService.saveJob(jobDTO);
        return ResponseEntity.ok(
                new ApiResponse<>(
                        201,
                        "Job created successfully",
                        null
                )
        );
    }

    @PutMapping
    public ResponseEntity<ApiResponse<String>> updateJob(@RequestBody JobDTO jobDTO) {
        jobService.updateJob(jobDTO);
        return ResponseEntity.ok(
                new ApiResponse<>(
                        200,
                        "Job updated successfully",
                        null
                )
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<JobDTO>>> getAllJobs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Page<JobDTO> jobPage = jobService.getAllJobs(PageRequest.of(page, size));
        if (jobPage.isEmpty()) {
            throw new ResourceNotFoundException("No jobs found");
        }
        return ResponseEntity.ok(
                new ApiResponse<>(
                        200,
                        "All jobs fetched successfully",
                        jobPage
                )
        );
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<String>> changeStatus(@PathVariable String id) {
        jobService.changeJobStatus(id);
        return ResponseEntity.ok(
                new ApiResponse<>(
                        200,
                        "Job status updated successfully",
                        null
                )
        );
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<ApiResponse<List<JobDTO>>> searchJob(@PathVariable String keyword) {
        List<JobDTO> results = jobService.getAllJobsByKeyword(keyword);
        return ResponseEntity.ok(
                new ApiResponse<>(
                        200,
                        "Jobs fetched successfully",
                        results
                )
        );
    }
}