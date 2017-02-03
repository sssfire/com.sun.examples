package com.sun.springmvc.example.hello;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class HelloWorldController implements Controller{

	public ModelAndView handleRequest(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		
		System.out.println("-------hello world-----");
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("key1", "Green");
		map.put("key2", "Red");
		map.put("key3", "Yello");
		
		return new ModelAndView("/hello/welcome","map",map);
		
	}

}
