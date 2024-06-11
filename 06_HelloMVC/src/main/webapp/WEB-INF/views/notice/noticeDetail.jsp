<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="com.web.notice.model.dto.Notice" %>
<%@ include file="/WEB-INF/views/common/header.jsp"%>
<%
	Notice n = (Notice)request.getAttribute("notice");
%>

<style>
    section#notice-container{width:600px; margin:0 auto; text-align:center;}
    section#notice-container h2{margin:10px 0;}
    table#tbl-notice{width:500px; margin:0 auto; border:1px solid black; border-collapse:collapse; clear:both; }
    table#tbl-notice th {width: 125px; border:1px solid; padding: 5px 0; text-align:center;} 
    table#tbl-notice td {border:1px solid; padding: 5px 0 5px 10px; text-align:left;}
    td>div:hover{
    	cursor: pointer;
    }
</style>
<section id="notice-container">
        <table id="tbl-notice">
        <% if (n != null) { %>
        
        <tr>
            <th>제 목</th>
            <td><%=n.getNoticeTitle() %></td>
        </tr>
        <tr>
            <th>작성자</th>
            <td><%=n.getNoticeWriter() %></td>
        </tr>
        <tr>
            <th>첨부파일</th>
            <td>
            <%if(n.getFilepath() != null){ %>
            <div onclick="fn_filedownload('<%=n.getFilepath()%>');">
				<img src="<%=request.getContextPath()%>/images/file.png"
				width="25"><span><%=n.getFilepath() %></span>            
			</div>	
            <%}else{ %>
            	첨부파일 없음!
            <%} %>
			</td>
        </tr>
        <tr>
            <th>내 용</th>
            <td><%=n.getNoticeContent() %></td>
        <%}else{ %>
       		없음
       	<%} %>
        </tr>
        
        <tr>
            <th colspan="2">
                <input type="button" value="수정하기" 
                onclick="location.assign('<%=request.getContextPath()%>/notice/noticeupdate.do?no=<%=n.getNoticeNo()%>')">
                <input type="button" value="삭제하기" onclick="">
            </th>
        </tr>
    </table>
</section>
<script>
	const fn_filedownload = (fileName) => {
		console.log(fileName);
	<%--location.assign("<%=request.getContextPath()%>/notice/filedown.do?filename=" + fileName); --%>
 		location.assign(`<%=request.getContextPath()%>/notice/filedown.do?filename=\${fileName}`);
 		<%--jsp에서 `(백틱) 사용하려면 ${} 앞에 \ 붙여야 함!--%>
	}
	
</script>

<%@ include file="/WEB-INF/views/common/footer.jsp"%>