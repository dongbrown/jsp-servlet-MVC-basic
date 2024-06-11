package com.web.board.model.service;

import static com.web.common.JDBCTemplate.getConnection;
import static com.web.common.JDBCTemplate.close;
import static com.web.common.JDBCTemplate.commit;
import static com.web.common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.List;

import com.web.board.model.dao.BoardDao;
import com.web.board.model.dto.Board;
import com.web.board.model.dto.BoardComment;

public class BoardService {
	
	private BoardDao dao = new BoardDao();
	
	public List<Board> selectBoardAll(int cPage, int numPerpage){
		Connection conn = getConnection();
		List<Board> boards = dao.selectBoardAll(conn, cPage, numPerpage);
		close(conn);
		return boards;
	}

	public int selectBoardAllCount() {
		Connection conn = getConnection();
		int result = dao.selectBoardAllCount(conn);
		close(conn);
		return result;
	}

	public Board selectBoardByNo(int no, boolean readResult) {
		Connection conn = getConnection();
		Board b = dao.selectBoardByNo(conn,no);
		
		//조회수
		// b가 null일 때 예외처리 해보기!
		if(b != null && !readResult) {  // readResult가 false일 때
			int result = dao.updateReadCount(conn, no);
			if(result > 0) {
				commit(conn);
				b.setReadCount(b.getReadCount());
			}
			else rollback(conn);
		}
		
		close(conn);
		return b;
	}
	
	public int insertBoard(Board b) {
		Connection conn = getConnection();
		int result = dao.insertBoard(conn, b);
		if(result > 0) commit(conn);
		else rollback(conn);
		close(conn);
		return result;
	}
	
	public int updateBoard(Board b) {
		Connection conn = getConnection();
		int result = dao.updateBoard(conn, b);
		if(result > 0) commit(conn);
		else rollback(conn);
		close(conn);
		return result;
	}
	
	public int deleteBoard(int no) {
		Connection conn = getConnection();
		int result = dao.deleteBoard(conn, no);
		if(result > 0) commit(conn);
		else rollback(conn);
		close(conn);
		return result;
	}
	
	public int updateBoardReadCount(int no) {
		Connection conn = getConnection();
		int result = dao.updateReadCount(conn, no);
		if(result > 0) commit(conn);
		else rollback(conn);
		close(conn);
		return result;
	}

	public List<BoardComment> selectBoardComment(int boardNo) {
		Connection conn = getConnection();
		List<BoardComment> result = dao.selectBoardComment(conn, boardNo);
		close(conn);
		return result;
	}

	public int insertBoardComment(BoardComment bc) {
		Connection conn = getConnection();
		int result = dao.insertBoardComment(conn, bc);
		if(result > 0) commit(conn);
		else rollback(conn);
		close(conn);
		return result;
	}
	

}
