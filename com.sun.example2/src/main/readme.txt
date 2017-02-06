1.最简单的例子，helloworld
--URL: 
  http://localhost:8080/com.sun.example2/hello/helloworld
--Files：
  com.sun.springmvc.example.hello.HelloWorldController
  /hello/welcome.jsp
--package：
  com.sun.springmvc.example.hello


2. 一个简单的login的例子
--URL：
    用户注册：http://localhost:8080/com.sun.example2/login/register
    测试PathVariable：http://localhost:8080/com.sun.example2/login/test
    测试PathVariable：http://localhost:8080/com.sun.example2/login/Test123
    测试PathVariable：http://localhost:8080/com.sun.example2/login/test/testProfile
    测试PathVariable：http://localhost:8080/com.sun.example2/login/test123/testProfile123
--Files：
  com.sun.springmvc.example.login.controller.UserController
  com.sun.springmvc.example.login.domain.User
  com.sun.springmvc.example.login.domain.Profile
  com.sun.springmvc.example.login.service.UserService
  /login/register.jsp
  /login/createSuccess.jsp
  /login/showDetail.jsp
  /login/showProfile.jsp
--package：
  com.sun.springmvc.example.login
