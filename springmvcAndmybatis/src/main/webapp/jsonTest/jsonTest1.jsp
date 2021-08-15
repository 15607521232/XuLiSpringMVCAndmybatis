<%--
  Created by IntelliJ IDEA.
  User: 94010
  Date: 2021/8/16
  Time: 0:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.js"></script>
    <script type="text/javascript">
        //请求是json,输出就是json
        function requestJson() {
            $.ajax(
                {
                    type:'post',
                    url:'${pageContext.request.contextPath}/requestJson.action',
                    // JSON数据格式
                    contentType:'application/json;charset=utf-8',
                    data:'{"name":"手机","price":"1999"}',
                    success:function (data) {//返回json结果
                        alert(data.name);

                    }

                }
            )
        };


        //请求是key/value,输出是json
        function responsetJson() {
            $.ajax(
                {
                    type:'post',
                    url:'${pageContext.request.contextPath}/responsetJson.action',
                    // key/value
                    data:'name=手机&price=2999',
                    success:function (data) {//返回json结果
                        alert(data.price);

                    }

                }
            )
        }
    </script>
</head>
<body>

<input type="button" onclick="requestJson()" value="请求是json,输出是json"></br>
<input type="button" onclick="responsetJson()" value="请求是key/value,输出是json">

</body>
</html>
