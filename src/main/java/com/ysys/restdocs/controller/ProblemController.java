package com.ysys.restdocs.controller;

import com.ysys.restdocs.model.dto.res.ProblemGetRes;
import com.ysys.restdocs.service.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/problems")
public class ProblemController {
    private final ProblemService problemService;

    @GetMapping
    public ResponseEntity<List<ProblemGetRes>> getAllProblem() {
        return new ResponseEntity<>(problemService.findAllProblem(), HttpStatus.OK);
    }
}
