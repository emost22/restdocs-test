package com.ysys.restdocs.model.dto.res;

import com.ysys.restdocs.model.entity.Problem;
import com.ysys.restdocs.model.entity.User;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserGetRes {
    private String userId;
    private String userName;
    private String userPassword;
    private List<ProblemGetRes> problemGetResList;

    public static UserGetRes of(User user) {
        return UserGetRes.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .userPassword(user.getUserPassword())
                .problemGetResList(user.getProblemList().stream().map(ProblemGetRes::of).collect(Collectors.toList()))
                .build();
    }
}
