package com.sun.example.spring.annotation.generic;

import org.springframework.stereotype.Service;

@Service("userService1")
public class UserService extends BaseService<User> {

}
