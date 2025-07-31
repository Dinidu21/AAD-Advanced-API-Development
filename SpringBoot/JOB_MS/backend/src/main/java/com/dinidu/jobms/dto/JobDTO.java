package com.dinidu.jobms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JobDTO {
    private Integer id;

    @NotBlank(message = "Job title is required")
    @Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = "Job title can only contain alphanumeric characters and spaces")
    private String jobTitle;

    @NotBlank(message = "Company is required")
    private String company;

    @NotBlank(message = "Location is required")
    private String location;

    @NotBlank(message = "Type is required")
    private String type;

    @Size(min = 10, max = 100, message = "Job Description should be at least 10 characters long")
    private String jobDescription;
    private String status;
}
