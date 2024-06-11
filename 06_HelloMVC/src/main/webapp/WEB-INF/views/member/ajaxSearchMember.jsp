<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List,com.web.member.model.dto.Member"%>
<%
	List<Member> members=(List<Member>)request.getAttribute("searchMember");
%>
<table>
	<%if(!members.isEmpty()){
		for(Member m : members){%>
		<tr>
			<td><%=m.getUserId()%></td>
			<td><%=m.getUserName()%></td>
			<td><%=m.getAge()%></td>
			<td><%=m.getEmail()%></td>
			<td><%=m.getGender()%></td>
			<td><%=m.getPhone()%></td>			
		</tr>
	<%}
	}%>
</table>

