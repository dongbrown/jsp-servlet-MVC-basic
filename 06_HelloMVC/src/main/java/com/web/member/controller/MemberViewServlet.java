package com.web.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.web.common.AESEncryptor;
import com.web.member.model.dto.Member;
import com.web.member.model.service.MemberSerivce;

/**
 * Servlet implementation class MemberViewServlet
 */
@WebServlet(name="memberView", urlPatterns ="/member/memberview.do")
public class MemberViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberViewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		Member m = new MemberSerivce().selectMemberById(request.getParameter("userId"));
//		request.setAttribute("member", m);
		
		//복호화 처리
		HttpSession session = request.getSession();
		Member loginMember = (Member)session.getAttribute("loginMember");
		String email = loginMember.getEmail();
		String phone = loginMember.getPhone();
		
		try {
			email = AESEncryptor.decryptData(email);
		}catch(Exception e) {
//			e.printStackTrace();
			System.err.println("암호화 안 된 값");
		}
		try {
			phone = AESEncryptor.decryptData(phone);
		}catch(Exception e) {
//			e.printStackTrace();
			System.err.println("암호화 안 된 값");
		}
		loginMember.setEmail(email);
		loginMember.setPhone(phone);
		
		request.getRequestDispatcher(getServletContext()
				.getInitParameter("viewpath") + "member/memberView.jsp")
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
