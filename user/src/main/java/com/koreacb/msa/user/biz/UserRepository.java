package com.koreacb.msa.user.biz;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	public User findById(long id);
	public List<User> findByNameContaining(String name);
	public List<User> findByEmailContaining(String email);
	public List<User> findByEmailEndingWith(String email);
}
