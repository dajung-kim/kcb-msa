package com.koreacb.msa.user.biz;

import java.util.List;

import org.springframework.stereotype.Service;


import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
	private final UserRepository repo;
	
	@GraphQLQuery(name = "users")
	public List<User> getUsers() {
		return repo.findAll();
	}
	
	@GraphQLQuery(name = "user")
	public User getUser(@GraphQLArgument(name = "id") long id) {
		return repo.findById(id);
	}
	
	@GraphQLQuery(name = "users")
	public List<User> getUserByName(@GraphQLArgument(name = "name") String name) {
		return repo.findByNameContaining(name);
	}
	
	@GraphQLQuery(name = "users")
	public List<User> getUserByEmail(@GraphQLArgument(name = "email") String email) {
		return repo.findByEmailContaining(email);
	}
	
	@GraphQLQuery(name = "users")
	public List<User> getUserByEmailDomain(@GraphQLArgument(name = "emaildomain") String email) {
		return repo.findByEmailEndingWith(email);
	}
}
