<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.util.List"%>

<%@ page import="yg0r2.dm.mvc.displayfield.DisplayField"%>
<%@ page import="yg0r2.dm.util.JspUtil"%>

<%
	String actionUrl = "/DataManipulator/2?beanId=" + request.getAttribute("beanId");
%>
<form action="<%=actionUrl%>" enctype="multipart/form-data" method="POST">
	<input name="redirect" type="hidden" />

	<%
		out.write(JspUtil.getFileds((List<DisplayField>) request.getAttribute("fields")));
	%>

	<div>
		<button type="submit">Submit</button>

		<button href="" type="cancel">Cancel</button>
	</div>
</form>
