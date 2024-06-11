package com.web.board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.board.model.dto.Board;
import com.web.board.model.service.BoardService;
import com.web.common.exception.MyPageError;

/**
 * Servlet implementation class BoardListServlet
 */
@WebServlet("/board/boardlist.do")
public class BoardListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int cPage = 1;
		
		try{
			cPage = Integer.parseInt(request.getParameter("cPage"));
		}catch(NumberFormatException e) {

		}
		int numPerpage = 5;	
		
		
		int totalData = new BoardService().selectBoardAllCount();
		int totalPage = (int)Math.ceil((double) totalData / numPerpage);
		
		if(totalPage < cPage) {
			request.setAttribute("prevPage", request.getRequestURI());
			throw new MyPageError("잘못된 페이지 번호입니다.");
		}
		
		int pageBarSize = 5;
		int pageNo = ((cPage - 1) / pageBarSize) * pageBarSize + 1;
		int pageEnd = pageNo + pageBarSize - 1;
				
		//pageBar html
		String pageBar = "<ul class='pagination justify-content-center'>";
		if(pageNo == 1) {
			pageBar += "<li class='page-item disabled'>";
			pageBar += "<a class='page-link' href='#'>이전</a>";
			pageBar += "</li>";
		}else {
			pageBar += "<li class='page-item'>";
			pageBar += "<a class='page-link' href='"+request.getRequestURI()
				+ "?cPage=" + (pageNo - 1) + "&numPerpage=" + numPerpage +">이전</a>";
			pageBar += "</li>";
		}
		while(!(pageNo > pageEnd || pageNo > totalPage)) {
			if(pageNo == cPage) {
				pageBar += "<li class='page-item'>";
				pageBar += "<a class='page-link' href='#'>"+ pageNo +"</a>";
				pageBar += "</li>";
			}else {
				pageBar += "<li class='page-item'>";
				pageBar += "<a class='page-link' href='"+request.getRequestURI()
						+ "?cPage=" + pageNo + "&numPerpage=" + numPerpage +"'>"
						+ pageNo +"</a>";
				pageBar += "</li>";	
			}
			pageNo++;
		}
		if(pageNo > totalPage) {
			pageBar += "<li class='page-item'>";
			pageBar += "<a class='page-link' href='#'>다음</a>";
			pageBar += "</li>";
		}else {
			pageBar += "<li class='page-item'>";
			pageBar += "<a class='page-link' href='"+request.getRequestURI()
					+ "?cPage=" + pageNo + "&numPerpage=" + numPerpage +"'>다음</a>";
			pageBar += "</li>";
		}
		pageBar += "</ul>";
	
		request.setAttribute("pageBar",pageBar);
		
		
		List<Board> boards = new BoardService().selectBoardAll(cPage, numPerpage);
		request.setAttribute("boards", boards);
		
		request.getRequestDispatcher("/WEB-INF/views/board/boardList.jsp").forward(request, response);
		

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
