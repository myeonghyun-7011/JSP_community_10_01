package org.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet ("/hello")
public class HelloServlet extends HttpServlet { // HttpServlet 상속받아와야함.

  @Override //alt + ins => ovrride doget클릭
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8"); // 들어오는 데이터를 UTF-8로 해석하겠다.
    resp.setCharacterEncoding("UTF-8"); // 완성되는 HTML의 인코딩을 UTF-8로 하겠다.
    resp.setContentType("text/html; charset-utf-8"); // 브라우저에게 우리가 만든 결과물이 UTF-8이라고 알리는 의미.

    resp.getWriter().append("HI25616516561!!");
  }
}