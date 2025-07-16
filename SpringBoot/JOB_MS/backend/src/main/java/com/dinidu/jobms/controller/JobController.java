package com.dinidu.jobms.controller;

import com.dinidu.jobms.dto.JobDTO;
import com.dinidu.jobms.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/v1/job")
@RestController
@RequiredArgsConstructor
public class JobController {

    //constructor injection
    private final JobService jobService;

    @PostMapping("create")
    public void createJob(@RequestBody JobDTO jobDTO){
        jobService.saveJob(jobDTO);
    }

    @PutMapping("edit")
    public void updateJob(@RequestBody JobDTO jobDTO){
        jobService.updateJob(jobDTO);
    }
    @GetMapping("alljobs")
    public List<JobDTO> getAllJobs(){
        return jobService.getAllJobs();
    }
    @PatchMapping("status/{id}")
    public void changeStatus(@PathVariable("id") String id){
        jobService.changeJobStatus(id);
    }
    @GetMapping("search/{keyword}")
    public List<JobDTO> searchJob(@PathVariable("keyword") String keyword){
        return jobService.getAllJobsByKeyword(keyword);
    }
}
