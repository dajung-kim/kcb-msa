package com.koreacb.msa.user.biz;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue
    @GraphQLQuery(name = "id", description = "A user's id")
    private long id;
    @GraphQLQuery(name = "name", description = "A user's name")
    private String name;
    @GraphQLQuery(name = "email", description = "A user's email")
    private String email;
    @GraphQLQuery(name = "age", description = "A user's age")
    private int age;
}
