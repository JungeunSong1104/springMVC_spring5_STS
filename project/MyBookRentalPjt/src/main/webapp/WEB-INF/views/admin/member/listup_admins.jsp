<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<jsp:include page="../../include/title.jsp" />
<link href="<c:url value='/resources/css/admin/listup_admins.css' />" rel="stylesheet" type="text/css">
</head>
<body>
	<jsp:include page="../../include/header.jsp" />
	<jsp:include page="../include/nav.jsp" />
	<section>
		<div id="section_wrap">
			<div class="word">
				<h3>ADMIN LIST</h3>
			</div>
			<div class="admin_list">
				<table>
					<thead>
						<tr>
							<th>계정</th>
							<th>이름</th>
							<th>성별</th>
							<th>부서</th>
							<th>직무</th>
							<th>메일</th>
							<th>연락처</th>
							<th>승인</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="item" items="${adminMemberVOs}">
							<tr>
								<td>${item.a_m_id}</td>
								<td>${item.a_m_name}</td>
								<td>${item.a_m_gender}</td>
								<td>${item.a_m_part}</td>
								<td>${item.a_m_position}</td>
								<td>${item.a_m_mail}</td>
								<td>${item.a_m_phone}</td>
								<td>
									<c:choose>
										<c:when test="${item.a_m_approval eq 0}">
											<c:url value='/admin/member/setAdminApproval' var='approval_url'>
												<c:param name='a_m_no' value='${item.a_m_no}'/>
												<!-- c:param : 경로 뒤에 추가적인 값을 넣어준다고 생각하면 됨
												승인처리 번튼을 눌렀을때 그 요청을  setAdminApproval요청이 가게되는데
												관리자1번에대한 승인처리를해야하는지 2번에 대한 처리르 해야하는지 알수가없기때문에
												구분지을수 있는 a_m_no컬럼값(멤버변수값)을 경로에 붙여서 전달해주는것
												그럼 컨트롤러 내부의 메서드처리과정에서 a_m_no가 1번이면 1번관리자를 2면 2번관리자에대해
												승인해주면 되는것 
												-->
											</c:url>
											<a href="${approval_url}">승인처리</a>
										</c:when>
										<c:when test="${item.a_m_approval eq 1}"> <c:out value="승인완료" /> </c:when>
									</c:choose>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</section>
	<jsp:include page="../../include/footer.jsp" />
</body>
</html>