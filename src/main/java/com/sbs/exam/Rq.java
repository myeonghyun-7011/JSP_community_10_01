package com.sbs.exam;

import com.sbs.exam.util.Util;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class Rq {
  private HttpServletRequest req;
  private HttpServletResponse resp;
  private String controllerTypeName;
  private String controllerName;
  private String actionMethodName;
  boolean isInvalid = false;


  public Rq(HttpServletRequest req, HttpServletResponse resp) {
    this.req = req;
    this.resp = resp;

    try {
      req.setCharacterEncoding("UTF-8"); // 들어오는 데이터를 UTF-8로 해석하겠다.
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
    resp.setCharacterEncoding("UTF-8"); // 완성되는 HTML의 인코딩을 UTF-8로 하겠다.
    resp.setContentType("text/html; charset-utf-8"); // 브라우저에게 우리가 만든 결과물이 UTF-8이라고 알리는 의미.

    String requestUri = req.getRequestURI(); // "/s/article/list" 로 들어오는데 이걸 쪼갬
    String[] requestUriBits = requestUri.split("/"); // 배열이기 때문에 String[] 으로.
    // ""/s/article/list
    // [0][1][2][3]

    int minBitsCount = 4;

    if (requestUriBits.length < minBitsCount) {
      isInvalid = true;
      return;
    }

    this.controllerTypeName = requestUriBits[1];
    this.controllerName = requestUriBits[2]; // controllerName 은 article 이냐  member인지 물어봄.
    this.actionMethodName = requestUriBits[3]; // actionMethodName 는 list인지 write 인지 detail or modify;


  }

  public HttpServletRequest getReq() {
    return req;
  }

  public int getIntParam(String paramName, int defaultValue) {
    String value = req.getParameter(paramName);

    if (value == null) {
      return defaultValue;
    }
// 위에가 실행해서 오류시 예외처리
    try {
      return Integer.parseInt(value);
    } catch (NumberFormatException e) {
      return defaultValue;
    }
  }

  public String getParam(String paramName, String defaultvalue) {
    String value = req.getParameter(paramName);

    if (value == null) {
      return defaultvalue;
    }
    return value;
  }

  public void jsp(String jspPath) { // 경로 줄여줌 코드 줄여줌.
    RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/" + jspPath + ".jsp");

    try {
      requestDispatcher.forward(req, resp);
    } catch (ServletException | IOException e) {
      e.printStackTrace(); // 오류 생겻을시 방지하는거라느데 / 시간되면 찾아보기
    }
  }

  public String getControllerTypeName() {
    return controllerTypeName;
  }

  public String getControllerName() {
    return controllerName;
  }

  public String getActionMethodName() {
    return actionMethodName;
  }
  public void print(String str) {
    try {
      resp.getWriter().append(str); //getWriter 오류 수정 try catch
    } catch (IOException e) {
      throw new RuntimeException(e); // 문제가 발생해도 지나가게끔.
    }
  }
  public void println(String str){
    print(str + "\n");
  }

  public void printf(String format, Object... args) {
    print(Util.f(format, args));
  }

  public void historyBack(String msg) {
    println("<script>");
    printf("alert('%s'); \n", msg);
    println("history.back();");
    println("</script>");
  }
  public void replace(String msg, String redirectUri){
    println("<script>");
    printf("alert('%s'); \n", msg);
    printf("location.replace('%s');\n", redirectUri); // back 햇을 경우 다시 dowrite로 돌아오지 않게끔.
    println("</script>");

  }
}

