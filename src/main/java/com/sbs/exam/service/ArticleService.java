package com.sbs.exam.service;

import com.sbs.exam.Rq;
import com.sbs.exam.util.DBUtil;
import com.sbs.exam.util.SecSql;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class ArticleService {
  private Rq rq;
  private Connection conn;
  public ArticleService(Connection conn, Rq rq) {
    this.rq = rq;
    this.conn = conn;
  }

  public int getItemsInAPage() {
    return 10; // 보여주는 페이지수
  }

  public int getForPrintListTotalPage() {
    int itemInAPage = getItemsInAPage();

    SecSql sql = SecSql.from("SELECT COUNT(*) AS cnt");
    sql.append("FROM article");

    int totalCount = DBUtil.selectRowIntValue(conn,sql);
    int totalPage = (int) Math.ceil((double) totalCount / itemInAPage );

    return totalPage;
  }

  public List<Map<String, Object>> getForPrintArticleRows(int page) {
    int itemInAPage = getItemsInAPage();
    int limitFrom = (page - 1) * itemInAPage;

    SecSql sql = new SecSql(); //SecSql 위에 생성해서 지워짐.

    sql.append("SELECT *");
    sql.append("FROM article");
    sql.append("ORDER BY id DESC");
    sql.append("LIMIT ?, ?", limitFrom, itemInAPage);

    List<Map<String, Object>> articleRows = DBUtil.selectRows(conn, sql);

    return articleRows;

    //2차원 데이터이기 때문에 list<map>으로 받아옴.

  }
}
