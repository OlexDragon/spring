<%@ include file="/WEB-INF/jsp/include.jsp"%>
<form:form id="mfr_form" method="post" action="/irt/manufacture-links" modelAttribute="MFRF">
	<form:button name="mfr_btn" value="Add">
		<c:choose>
			<c:when test="${ADD}">Add </c:when>
			<c:when test="${SAVE}">Save </c:when>
			<c:otherwise>New </c:otherwise>
		</c:choose>
		Manufacture
	</form:button>
	<c:if  test="${ADD}">
		<form:button name="mfr_btn" value="Cansel">Cansel</form:button>
		<form:label path="manufacture.id">ID:</form:label>
		<form:input id="id" class="c3em" path="manufacture.id"/>
		<form:label path="manufacture.name">Name:</form:label>
		<form:input id="name" path="manufacture.name"/>
		<form:label path="manufacture.link">Link:</form:label>
		<form:input id="link" class="c25em" path="manufacture.link"/>
		<form:errors>${ERROPR}</form:errors>
	</c:if>
	<s:message text="${MFRF.table}"/>
</form:form>