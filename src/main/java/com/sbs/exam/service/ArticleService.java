package com.sbs.exam.service;

import com.sbs.exam.container.Container;
import com.sbs.exam.dto.Article;
import com.sbs.exam.dto.Member;
import com.sbs.exam.dto.ResultData;
import com.sbs.exam.repository.ArticleRepository;
import com.sbs.exam.util.Util;

import java.util.List;

public class ArticleService {
  private ArticleRepository articleRepository = Container.articleRepository;

  public int getItemsInAPage() {
    return 10; // 보여주는 페이지수
  }

  public int getForPrintListTotalPage() {
    int itemInAPage = getItemsInAPage();
    int totalCount = articleRepository.getTotalCount();

    int totalPage = (int) Math.ceil((double) totalCount / itemInAPage); // 반올림
    return totalPage;
  }

  public List<Article> getForPrintArticles(int page) {
    int itemInAPage = getItemsInAPage();
    int limitFrom = (page - 1) * itemInAPage;

    List<Article> articles = articleRepository.getArticles(itemInAPage, limitFrom);

    return articles;
    //2차원 데이터이기 때문에 list<map>으로 받아옴.
  }

  public ResultData write(String title, String body, int loginedMemberId) {
    int id = articleRepository.write(title, body, loginedMemberId);
    return ResultData.from("S-1", Util.f("%d번 게시물이 생성되었습니다.", id), "id", id);
  }

  public Article getForPrintArticleById(Member member, int id) { // 아이디줄테니 아디줘.
    Article article = articleRepository.getForPrintArticleById(id);

    updateForPrintData(member, article);

    return article;

  }

  private void updateForPrintData(Member actor, Article article) { //actor = 글쓴사람, article=게시물정보 넘김.
    if (actor == null) {
      return;
    }

    boolean actorCanModify = actorCanModifyRd(actor, article).isSuccess();
    // 글작성자가 actor, artice 넘겨주면 수정할수있냐 참 결과를 actorCanModify 넣어줌.
    boolean actorCanDelete = actorCanDeleteRd(actor, article).isSuccess();

    article.setExtra__actorCanModify(actorCanModify);
    article.setExtra__actorCanDelete(actorCanDelete);
  }

  public ResultData delete(int id) {
    articleRepository.delete(id);
    return ResultData.from("S-1", Util.f("%d번 게시물이 삭제되었습니다.", id), "id", id);
  }

  public ResultData modify(int id, String title, String body) {
    articleRepository.modify(id, title, body);

    return ResultData.from("S-1", Util.f("%d번 게시물이 수정되었습니다.", id), "id", id);
  }

  public ResultData actorCanModifyRd(Member member, Article article) {
    int memberId = member.getId();
    int wrteMemberId = article.getMemberId();

    if (memberId != wrteMemberId) {
      return ResultData.from("F-1", "권한이 없습니다.");
    }
    return ResultData.from("S-1", "수정이 가능합니다.");
  }

  public ResultData actorCanDeleteRd(Member member, Article article) {
    int memberId = member.getId();
    int writeMemberId = article.getMemberId();

    if (memberId != writeMemberId) {
      return ResultData.from("F-1", "권한이 없습니다.");
    }
    return ResultData.from("S-1", "삭제가 가능합니다.");
  }

}
