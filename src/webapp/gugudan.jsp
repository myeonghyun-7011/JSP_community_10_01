<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.sbs.exam.Rq"%>

<%
Rq rq = new Rq(request, response);

    int dan = rq.getIntParam("dan", 9);
    int limit = rq.getIntParam("limit", 9);
%>

<h1><%=dan%>단</h1>
<% for(int i = 1; i <= limit; i++) { %>
    <div><%=dan%> * <%=i%> = <%=dan * i%></div>
<% } %>

<%
int a = 10;
%>

<!-- 주석방식 1-->
<h1><% out.println(a); %></h1>
<!-- 주석방식 2-->
<h1><%=a%></h1>