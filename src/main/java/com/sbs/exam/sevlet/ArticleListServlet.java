package com.sbs.exam.sevlet;

import com.sbs.exam.util.DBUtil;
import com.sbs.exam.util.SecSql;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@WebServlet("/article/list")
public class ArticleListServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    // DB 연결시작
    Connection conn = null;
    try {
      Class.forName("com.mysql.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      System.out.printf("[ClassNotFoundException 예외, %s]", e.getMessage());
      System.out.println("DB 드라이버 클래스 로딩 실패");
      return;
    }

    String url = "jdbc:mysql://127.0.0.1:3306/JSP_Community?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";
    String user = "sbsst";
    String password = "sbs123414";

    try {
      conn = DriverManager.getConnection(url, user, password);
      DBUtil dbUtil = new DBUtil();

      SecSql sql = new SecSql();
      sql.append("SELECT *");
      sql.append("FROM article");

      List<Map<String, Object>> articleRows = dbUtil.selectRows(conn, sql);

    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
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
}