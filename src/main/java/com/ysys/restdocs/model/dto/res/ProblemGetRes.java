package com.ysys.restdocs.model.dto.res;

import com.ysys.restdocs.model.entity.Problem;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProblemGetRes {
    private String problemId;
    private String problemName;

    public static ProblemGetRes of(Problem problem) {
        return ProblemGetRes.builder()
                .problemId(problem.getProblemId())
                .problemName(problem.getProblemName())
                .build();
    }
}
