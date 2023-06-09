package com.ysys.restdocs.repository;

import com.ysys.restdocs.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

}
