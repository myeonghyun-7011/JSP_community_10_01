package com.sbs.exam.container;

import com.sbs.exam.controller.ArticleController;
import com.sbs.exam.controller.MemberController;
import com.sbs.exam.repository.ArticleRepository;
import com.sbs.exam.repository.MemeberRepository;
import com.sbs.exam.service.ArticleService;
import com.sbs.exam.service.MemberService;

import java.sql.Connection;

public class Container {
  public static ArticleRepository articleRepository;
  public static MemeberRepository memberRepository;

  public static ArticleService articleService;
  public static MemberService memberService;

  public static ArticleController articleController ; // ArticleController 객체를 생성해서.
   public static MemberController memberController ; // MemberController 객체를 생성해서.

  public static Connection conn;

  public static void init() { // init 초기화
    articleRepository = new ArticleRepository();
    memberRepository = new MemeberRepository();

    articleService = new ArticleService();
    memberService = new MemberService();

    articleController = new ArticleController();
    memberController = new MemberController();
  }
}
