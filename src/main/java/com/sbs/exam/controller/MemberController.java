package com.sbs.exam.controller;

import com.sbs.exam.Rq;
import com.sbs.exam.container.Container;
import com.sbs.exam.dto.Member;
import com.sbs.exam.dto.ResultData;
import com.sbs.exam.service.ArticleService;
import com.sbs.exam.service.MemberService;
import com.sbs.exam.util.DBUtil;
import com.sbs.exam.util.SecSql;
import com.sbs.exam.util.Util;
import jakarta.servlet.http.HttpSession;

import java.sql.Connection;
import java.util.Map;

public class MemberController extends Controller {
  private MemberService memberService = Container.memberService;

  @Override // Controller라는 추상매서드를 사용햇기때문에 override를 해야함.
  public void performAction(Rq rq) { // performAction 실행해서.
    switch (rq.getActionMethodName()) { //3.list가 들어오면 actionList 를 싫행시켜줘라.
      case "login":
        actionShowLogin(rq);
        break;
      case "doLogin":
        actionDoLogin(rq);
        break;
      default:
        rq.println("존재하지 않는 페이지입니다.");

    }
  }

  private void actionDoLogin(Rq rq) {
    String loginId = rq.getParam("loginId", "");
    String loginPw = rq.getParam("loginPw", "");

    if (loginId.length() == 0) {
      rq.historyBack("로그인 아이디를 입력해주세요.");
    }

    if (loginPw.length() == 0) {
      rq.historyBack("로그인 비밀번호를 입력해주세요.");
    }

    ResultData loginRd = memberService.login(loginId, loginPw);

    if (loginRd.isFail()) {
      rq.historyBack(loginRd.getMsg());
    } // 실패 했을때 실패 msg 출력

    Member member = (Member) loginRd.getBody().get("member");

    rq.setSessionAttr("loginedMemberJson", Util.toJson(member,""));

    rq.replace(loginRd.getMsg(), "../article/list"); // 성공메세지 출력

  }

  private void actionShowLogin(Rq rq) {
    rq.jsp("../member/login");
  }
}

