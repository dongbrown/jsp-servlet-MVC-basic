<%@page import="com.web.board.model.dto.BoardComment"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.web.board.model.dto.*,java.util.List" %>
<%@ include file="/WEB-INF/views/common/header.jsp"%>

<%
	Board b = (Board)request.getAttribute("board");
	List<BoardComment> comments = b.getComments();
%>

<style>
    section#board-container{width:600px; margin:0 auto; text-align:center;}
    section#board-container h2{margin:10px 0;}
    table#tbl-board{width:500px; margin:0 auto; border:1px solid black; border-collapse:collapse; clear:both; }
    table#tbl-board th {width: 125px; border:1px solid; padding: 5px 0; text-align:center;} 
    table#tbl-board td {border:1px solid; padding: 5px 0 5px 10px; text-align:left;}
      div#comment-container button#btn-insert{width:60px;height:50px; color:white;
    background-color:#3300FF;position:relative;top:-20px;}
        /*댓글테이블*/
    table#tbl-comment{width:580px; margin:0 auto; border-collapse:collapse; clear:both; } 
    table#tbl-comment tr td{border-bottom:1px solid; border-top:1px solid; padding:5px; text-align:left; line-height:120%;}
    table#tbl-comment tr td:first-of-type{padding: 5px 5px 5px 50px;}
    table#tbl-comment tr td:last-of-type {text-align:right; width: 100px;}
    table#tbl-comment button.btn-reply{display:none;}
    table#tbl-comment button.btn-delete{display:none;}
    table#tbl-comment tr:hover {background:lightgray;}
    table#tbl-comment tr:hover button.btn-reply{display:inline;}
    table#tbl-comment tr:hover button.btn-delete{display:inline;}
    table#tbl-comment tr.level2 {color:gray; font-size: 14px;}
    table#tbl-comment sub.comment-writer {color:navy; font-size:14px}
    table#tbl-comment sub.comment-date {color:tomato; font-size:10px}
    table#tbl-comment tr.level2 td:first-of-type{padding-left:100px;}
    table#tbl-comment tr.level2 sub.comment-writer {color:#8e8eff; font-size:14px}
    table#tbl-comment tr.level2 sub.comment-date {color:#ff9c8a; font-size:10px}
    /*답글관련*/
    table#tbl-comment textarea{margin: 4px 0 0 0;}
    table#tbl-comment button.btn-insert2{width:60px; height:23px; color:white; background:#3300ff; position:relative; top:-5px; left:10px;}
</style>
   
<section id="board-container">
	<h2>게시판</h2>
	<table id="tbl-board">
		<tr>
			<th>글번호</th>
			<td><%=b.getBoardNo() %></td>
		</tr>
		<tr>
			<th>제 목</th>
			<td><%=b.getBoardTitle() %></td>
		</tr>
		<tr>
			<th>작성자</th>
			<td><%=b.getBoardWriter() %></td>
		</tr>
		<tr>
			<th>조회수</th>
			<td><%=b.getReadCount() %></td>
		</tr>
		<tr>
			<th>첨부파일</th>
			<td>
			 <%if(b.getOriginalFileName()!=null){ %>
			 	<%=b.getOriginalFileName()%>
			 <%} %>
			</td>
		</tr>
		<tr>
			<th>내 용</th>
			<td><%=b.getBoardContent() %></td>
		</tr>
		<%--글작성자/관리자인경우 수정삭제 가능 --%>
	
		<tr>
			<th colspan="2">
                <input type="button" value="수정하기" 
                onclick="location.assign('<%=request.getContextPath()%>/board/boardupdate.do?no=<%=b.getBoardNo()%>')">
                <input type="button" value="삭제하기" onclick="">			
            </th>
		</tr>
	</table>
	<div id="comment-container">
		<div class="comment-editor">
			<form action="<%=request.getContextPath()%>/board/insertcomment.do" 
				method="post">
				<input type="hidden" name="boardRef" value="<%=b.getBoardNo()%>">
				<input type="hidden" name="level" value="1">
				<input type="hidden" name="writer" value="<%=loginMember!=null?loginMember.getUserId():""%>">
				<input type="hidden" name="boardCommentRef" value="0">
				<textarea name="content" cols="55" rows="3"></textarea>
				<button type="submit" id="btn-insert">등록</button>
			</form>
		</div>
		<table id="tbl-comment">
			<%if(!comments.isEmpty()){
				for(BoardComment bc:comments){
				if(bc.getLevel()==1){%>
				<tr class="level1">
					<td>
						<sub class="comment-writer"><%=bc.getBoardCommentWriter() %></sub>
						<sub class="comment-date"><%=bc.getBoardCommentDate() %></sub>
						<br>
						<%=bc.getBoardCommentContent() %>
					</td>
					<td>
						<button class="btn-reply" value="<%=bc.getBoardCommentNo()%>">답글</button>
						<span style="display:none"><%=bc.getBoardCommentNo() %></span>
						<button class="btn-delete">삭제</button>
					</td>
				</tr>
				<%}else if(bc.getLevel()==2){%>
					<tr class="level2">
						<td>
							<sub><%=bc.getBoardCommentWriter() %></sub>
							<sub><%=bc.getBoardCommentDate() %></sub>
							<br>
							<%=bc.getBoardCommentContent() %>
						</td>
						<td>
						</td>
					</tr>
				<%}
				}
			} %>	
		</table>
	</div>
</section>
<script>
	$("textarea[name=content]").click(e=>{
		if(<%=loginMember == null%>){
			alert("로그인 후 이용할 수 있습니다");
			$("#userId").focus();
		}
	});
	

		
	$(".btn-reply").click(e=>{
		const $tr=$("<tr>");
		const $td=$("<td>").attr("colspan","2");
		const $form=$(".comment-editor>form").clone();
		$form.find("input[name=level]").val("2");
		/* find (자식들 중에서 name이 level인 input태그 찾기 -> level2로 바꾸기  */

		$form.find("textarea").attr({"rows":"1","cols":"50"});
		$form.find("button").removeAttr("id").addClass("btn-insert2");
		$form.find("input[name=boardCommentRef]").val($(e.target).val());
		/* no -> hidden/display:none 혹은 속성으로 저장해두고 js에서 사용 */

		$td.append($form);
		$tr.append($td);
		
		$(e.target).parents("tr").after($tr);
		
	});
</script>

<%@ include file="/WEB-INF/views/common/footer.jsp"%>