<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp"%>

<%@ page import="com.web.member.model.dto.Member"%>

    <h3>회원 상세 정보</h3>
    <% Member m = (Member)request.getAttribute("member"); %>
    <% if (m != null) { %>
        <table>
            <tr>
                <td>아이디:</td>
                <td><%= m.getUserId() %></td>
            </tr>
            <tr>
                <td>이름:</td>
                <td><%= m.getUserName() %></td>
            </tr>
            <tr>
                <td>성별:</td>
                <td><%= m.getGender() %></td>
            </tr>
            <tr>
                <td>나이:</td>
                <td><%= m.getAge() %></td>
            </tr>
            <tr>
                <td>이메일:</td>
                <td><%= m.getEmail() %></td>
            </tr>
            <tr>
                <td>전화번호:</td>
                <td><%= m.getPhone() %></td>
            </tr>
            <tr>
                <td>주소:</td>
                <td><%= m.getAddress() %></td>
            </tr>
            <tr>
                <td>취미:</td>
                <td>
                    <% String[] hobbies = m.getHobby();
                    if (hobbies != null && hobbies.length > 0) {
                        for (String hobby : hobbies) { %>
                            <%= hobby %><br>
                    <%  }
                    } else { %>
                        없음
                    <% } %>
                </td>
            </tr>
            <tr>
                <td>가입날짜:</td>
                <td><%= m.getEnrollDate() %></td>
            </tr>
                <% } %>
        </table>


<%@ include file="/WEB-INF/views/common/footer.jsp"%>