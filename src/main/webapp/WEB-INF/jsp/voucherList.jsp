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
    <title>Vouch列表</title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="css/custom.css">
    <!--[if lt IE 9]>
    <script src="js/html5.js"></script>
    <![endif]-->
    <script src="js/jquery-1.11.2.min.js"></script>
    <script language="javascript" src="js/voucher.js"></script>
    <script language="javascript" src="js/globalfun.js"></script>
</head>
<body>
<div class="main">${openId}
    <h3>Vouch列表${key}</h3>
    <div class="voucher-tab">
        <button id="add-voucher">增加</button>
        <button id="delete-voucher">删除</button>
        <button id="search-voucher">查询</button>
        <input type="text" id="voucher-id" placeholder="请输入id"/>
    </div>

    <div id="voucher-table" class="voucher-table">
        <table>
            <thead>
            <tr>
                <th><input type="checkbox" id="checkbox-all"></th>
                <th>|id</th>
                <th>|voucherNo</th>
                <th>|amount</th>
                <th>|activeId</th>
                <th>|batchNo</th>
                <th>|consumerType</th>
                <th>|activeName</th>
                <th>|activeType</th>
                <th>|status</th>
                <th>|voucherBeginTime</th>
                <th>|voucherEndTime</th>
                <th>|activeStartTime</th>
                <th>|activeEndTime</th>
                <th>|activeImageUrl</th>
                <th>|activeRule</th>
                <th>|full</th>
                <th>|type</th>
                <th>|useTimes</th>
                <th>|version</th>
                <th>|createTime</th>
                <th>|lastModified</th>
                <th>|modifiedBy</th>
                <th>|businessType</th>
                <th>|phoneNum</th>
                <th>|cityId</th>
                <th>|strategyCount</th>
            </tr>
            </thead>
            <tbody id="voucher-table-body">
            <c:choose>
                <c:when test="${!empty voucherList}">
                    <c:forEach items="voucherList" var="item">
                        <tr>
                            <td></td>
                            <td>${item.voucherNo}</td>
                        </tr>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                   <!-- <tr>
                        <td colspan="6" style="text-align:center;color:red">还没有数据！</td>
                    </tr> -->
                </c:otherwise>
            </c:choose>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
