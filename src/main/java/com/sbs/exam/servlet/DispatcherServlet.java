package com.sbs.exam.servlet;

import com.sbs.exam.Config;
import com.sbs.exam.Rq;
import com.sbs.exam.container.Container;
import com.sbs.exam.controller.ArticleController;
import com.sbs.exam.controller.MemberController;
import com.sbs.exam.exception.SQLErrorException;
import com.sbs.exam.util.DBUtil;
import com.sbs.exam.util.SecSql;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@WebServlet("/usr/*")
public class DispatcherServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    new Container().init(); // 객체를 만들고 초기화를 시킴
    Rq rq = new Rq(req, resp);

    // DB 연결시작
    Connection conn = null;
    String driverName = Config.getDriverClassName();
    try {
      Class.forName(driverName);
    } catch (ClassNotFoundException e) {
      System.out.printf("[ClassNotFoundException 예외, %s]", e.getMessage());
      System.out.println("DB 드라이버 클래스 로딩 실패");
      return;
    }

    try {
      conn = DriverManager.getConnection(Config.getDBUrl(), Config.getDBId(), Config.getDBPw()); // 정보들이 들어옴.
      Container.conn = conn; // 들어온 정보들을 다시 연결해줌.

      // 모든 요청을 들어가기 전에 무조건 해야 하는 일 시작.
      HttpSession session = req.getSession();

      boolean isLogined = false;
      int loginedMemberId = -1; // 없다.
      Map<String , Object> loginedMemberRow = null;

      if (session.getAttribute("loginedMemberId") != null) {
        loginedMemberId = (int) session.getAttribute("loginedMemberId");
        isLogined = true;

        SecSql sql = SecSql.from("SELECT * FROM member");
        sql.append("WHERE id = ? ", loginedMemberId);
        loginedMemberRow = DBUtil.selectRow(conn, sql);
      }
      req.setAttribute("isLogined", isLogined);
      req.setAttribute("loginedMemberId", loginedMemberId);
      req.setAttribute("loginedMemberRow", loginedMemberRow);
      // 모든 요청을 들어가기 전에 무조건 해야 하는 일 끝

      switch (rq.getControllerTypeName()) { //1. TpyName = usr 가리킴.
        case "usr" :
          switch (rq.getControllerName()){ // 2. 이게 article이라면
            case "article" :
              Container.articleController.performAction(rq); //performAction를 실행시켜줘라.
              break;
            case "member" :
              Container.memberController.performAction(rq); //performAction를 실행시켜줘라.
              break;
          }
      }

    } catch (SQLException e) {
      e.printStackTrace();
    } catch (SQLErrorException e){
      e.getOrigin().printStackTrace();
    }
    finally {
      try {
        if (conn != null && !conn.isClosed()) {
          conn.close();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    // DB 연결 끝
  }
  @Override // write.jsp.에서 post된걸 날려줘야함.
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    doGet(req, resp);
  }
}