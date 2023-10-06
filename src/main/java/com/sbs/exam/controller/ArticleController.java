package com.sbs.exam.controller;

import com.sbs.exam.Config;
import com.sbs.exam.Rq;
import com.sbs.exam.util.DBUtil;
import com.sbs.exam.util.SecSql;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class ArticleController {
  private Connection conn;
  private Rq rq;

  public ArticleController(Connection conn, Rq rq) {
    this.conn = conn;
    this.rq = rq;
  }

  public void actionList() {
    // 공식임 page창  외워두면 편함.
    int page = rq.getIntParam("page", 1);
    int itemInAPage = 10;
    int limitFrom = (page - 1) * itemInAPage;

    SecSql sql = SecSql.from("SELECT COUNT(*) AS cnt");
    sql.append("FROM article");

    int totalCount = DBUtil.selectRowIntValue(conn,sql);;
    int totalPage = (int) Math.ceil((double) totalCount / itemInAPage); //반올림 하는방법

    sql = new SecSql(); //SecSql 위에 생성해서 지워짐.

    sql.append("SELECT *");
    sql.append("FROM article");
    sql.append("ORDER BY id DESC");
    sql.append("LIMIT ?, ?", limitFrom, itemInAPage);

    List<Map<String, Object>> articleRows = DBUtil.selectRows(conn, sql);
    //2차원 데이터이기 때문에 list<map>으로 받아옴.

    rq.getReq().setAttribute("articleRows", articleRows);
    rq.getReq().setAttribute("page", page);
    rq.getReq().setAttribute("totalPage", totalPage);

    rq.jsp("../article/list");


  }
}
