<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
	boolean result = (Boolean)request.getAttribute("result");

	String paramId = request.getParameter("userId"); //사용자가 입력한(확인하는) id
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아이디 중복확인</title>

<style>
	div#checkId-container{
		text-align: center;
		padding-top: 50px;
	}
	span#duplicated{
		color: red;
		font-weight: bolder;
	}
</style>


</head>
<body>

	<div id="checkId-container">
		<%if(result){ %>
			[<span><%=paramId %></span>]는 사용가능합니다.	
			<br><br>
			<button type="button">닫기</button>
		<%}else{ %>
			[<span id="duplicated"><%=paramId %></span>]는 사용중입니다.
			<br><br>
			<!-- 아이디 재입력창 구성 -->
			<form action="<%=request.getContextPath() %>/member/idDuplicate.do" method="get">
				<input type="text" name="userId" id="userId">
				<input type="submit" value="중복검사" >
			</form>
		<%} %>	
		
		<script>
			document.querySelector("button").addEventListener("click", 
					e=> {
						const $userId = opener.document.querySelector("#userId_");
						/* 스크립트문 내에서 문자열 처리 중요! (안하면 변수로 인식) */
						$userId.value = "<%=paramId%>";
						/* 사용하기 누르면 고정 (이메일 인증 받으면 고정 시킬 때 사용) */
						$userId.readOnly = true;
						$userId.style.backgroundColor = "lightgray";
						window.close();
					});
	
		</script>
		
		
		
	</div>

</body>
</html>