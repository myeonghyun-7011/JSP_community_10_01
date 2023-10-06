package com.sbs.exam.service;

import com.sbs.exam.Rq;
import com.sbs.exam.dto.Article;
import com.sbs.exam.repository.ArticleRepository;
import com.sbs.exam.util.DBUtil;
import com.sbs.exam.util.SecSql;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class ArticleService {
  private ArticleRepository articleRepository;
  public ArticleService(Connection conn) {
    articleRepository = new ArticleRepository(conn);
  }
  public int getItemsInAPage() {
    return 10; // 보여주는 페이지수
  }

  public int getForPrintListTotalPage() {
    int itemInAPage = getItemsInAPage();
    int totalCount = articleRepository.getTotalCount();

    int totalPage = (int) Math.ceil((double) totalCount / itemInAPage ); // 반올림
    return totalPage;
  }
  public List<Article> getForPrintArticles(int page) {
    int itemInAPage = getItemsInAPage();
    int limitFrom = (page - 1) * itemInAPage;

    List<Article> articles = articleRepository.getArticles(itemInAPage, limitFrom );

    return articles;
    //2차원 데이터이기 때문에 list<map>으로 받아옴.
  }
}
