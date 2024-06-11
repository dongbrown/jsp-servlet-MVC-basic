package com.web.member.model.service;

import java.sql.Connection;

import com.web.member.model.dao.MemberDao;
import com.web.member.model.dto.Member;
import static com.web.common.JDBCTemplate.getConnection;
import static com.web.common.JDBCTemplate.close;
import static com.web.common.JDBCTemplate.rollback;
import static com.web.common.JDBCTemplate.commit;

public class MemberSerivce {
	private MemberDao dao=new MemberDao();
	
	public Member selectMemberById(String userId,String password) {
		Connection conn=getConnection();
		Member m=dao.selectMemberById(conn, userId);

		//컨트롤러 or 서비스 둘 중 하나에 넣으면 됨
		if(m==null||!m.getPassword().equals(password)) m=null;
		close(conn);
		return m;
	}
	
	//아이디 중복확인에 사용
	public Member selectMemberById(String userId) {
		Connection conn=getConnection();
		Member m=dao.selectMemberById(conn, userId);
		close(conn);
		return m;
	}

	public int insertMember(Member m) {
		Connection conn = getConnection();
		int result = dao.insertMember(conn, m);
		if(result > 0) commit(conn);
		else rollback(conn);
		close(conn);
		
		return result;
	}
	
}
