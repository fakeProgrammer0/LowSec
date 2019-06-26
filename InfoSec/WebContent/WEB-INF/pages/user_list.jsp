<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>InfoSec: 用户列表</title>
</head>
<body>
	<a href="<c:url value="/index.jsp" />">首页</a>
	<br><br>
	<h1>用户列表</h1>
	<br>
	<table border="1">
		<tr>
			<th>登录名</th>
			<th>显示名</th>
			<th>角色</th>
		</tr>
		<c:forEach var="user" items="${users}">
			<tr>
				<td>${user.username}</td>
				<td>${user.showname}</td>
				<td>${user.rolecode}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>