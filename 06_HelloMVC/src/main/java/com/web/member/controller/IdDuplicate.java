package com.web.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.member.model.dto.Member;
import com.web.member.model.service.MemberSerivce;

/**
 * Servlet implementation class IdDuplicate
 */
@WebServlet("/member/idDuplicate.do")
public class IdDuplicate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IdDuplicate() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userId = request.getParameter("userId");
		System.out.println(userId);
		Member m = new MemberSerivce().selectMemberById(userId);
		
		//true (->아이디 사용가능) 아니면 false (->아이디 사용불가) 저장
		request.setAttribute("result", m == null);
		
		request.getRequestDispatcher(
				//web.xml에 context-param 등록하여 getServletContext().getInitParameterr이용
				getServletContext().getInitParameter("viewpath") + "member/idDuplicate.jsp")
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
