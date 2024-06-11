package com.web.admin.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.admin.model.service.AdminService;
import com.web.member.model.dto.Member;

/**
 * Servlet implementation class SearchMember
 */
@WebServlet("/admin/searchMember")
public class SearchMember extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchMember() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int cPage=1;
		try {
			cPage=Integer.parseInt(request.getParameter("cPage"));
		}catch(NumberFormatException e) {
			
		}
		int numPerpage=5;
		try {
			numPerpage = Integer.parseInt(request.getParameter("numPerpage"));
		}catch(NumberFormatException e) {
			//예외가 발생하면 넘어가도록 int numPerpage=5;
		}
				
		
		String type=request.getParameter("searchType");
		String keyword=request.getParameter("searchKeyword");
		
		List<Member> searchMembers=new AdminService()
				.searchMember(cPage,numPerpage, type,keyword);
		
		request.setAttribute("members", searchMembers);
		
		int totalData = new AdminService().searchMemberCount(type,keyword);
		int totalPage = (int)Math.ceil(((double)totalData / numPerpage));
		int pageBarSize = 5;
		int pageNo = ((cPage - 1) / pageBarSize) * pageBarSize+1;
		int pageEnd=pageNo + pageBarSize - 1;
		
		StringBuffer sb=new StringBuffer();
		//부트스트랩 - 페이지네이션 이용
		sb.append("<ul class='pagination justify-content-end'>");
		if(pageNo == 1) {
			sb.append("<li class='page-item disabled'>");
			sb.append("<a class='page-link' href='#'>이전</a>");
			sb.append("</li>");
		}else {
			sb.append("<li class='page-item'>");
			sb.append("<a class='page-link' href='"+
					request.getRequestURI()
					+"?cPage="+(pageNo-1)+"&"
					+"searchType="+type+"&"
					+"searchKeyword="+keyword
					+"'>이전</a>");
			sb.append("</li>");
		}
		
		while(!(pageNo > pageEnd || pageNo > totalPage)) {
			if(pageNo==cPage) {
				sb.append("<li class='page-item active'>");
				sb.append("<a class='page-link' href='#'>" + pageNo + "</a>");
				sb.append("</li>");
			}else {
				sb.append("<li class='page-item'>");
				sb.append("<a class='page-link' href='"+
						request.getRequestURI()
						+"?cPage=" + pageNo + "&"
						+"searchType=" + type + "&"
						+"searchKeyword=" + keyword
						+"'>" + pageNo + "</a>");
				sb.append("</li>");
			}
			pageNo++;
		}
		if(pageNo > totalPage) {
			sb.append("<li class='page-item disabled'>");
			sb.append("<a class='page-link' href='#'>다음</a>");
			sb.append("</li>");
		}else {
			sb.append("<li class='page-item'>");
			sb.append("<a class='page-link' href='"+
					request.getRequestURI()
					+"?cPage="+pageNo+"&"
					+"searchType="+type+"&"
					+"searchKeyword="+keyword
					+"'>다음</a>");
			sb.append("</li>");
		}
		sb.append("</ul>");
		
		request.setAttribute("pageBar",sb);
		
		request.getRequestDispatcher("/WEB-INF/views/member/memberList.jsp")
		.forward(request,response);
	
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
