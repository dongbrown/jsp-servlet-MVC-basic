package com.web.board.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.board.model.dto.BoardComment;
import com.web.board.model.service.BoardService;

/**
 * Servlet implementation class BoardCommentInsertServlet
 */
@WebServlet("/board/insertcomment.do")
public class BoardCommentInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardCommentInsertServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		BoardComment bc = BoardComment.builder()
				.boardCommentContent(request.getParameter("content"))
				.boardRef(Integer.parseInt(request.getParameter("boardRef")))
				.level(Integer.parseInt(request.getParameter("level")))
				.boardCommentRef(Integer.parseInt(request.getParameter("boardCommentRef")))
				.boardCommentWriter(request.getParameter("writer"))
				.build();
		
		int result = new BoardService().insertBoardComment(bc);
		
		request.setAttribute("msg", result > 0 ? "댓글 등록 성공!" : "댓글 등록 실패 ㅎ");
		request.setAttribute("loc", "/board/boarddetail.do?no=" + bc.getBoardRef());
		
		request.getRequestDispatcher(getServletContext().getInitParameter("viewpath") 
				+ "common/msg.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
