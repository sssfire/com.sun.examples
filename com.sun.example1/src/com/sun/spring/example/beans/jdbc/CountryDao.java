package com.sun.spring.example.beans.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class CountryDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public Country getCity(int country_id){
		String sql = "select * from country where country_id = ?";
		RowMapper<Country> rowMapper = new BeanPropertyRowMapper<>(Country.class);
		Country country = jdbcTemplate.queryForObject(sql, rowMapper, country_id);
		
		return country;
	}
	
}
