package com.dinidu.jobms.service.impl;

import com.dinidu.jobms.dto.JobDTO;
import com.dinidu.jobms.entity.Job;
import com.dinidu.jobms.exceptions.DuplicateJobException;
import com.dinidu.jobms.exceptions.ResourceNotFoundException;
import com.dinidu.jobms.repository.JobRepository;
import com.dinidu.jobms.service.JobService;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final ModelMapper modelMapper;

    @Override
    public void saveJob(JobDTO jobDTO) {
        boolean exists = jobRepository.existsByJobTitleAndCompanyAndLocation(
                jobDTO.getJobTitle(), jobDTO.getCompany(), jobDTO.getLocation()
        );
        if (exists) {
            throw new DuplicateJobException("Job already exists with same title, company, and location");
        }
        // convert DTO to entity and save
        jobRepository.save(modelMapper.map(jobDTO, Job.class));
    }


    @Override
    public void updateJob(JobDTO jobDTO) {
        jobRepository.save(modelMapper.map(jobDTO, Job.class));
    }

    @Override
    public void changeJobStatus(String id) {
        jobRepository.updateJobStatus(id);
    }

    @Override
    public List<JobDTO> getAllJobsByKeyword(String keyword) {
        List<Job> list=jobRepository.findJobByJobTitleContainingIgnoreCase(keyword);
        return modelMapper.map(list,new TypeToken<List<JobDTO>>(){}.getType());
    }

    @Override
    public Page<JobDTO> getAllJobs(PageRequest of) {
        Page<Job> jobPage = jobRepository.findAll(of);
        return jobPage.map(job -> modelMapper.map(job, JobDTO.class));
    }
    public void deleteJob(Integer id) {
        if (!jobRepository.existsById(id)) {
            throw new ResourceNotFoundException("Job not found");
        }
        jobRepository.deleteById(id);
    }
}
