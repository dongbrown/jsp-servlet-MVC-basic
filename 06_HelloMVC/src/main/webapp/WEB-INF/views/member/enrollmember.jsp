<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

	<section id=enroll-container>
        <h2>회원 가입 정보 입력</h2>
        <form action="<%=request.getContextPath() %>/member/enrollmemberend.do" method="post" 
        onsubmit="return enroll_validate();" >
        <!-- 유효성 검사 -->
        
        <!-- Post로 받으면 한글깨짐 -> 인코딩 처리 (공통 => 필터) -->
        
        <table>
			<tr>
				<th>아이디</th>
				<td>
					<input type="text" placeholder="4글자이상" name="userId" id="userId_" >
					<input type="button" value="중복확인" onclick="checkId();">
				</td>
			</tr>
			<tr>
				<th>패스워드</th>
				<td>
					<input type="password" name="password" id="password_" ><br>
				</td>
			</tr>
			<tr>
				<th>패스워드확인</th>
				<td>	
					<input type="password" id="password_2" ><br>
					<span id="ckresult"></span>
				</td>
			</tr>  
			<tr>
				<th>이름</th>
				<td>	
				<input type="text"  name="userName" id="userName" ><br>
				</td>
			</tr>
			<tr>
				<th>나이</th>
				<td>	
				<input type="number" name="age" id="age"><br>
				</td>
			</tr> 
			<tr>
				<th>이메일</th>
				<td>	
					<input type="email" placeholder="abc@xyz.com" name="email" id="email"><br>
				</td>
			</tr>
			<tr>
				<th>휴대폰</th>
				<td>	
					<input type="tel" placeholder="(-없이)01012345678" name="phone" id="phone" maxlength="11" required><br>
				</td>
			</tr>
			<tr>
				<th>주소</th>
				<td>	
					<input type="text" placeholder="" name="address" id="address"><br>
				</td>
			</tr>
			<tr>
				<th>성별 </th>
				<td>
					<input type="radio" name="gender" id="gender0" value="M" >
					<label for="gender0">남</label>
					<input type="radio" name="gender" id="gender1" value="F">
					<label for="gender1">여</label>
				</td>
			</tr>
			<tr>
				<th>취미 </th>
				<td>
					<input type="checkbox" name="hobby" id="hobby0" value="운동"><label for="hobby0">운동</label>
					<input type="checkbox" name="hobby" id="hobby1" value="등산"><label for="hobby1">등산</label>
					<input type="checkbox" name="hobby" id="hobby2" value="독서"><label for="hobby2">독서</label><br />
					<input type="checkbox" name="hobby" id="hobby3" value="게임"><label for="hobby3">게임</label>
					<input type="checkbox" name="hobby" id="hobby4" value="여행"><label for="hobby4">여행</label><br />
				</td>
			</tr>
		</table>
		<input type="submit" value="가입" >
		<input type="reset" value="취소">
        </form>
    </section>
    <script>
    	const enroll_validate = (e) => {
    		const userId = $("#userId_").val().trim();
    		if(userId.length < 4){
    			alert('아이디는 4글자 이상 작성해야합니다.');
    			return false;
    		}
    		const password = $("#password_").val().trim();
    		
/*     		//비밀번호 8글자 이상 정규표현식
    		const passReg = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*()]).{8,25}$/
			if(!passReg.test(password){
				return false;
			} */
			
    		
    		if(password.length < 4){
    			alert("패스워드는 4글자 이상 작성해야 합니다.");
    			return false;
    		}
    	}
    	
    	document.getElementById("password_2").addEventListener("keyup",
    			e=>{
    				const pw=document.querySelector("#password_").value;
    				const pwck=e.target.value;
    				const resultContainer=document.querySelector("#ckresult");
    				
    				/* 비밀번호 4자리이상 입력부터 문구 출력되도록 */
    				if(pw.length >= 4 && pwck.length >= 4){
	    				
	    				if(pw==pwck){
	    					resultContainer.innerText="비밀번호가 일치합니다";
	    					resultContainer.style.color="green";
	    				}else{
	    					resultContainer.innerText="비밀번호가 일치하지 않습니다";
	    					resultContainer.style.color="red";
	    					/* 지워지도록 */
	    					/* document.querySelector("#password_").value = "";
	    					e.target.value = ""; */
	    					document.querySelector("#password_").focus();
	    				}
    				}else{
    					resultContainer.innerText = "";
    				}
    			});
    	
    	const checkId=e=>{
    		const userId=$("#userId_").val().trim();
    		if(userId.length>=4){
    			open("<%=request.getContextPath()%>/member/idDuplicate.do?userId="+userId,
    					"_blank","width=300,height=200");
    		}else{
    			alert("아이디를 4글자 이상 입력하세요");
    		}
    	}
    	
    	
    </script>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>