package com.web.admin.model.dao;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.web.common.AESEncryptor;
import com.web.member.model.dao.MemberDao;
import com.web.member.model.dto.Member;
import static com.web.common.JDBCTemplate.close;

public class AdminDao {

	private Properties sql = new Properties();
	
	{
		String path = AdminDao.class.getResource("/sql/admin/sql_admin.properties").getPath();
		try (FileReader fr = new FileReader(path)){
			sql.load(fr);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public List<Member> selectMemberAll(Connection conn, int cPage, int numPerpage){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Member> members = new ArrayList<Member>();
		try {
			pstmt = conn.prepareStatement(sql.getProperty("selectMemberAll"));
			pstmt.setInt(1, (cPage - 1) * numPerpage + 1);
			pstmt.setInt(2, cPage * numPerpage);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				members.add(MemberDao.getMember(rs));
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return members;
	}
	
	public int selectMemberAllCount(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		
		try {
			pstmt = conn.prepareStatement(sql.getProperty("selectMemberAllCount"));
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}
	public List<Member> searchMember(Connection conn, int cPage, int numPerpage, String type, String keyword){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Member> result = new ArrayList<>();
		
		try {
			String sql = this.sql.getProperty("selectSearchMember");
			sql = sql.replace("#COL", type); // -> 문자열을 파싱
			
			//조건절 통째로 문자열로 설정해서 대치해서 해보기 
			
			pstmt = conn.prepareStatement(sql);
//			pstmt.setString(1, keyword);
			
			//이름은 % 붙여줘야 함 -> 분기처리
			pstmt.setString(1, type.equals("userName")? "%" + keyword + "%" : keyword);
			pstmt.setInt(2, (cPage - 1) * numPerpage + 1);
			pstmt.setInt(3, cPage * numPerpage);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				result.add(MemberDao.getMember(rs));
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}
	
	public int searchMemberCount(Connection conn, String type, String keyword) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		String sql = this.sql.getProperty("searchMemberCount");
		sql = sql.replace("#COL", type);
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, type.equals("userName") ? "%" + keyword + "%" : keyword);
			rs = pstmt.executeQuery();
			
			if(rs.next()) result = rs.getInt(1);
				
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}

	public Member searchMemberById(Connection conn, String userId) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Member m = null;
		
		try {
			pstmt = conn.prepareStatement(sql.getProperty("searchMemberById"));
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			if(rs.next()) m = getMember(rs);
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return m;
	}
	
	public static Member getMember(ResultSet rs) throws SQLException{
		String hobby=rs.getString("hobby");
		Date enrollDate=rs.getDate("enrolldate");
		
		
		String email, phone;
		//암호화된 email, address 복호화하기 (try-catch문으로 분기처리 - if-else문 처럼)
		//암호화 되어 있으면 try문에 걸리고 아니면 catch문(원본값 그대로 가져옴)
		try {
			email = AESEncryptor.decryptData(rs.getString("email"));
		}catch (Exception e) {
			email = rs.getString("email");
		}
		try {
			phone = AESEncryptor.decryptData(rs.getString("phone"));
		}catch(Exception e) {
			phone = rs.getString("phone");
		}
		
		
		return Member.builder()
				.userId(rs.getString("userid"))
				.password(rs.getString("password"))
				.userName(rs.getString("username"))
				.gender(rs.getString("gender"))
				.age(rs.getInt("age"))
				.email(email)
				.phone(phone)
				.address(rs.getString("address"))
				.hobby(hobby!=null?hobby.split(","):null)
				.enrollDate(enrollDate!=null?enrollDate.toLocalDate()
							:null)
				.build();
	}
	
	
	
	
	
	
	
	
	
	
}
