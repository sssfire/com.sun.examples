package com.sun.spring.example.beans.annotation.generic;

import org.springframework.stereotype.Service;

@Service("userService1")
public class UserService extends BaseService<User> {

}
