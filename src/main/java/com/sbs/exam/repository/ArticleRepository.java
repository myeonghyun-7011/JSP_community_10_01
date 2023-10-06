package com.sbs.exam.repository;

import com.sbs.exam.Rq;
import com.sbs.exam.util.DBUtil;
import com.sbs.exam.util.SecSql;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class ArticleRepository {
  private Connection conn;
  public ArticleRepository(Connection conn) {
    this.conn = conn;

  }

  public int getTotalCount() {
    SecSql sql = SecSql.from("SELECT COUNT(*) AS cnt");
    sql.append("FROM article");

    int totalCount = DBUtil.selectRowIntValue(conn, sql);
    return totalCount;
  }

  public List<Map<String, Object>> getArticleRows(int itemInAPage, int limitFrom) {
    SecSql sql = new SecSql(); //SecSql 위에 생성해서 지워짐.

    sql.append("SELECT *");
    sql.append("FROM article");
    sql.append("ORDER BY id DESC");
    sql.append("LIMIT ?, ?", limitFrom, itemInAPage);

    List<Map<String, Object>> articleRows = DBUtil.selectRows(conn, sql);
    return articleRows;
  }
}
