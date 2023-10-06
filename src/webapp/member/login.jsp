<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Map" %>


<!doctype html>
<html lang="ko">
<head>
  <title> 로그인 </title>
</head>
<body>
<h1> 로그인 </h1>
<script>
  let loginForm__submitDone = false;

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

  <div>로그인 아이디 : <input placeholder="아이디를 입력해주세요." name="loginId" type="text"></div>
  <div>로그인 패스워드 : <input  placeholder="비밀번호를 입력해주세요." name="loginPw" type="password"></div>
  <div>
    <button type="submit">로그인</button>
    <button type="button">
      <a href="../member/join">회원가입</a>
    </button>
  </div>
</form>


</body>
</html>