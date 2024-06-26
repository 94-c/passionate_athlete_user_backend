<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Auth Example</title>
    <link rel="stylesheet" href="<c:url value='../css/styles.css' />">
</head>
<body>
<header>
    <nav>
        <ul>
            <li><a href="<c:url value='/auth/register' />">Register</a></li>
            <li><a href="<c:url value='/auth/login' />">Login</a></li>
        </ul>
    </nav>
</header>
