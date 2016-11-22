package com.sun.spring.example.beans.annotation.generic;

import org.springframework.stereotype.Repository;

@Repository
public class UserDao extends BaseDao<User> {

}
