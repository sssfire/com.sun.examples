package com.sun.spring.example.beans.annotation.generic;

import org.springframework.stereotype.Repository;

@Repository
public class RoleDao extends BaseDao<Role>{
	
	@Override
	public void save(Role entity) {
		System.out.println("save in RoleDao.");
	}
	
}
