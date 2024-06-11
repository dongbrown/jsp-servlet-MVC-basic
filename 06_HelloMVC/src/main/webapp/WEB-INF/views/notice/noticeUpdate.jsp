<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.web.notice.model.dto.Notice" %>
<%
	Notice n = (Notice)request.getAttribute("notice");
%>    
    
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

    section#notice-container{width:600px; margin:0 auto; text-align:center;}
    section#notice-container h2{margin:10px 0;}
    table#tbl-notice{width:500px; margin:0 auto; border:1px solid black; border-collapse:collapse; clear:both; }
    table#tbl-notice th {width: 125px; border:1px solid; padding: 5px 0; text-align:center;} 
    table#tbl-notice td {border:1px solid; padding: 5px 0 5px 10px; text-align:left;}
</style>
<section id="notice-container">
    <form action="<%=request.getContextPath() %>/notice/noticeupdateend.do" method="post" enctype="multipart/form-data">
        <table id="tbl-notice">
        
        <tr>
            <th>제 목</th>
            <td><input class="form-control" type="text" name="title" value="<%=n.getNoticeTitle()%>" required> </td>
        </tr>
        <tr>
            <th>작성자</th>
            <td><input class="form-control"  type="text" name="writer" value="<%=n.getNoticeWriter()%>" readonly></td>
        </tr>
        <tr>
            <th>첨부파일</th>
            <td> <input class="form-control"  type="file" name="upfile">
            	<span><%=n.getFilepath() %></span>
            	<input type="hidden" name="preFile" value="<%=n.getFilepath()%>">
            </td>
        </tr>
        <tr>
            <th>내 용</th>
            <td> <textarea class="form-control"  rows="10" cols="40" name="content" style="resize:none"
            ><%=n.getNoticeContent() %></textarea> </td>
        </tr>
        <tr>
            <th colspan="2">
            	<input type="hidden" name="no" value="<%=n.getNoticeNo()%>">
                <input type="submit" value="수정하기" onclick="">
            </th>
        </tr>
    </table>
    </form>
</section>


<%@ include file="/WEB-INF/views/common/footer.jsp"%>