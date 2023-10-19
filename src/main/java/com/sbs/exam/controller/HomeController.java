package com.sbs.exam.controller;

import com.sbs.exam.Rq;

public class HomeController extends Controller{
  @Override
  public void performAction(Rq rq) {
    switch (rq.getActionMethodName()) { //3.list가 들어오면 actionList 를 싫행시켜줘라.
      case "main":
        actionShowHome(rq);
        break;
    }
  }

  private void actionShowHome(Rq rq) {
    rq.jsp("../home/main");
  }
}
