<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>


<%
  List<Map<String, Object>> articleRows = (List<Map<String, Object>>) request.getAttribute("articleRows");
%>


<!doctype html>
<html lang="ko">
<head>
  <title>게시물리스트</title>
</head>
<body>
  <h1>게시물 리스드 V1</h1>
  <ul>
    <li>
      <%= articleRows.get(0).get("id")%>번,
      <%= articleRows.get(0).get("regDate")%>,
      <%= articleRows.get(0).get("updateDate")%>,
      <%= articleRows.get(0).get("title")%>
    </li>

    <li>
      <%= articleRows.get(1).get("id")%>번,
      <%= articleRows.get(1).get("regDate")%>,
      <%= articleRows.get(1).get("updateDate")%>,
      <%= articleRows.get(1).get("title")%>
    </li>

    <li>
      <%= articleRows.get(2).get("id")%>번,
      <%= articleRows.get(2).get("regDate")%>,
      <%= articleRows.get(2).get("updateDate")%>,
      <%= articleRows.get(2).get("title")%>
    </li>
  </ul>

  <h1>게시물 리스드 V2</h1>
  <ul>
    <% for(int i = 0; i < 3; i++) {%>
    <li>
      <%= articleRows.get(i).get("id")%>번,
      <%= articleRows.get(i).get("regDate")%>,
      <%= articleRows.get(i).get("updateDate")%>,
      <%= articleRows.get(i).get("title")%>
    </li>
    <% } %>
  </ul>

  <h1>게시물 리스드 V3</h1>
  <ul>
    <%
      for(int i = 0; i < 3; i++) {
      Map<String, Object> articleRow = articleRows.get(i);
    %>
    <li>
      <%= articleRow.get("id")%>번,
      <%= articleRow.get("regDate")%>,
      <%= articleRow.get("updateDate")%>,
      <%= articleRow.get("title")%>
    </li>
    <% } %>
  </ul>

  <h1>게시물 리스드 V4</h1>
  <ul>
    <%
    for(int i = 0; i < articleRows.size(); i++) {
    Map<String, Object> articleRow = articleRows.get(i);
    %>
    <li>
      <%= articleRow.get("id")%>번,
      <%= articleRow.get("regDate")%>,
      <%= articleRow.get("updateDate")%>,
      <%= articleRow.get("title")%>
    </li>
    <% } %>
  </ul>

  <h1>게시물 리스드 V5</h1>
  <ul>
    <%
    for( Map<String, Object> articleRow : articleRows) {
    %>
    <li>
      <%= articleRow.get("id")%>번,
      <%= articleRow.get("regDate")%>,
      <%= articleRow.get("updateDate")%>,
      <%= articleRow.get("title")%>
    </li>
    <% } %>
  </ul>


</body>
</html>