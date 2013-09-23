<!DOCTYPE html>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<html lang="en">
<head>
<meta charset="utf-8">
<title>IRT Manufactures</title>
</head>
<body>
	<form:form id="mfr_form" method="post" action="/irt/manufacture-links" modelAttribute="MFRF">
		<form:input id="id" path="manufacture.id"/>
		<form:input id="name" path="manufacture.name"/>
		<form:input id="link" path="manufacture.link"/>
		<form:button>Submit</form:button>
		<p><s:message text="${MFRF.table}"/></p>
	</form:form>
</body>
</html>