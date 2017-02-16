package com.sun.springmvc.example.annotation4controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.security.Principal;
import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

/**
 * @RequestMapping：
 * 1. 主要工作是把请求映射到具体的处理方法上。
 * 2. 映射的规则是根据请求中的信息制定，映射规则可以确保URL更为精确的映射到相应的方法上。这些信息包括：
 *  - 请求的URL， 包括带占位符的URL，参见sun.springmvc.example.login下的UserController
 *  - 请求的参数
 *  - 请求的method：GET，POST，DELETE，PUT等
 *  - 请求的header信息
 * 3. 映射规则之间不能有冲突
 * 
 * @RequestParam：
 * 1. 可以用于标注方法的参数，以获取具体的参数值
 * 2. 参数可来源于URL中的参数，例如：param1=123&param2=456, 也可来源于表单提交的数据
 * 
 * @CookieValue
 * 1. 可以用于标注方法的参数，以获取具体的Cookie值
 * 2. 参数来源于客户端提交的Header中的Cookie值
 * 
 * @RequestHeader
 * 1. 可以用于标注方法的参数，以获取具体的Http response中的Header值
 */

@Controller
@RequestMapping("/controllerdemo")
public class AnnotationInControllerDemo {
	
	@RequestMapping(value="/login")
	public String goLogin(){
		return "/controllerdemo/login";
	}
	
	/**
	 * @RequestMapping演示
	 * 1. 不带参数的URL将映射到该函数：
	 * 2. URL: controllerdemo/showMessage
	 */
	@RequestMapping(value="/showMessage")
	public ModelAndView showMessage1(){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("controllerdemo/showMessage");
		mav.addObject("message", "URL has no parameters");
		return mav;
	}
	
	/**
	 * @RequestMapping演示
	 * 1. 带参数language的URL将映射到该函数
	 * 2. URL: controllerdemo/showMessage?language=EN
	 */
	@RequestMapping(value="/showMessage", params="language")
	public ModelAndView showMessage2(@RequestParam("language") String language){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("controllerdemo/showMessage");
		mav.addObject("message", "URL has parameters 'lanugage' and value is '" + language + "'");
		return mav;
	}
	
	/**
	 * @RequestMapping演示
	 * 1. 不带参数language，同时带client的URL将映射到该函数
	 * 2. URL: controllerdemo/showMessage?client=001
	 */
	@RequestMapping(value="/showMessage", params={"!language","client"})
	public ModelAndView showMessage3(){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("controllerdemo/showMessage");
		mav.addObject("message", "URL cannot has parameter 'lanugage' and It must has parameter 'client'");
		return mav;
	}
	
	/**
	 * @RequestMapping演示
	 * 1. 带参数language，同时language不能为EN的URL将映射到该函数
	 * 2. URL: controllerdemo/showMessage?language=CN
	 */
	@RequestMapping(value="/showMessage", params={"language","language!=EN"})
	public ModelAndView showMessage4(){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("controllerdemo/showMessage");
		mav.addObject("message", "URL has parameter 'lanugage' and it's value is not 'EN'");
		return mav;
	}
	
	/**
	 * @RequestMapping演示
	 * 1. 带参数language，同时language为EN的URL将映射到该函数
	 * 2. URL: controllerdemo/showMessage?language=EN&client=001
	 * 
	 * @RequestParam 将把相应参数的值放入函数入参中
	 *  - value: 参数名称
	 *  - required：是否必须有该参数
	 *  - defaultValue：默认参数值，设置该值时自动把该参数required设为false
	 */
	@RequestMapping(value="/showMessage", params={"language=EN","client"})
	public ModelAndView showMessage5(@RequestParam(value="language",required=true) String language, 
									 @RequestParam("client") String client){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("controllerdemo/showMessage");
		String message1 = "URL has parameter 'language' and it's value should be 'EN', and there should also have parameter 'client'<br>";
		String message2 = "The real value for 'language' is " + language +" and for 'client' is " + client;
		mav.addObject("message", message1 + message2);
		return mav;
	}
	
	/**
	 * @RequestParam演示
	 * 1. 不但可以获取来自URL中的参数值，还可以获取表单传过来的参数
	 * 2. 如果不存在realName参数，将赋默认值"test user"
	 * 
	 * @CookieValue演示
	 * 1. 获取客户端传来的Cookie值
	 * 2. 也有value，required和defaultValue值，含义和@RequestParam相同
	 * 
	 * @Reuestheader样式
	 * 1. 获取客户端请求的Header值
	 * 2. 也有value，required和defaultValue值，含义和@RequestParam相同
	 * 
	 * URL: /controllerdemo/login, 不选择Use HttpServletRequest
	 */
	@RequestMapping(value="/showMessage", params={"userName","password", "!http_servlet_request"})
	public ModelAndView showMessage6(@RequestParam("userName") String userName, 
									 @RequestParam("password") String password,
									 @RequestParam(value="realName",required=false,defaultValue="test user") String realName,
									 @CookieValue("pageTitle") String pageTitle,
									 @CookieValue("JSESSIONID") String jssessionid,
									 @RequestHeader("Accept-Language") String acceptLanguage){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("controllerdemo/showMessage");
		String message = "The information comes from login page is as following:<br>"
				+ "param - userName: " + userName + "<br>"
				+ "param - password: " + password + "<br>"
				+ "param - realName: " + realName + "<br>"
				+ "cookie - pageTitle: " + pageTitle + "<br>"
				+ "cookie - JSESSIONID: " + jssessionid + "<br>"
				+ "header - AcceptLanguage: " + acceptLanguage + "<br>";
		
		mav.addObject("message", message);
		return mav;
	}
	
