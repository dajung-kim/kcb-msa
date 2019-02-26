package com.koreacb.msa.card.repository;

import com.koreacb.msa.card.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
