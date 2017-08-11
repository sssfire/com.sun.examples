package com.sun.example.spring.annotation.generic;

import org.springframework.stereotype.Repository;

@Repository
public class UserDao extends BaseDao<User> {

}
