package com.dinidu.jobms.service;


import com.dinidu.jobms.dto.JobDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface JobService {
    void saveJob(JobDTO jobDTO);
    void updateJob(JobDTO jobDTO);
    void changeJobStatus(String id);
    List<JobDTO> getAllJobsByKeyword(String keyword);
    Page<JobDTO> getAllJobs(PageRequest of);
}
