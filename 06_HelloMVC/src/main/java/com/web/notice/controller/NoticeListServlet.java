package com.web.notice.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.admin.model.service.AdminService;
import com.web.common.exception.MyPageError;
import com.web.notice.model.dto.Notice;
import com.web.notice.model.service.NoticeService;

/**
 * Servlet implementation class NoticeListServlet
 */
@WebServlet("/notice/noticelist.do")
public class NoticeListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		int cPage = 1;
		try{
			cPage=Integer.parseInt(request.getParameter("cPage"));
		}catch(NumberFormatException e) {

		}
		int numPerpage = 5;
		
		List<Notice> notices = new NoticeService().selectNoticeAll(cPage,numPerpage);
		request.setAttribute("notices", notices);
		
		//pageBar만들기
		StringBuffer pageBar = new StringBuffer();
		int totalData = new NoticeService().selectNoticeCount();
		int totalPage = (int)Math.ceil((double)totalData / numPerpage);
				
		if(totalPage < cPage) {
			request.setAttribute("prevPage", request.getRequestURI());
			throw new MyPageError("잘못된 페이지 번호입니다.");
		}
				
		int pageBarSize = 5;
		int pageNo = ((cPage - 1) / pageBarSize) * pageBarSize + 1;
		int pageEnd = pageNo + pageBarSize - 1;
				
		//pageBar html
		if(pageNo == 1) {
			pageBar.append("<span>[이전]</span>");
		}else {
			pageBar.append("<a href='"+request.getRequestURI()				
			+ "?cPage=" + (pageNo-1) + "'>[이전]</a>");
		}
				
		while(!(pageNo > pageEnd || pageNo > totalPage)) {
			if(pageNo == cPage) {
				pageBar.append("<span>" + pageNo + "</span>");
			}else {
				pageBar.append("<a href='"+request.getRequestURI()
				+ "?cPage=" + (pageNo) + "'>" + pageNo + "</a>");
			}
			pageNo++;
		}
				
		if(pageNo>totalPage) {
			pageBar.append("<span>[다음]</span>");
		}else {
			pageBar.append("<a href='"+request.getRequestURI()
			+"?cPage="+(pageNo)+"'>[다음]</a>");
		}
		request.setAttribute("pageBar",pageBar);
		
		request.getRequestDispatcher("/WEB-INF/views/notice/noticeList.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
