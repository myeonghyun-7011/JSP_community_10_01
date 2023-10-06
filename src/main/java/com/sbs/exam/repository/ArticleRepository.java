package com.sbs.exam.repository;

import com.sbs.exam.Rq;
import com.sbs.exam.dto.Article;
import com.sbs.exam.util.DBUtil;
import com.sbs.exam.util.SecSql;

import java.sql.Connection;
import java.util.ArrayList;
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
  public List<Article> getArticles(int itemInAPage, int limitFrom) { // Article은 dto에 있는 Map을 가져다씀.
    SecSql sql = new SecSql(); //SecSql 위에 생성해서 지워짐.

    sql.append("SELECT *");
    sql.append("FROM article");
    sql.append("ORDER BY id DESC");
    sql.append("LIMIT ?, ?", limitFrom, itemInAPage);

    List<Map<String, Object>> articleRows = DBUtil.selectRows(conn, sql);

    List<Article> articles = new ArrayList<>();

    for(Map<String , Object> articleRow : articleRows) {
      articles.add(new Article(articleRow));
    }

    return articles;
  }
}
