<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<html>
	<head>
		<title>Home</title>
	</head>
	<body>
		<h1>Home</h1>
		
		<a href="<c:url value="/mybatis/widgets"/>">MyBatis</a>
		<br/>
		<a href="<c:url value="/jpa/widgets"/>">JPA</a>
	</body>
</html>
