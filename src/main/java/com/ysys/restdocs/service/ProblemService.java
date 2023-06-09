package com.ysys.restdocs.service;

import com.ysys.restdocs.model.dto.res.ProblemGetRes;
import com.ysys.restdocs.repository.ProblemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProblemService {
    private final ProblemRepository problemRepository;

    public List<ProblemGetRes> findAllProblem() {
        return problemRepository.findAll().stream().map(ProblemGetRes::of).collect(Collectors.toList());
    }
}
