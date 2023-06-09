package com.ysys.restdocs.service;

import com.ysys.restdocs.model.dto.res.UserGetRes;
import com.ysys.restdocs.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public int getCnt() {
        return 1;
    }

    public List<UserGetRes> findAllUser() {
        return userRepository.findAll().stream().map(UserGetRes::of).collect(Collectors.toList());
    }
}
