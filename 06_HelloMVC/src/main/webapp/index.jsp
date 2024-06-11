<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- <%@ include file="/WEB-INF/views/common/header.jsp" %>  --%>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<section id="content">
	<h2 align="center" style="margin-top:200px">Hello MVC</h2>
	
	<h2>아이디검색</h2>
	<input type="text" id="searchId" list="searchlist">
	<button id="searchBtn" class="btn btn-outline-success">이름검색</button>
	<div id="searchResult"></div>
	<datalist id="searchlist"></datalist>
	<script>
		$("#searchId").keyup(e=>{
			$.get("<%=request.getContextPath()%>/search/memberid.do?keyword="+$(e.target).val())
			.done(data=>{
				//console.log(data);
				$("#searchlist").html("");
				const names=data.split(",");
				names.forEach(n=>{
					$("<option>").attr("value",n).text(n)
					.appendTo($("#searchlist"));
				});
			});
		});
		$("#searchBtn").click(e=>{
			$.get("<%=request.getContextPath()%>/admin/ajaxsearchMember?searchType=userName&searchKeyword=" +$("#searchId").val())
			.done(data=>{
				console.log(data);
				$("#searchResult").html(data);
			});		
		});

	</script>
</section>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
