<!DOCTYPE html>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<html lang="en">
<head>
<meta charset="utf-8">
<title>IRT Part Numbers</title>
</head>
<body>
	<form:form id="pn_form" method="post" action="/irt/part-numbers" modelAttribute="PNF">
		<form:select id="first" path="componentGroup">
			<form:option value='-' label="Select"/>
			<form:options items="${GROUPS}" itemValue="id" itemLabel="description"/>
		</form:select>
		<form:select id="second" path="componentType">
			<form:option value='0' label="Select"/>
			<form:options items="${TYPES}" itemValue="classId" itemLabel="description"/>
		</form:select>
		<form:button>Submit</form:button>
	</form:form>
</body>
</html>