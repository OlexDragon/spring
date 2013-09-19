<!DOCTYPE html>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<html>
<head>
<%@ include file="/WEB-INF/jsp/meta.jsp"%>
<title><tiles:insertAttribute name="title" ignore="true" /></title>
</head>
<body>
	<header>
		<div>
			<tiles:insertAttribute name="header" />
		</div>
		<div>
			<tiles:insertAttribute name="navigation" />
		</div>
	</header>
	<div id="body">
		<tiles:insertAttribute name="body" />
	</div>
	<div id="footer">
		<tiles:insertAttribute name="footer" />
	</div>
</body>
</html>
