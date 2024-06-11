package com.web.notice.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.web.notice.model.dto.Notice;
import com.web.notice.model.service.NoticeService;

/**
 * Servlet implementation class NoticeUpdateEndServlet
 */
@WebServlet("/notice/noticeupdateend.do")
public class NoticeUpdateEndServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeUpdateEndServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = getServletContext().getRealPath("/upload/notice");
		int maxSize = 1024 * 1024 * 10;
		String encode = "UTF-8";
		
		MultipartRequest mr = new MultipartRequest(request,
				path, maxSize, encode, new DefaultFileRenamePolicy());
		
		String newfilename = mr.getFilesystemName("upfile");
//		String orifilename = mr.getOriginalFileName("upfile");
		String prevFilename = mr.getParameter("preFile");
		System.out.println(newfilename); 
		System.out.println(prevFilename); 
		
		int no = Integer.parseInt(mr.getParameter("no"));
		//no
		Notice n = Notice.builder()
				.noticeNo(no)
				.noticeTitle(mr.getParameter("title"))
				.noticeContent(mr.getParameter("content"))
				.noticeWriter(mr.getParameter("writer"))
				.filepath(newfilename == null ? prevFilename : newfilename)
				.build();
		int result = new NoticeService().updateNotice(n);
		String msg = "";
		String loc = "";
		
		if(result > 0) {
			if(newfilename != null) {
				File delFile = new File(path + "/" + prevFilename);
				delFile.delete(); //이전 파일 지우기
				msg = "공지사항 수정 성공";
				loc = "/notice/noticelist.do";
			}
		}else {
			if(newfilename != null) {
				File delFile = new File(path + "/" + newfilename);
				delFile.delete();
				msg = "공지사항 수정 실패";
				loc = "/notice/noticewrite.do";
			}
		}
		
		request.setAttribute("msg", msg);
		request.setAttribute("loc", loc);
		request.getRequestDispatcher(getServletContext()
				.getInitParameter("viewpath") + "common/msg.jsp")
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
