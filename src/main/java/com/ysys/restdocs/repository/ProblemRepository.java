package com.ysys.restdocs.repository;

import com.ysys.restdocs.model.entity.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepository extends JpaRepository<Problem, String> {
    
}
