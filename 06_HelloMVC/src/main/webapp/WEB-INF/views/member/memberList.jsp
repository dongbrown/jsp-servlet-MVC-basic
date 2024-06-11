<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>    
<%@ page import="java.util.List" %>    
<%@ include file="/WEB-INF/views/common/header.jsp"%>
<%
	List<Member> members=(List<Member>)request.getAttribute("members");
	String numPerpage = request.getParameter("numPerpage");
	String searchType = request.getParameter("searchType");
	String searchKeyword = request.getParameter("searchKeyword");
%>
<style type="text/css">
	section#memberList-container {text-align:center;}	
	section#memberList-container table#tbl-member {width:100%; border:1px solid gray; border-collapse:collapse;}
	section#memberList-container table#tbl-member th, table#tbl-member td {border:1px solid gray; padding:10px; }
	div#pageBar>*{
		margin-left:5px;
		margin-right:5px;
	}
	/* 검색창에 대한 스타일 */
    div#search-container {margin:0 0 10px 0; padding:3px; 
    background-color: rgba(0, 188, 212, 0.3);}
    div#search-userId{display:inline-block;}
    div#search-userName{display:none;}
    div#search-gender{display:none;}
    div#numPerpage-container{float:right;}
    form#numperPageFrm{display:inline;}
</style>


    
    <section id="memberList-container">
        <h2>회원관리</h2>
         <div id="search-container">
        	검색타입 : 
        	<select id="searchType">
        		<option value="userId" <%=searchType != null && searchType.equals("userId")? "selected":"" %>>아이디</option>
        		<option value="userName" <%=searchType != null && searchType.equals("userName")? "selected":"" %> >회원이름</option>
        		<option value="gender" <%=searchType != null && searchType.equals("gender")? "selected":"" %> >성별</option>
        	</select>
        	<div id="search-userId">
        		<form action="<%=request.getContextPath()%>/admin/searchMember">
        			<input type="hidden" name="searchType" value="userId" >
        			<input type="text" name="searchKeyword" size="25" 
        			placeholder="검색할 아이디를 입력하세요" >
        			<button type="submit">검색</button>
        		</form>
        	</div>
        	<div id="search-userName">
        		<form action="<%=request.getContextPath()%>/admin/searchMember">
        			<input type="hidden" name="searchType" value="userName">
        			<input type="text" name="searchKeyword" size="25" 
        			placeholder="검색할 이름을 입력하세요">
        			<button type="submit">검색</button>
        		</form>
        	</div>
        	<div id="search-gender">
        		<form action="<%=request.getContextPath()%>/admin/searchMember">
        			<input type="hidden" name="searchType" value="gender">
        			<label><input type="radio" name="searchKeyword" value="M"
        			<%=searchKeyword!=null && searchKeyword.equals("M")?"checked":"" %> >남</label>
        			<label><input type="radio" name="searchKeyword" value="F"
        			<%=searchKeyword!=null && searchKeyword.equals("F")?"checked":"" %> >여</label>
        			<button type="submit">검색</button>
        		</form>
        	</div>
        </div>
        <div id="numPerpage-container">
        	페이지당 회원수 : 
        	<form id="numPerFrm" action="">
        		<select name="numPerpage" id="numPerpage">
        			<option value="10" <%=numPerpage != null && numPerpage.equals("10")? "selected" : "" %>>10</option>
        			<option value="5" <%=numPerpage != null && numPerpage.equals("5")? "selected" : "" %>>5</option>
        			<option value="3" <%=numPerpage != null && numPerpage.equals("3")? "selected" : "" %>>3</option>
        		</select>
        	</form>
        </div>
        <table id="tbl-member">
            <thead>
                <tr>
                    <th>아이디</th>
				    <th>이름</th>
				    <th>성별</th>
				    <th>나이</th>
				    <th>이메일</th>
				    <th>전화번호</th>
				    <th>주소</th>
				    <th>취미</th>
				    <th>가입날짜</th>
                </tr>
            </thead>
            <tbody>
       	  <%if(members.size()>0){ 
       	  	for(Member m : members){%>
       	  	<tr>
       	  		<td><a href="<%=request.getContextPath() %>/admin/memberDetail.do?userId=<%=m.getUserId() %>">
       	  			 <%=m.getUserId() %></a></td>
       	  		<td><%=m.getUserName() %></td>
       	  		<td><%=m.getGender() %></td>
       	  		<td><%=m.getAge() %></td>
       	  		<td><%=m.getEmail() %></td>
       	  		<td><%=m.getPhone() %></td>
       	  		<td><%=m.getAddress() %></td>
       	  		<td>
       	  			<%if(m.getHobby()!=null){
       	  				for(String h:m.getHobby()){ %>
       	  			
       	  				<p><%=h %></p>
       	  			<%}
       	  			}else{%>
       	  				<p>없음</p>
       	  			<%}%>
       	  		</td>
       	  		<td><%=m.getEnrollDate() %></td>
       	  	</tr>
       	  	<%}
       	  	}else{ %>
       	  	<tr>
       	  		<td colspan="9">조회된 데이터가 없습니다</td>
       	  	</tr>
       	  <%} %>
            </tbody>
        </table>
        <div id="pageBar">
        	<%=request.getAttribute("pageBar") %>
        </div>
        <script>
        	//검색창 고정
        	$(() => {
        		$("#searchType").change();
        	})
        
        
        	$("#searchType").change(e=>{
        		const type=e.target.value;
        		$(e.target).parent().children("div").hide();
        		/* $("#search-container>div").hide(); */
				$("#search-" + type).css("display" , "inline-block");        		
        	});
        	
        	$("#numPerpage").change(e=>{
        		<%if(searchType == null){%>
        		$(e.target).parent().attr("action", "<%=request.getContextPath()%>/admin/memberlist.do");
        		//$(e.target).parent() -> #numPerpage(select태그)의 부모 form태그
				<%}else{%>
				$(e.target).parent().attr("action", "<%=request.getContextPath()%>/admin/searchMember");
				$("<input>").attr({name:"searchType", value :"<%=searchType%>",
					type:"hidden"}).appendTo($(e.target).parent());
				$("<input>").attr({name:"searchKeyword", value :"<%=searchKeyword%>",
					type:"hidden"}).appendTo($(e.target).parent());
				<%}%>
        		$(e.target).parent().submit();
        	});
        	/* $("<input>") == document.createElement("input") */
        	
        </script>
    </section>

<%@ include file="/WEB-INF/views/common/footer.jsp"%>
