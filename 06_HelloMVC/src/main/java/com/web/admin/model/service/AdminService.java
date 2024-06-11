package com.web.admin.model.service;

import java.sql.Connection;
import java.util.List;

import com.web.admin.model.dao.AdminDao;
import com.web.member.model.dto.Member;
import static com.web.common.JDBCTemplate.getConnection;
import static com.web.common.JDBCTemplate.close;

public class AdminService {
	
	private AdminDao dao = new AdminDao();
	public List<Member> selectMemberAll(int cPage, int numPerpage){
		
		Connection conn = getConnection();
		List<Member> members = dao.selectMemberAll(conn, cPage, numPerpage);
		close(conn);
		return members;
	}

	public int selectMemberAllCount() {
		Connection conn = getConnection();
		int result = dao.selectMemberAllCount(conn);
		close(conn);
		return result;
	}
	
	public List<Member> searchMember(int cPage, int numPerpage, String type, String keyword) {
		Connection conn = getConnection();
		List<Member> result = dao.searchMember(conn, cPage, numPerpage, type, keyword);
		close(conn);
		return result;
	}
	
	public int searchMemberCount(String type, String keyword) {
		Connection conn = getConnection();
		int count = dao.searchMemberCount(conn, type, keyword);
		close(conn);
		return count;
	}

	public Member searchMemberById(String userId) {
		Connection conn = getConnection();
		Member m = dao.searchMemberById(conn, userId);
		close(conn);
		return m;
	}
}