	/**
	 * 演示如何使用HttpServletRequest
	 * URL: /controllerdemo/login, 选择Use HttpServletRequest
	 */
	@RequestMapping(value="/showMessage", params={"http_servlet_request=on"})
	public ModelAndView showMessage7(HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("controllerdemo/showMessage");
		String message = "The information comes from login page is as following:<br>"
				+ "param - userName: " + request.getParameter("userName") + "<br>"
				+ "param - password: " + request.getParameter("password") + "<br>"
				+ "param - realName: " + request.getParameter("realName") + "<br>"
				+ "cookie - " + request.getCookies()[0].getName()+ ":" + request.getCookies()[0].getValue() + "<br>"
				+ "header - AcceptLanguage: " + request.getHeader("Accept-Language") + "<br>";	
		mav.addObject("message", message);
		return mav;
	}
	
	/**
	 * 演示如何使用HttpServletRequest, HttpServletResponse
	 * URL: /controllerdemo/login, 选择Use HttpServletRequest和HttpServletResponse
	 */
	@RequestMapping(value="/showMessage", params={"http_servlet_request=on","http_servlet_response=on"})
	public ModelAndView showMessage7(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String userName = WebUtils.findParameterValue(request, "userName");
		response.addCookie(new Cookie("cookie_test", "Test Cookie"));
		response.addCookie(new Cookie("userName", userName));
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("controllerdemo/showMessage");
		String message = "Using HttpServletRequest and HttpServletResponse, and set some cookies. Please press F12 to check cookies";
		mav.addObject("message", message);
		return mav;
	}
	
	/**
	 * 1. 演示如何使用HttpSession
	 * 2. 演示Servlet内部对象可以和@RequestParam混用
	 * URL: /controllerdemo/login, 仅仅选择Use HttpSession
	 */
	@RequestMapping(value="/showMessage", params={"userName","password", "!http_servlet_request","!http_servlet_response","http_session"})
	public ModelAndView showMessage8(HttpSession httpSession, @RequestParam("userName") String userName) throws IOException{
		
		String serverInfo = httpSession.getServletContext().getServerInfo();
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("controllerdemo/showMessage");
		String message = "Using HttpSession, userName is " + userName + " and server information is " + serverInfo;
		mav.addObject("message", message);
		return mav;
	}
	
	/**
	 * 1. 演示如何使用WebRequest
	 * 2. 在org.springframework.web.context.request中定义了若干可代理Servlet原生API的接口，如WebRequest，NativeWebRequest，他们也可以作为参数访问
	 * URL: /controllerdemo/login, 仅仅选择Use WebRequest
	 */
	@RequestMapping(value="/showMessage", params={"userName","password", "!http_servlet_request","!http_servlet_response","!http_session","web_request"})
	public ModelAndView showMessage9(WebRequest request) throws IOException{
		
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String realName = request.getParameter("realName");
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("controllerdemo/showMessage");
		String message = "The information comes from login page is as following:<br>"
				+ "param - userName: " + userName + "<br>"
				+ "param - password: " + password + "<br>"
				+ "param - realName: " + realName + "<br>";
		
		mav.addObject("message", message);
		return mav;
	}
	
	/**
	 * 1. 演示如何使用IO对象作为参数
	 * 2. 输出一张图片
	 * URL: /controllerdemo/showImage
	 */
	@RequestMapping(value="/showImage")
	public void showImage(OutputStream os, HttpSession session) throws IOException{
		URL url = session.getServletContext().getResource("/img/a.png");
		Resource res = new UrlResource(url);
		FileCopyUtils.copy(res.getInputStream(), os);
	}
	
	/**
	 * 控制函数的入参还可以使用下列类型为参数，SpringMVC将进行自动赋值：
	 * 1. java.util.Locale
	 * 2. java.security.Principal
	 * URL: /controllerdemo/showLocaleAndPrincipal
	 */
	@RequestMapping(value="/showLocaleAndPrincipal")
	public ModelAndView showMessage10(Locale locale, Principal principal) throws IOException{
		
		String localeInfo = locale.toString();
		String principalInfo;
		if(principal == null){
			principalInfo = "empty information";
		}else{
			principalInfo = principal.getName() + ":" + principal.toString();
		}
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("controllerdemo/showMessage");
		String message = "The locale and principal information is as below:<br>"
				+ "locale: " + localeInfo + "<br>"
				+ "principal: " + principalInfo + "<br>";
		
		mav.addObject("message", message);
		return mav;
	}
}
