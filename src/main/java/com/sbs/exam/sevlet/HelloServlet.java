package com.sbs.exam.sevlet;

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


    resp.getWriter().append("HI25616516561!!");
  }
}
