package com.dinidu.jobms.repository;

import com.dinidu.jobms.entity.Job;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {
    @Transactional
    @Modifying
    @Query(value = "UPDATE Job SET status='Deactivate' WHERE id =?1",nativeQuery = true)
    void updateJobStatus(String id);
    List<Job> findJobByJobTitleContainingIgnoreCase(String keyword);
    boolean existsByJobTitleAndCompanyAndLocation(String jobTitle, String company, String location);

    //JPA METHODS
    // Prefix + FieldName + Operator + Conjunctions

    //Prefix
        //findBy - readyBy - getBy - countBy - existsBy - deleteBy
    //FieldName
        // entity attribute name
    //Operator
        //Equals - IsNull - IsNotNull - LessThan - LessThanEquals - GreaterThan - GreaterThanEquals
        //Between - In , NotIn ,Like NotLike ,Containing , StartingWith, EndingWith, IgnoreCase
    // Conjunctions
        //And , Or
}
