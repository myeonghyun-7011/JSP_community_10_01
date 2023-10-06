package com.sbs.exam.servlet;

import com.sbs.exam.Config;
import com.sbs.exam.Rq;
import com.sbs.exam.controller.ArticleController;
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

@WebServlet("/s/*")
public class DispatcherServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    Rq rq = new Rq(req, resp);

    String requestUri = req.getRequestURI(); // "/s/article/list" 로 들어오는데 이걸 쪼갬
    String[] requestUriBits =  requestUri.split("/"); // 배열이기 때문에 String[] 으로.
    // ""/s/article/list
    // [0][1][2][3]

    if(requestUriBits.length < 4){
      rq.appendBody("올바른 요청이 아닙니다.");
      return;
    }

    String controllerName = requestUriBits[2]; // controllerName 은 article 이냐  member인지 물어봄.
    String actionMethodName =  requestUriBits[3]; // actionMethodName 는 list인지 write 인지 detail or modify;


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
      conn = DriverManager.getConnection(Config.getDBUrl(), Config.getDBId(), Config.getDBPw());

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

      if(controllerName.equals("article")) {   //controllerName이 article을 만낫다면
        ArticleController articleController = new ArticleController(conn, rq);

        if(actionMethodName.equals("list")){
          articleController.actionList(rq);
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