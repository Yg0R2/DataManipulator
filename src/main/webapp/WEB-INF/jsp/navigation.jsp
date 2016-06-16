<%@ page import="yg0r2.dm.util.JspUtil"%>

<form action="/DataManipulator/1" method="post" name="handlerSelection">
	<select name="handler-select-navigator" onchange="self.location = form.action + '?beanId=' + this.value">
		<option label="" />

		<%
			out.write(JspUtil.getSelectOptions((String[]) request.getAttribute("beanIds")));
		%>
	</select>
</form>
