<%--
  Created by IntelliJ IDEA.
  User: 94010
  Date: 2021/8/18
  Time: 21:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>系统登陆</title>
</head>
<body>
<form action="${pageContext.request.contextPath }/login.action" method="post">
    用户账号：<input type="text" name="username" /><br/>
    用户密码 ：<input type="password" name="password" /><br/>
    <input type="submit" value="登陆"/>
</form>
</body>
</html>
