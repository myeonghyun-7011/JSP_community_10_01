package com.sbs.exam.service;

import com.sbs.exam.container.Container;
import com.sbs.exam.dto.ResultData;
import com.sbs.exam.repository.ArticleRepository;
import com.sbs.exam.repository.MemeberRepository;

import com.sbs.exam.dto.Member;
import com.sbs.exam.util.Util;

import java.sql.Connection;

public class MemberService {
  private MemeberRepository memeberRepository = Container.memberRepository;

  public ResultData login(String loginId, String loginPw) {
    Member member = getMemberByLoginId(loginId);

    if (member == null) {
      return ResultData.from("F-1", "존재하지 않는 회원의 아이디입니다.");
    }
    if (member.getLoginPw().equals(loginPw) == false) {
      return ResultData.from("F-2", "비밀번호가 일치하지 않습니다.");
    }

    return ResultData.from("S-1", "로그인 되었습니다.", "member", member);
  }

  public ResultData join(String loginId, String loginPw, String name) {
    boolean oldMember = memeberRepository.isJoinDuplicateLoginId(loginId);

    if (oldMember == false) {
      return ResultData.from("F-1", Util.f("%s는 이미 사용중인 로그인아이디입니다.", loginId));
    }

    int id = memeberRepository.join(loginId, loginPw, name);

    return ResultData.from("S-1", "회원 가입에 성공했습니다.", "id", id);
  }

  public Member getMemberByLoginId(String loginId) {
    return memeberRepository.getMemberByLoginId(loginId);
  }
  public Member getMemberByName(String name) {
    return memeberRepository.getMemberByName(name);
  }
}
