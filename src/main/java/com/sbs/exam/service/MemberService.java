package com.sbs.exam.service;

import com.sbs.exam.dto.ResultData;
import com.sbs.exam.repository.ArticleRepository;
import com.sbs.exam.repository.MemeberRepository;

import com.sbs.exam.dto.Member;
import java.sql.Connection;

public class MemberService {
  private MemeberRepository memeberRepository;
  public MemberService(Connection conn) {
    memeberRepository = new MemeberRepository(conn);
  }

  public ResultData login(String loginId, String loginPw) {
    Member member = memeberRepository.getMemberByLoginId(loginId);

    if (member == null){
      return ResultData.from("F-1", "존재하지 않는 회원의 아이디입니다.");
    }
    if (member.getLoginPw().equals(loginPw) == false){
      return ResultData.from("F-2", "비밀번호가 일치하지 않습니다.");
    }

    return ResultData.from("S-1","로그인 되었습니다.","member",member);


  }
}
