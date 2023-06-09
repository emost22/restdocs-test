package com.ysys.restdocs.controller;

import com.ysys.restdocs.model.dto.res.UserGetRes;
import com.ysys.restdocs.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserGetRes>> getAllUser() {
        return new ResponseEntity<>(userService.findAllUser(), HttpStatus.OK);
    }
}
