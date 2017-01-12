<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
</script>
</head>
<body>
<c:choose>
	<c:when test="${!empty userInfo}">
		<img src="${userInfo.headimgurl}" height="100px" width="100px">
	</c:when>
</c:choose><br>
用户信息:${userInfo}
</body>
<%-- <script src ="${pageContext.request.contextPath}/js/canvas-nest.min.js" count="200" zindex="-2" opacity="0.7" color="47,135,193"></script> --%>
</html>