<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<body>
<h1>Polls</h1>
<table>
    <thead><tr><td>ID</td><td>Name</td></tr></thead>
    <tbody>
    <c:forEach var="poll" items="${polls}">
        <tr><td>${poll.id}</td><td>${poll.name}</td></tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>