<%@ include file="/WEB-INF/jsp/include.jsp"%>
<form:form id="mfr_form" method="post" action="/irt/manufacture-links" modelAttribute="MFRF">
	<form:button name="mfr_btn" value="Add">Add</form:button>
	<c:if  test="${ADD}">
		<form:button name="mfr_btn" value="Cansel">Cansel</form:button>
		<form:input id="id" class="c3em" path="manufacture.id"/>
		<form:input id="name" path="manufacture.name"/>
		<form:input id="link" class="c25em" path="manufacture.link"/>
	</c:if>
	<p><s:message text="${MFRF.table}"/></p>
</form:form>