<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Map" %>
<%@ page import = "com.sbs.exam.dto.Article" %>


<%
  Article article = (Article) request.getAttribute("article");
%>


<!doctype html>
<html lang="ko">
<head>
  <title>게시물 상세보기</title>
</head>
<body>
  <h1>게시물 상세보기</h1>

  <%@ include file ="../part/topBar.jspf" %>

  <table border="1" style="text-align : center">
    <colgroup>
      <col width="100">
      <col>
      <col>
      <col width="100">
      <col width="100">
    </colgroup>
    <thead>
    <tr>
      <th>번호</th>
      <th>작성날짜</th>
      <th>수정날짜</th>
      <th>제목</th>
      <th>내용</th>
      <th>비고</th>
    </tr>
    </thead>

    <tbody>
    <tr>
      <td> <%= article.id %>번</td>
      <td> <%= article.regDate %></td>
      <td><%= article.updateDate %></td>
      <td> <%= article.title %></td>
      <td><%= article.body %></td>
      <td>
        <a href="doDelete?id=&{param.id}">삭제</a>
        <a href="modify?id=&{param.id}">수정</a>
      </td>
    </tr>
    </tbody>

  </table>

  <div>
    <a href="list">리스트로 돌아가기</a>
  </div>

</body>
</html>