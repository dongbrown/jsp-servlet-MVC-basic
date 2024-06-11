package com.web.member.controller;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.common.AESEncryptor;
import com.web.member.model.dto.Member;
import com.web.member.model.service.MemberSerivce;

/**
 * Servlet implementation class EnrollMemberEnd
 */
@WebServlet(name="enrollMember", urlPatterns = "/member/enrollmemberend.do")
public class EnrollMemberEnd extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EnrollMemberEnd() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//클라이언트가 보낸 회원 정보를 DB Member 테이블에 저장하기
		
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		String userName = request.getParameter("userName");
		int age = Integer.parseInt(request.getParameter("age"));
		String email = request.getParameter("email");
		
		try {
			email = AESEncryptor.encryptData(email);
		}catch(Exception e) {
			e.printStackTrace();
		}
		String phone = request.getParameter("phone");
		
		try {
			phone = AESEncryptor.encryptData(phone);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		String address = request.getParameter("address");
		String gender = request.getParameter("gender");
		String[] hobby = request.getParameterValues("hobby");
		
		
		
		//잘 받아왔는지 확인
//		System.out.println(userId + password + userName + age + email + phone + address +
//				gender + Arrays.toString(hobby));
		Member m = Member.builder().userId(userId)
					.password(password)
					.userName(userName)
					.gender(gender)
					.age(age).email(email).phone(phone).address(address).hobby(hobby)
					.build();
		int result = new MemberSerivce().insertMember(m);
		String msg = "", loc = "";
		if(result > 0) {
			msg = "회원가입을 축하합니다.";
			loc = "/";
		}else {
			msg = "회원가입을 실패했습니다.";
			loc = "/member/enrollmember.do";
		}
		request.setAttribute("msg", msg);
		request.setAttribute("loc", loc);
		
		request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp")
		.forward(request, response);
		
//		Member m = new Member(userId, password, userName, age, email, phone, address, gender, hobby);
//		MemberSerivce ms = new MemberSerivce();
//		int result = 0;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
