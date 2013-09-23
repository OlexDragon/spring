<%@ include file="/WEB-INF/jsp/include.jsp"%>
<form:form id="mfr_form" method="post" action="/irt/manufacture-links" modelAttribute="MFRF">
	<form:input id="id" path="manufacture.id"/>
	<form:input id="name" path="manufacture.name"/>
	<form:input id="link" path="manufacture.link"/>
	<form:button>Submit</form:button>
	<p><s:message text="${MFRF.table}"/></p>
</form:form>