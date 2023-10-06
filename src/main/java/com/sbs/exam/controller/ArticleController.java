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

public class ArticleController {
  private ArticleService articleService;
  public ArticleController(Connection conn) {
    articleService = new ArticleService(conn);
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
