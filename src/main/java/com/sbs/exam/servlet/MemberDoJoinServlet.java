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

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebServlet("/member/doJoin")
public class MemberDoJoinServlet extends HttpServlet {
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

      String loginId = rq.getParam("loginId", "");
      String loginPw = rq.getParam("loginPw", "");
      String name = rq.getParam("name", "");

      // true = 1 / false = 0 으로 출력함.
      SecSql sql = SecSql.from("SELECT COUNT(*) AS cnt");
      sql.append("FROM member");
      sql.append("WHERE loginId = ? ", loginId);

      boolean isJoinDuplicateLoginId = DBUtil.selectRowIntValue(conn, sql) == 0;

      // 아이디 중복체크
      if(isJoinDuplicateLoginId == false) {
        rq.appendBody("<script>alert('%s (은)는 이미 사용중인 아이디입니다.'); history.back(); </script>".formatted(loginId));
        return;
      } // history.back() 은 "이미사용중 아이디"라고 띄워주고 그 화면으로 남겨주기 위함.

      sql = SecSql.from("INSERT INTO member");
      sql.append("SET regDate = NOW()");
      sql.append(", updateDate = NOW()");
      sql.append(", loginId = ?" , loginId);
      sql.append(", loginPw = ?" , loginPw);
      sql.append(", name = ?" , name);

      int id = DBUtil.insert(conn, sql);

      rq.appendBody("<script>alert('%d번 회원이 생성되었습니다.'); location.replace('../home/main');</script>".formatted(id));
      // rq.appendBody(String.format("<script>alert('%d번 글이 생성되었습니다.'); location.replace('list');</script>",id));
      // 글이 생성된후 다시 list로 페이지를 돌려줌. formatted 는 치환문
    } catch (SQLException e) {
      e.printStackTrace();
    }  catch (SQLErrorException e){
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