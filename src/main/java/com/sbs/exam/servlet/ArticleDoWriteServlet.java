package com.sbs.exam.servlet;

import com.sbs.exam.Rq;
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

@WebServlet("/article/doWrite")
public class ArticleDoWriteServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    Rq rq = new Rq(req,resp);

    // DB 연결시작
    Connection conn = null;
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
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

      String title = rq.getParam("title", "");
//      if(title == "" || title.trim().length() == 0){
//        System.out.println("제목을 입력해주세요.");
//      }

      String body = rq.getParam("body", "");


      SecSql sql = SecSql.from("INSERT INTO article");
      sql.append("SET regDate = NOW()");
      sql.append(", updateDate = NOW()");
      sql.append(", title = ?" , title);
      sql.append(", body = ?" , body);

      int id = DBUtil.insert(conn, sql);

      rq.appendBody("<script>alert('%d번 글이 생성되었습니다.'); location.replace('list');</script>".formatted(id));
      // rq.appendBody(String.format("<script>alert('%d번 글이 생성되었습니다.'); location.replace('list');</script>",id));
      // 글이 생성된후 다시 list로 페이지를 돌려줌. formatted 는 치환문
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