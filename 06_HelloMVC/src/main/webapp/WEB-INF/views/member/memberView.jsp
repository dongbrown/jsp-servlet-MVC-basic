<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp"%>
<%@ page import="java.util.List, java.util.Arrays" %>

<%
	List<String> hobby = Arrays.asList(loginMember.getHobby());
%>
<section id=enroll-container>
      <h2>회원 정보 수정</h2>
      <form id="memberFrm" method="post" >
         <table>
            <tr>
               <th>아이디</th>
               <td>
                  <input type="text" name="userId" id="userId_"
                   value="<%=loginMember.getUserId()%>"readonly>
               </td>
            </tr>
<!--             <tr>
               <th>패스워드</th>
               <td>
                  <input type="password" name="password" id="password_">
               </td>
            </tr> -->
<!--             <tr>
               <th>패스워드확인</th>
               <td>   
                  <input type="password" id="password_2"><br>
               </td>
            </tr>   -->
            <tr>
               <th>이름</th>
               <td>   
               <input type="text"  name="userName" id="userName" required
               value="<%=loginMember.getUserName()%>"><br>
               </td>
            </tr>
            <tr>
               <th>나이</th>
               <td>   
               <input type="number" name="age" id="age"
               value="<%=loginMember.getAge()%>"><br>
               </td>
            </tr> 
            <tr>
               <th>이메일</th>
               <td>   
                  <input type="email" placeholder="abc@xyz.com" name="email" id="email"
                  value="<%=loginMember.getEmail()%>"><br>
               </td>
            </tr>
            <tr>
               <th>휴대폰</th>
               <td>   
                  <input type="tel" placeholder="(-없이)01012345678" name="phone" id="phone" maxlength="11"
                  value="<%=loginMember.getPhone()%>"><br>
               </td>
            </tr>
            <tr>
               <th>주소</th>
               <td>   
                  <input type="text" placeholder="" name="address" id="address"
                  value="<%=loginMember.getEmail()%>" ><br>
               </td>
            </tr>
            <tr>
               <th>성별 </th>
               <td>
                     <input type="radio" name="gender" id="gender0" value="M" 
                     <%=loginMember.getGender().equals("M")?"checked":"" %>>
                     <label for="gender0">남</label>
                     <input type="radio" name="gender" id="gender1" value="F"
                     <%=loginMember.getGender().equals("F")?"checked":"" %>>
                     <label for="gender1">여</label>
               </td>
            </tr>
            <tr>
               <th>취미 </th>
               <td>
                  <input type="checkbox" name="hobby" id="hobby0" value="운동"
                  <%=hobby.contains("운동")?"checked":"" %>><label for="hobby0">운동</label>
                  <input type="checkbox" name="hobby" id="hobby1" value="등산"
                  <%=hobby.contains("등산")?"checked":"" %>><label for="hobby1">등산</label>
                  <input type="checkbox" name="hobby" id="hobby2" value="독서"
                  <%=hobby.contains("독서")?"checked":"" %>><label for="hobby2">독서</label><br />
                  <input type="checkbox" name="hobby" id="hobby3" value="게임"
                  <%=hobby.contains("게임")?"checked":"" %>><label for="hobby3">게임</label>
                  <input type="checkbox" name="hobby" id="hobby4" value="여행"
                  <%=hobby.contains("여행")?"checked":"" %>><label for="hobby4">여행</label><br />
                  

               </td>
            </tr>
         </table>
         <input type="button" value="정보수정"/>
         <input type="button" value="탈퇴"/>
      </form>
      <div id="pageBar">
        	<%=request.getAttribute("pageBar") %>
        </div>
   </section>

<%@ include file="/WEB-INF/views/common/footer.jsp"%>