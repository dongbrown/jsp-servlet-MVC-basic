package com.web.notice.model.service;

import java.sql.Connection;
import java.util.List;

import com.web.notice.model.dao.NoticeDao;
import com.web.notice.model.dto.Notice;
import static com.web.common.JDBCTemplate.getConnection;
import static com.web.common.JDBCTemplate.close;
import static com.web.common.JDBCTemplate.commit;
import static com.web.common.JDBCTemplate.rollback;

public class NoticeService {
	
	private NoticeDao dao = new NoticeDao();
	
	public List<Notice> selectNoticeAll(int cPage, int numPerpage){
		Connection conn = getConnection();
		List<Notice> notices = dao.selectNoticeAll(conn, cPage, numPerpage);
		close(conn);
		return notices;
	}
	
	public int selectNoticeCount() {
		Connection conn = getConnection();
		int result = dao.selectNoticeCount(conn);
		close(conn);
		return result;
	}
	public Notice selectNoticeByNo(int no) {
		Connection conn = getConnection();
		Notice n = dao.selectNoticeByNo(conn, no);
		close(conn);
		return n;
	}
	
	public int insertNotice(Notice n) {
		Connection conn = getConnection();
		int result = dao.insertNotice(conn, n);
		if(result > 0) commit(conn);
		else rollback(conn);
		return result;
	}

	public int updateNotice(Notice n) {
		Connection conn = getConnection();
		int result = dao.updateNotice(conn, n);
		if(result > 0) commit(conn);
		else rollback(conn);
		return result;
	}


	
}
