package com.web.admin.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.web.admin.model.service.AdminService;
import com.web.common.GsonLocalDateTimeAdapter;
import com.web.member.model.dto.Member;

/**
 * Servlet implementation class AjaxSearchMemberServlet
 */
@WebServlet("/admin/ajaxsearchMember")
public class AjaxSearchMemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjaxSearchMemberServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("searchType");
		String keyword = request.getParameter("searchKeyword");
		
		List<Member> result = new AdminService().searchMember(1, 100, type, keyword);
		
//		request.setAttribute("serachMember", result);
//		request.getRequestDispatcher("/WEB-INF/views/member/ajaxSearchMember.jsp")
//		.forward(request, response);
		Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new GsonLocalDateTimeAdapter())
				.create();
		gson.toJson(result, response.getWriter());
		
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
