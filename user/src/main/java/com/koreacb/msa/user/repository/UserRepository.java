package com.koreacb.msa.user.repository;

import com.koreacb.msa.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
