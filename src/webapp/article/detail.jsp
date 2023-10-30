<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="pageTitle" value="게시물 상세보기"/>
<%@ include file ="../part/head.jspf" %>


<section class="section-article section-article-detail ">
  <div class="con mx-auto flex-col items-center ">
    <div class="mt-[40px] flex justify-end">
      <a href="list" class="badge badge-secondary hover:underline">리스트로 돌아가기</a>
    </div>
    <div class="article-detail__box w-[1100px] p-[10px]  mt-[10px]">
      <div class="detail__id flex items-center h-[50px] border-b">
				<span class="badge badge-primary">
					번호
				</span>
        <div class=" flex-grow text-center ">
          <span>${article.id}번</span>
        </div>
      </div>
      <div class="detail__regDatge flex items-center h-[50px] border-b">
				<span class="badge badge-primary">
					작성날짜
				</span>
        <div class="flex-grow text-center">
          <span>${article.regDate}</span>
        </div>
      </div>
      <div class="detail__updateDate flex items-center h-[50px] border-b">
				<span class="badge badge-primary">
					수정날짜
				</span>
        <div class="flex-grow text-center">
          <span>${article.updateDate}</span>
        </div>
      </div>
      <div class="detail__title flex items-center h-[50px] border-b">
				<span class="badge badge-primary">
					제목
				</span>
        <div class="flex-grow text-center">
          <span>${article.title}</span>
        </div>
      </div>
      <div class="detail__title flex items-center h-[50px] border-b">
				<span class="badge badge-primary">
					조회수
				</span>
        <div class="flex-grow text-center">
          <span></span>
        </div>
      </div>
      <div class="detail__body flex flex-col h-[300px] mt-[15px]">
				<span class="badge badge-primary">
					내용
				</span>
        <div class="flex-grow mt-[15px] ">
          <span>${article.body}</span>
        </div>
      </div>
    </div>
    <div class="btns flex justify-end mt-[30px]">
      <a href="doDelete?id=${param.id}" class="badge badge-primary badge-outline hover:underline">삭제</a>
      &nbsp;
      &nbsp;
      <a href="modify?id=${param.id}" class="badge badge-primary badge-outline hover:underline">수정</a>
    </div>
  </div>

</section>


<%@ include file ="../part/foot.jspf" %>