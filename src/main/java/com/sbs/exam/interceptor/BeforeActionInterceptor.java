package com.sbs.exam.interceptor;

import com.sbs.exam.Rq;
import com.sbs.exam.dto.Member;
import com.sbs.exam.util.DBUtil;
import com.sbs.exam.util.SecSql;
import com.sbs.exam.util.Util;
import jakarta.servlet.http.HttpSession;

import java.util.Map;

public class BeforeActionInterceptor extends Interceptor{
  @Override
  public boolean runBeforeAction(Rq rq) {
    // 모든 요청을 들어가기 전에 무조건 해야 하는 일 시작.
    boolean isLogined = false; // 로그인 여부
    int loginedMemberId = 0;
    String loginedMemberName = null;
    Member loginedMember = null;

    String loginedMemberJson = rq.getSessionAttr("loginedMemberJson");

    if (loginedMemberJson != null) { // 데이터가 있다.
      isLogined = true;
      loginedMember = Util.toObjFromJson(loginedMemberJson, Member.class);

      loginedMemberId = loginedMember.getId(); // 로그인 정보를 가져옴.
      loginedMemberName = loginedMember.getName();

    }

    rq.setAttr("isLogined", isLogined);
    rq.setAttr("loginedMember", loginedMember);
    rq.setAttr("loginedMemberId", loginedMemberId);
    rq.setAttr("loginedMemberName", loginedMemberName);
    // 모든 요청을 들어가기 전에 무조건 해야 하는 일 끝

    return true;
  }

}
