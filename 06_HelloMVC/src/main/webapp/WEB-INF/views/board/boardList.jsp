<%@page import="com.web.board.model.service.BoardService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.web.board.model.dto.Board, java.util.List" %>    
<%
	List<Board> boards = (List<Board>)request.getAttribute("boards");
	String pageBar = (String)request.getAttribute("pageBar");
%>
    
<%@ include file="/WEB-INF/views/common/header.jsp"%>



<style>
	section#board-container{width:600px; margin:0 auto; text-align:center;}
	section#board-container h2{margin:10px 0;}
	table#tbl-board{width:100%; margin:0 auto; border:1px solid black; border-collapse:collapse; clear:both; }
	table#tbl-board th, table#tbl-board td {border:1px solid; padding: 5px 0; text-align:center;} 
	/*글쓰기버튼*/
	input#btn-add{float:right; margin: 0 0 15px;}
	/*페이지바*/
	div#pageBar{margin-top:10px; text-align:center; background-color:rgba(0, 188, 212, 0.3);}
	div#pageBar span.cPage{color: #0066ff;}
</style>
<section id="board-container">
		<h2>게시판 </h2>
		<div class="justify-content-end">
	    	<button class="btn btn-outline-primary" onclick="location.assign('<%=request.getContextPath()%>/board/boardinsertview.do')">글쓰기</button>
	    </div>
		<table class="table table-success table-striped table-hover">
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>작성자</th>
				<th>작성일</th>
				<th>첨부파일</th>
				<th>조회수</th>
			</tr>
			<tbody>
			<%if(boards.isEmpty()){ %>
				<tr>
					<td colspan="6">조회된 게시글이 없습니다.</td>
				</tr>
			<%}else{
				for(Board b : boards){%>
				<tr>
					<td><%=b.getBoardNo() %></td>
					<td>
						<%-- <a href="<%=request.getContextPath() %>/board/boardDetail.do?boardNo=<%=b.getBoardNo() %>" >
						<%=b.getBoardTitle() %></a>  --%>
						<a href="javascript:fn_boardview('<%=b.getBoardNo() %>')" ><%=b.getBoardTitle() %>
						<%-- <span>(<%=b.getCommentCount() %>)</span></a> --%>
						<span>(<%=b.getComments().size() %>)</span></a>
					</td>
					<td><%=b.getBoardWriter() %></td>
					<td><%=b.getBoardDate() %></td>
					<td>
						<%if(b.getRenamedFileName() != null){ %>
							<img src="<%=request.getContextPath()%>/images/file.png" width="25">
						<%} %>
					</td>
					<td><%=b.getReadCount() %></td>
				</tr>
				<%}
				}%>
			</tbody>
		</table>

		<div id="pageBar">
			<%=pageBar %>
		</div>
		
</section>
<script>
	const fn_boardview = (boardNo) => {
		location.assign(`<%=request.getContextPath()%>/board/boarddetail.do?no=\${boardNo}`);
	}
</script>




<%@ include file="/WEB-INF/views/common/footer.jsp"%>