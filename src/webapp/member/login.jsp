<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="pageTitle" value="로그인"/>
<%@ include file ="../part/head.jspf" %>
<body>
<script>
  let LoginForm__submitDone = false;

  function LoginForm__submit(form) {
    if(LoginForm__submitDone) {
      alert('로그인중입니다.');
      return;
    }

    form.loginId.value = form.loginId.value.trim();

    if(form.loginId.value.length == 0) {
      alert('로그인 아이디를 입력해주세요.');
      form.loginId.focus();
      return;
      }

    form.loginPw.value = form.loginPw.value.trim();

    if(form.loginPw.value.length == 0) {
      alert('로그인 비밀번호를 입력해주세요.');
      form.loginPw.focus();
      return;
      }

      form.submit();
      LoginForm__submitDone = true;
   }
</script>

<form action="doLogin" method="POST" onsubmit="LoginForm__submit(this); return false">

  <div>로그인 아이디 : <input placeholder="아이디를 입력해주세요." name="loginId" type="text" value="${param.loginId}"></div>
  <div>로그인 패스워드 : <input placeholder="비밀번호를 입력해주세요." name="loginPw" type="password"></div>
  <div class="btns">
    <button type="submit">로그인</button>
    &nbsp
    <a href="../member/findLoginId">아이디찾기</a>
    &nbsp
    <a href="../member/join">회원가입</a>
    </button>
  </div>
</form>


<%@ include file ="../part/foot.jspf" %>