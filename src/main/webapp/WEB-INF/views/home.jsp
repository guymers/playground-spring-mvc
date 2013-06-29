<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
	<head>
		<title>Home</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	</head>
	<body>
		<h1>Home</h1>
		
		<a href="<c:url value="/mybatis/widgets"/>">MyBatis</a>
		<br/>
		<a href="<c:url value="/jpa/widgets"/>">JPA</a>
	</body>
</html>
