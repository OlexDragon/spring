<!DOCTYPE html>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<html lang="en">
<head>
<meta charset="utf-8">
<title>IRT Part Numbers</title>
</head>
<body>
	<form:form method="post" action="/irt/part-numbers" modelAttribute="PNF">
		<form:select id="componentGroup" path="componentGroup.id" >
			<form:option value="-">Select</form:option>
			<c:forEach var="cg" items="${GROUPS}">
				<option value="${cg.id}">${cg.description}</option>
			</c:forEach>
		</form:select>
		<form:button>Submit</form:button>
	</form:form>
</body>
</html>