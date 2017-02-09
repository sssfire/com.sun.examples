package com.sun.springmvc.example.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sun.springmvc.example.login.domain.User;
import com.sun.springmvc.example.login.service.UserService;

/**
 * 处理过程：
 * S1. DispatchServlet接受客户端的/login/createUser请求
 * S2. DispatchServlet使用DefaultAnnotationHandlerMapping查找负责处理该请求的处理器，关于默认配置信息在下面的位置可以找到：
 *     spring-webmvc-xx.jar包下的org.springframework.web.servlet->DispatcherServlet.properties中关于org.springframework.web.servlet.HandlerMapping的配置
 * S3. DispatchServlet请求/login/createUser，找到处理该请求的处理器为UserController
 * S4. DispatchServlet使用AnnotationMethodHandlerAdapter调用处理器UserController处理完业务后，返回ModelAndView对象，其中View的逻辑名为/login/createSuccess，
 *     模型包含一个键值为user的User对象。关于AnnotationMethodHandlerAdapter的配置，同S2，可以在相同的配置文件中找到。
 * S5. DispatchServlet调用InternalResourceViewResolver组建对ModelAndView中的逻辑视图名进行解析，得到真实名称为/login/createSuccess.jsp视图对象
 * S6. DispatchServlet使用该视图对模型中的user模型进行渲染
 * S7. 返回响应给客户端
 */

/**
 * 1. @Controller， 所有的处理请求控制器必须使用该注解进行标注，在通过配置文件<context:component-scan/>控制器会自动把
 *    该类解析为一个处理请求控制器类
 * 2. @RequestMapping，标注该类处理来自/login URI的请求。
 *    - 在类中所标注的URL是相对于Web应用的部署路径。在"类"中进行标注不是必须的。
 *    - 在方法中所标注的URL是相对于类中所标注的URL路径。例如register方法将处理/login/register请求。
 *    - RequestMapping中定义的URL值其实指的是其value属性值，即：@RequestMapping(value="/login")
 *    - RequestMapping支持通配符和{XXX}占位符，下列都是合法的：
 *      -- "/login/ * /createUser" : 匹配/login/aaa/createUser, /login/bbb/createUser
 *      -- "/login/ ** /createUser": 匹配/login/createUser, /login/aaa/bbb/createUser
 *      -- "/login/createUser??"   : 匹配/login/createUseraa, /login/createUserbb
 *      -- "/login/{userId}"       : 匹配/login/123, /login/456
 *      -- "/login/ ** /{userId}"  : 匹配/login/aaa/bbb/123, /login/aaa/456
 *      -- "company/{companyId}/user/{userId}/detail": 匹配/company/123/user/456/detail
 * 3. @PathVarible，通过该注解可以把URL中的{xxx}占位符绑定到操作方法的参数中
 */
@Controller
@RequestMapping("/login")
public class UserController {
	
	//注入业务层的bean
	@Autowired
	private UserService userService;
	
	/**
	 * 返回值代表一个逻辑视图名称，将由视图解析器解析为一个具体的视图对象，例如下面将映射到：/login/register.jsp
	 */
	@RequestMapping("/register")
	public String register(){
		return "login/register";
	}
	
	/**
	 * 1. 接受来自/login/createUser的请求
	 * 2. 使用的方法必须为Post
	 * 3. 将表单值映射到user对象中，作为参数传入函数
	 * 4. ModelAndView对象存放View的名字和模型对象，视图解析器将解析视图为/login/createSuccess.jsp,
	 *    同时把模型对象user传给该视图，因此在视图中可以使用${user.userName}进行访问。
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/createUser",method=RequestMethod.POST)
	public ModelAndView createUser(User user){
		userService.createUser(user);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("login/createSuccess");
		mav.addObject("user", user);
		return mav;
	}
	
	/**
	 * 1. 接受来自/login/test的请求，其中test可以为任意值
	 * 2. @PathVariable 会根据URL中所传入的{userId}，绑定到方法的入参中
	 * 3. 可以在URL中尝试使用下列URL形式，得到不同的结果：
	 *    -- /login/test
	 *    -- /login/test123
	 */
	@RequestMapping("/{userId}")
	public ModelAndView showDetail(@PathVariable("userId") String userId){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("login/showDetail");
		mav.addObject("user", userService.getUserById(userId));
		return mav;
	}
	
	/**
	 * 一个更为复杂的@PathVariable的例子，可尝试使用下列URL形式进行测试,会得到不同的结果：
	 * -- /login/test/testProfile
	 * -- /login/test123/testProfile123
	 */
	@RequestMapping("/{userId}/{profileName}")
	public ModelAndView showPrivilage(@PathVariable("userId") String userId, @PathVariable("profileName") String profileName){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("login/showProfile");
		mav.addObject("profile", userService.getProfileByUserId(userId, profileName));
		mav.addObject("userId", userId);
		return mav;		
	}
}
