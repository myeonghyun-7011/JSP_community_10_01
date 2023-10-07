package com.sbs.exam.servlet;

import com.sbs.exam.Config;
import com.sbs.exam.Rq;
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
import java.util.Map;

@WebServlet("/article/detail")
public class ArticleDetailServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    Rq rq = new Rq(req,resp);

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

      // 공통 상단 정보 시작
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
      // 공통 상단 정보 끝


      int id = rq.getIntParam("id" , 0);

      if(id == 0) {
        rq.print("%d번 게시물은 없습니다.".formatted(id));
      }

      SecSql sql = new SecSql();
      sql.append("SELECT *");
      sql.append("FROM article");
      sql.append("WHERE id = ?", id);

     Map<String, Object> articleRow = DBUtil.selectRow(conn, sql);
      //2차원 데이터이기 때문에 list<map>으로 받아옴.

      req.setAttribute("articleRow" , articleRow);
      rq.jsp("../article/detail");

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