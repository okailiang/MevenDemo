<%--
  Created by IntelliJ IDEA.
  User: oukailiang
  Date: 16/7/22
  Time: 上午10:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
    <base href="<%=basePath%>">
    <title>请求出错</title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="css/custom.css">
    <!--[if lt IE 9]>
    <script src="js/html5.js"></script>
    <![endif]-->
    <script src="js/jquery-1.11.2.min.js"></script>
    <script language="javascript" src="js/globalfun.js"></script>
</head>
<body>
<div class="main">
    error message:${errcode}${errmsg}
</div>
</body>
</html>
