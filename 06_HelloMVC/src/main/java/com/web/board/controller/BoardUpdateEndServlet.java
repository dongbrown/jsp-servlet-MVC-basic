package com.web.board.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.web.board.model.dto.Board;
import com.web.board.model.service.BoardService;

/**
 * Servlet implementation class BoardUpdateEndServlet
 */
@WebServlet("/board/boardupdateend.do")
public class BoardUpdateEndServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardUpdateEndServlet() {
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
		
		int maxSize = 1024 * 1024 * 10;
		
		String encode = "UTF-8";
		
		DefaultFileRenamePolicy dfrp = new DefaultFileRenamePolicy();
		
		MultipartRequest mr = new MultipartRequest(request, path, maxSize, encode, dfrp);
		
		String title = mr.getParameter("title");
		String writer = mr.getParameter("writer");
		String content = mr.getParameter("content");
				
		String oriName = mr.getOriginalFileName("upfile"); // "name값"
		String rename = mr.getFilesystemName("upfile");

		Board b = Board.builder()
				.boardNo(Integer.parseInt(request.getParameter("no")))
				.boardContent(content)
				.boardTitle(title)
				.boardWriter(writer)
				.originalFileName(oriName)
				.renamedFileName(rename)
				.build();
				
		int result = new BoardService().updateBoard(b);
		String msg, loc;
		if(result > 0) {
			msg = "게시글 수정 성공";
			loc = "/board/boardlist.do";
		}else {
			msg = "게시글 수정 실패";
			loc = "/board/updateboard.do";
			//파일 업로드는 됐지만 DB저장 실패 => 업로드된 파일 지워줘야함! (여러개일 경우 for문으로 지우기)
			File delFile = new File(path + "/" + rename);
			if(delFile.exists()) delFile.delete();
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
