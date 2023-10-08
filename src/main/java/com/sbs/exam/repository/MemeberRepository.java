package com.sbs.exam.repository;

import com.sbs.exam.util.DBUtil;
import com.sbs.exam.util.SecSql;

import com.sbs.exam.dto.Member;
import java.sql.Connection;
import java.util.Map;

public class MemeberRepository {
  private Connection conn;
  public MemeberRepository(Connection conn) {
    this.conn = conn;
  }

  public Member getMemberByLoginId(String loginId) {
    SecSql sql = SecSql.from("SELECT * ");
    sql.append("FROM member");
    sql.append("WHERE loginId = ? ", loginId);

    return new Member (DBUtil.selectRow(conn, sql));

  }
}
