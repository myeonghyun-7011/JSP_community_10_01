package com.sbs.exam.interceptor;

import com.sbs.exam.Rq;

public class NeedLoginInterceptor extends Interceptor {


  // 로그인이 필요한 작업들
  @Override
  public boolean runBeforeAction(Rq rq) {

    switch (rq.getActionPath()) {
      case "/usr/article/write":
      case "/usr/article/modify":
      case "/usr/article/doModify":
      case "/usr/article/delete":
      case "/usr/article/doDelete":
      case "/usr/member/doLogout":
      case "/usr/member/myPage":

        if (rq.isNotLogined()) {
          rq.historyBack("로그인 후 이용해주세요.");
          return false;
        }
    }
    return true;
  }
}
