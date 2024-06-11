package com.web.board.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.web.board.model.dto.Board;
import com.web.board.model.service.BoardService;
import com.web.common.MyFileRenamePolicy;

/**
 * Servlet implementation class BoardInsertServlet
 */
@WebServlet("/board/boardinsert.do")
public class BoardInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardInsertServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String path = getServletContext().getRealPath("/upload/board");
		File dir = new File(path);
		if(!dir.exists()) dir.mkdirs();
		
		int maxSize = 1024 * 1024 * 100;
		String encode = "UTF-8";
		
		MultipartRequest mr = new MultipartRequest(request, path, maxSize,
				encode, new MyFileRenamePolicy()); //->common/MyFileRenamePolicy에서 재정의
	
		Board b = Board.builder()
				.boardTitle(mr.getParameter("title"))
				.boardContent(mr.getParameter("content"))
				.boardWriter(mr.getParameter("writer"))
				.originalFileName(mr.getOriginalFileName("upfile"))
				.renamedFileName(mr.getFilesystemName("upfile"))
				.build();
			
		int result = new BoardService().insertBoard(b);
		String msg, loc;
		if(result > 0) {
			msg = "게시글 등록 성공";
			loc = "/board/boardlist.do";
		}else {
			msg = "게시글 등록 실패";
			loc = "/board/boardinsertview.do";
			File delFile = new File(path + "/" + b.getRenamedFileName());
			delFile.delete();
		}
		
		request.setAttribute("msg", msg);
		request.setAttribute("loc", loc);
		
		
		request.getRequestDispatcher(getServletContext()
				.getInitParameter("viewpath") + "common/msg.jsp")
		.forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
