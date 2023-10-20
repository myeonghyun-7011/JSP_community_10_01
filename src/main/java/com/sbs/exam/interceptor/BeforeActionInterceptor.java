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
    String loginedMemberJson = rq.getSessionAttr("loginedMemberJson");

    if (loginedMemberJson != null) { // 데이터가 있다.
      rq.setLogined(true);
      rq.setLoginedMember(Util.toObjFromJson(loginedMemberJson, Member.class));
      rq.setLoginedMemberId(rq.getLoginedMember().getId());
      rq.setLoginedMemberName(rq.getLoginedMember().getName());
    }
    rq.setAttr("rq", rq);
    // 모든 요청을 들어가기 전에 무조건 해야 하는 일 끝.
    return true;
  }

}
