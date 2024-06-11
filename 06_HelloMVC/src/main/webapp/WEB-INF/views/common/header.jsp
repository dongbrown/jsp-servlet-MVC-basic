<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.web.member.model.dto.Member" %>    
<%
	Member loginMember=(Member)session.getAttribute("loginMember");
	//Cookie값 가져오기
	Cookie[] cookies = request.getCookies();
	String saveId = null;
	if(cookies != null){
		for(Cookie c : cookies){
			if(c.getName().equals("saveId")){
				saveId = c.getValue();
				break;
			}
		}
	}
	
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>GDJ79</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css"/>
<script src="<%=request.getContextPath()%>/js/jquery-3.7.1.min.js"></script>
</head>
<body>
<script>
	
</script>

	<div id="header">
		<header>
			<h1>HelloMVC</h1>
			<div class="login-container">
			<%if(loginMember==null){ %>
				<form action="<%=request.getContextPath()%>/login.do" method="post">
					<table>
						<tr>
							<td>
								<input type="text" name="userId" id="userId"
								value="<%=saveId != null? saveId:"" %>"
								placeholder="아이디입력">
							</td>
							<td></td>
						</tr>
						<tr>
							<td>
								<input type="password" name="password" id="passwod"
								placeholder="패스워드입력">
							</td>
							<td>
								<input type="submit" value="로그인" >
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<input type="checkbox" name="saveId" 
								id="saveId" <%=saveId != null?"checked":"" %>>
								<label for="saveId">아이디저장</label>
								<input type="button" value="회원가입"
								onclick="location.assign('<%=request.getContextPath()%>/member/enrollMember.do')">
								<!-- WEB-INF폴더아래 화면으로 바로 전환 불가능 -->
							</td>
						</tr>
					</table>
				</form>
				<%}else{ %>
					<table id="logged-in">
						<tr>
							<td colspan="2">
								<%=loginMember.getUserName() %>님, 방가방가. :)
							</td>
						</tr>
						<tr>
							<td>
							<!-- 세션값 사용 가능! 여기서는 아이디로 조회해서 정보 가져오기 -->
								<input type="button" value="내정보보기"
								onclick="location.assign('<%=request.getContextPath()%>/member/memberview.do?userId=<%=loginMember.getUserId()%>')"/>
							</td>
							<td>
								<input type="button" value="로그아웃"
								onclick="location.replace('<%=request.getContextPath()%>/logout.do')"/>
							</td>
						</tr>
					</table>
				<%} %>
			</div>
			<nav>
				<ul class="main-nav">
					<li class="home">
						<a href="">Home</a>
					</li>
					
					<li id="notice">
						<a href="<%=request.getContextPath()%>/notice/noticelist.do">공지사항</a>
					</li>
					
					<li>
						<a href="<%=request.getContextPath()%>/board/boardlist.do">게시판</a>
					</li>
					<%if(loginMember != null && loginMember.getUserId().equals("admin")){ %>
					<li>
						<a href="<%=request.getContextPath()%>/admin/memberlist.do">회원관리</a>
					</li>
					<%} %>
				</ul>
			</nav>
		</header>
	</div>