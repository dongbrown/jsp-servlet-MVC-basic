package com.web.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.web.common.exception.MyPageError;
import com.web.member.model.dto.Member;

/**
 * Servlet Filter implementation class ZCheckAuthenticateFilter
 */
@WebFilter("/admin/*")
public class ZCheckAuthenticateFilter extends HttpFilter implements Filter {
       
    /**
     * @see HttpFilter#HttpFilter()
     */
    public ZCheckAuthenticateFilter() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		HttpServletRequest hrequest = (HttpServletRequest)request;
		HttpSession session = hrequest.getSession();
		Member loginMember = (Member)session.getAttribute("loginMember");
		
		//id 대신 레벨이나 권한으로 설정 가능!
		if(loginMember != null && loginMember.getUserId().equals("admin")) {
			chain.doFilter(request, response);	
		}else {
			request.setAttribute("prevPage", hrequest.getContextPath()); //메인으로 이동
			throw new MyPageError("접근 권한이 없습니다.");
		}
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
