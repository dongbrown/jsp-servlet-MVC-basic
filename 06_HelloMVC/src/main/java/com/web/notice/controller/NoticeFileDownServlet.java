package com.web.notice.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class NoticeFileDownServlet
 */
@WebServlet("/notice/filedown.do")
public class NoticeFileDownServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeFileDownServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fileName = request.getParameter("filename");
		
		//파일 다운로드
		//1. 연결된 파일을 가져옴 -> InputStream을 이용해서 가져오기
		// 1) 파일에 대한 절대경로
		// 2) FileInputStream클래스를 이용해서 파일 가져오기
		String path = getServletContext().getRealPath("/upload/notice");
		File f = new File(path + "/" + fileName);
		try (
			FileInputStream fis = new FileInputStream(f);
				BufferedInputStream bis = new BufferedInputStream(fis);
				BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());) {
		
			//응답메세지 설정
			response.setContentType("application/octet=stram");
			
			// 한글파일명 안 깨지도록 인코딩
			String rename = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
			
			//다운로드파일명에 대한 설정
			response.setHeader("Content-disposition", "attachment;filename=" + rename);
			// inline - 브라우저에서 오픈할 수 있으면 브라우저에서 오픈  <=> attachment
			
			//연결된 파일 전송
			int read = -1;
			while((read = bis.read()) != -1) {
				bos.write(read);
			}
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
