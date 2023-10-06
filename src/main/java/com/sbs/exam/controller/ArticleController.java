package com.sbs.exam.controller;

import com.sbs.exam.Config;
import com.sbs.exam.Rq;
import com.sbs.exam.dto.Article;
import com.sbs.exam.service.ArticleService;
import com.sbs.exam.util.DBUtil;
import com.sbs.exam.util.SecSql;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class ArticleController extends Controller {
  private ArticleService articleService;
  public ArticleController(Connection conn) {
    articleService = new ArticleService(conn);
  }


  @Override // Controller라는 추상매서드를 사용햇기때문에 override를 해야함.
  public void performAction(Rq rq){ // performAction 실행해서.
    switch (rq.getActionMethodName()){ //3.list가 들어오면 actionList 를 싫행시켜줘라.
      case "list":
        actionList(rq);
        break;
    }
  }
  public void actionList(Rq rq) {
    // 공식임 page창  외워두면 편함.
    int page = rq.getIntParam("page", 1); //페이지 가져오고
    int totalPage = articleService.getForPrintListTotalPage(); // 토탈페이지 구해줌

    List<Article> articles = articleService.getForPrintArticles(page);
    //2차원 데이터이기 때문에 list<map>으로 받아옴.

    rq.getReq().setAttribute("articles", articles);
    rq.getReq().setAttribute("page", page);
    rq.getReq().setAttribute("totalPage", totalPage);

    rq.jsp("../article/list");


  }
}
