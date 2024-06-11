package com.web.board.model.dao;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.web.board.model.dto.Board;
import com.web.board.model.dto.BoardComment;
import com.web.notice.model.dao.NoticeDao;

import static com.web.common.JDBCTemplate.close;



public class BoardDao {
	
	private Properties sql = new Properties();
	
	{
		String path = BoardDao.class.getResource("/sql/board/sql_board.properties").getPath();
		try (FileReader fr = new FileReader(path)){ //스트림 열기
			sql.load(fr);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<Board> selectBoardAll(Connection conn, int cPage, int numPerpage){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Board> result = new ArrayList<Board>();
		
		try {
			pstmt = conn.prepareStatement(sql.getProperty("selectBoardAll"));
			pstmt.setInt(1, (cPage - 1) * numPerpage + 1);
			pstmt.setInt(2, cPage * numPerpage);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
//				Board b = getBoard(rs);
//				do {
//					b.getComments().add(getBoardComment(rs));
//				}while(rs.next());
//				result.add(b);
				getBoardAndComment(result, rs);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}
	 
	public static Board getBoard(ResultSet rs) throws SQLException {
		return Board.builder()
				.boardNo(rs.getInt("board_no"))
				.boardTitle(rs.getString("board_title"))
				.boardWriter(rs.getString("board_writer"))
				.boardContent(rs.getString("board_content"))
				.originalFileName(rs.getString("board_original_filename"))
				.renamedFileName(rs.getString("board_renamed_filename"))
				.boardDate(rs.getDate("board_date"))
				.readCount(rs.getInt("board_readcount"))
				.comments(new ArrayList<BoardComment>()) //작성 안해주면 comments에 null 들어감
//				.commentCount(rs.getInt("comment_count")) // 컬럼 추가
				.build();
	}

	public int selectBoardAllCount(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		
		try {
			pstmt = conn.prepareStatement(sql.getProperty("selectBoardAllCount"));
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

	public Board selectBoardByNo(Connection conn, int no) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Board b = null;
		
		try {
			pstmt = conn.prepareStatement(sql.getProperty("selectBoardByNo"));
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				b = getBoard(rs);
				do {
					b.getComments().add(getBoardComment(rs)); // 댓글은 여러개 -> do while문(while문 사용시 1번 인덱스값 안들어감)
				}while(rs.next());
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return b;
	}
	
	public int insertBoard(Connection conn, Board b) {
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			pstmt = conn.prepareStatement(sql.getProperty("insertBoard"));
			pstmt.setString(1, b.getBoardTitle());
			pstmt.setString(2, b.getBoardWriter());
			pstmt.setString(3, b.getBoardContent());
			pstmt.setString(4, b.getOriginalFileName());
			pstmt.setString(5, b.getRenamedFileName());
			result = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}return result;
	}
	
	public int updateReadCount(Connection conn, int no) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			pstmt = conn.prepareStatement(sql.getProperty("updateReadCount"));
			pstmt.setInt(1, no);
			result = pstmt.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			close(pstmt);
		}return result;
	}
	
	public int deleteBoard(Connection conn, int no) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			pstmt = conn.prepareStatement(sql.getProperty("deleteBoard"));
			pstmt.setInt(1, no);
			result = pstmt.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			close(pstmt);
		}return result;
	}
	
	public int updateBoard(Connection conn, Board b) {
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			pstmt = conn.prepareStatement(sql.getProperty("updateBoard"));
			pstmt.setString(1, b.getBoardTitle());
			pstmt.setString(2, b.getBoardContent());
			pstmt.setString(3, b.getOriginalFileName());
			pstmt.setString(4, b.getRenamedFileName());
			pstmt.setInt(5, b.getBoardNo());
			result = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}return result;
	}

	public List<BoardComment> selectBoardComment(Connection conn, int boardNo) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BoardComment> result = new ArrayList<BoardComment>();
		
		try {
			pstmt = conn.prepareStatement(sql.getProperty("selectBoardComment"));
			pstmt.setInt(1, boardNo);
			rs = pstmt.executeQuery();
			while(rs.next()) result.add(getBoardComment(rs));
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}

	public static BoardComment getBoardComment(ResultSet rs) throws SQLException{
		return BoardComment.builder()
				.boardCommentNo(rs.getInt("board_comment_no"))
				.boardCommentContent(rs.getString("board_comment_content"))
				.level(rs.getInt("board_comment_level"))
				.boardRef(rs.getInt("board_ref"))
				.boardCommentWriter(rs.getString("board_comment_writer"))
				.boardCommentDate(rs.getDate("board_comment_date"))
				.boardCommentRef(rs.getInt("board_comment_ref"))
				.build();
	}

	public int insertBoardComment(Connection conn, BoardComment bc) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			pstmt = conn.prepareStatement(sql.getProperty("insertBoardComment"));
			pstmt.setInt(1, bc.getLevel());
			pstmt.setString(2, bc.getBoardCommentWriter());
			pstmt.setString(3, bc.getBoardCommentContent());
			pstmt.setInt(4, bc.getBoardRef());

			// @ 0이면 무결성 제약조건 -> 0이면 null 넣기@ 다시 보자
			pstmt.setString(5, bc.getBoardCommentRef() == 0 ? null
					: String.valueOf(bc.getBoardCommentRef()));
			result = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}
	
	//?
	public static void getBoardAndComment(List<Board> boards, ResultSet rs) throws SQLException{
		int pk = rs.getInt("board_no");
		if(boards.stream().anyMatch(b -> b.getBoardNo() == pk)) {
			boards.stream().filter(b -> b.getBoardNo() == pk)
			.forEach(b -> {
				try {
					if(rs.getString("board_comment_no")!= null) {
						b.getComments().add(getBoardComment(rs));
					}
				}catch(SQLException e) {
					e.printStackTrace();
				}
			});
		}else {
			Board b = getBoard(rs);
			if(rs.getString("board_comment_no") != null) {
				b.getComments().add(getBoardComment(rs));
			}
			boards.add(b);
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
