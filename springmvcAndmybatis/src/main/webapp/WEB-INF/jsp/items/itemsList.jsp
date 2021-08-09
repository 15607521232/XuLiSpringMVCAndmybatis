<%--
  Created by IntelliJ IDEA.
  User: 94010
  Date: 2021/8/6
  Time: 1:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>查询商品列表</title>
</head>
<body>
<form action="${pageContext.request.contextPath }/items/queryItem.action" method="post">
    查询条件：
    <table width="100%" border=1>
        <tr>
            <td><input type="submit" value="查询"/></td>
        </tr>
    </table>
    商品列表：
    <table width="100%" border=1>
        <tr>
            <td>商品名称</td>
            <td>商品价格</td>
            <td>生产日期</td>
            <td>商品描述</td>
            <td>操作</td>
        </tr>
        <c:forEach items="${itemsList }" var="item">
            <tr>
                <td>${item.name }</td>
                <td>${item.price }</td>
                <td><fmt:formatDate value="${item.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                <td>${item.detail }</td>

                <td><a href="${pageContext.request.contextPath }/items/editItems.action?id=${item.id}">修改</a></td>

            </tr>
        </c:forEach>

    </table>
</form>
</body>
</html>
