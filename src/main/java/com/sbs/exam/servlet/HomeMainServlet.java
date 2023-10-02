package com.sbs.exam.servlet;

import com.sbs.exam.Rq;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet ("/home/main")
public class HomeMainServlet extends HttpServlet { // HttpServlet 상속받아와야함.

  @Override //alt + ins => ovrride doget클릭
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    Rq rq = new Rq(req, resp);

    req.getRequestDispatcher("../home/main.jsp").forward(req, resp);
  }
}
