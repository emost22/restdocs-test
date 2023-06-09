package com.ysys.restdocs.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Problem extends BaseTimeEntity {
    @Id
    private String problemId;
    private String problemName;

//    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    public Problem(String problemId, String problemName) {
        this.problemId = problemId;
        this.problemName = problemName;
    }
}
