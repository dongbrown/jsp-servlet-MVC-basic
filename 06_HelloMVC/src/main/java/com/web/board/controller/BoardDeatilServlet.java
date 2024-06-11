package com.web.board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.board.model.service.BoardService;

/**
 * Servlet implementation class BoardDeatilServlet
 */
@WebServlet("/board/boarddetail.do")
public class BoardDeatilServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardDeatilServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int boardNo=Integer.parseInt(request.getParameter("no"));
		
		//@조회수 고정하기!
		//readBoard에 boardNo들을 Cookie 저장하기 -> |번호|  (,(쉼표)는 저장 안됨!)
		Cookie[] cookies = request.getCookies();
		
		String readBoard = ""; // -> ||를 기준으로 숫자(쿠키)들이 들어감
		Boolean readResult = false; // 읽으면 true
		if(cookies != null) {
			for(Cookie c : cookies) {
				//cookie의 key값이 readBoard면  (c.getName => key값)
				if(c.getName().equals("readBoard")) { 
					readBoard = c.getValue();
					if(readBoard.contains("|" + boardNo + "|")) {
						readResult = true;
					}
				}
			}
		}
		if(!readResult) { // 안 읽었으면 readBoard에 누적해서 추가
			Cookie c = new Cookie("readBoard", readBoard + "|" + boardNo + "|");
			c.setMaxAge(60 * 60 * 24);
			response.addCookie(c);
		}
		
		
		
		//readResult도 같이 보내줌
		request.setAttribute("board",
				new BoardService().selectBoardByNo(boardNo, readResult));
//		request.setAttribute("boardComment",
//				new BoardService().selectBoardComment(boardNo)); 
		//=> 쿼리문 수정해서 DB 한번만 갔다오기
		
		request.getRequestDispatcher(getServletContext()
				.getInitParameter("viewpath")
				+"board/boardDetail.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
