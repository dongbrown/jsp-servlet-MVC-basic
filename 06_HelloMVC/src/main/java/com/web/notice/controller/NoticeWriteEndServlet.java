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
 * Servlet implementation class NoticeWriteEndServlet
 */
@WebServlet("/notice/noticewriteend.do")
public class NoticeWriteEndServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeWriteEndServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//enctype이 multipart/form-data방식인 데이터 처리하기
		//cos.jar라이브러리를 이용해서 -> 톰켓 9버전이하에서 작동, 
		// 10버전 이상은 common-io, common-fileupload2 라이브러리 이용해야함
		//cos.jar에서 제공하는 MultipartRequest클래스를 이용해서 처리
		// 매개변수 있는 생성자를 이용해서 생성
		// 5가지 매개변수 인자를 대입
		// 1. HttpServletRequest객체
		// 2. 파일을 업로드할 위치 대입(String) -> 절대경로로 지정
		// 3. 업로드 파일에 대한 최대 크기 설정(int) -> byte 단위 (1024배수 - KB -> MB -> GB)
		// 4. 인코딩 방식(String) -> UTF-8
		// 5. 파일 rename 규칙 설정(파일명 같을 경우..)(객체) -> 
		//   -> 기본제공 DefaultFileRenamePolicy클래스 - 똑같은 파일명 -> 넘버링 *재정의해서 사용 가능
		 
		// MultipartRequest객체를 생성하면 자동으로 클라이언트가 보낸 파일을 지정한 위치에 저장함
		// 추가 데이터 -> MultipartRequest.getParameter() / MultipartRequest.getParameterValues() 메소드를 이용해서 가져옴
		
		//파일업로드 경로 가져오기
		//getServletContext().getRealPath("/"); //-> webapp폴더
		String path = getServletContext().getRealPath("/upload/notice");
		
		//만들고자 하는 디렉토리의 상위 디렉토리가 존재하지 않을 경우 상위 디렉토리까지 생성
		File dir = new File(path);
		if(!dir.exists()) dir.mkdirs();
		
		
		//파일크기
		int maxSize = 1024 * 1024 * 10; //10MB
		
		//인코딩 방식
		String encode = "UTF8";
		
		//rename
		DefaultFileRenamePolicy dfrp = new DefaultFileRenamePolicy();
		
		MultipartRequest mr = new MultipartRequest(
				request, path, maxSize, encode, dfrp);
		
		//나머지 정보를 가져오기
		String title = mr.getParameter("title");
		String writer = mr.getParameter("writer");
		String content = mr.getParameter("content");
		
		//업로드된 파일 정보
		// 원본파일명 가져오기
		String oriName = mr.getOriginalFileName("upfile"); // "name값"
		// rename파일명 가져오기
		String rename = mr.getFilesystemName("upfile");
//		System.out.println(title + writer + content + oriName + rename);
		
		// 서버에 저장(DB에 저장 x)
		Notice n = Notice.builder()
				.noticeTitle(title)
				.noticeWriter(writer)
				.noticeContent(content)
				.filepath(rename)
				.build();
		int result = new NoticeService().insertNotice(n);
		String msg, loc;
		if(result > 0) {
			msg = "공지사항 등록 성공";
			loc = "/notice/noticelist.do";
		}else {
			msg = "공지사항 등록 실패";
			loc = "/notice/noticewrite.do";
			//파일 업로드는 됐지만 DB저장 실패 => 업로드된 파일 지워줘야함! (여러개일 경우 for문으로 지우기)
			File delFile = new File(path + "/" + rename);
			if(delFile.exists()) delFile.delete();
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
