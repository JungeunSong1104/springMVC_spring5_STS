<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<jsp:include page="../../include/title.jsp" />
<link href="<c:url value='/resources/css/admin/book_detail.css' />" rel="stylesheet" type="text/css">
<jsp:include page="../include/book_detail_js.jsp" />
</head>
<body>
	<jsp:include page="../../include/header.jsp" />
	<jsp:include page="../include/nav.jsp" />
	<section>
		<div id="section_wrap">
			<div class="word">
				<h3>BOOK DETAIL</h3>
			</div>
			<div class="book_detail">
				<ul>
					<li>
						<img src="<c:url value="/libraryUploadImg/${bookVO.b_thumbnail}"/>">
					</li>
					<li><!-- 대부분의 정보들은 문자열이라 출력만 해주면 되는데 이미지는 서버에 따로 저장 되어있고 그 파일(이미지)의 위치정보가없음 
					C:\library\upload에 저장됨 프로젝트와 다른 위치에 있음-->
						<table>
							<tr>
								<td>도서명</td>
								<td>${bookVO.b_name}</td>
							</tr>
							<tr>
								<td>저자</td>
								<td>${bookVO.b_author}</td>
							</tr>
							<tr>
								<td>발행처</td>
								<td>${bookVO.b_publisher}</td>
							</tr>
							<tr>
								<td>발행년도</td>
								<td>${bookVO.b_publish_year}</td>
							</tr>
							<tr>
								<td>ISBN</td>
								<td>${bookVO.b_isbn}</td>
							</tr>
							<tr>
								<td>청구기호</td>
								<td>${bookVO.b_call_number}</td>
							</tr>
							<tr>
								<td>대출가능</td>
								<td>
								<c:choose>
									<c:when test="${bookVO.b_rantal_able eq '0'}"> <c:out value="X" /> </c:when>
									<c:when test="${bookVO.b_rantal_able eq '1'}"> <c:out value="O" /> </c:when>
									<c:otherwise> <c:out value="X" /> </c:otherwise>
								</c:choose>
								</td>
							</tr>
							<tr>
								<td>등록일</td>
								<td>${bookVO.b_reg_date}</td>
							</tr>
							<tr>
								<td>수정일</td>
								<td>${bookVO.b_mod_date}</td>
							</tr>
						</table>
					</li>
				</ul>
			</div>
			<div class="buttons">
				<c:url value='/book/admin/modifyBookForm' var='modify_url'>
					<c:param name='b_no' value='${bookVO.b_no}'/>
				</c:url>
				<a class="modify_book_button" href="${modify_url}">도서 수정</a>
				<c:url value='/book/admin/deleteBookForm' var='delete_url'>
					<c:param name='b_no' value='${bookVO.b_no}'/>
				</c:url>
				<a class="delete_book_button" href="#none" onclick="deleteBook(${bookVO.b_no},'${bookVO.b_name}')">도서 삭제</a>
			</div>
		</div>
	</section>
	<jsp:include page="../../include/footer.jsp" />
</body>
</html>