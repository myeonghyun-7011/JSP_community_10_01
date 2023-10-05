package com.sbs.exam.servlet;

import com.sbs.exam.Rq;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/home/main")
public class HomeMainServlet extends HttpServlet { // HttpServlet 상속받아와야함.

  @Override //alt + ins => ovrride doget클릭
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    Rq rq = new Rq(req, resp);
    HttpSession session = req.getSession();

    boolean isLogined = false;
    int loginedMemberId = -1; // 없다.

    if (session.getAttribute("loginedMemberId") != null) {
      loginedMemberId = (int) session.getAttribute("loginedMemberId");
      isLogined = true;
    }
    req.setAttribute("isLogined", isLogined);
    req.setAttribute("loginedMemberId", loginedMemberId);

    rq.jsp("../home/main");
  }

  @Override // write.jsp.에서 post된걸 날려줘야함.
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    doGet(req, resp);
  }
}
