package com.dinidu.jobms.service;


import com.dinidu.jobms.dto.JobDTO;

import java.util.List;

public interface JobService {
    public void saveJob(JobDTO jobDTO);
    public void updateJob(JobDTO jobDTO);

    List<JobDTO> getAllJobs();


    void changeJobStatus(String id);

    List<JobDTO> getAllJobsByKeyword(String keyword);
}
