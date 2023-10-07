package com.sbs.exam.controller;

import com.sbs.exam.Config;
import com.sbs.exam.Rq;
import com.sbs.exam.dto.Article;
import com.sbs.exam.dto.ResultData;
import com.sbs.exam.service.ArticleService;
import com.sbs.exam.util.DBUtil;
import com.sbs.exam.util.SecSql;
import jakarta.servlet.http.HttpSession;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class ArticleController extends Controller {
  private ArticleService articleService;

  public ArticleController(Connection conn) {
    articleService = new ArticleService(conn);
  }


  @Override // Controller라는 추상매서드를 사용햇기때문에 override를 해야함.
  public void performAction(Rq rq) { // performAction 실행해서.
    switch (rq.getActionMethodName()) { //3.list가 들어오면 actionList 를 싫행시켜줘라.
      case "list":
        actionList(rq);
        break;
      case "write":
        actionWrite(rq);
        break;
      case "doWrite":
        actionDoWrite(rq);
        break;
      case "detail":
        actionDetailList(rq);
        break;
      default:
        rq.println("존재하지 않는 페이지입니다.");
    }
  }

  private void actionDetailList(Rq rq) {
    int id = rq.getIntParam("id" , 0);

    if(id == 0) {
      rq.historyBack("id를 입력해주세요.");
      return;
    }
    Article article = articleService.getForPrintArticleById(id);

    rq.setAttr("article" , article);
    rq.jsp("../article/detail");
  }

  private void actionWrite(Rq rq) {
    rq.jsp("../article/write");
  }

  private void actionDoWrite(Rq rq) {
    HttpSession session = rq.getReq().getSession(); //home서블릿 에 저장


    if (session.getAttribute("loginedMemberId") == null) {
      rq.print("<script>alert('로그인 후 이용해주세요.'); location.replace('../member/login');</script>");
      return;
    }
    String title = rq.getParam("title", "");
    String body = rq.getParam("body", "");
    String redirectUri = rq.getParam("redirectUri","../article/list");
    // redirectUri 요청이 안들어오면 리스트 보여줌.
    int loginedMemberId = (int) session.getAttribute("loginedMemberId");

    if (title.length() == 0) {
      rq.historyBack("title을 입력해주세요");
      return;
    }
    if (body.length() == 0) {
      rq.historyBack("body를 입력해주세요");
      return;
    }
    ResultData writeRd = articleService.write(title, body, loginedMemberId); // ResultData(Dto)  보고서 됏다/안됏다를 담음.
    int id = (int)writeRd.getBody().get("id");
    redirectUri = redirectUri.replace("[NEW_ID]",id + "");

    // rq.printf(writeRd.getMsg());
    rq.replace(writeRd.getMsg(), redirectUri); // 몇번 글이 생성되었다.
  }
  // rq.print("<script>alert('%d번 글이 생성되었습니다.'); location.replace('list');</script>".formatted(id));
  // rq.print(String.format("<script>alert('%d번 글이 생성되었습니다.'); location.replace('list');</script>",id));
  // 글이 생성된후 다시 list로 페이지를 돌려줌. formatted 는 치환문


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
