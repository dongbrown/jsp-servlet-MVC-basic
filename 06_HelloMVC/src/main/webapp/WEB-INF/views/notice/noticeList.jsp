<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List,com.web.notice.model.dto.Notice,java.text.SimpleDateFormat" %>
<%@ include file="/WEB-INF/views/common/header.jsp"%>
<%
	List<Notice> notices=(List<Notice>)request.getAttribute("notices");
%>

 


<section id="notice-container">
    <h2>공지사항</h2>
    <div class="justify-content-end">
	    <button class="btn btn-outline-primary" onclick="location.assign('<%=request.getContextPath()%>/notice/noticewrite.do')">글쓰기</button>
    </div>
    
    <table id="tbl-notice" class="table">
        <tr>
            <th>번호</th>
            <th>제목</th>
            <th>작성자</th>
            <th>첨부파일</th>
            <th>작성일</th>
        </tr>
        <tbody>
		<%if(notices.isEmpty()){
			%>
			<tr>
				<td colspan="5">조회된 공지사항이 없습니다</td>
			</tr>
		<%}else{ 
			for(Notice n: notices){%>
				<tr onclick="goNoticeDetail(<%=n.getNoticeNo()%>)">
					<td><%=n.getNoticeNo() %></td>
					<td><%=n.getNoticeTitle() %></td>
					<td><%=n.getNoticeWriter() %></td>
					<td><%if(n.getFilepath() != null){ %>
							<img src="<%=request.getContextPath()%>/images/file.png"
							width="25">
						<%}else{ %>
							없음
						<%} %>
					</td>
					<td><%=new SimpleDateFormat("yyyy.MM.dd").format(n.getNoticeDate()) %></td>
				</tr>
				
			<%} 
		}%>
		
		</tbody>
    </table>
    <div id="pageBar">
    	<%=request.getAttribute("pageBar") %>
    </div>
    <script>
    	function goNoticeDetail(noticeNo) {
    		
			window.location.href = "noticeDetail.do?noticeNo=" + noticeNo;
		}
    </script>
    
</section>
<%@ include file="/WEB-INF/views/common/footer.jsp"%>