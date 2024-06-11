<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp"%>

<style>
	div#board-container
	{
		width:600px;
		margin:0 auto;
		text-align:center;
	}
	div#board-container h2
	{
		margin:10px 0;
	}
	table#tbl-board
	{
		width:500px;
		margin:0 auto;
		border:1px solid black;
		border-collapse:collapse;
	}
	table#tbl-board th
	{
		width:125px;
		border:1px solid;
		padding:5px 0;
		text-align:center;
	}
	table#tbl-board td
	{
		border:1px solid;
		padding:5px 0 5px 10px;
		text-align:left;
	}

</style>
<script>
	내용입력여부 확인 후 전송
</script>

	<div id='board-container'>
		<h2>게시판 작성</h2>
		<form action='<%=request.getContextPath()%>/board/boardinsert.do' method="post" enctype="multipart/form-data">
			<table id='tbl-board'>
				<tr>
					<th>제목</th>
					<td><input class="form-control" type="text" name="title" required></td>
				</tr>
				<tr>
					<th>작성자</th>
					<td><input class="form-control" type="text" name="writer" value="<%=loginMember.getUserId()%>" readonly> </td>
				</tr>
				<tr>
					<th>첨부파일</th>
					<td><input class="form-control" type="file" name="upfile" ></td>
				</tr>
				<tr>
					<th>내용</th>
					<td><input class="form-control" type="text" name="content" required></td>
				</tr>
				<tr>
					<th colspan="2">
						<input type="submit" value="등록하기" onclick="">
					</th>
				</tr>
			</table>
		</form>
	</div>

<%@ include file="/WEB-INF/views/common/footer.jsp"%>