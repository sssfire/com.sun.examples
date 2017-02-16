<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script>
	document.cookie = "pageTitle='Welcome'"
</script>
</head>
<body>
	<form method="post" action="<c:url value="/controllerdemo/showMessage"/>">
		<table>
			<tr>
				<td>用户名：</td>
				<td><input type="text" name="userName"/></td>
			</tr>
			<tr>
				<td>密码：</td>
				<td><input type="password" name="password"/></td>
			</tr>
			<tr>
				<td>姓名：</td>
				<td><input type="text" name="realName"/></td>
			</tr>
			<tr>
				<td>Using HttpServletRequest：</td>
				<td><input type="Checkbox" name="http_servlet_request"/></td>
			</tr>
			<tr>
				<td>Using HttpServletResponse：</td>
				<td><input type="Checkbox" name="http_servlet_response"/></td>
			</tr>
			<tr>
				<td>Using HttpSession：</td>
				<td><input type="Checkbox" name="http_session"/></td>
			</tr>
			<tr>
				<td>Using WebRequest：</td>
				<td><input type="Checkbox" name="web_request"/></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" name="提交"/></td>
			</tr>		
		</table>
	</form>
</body>
</html>