<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>This is a page for you to solve some quizes</title>
</head>
<body>
	<c:if test="${empty answer}">
		<!-- answer가 비어있으면  -->
		<form action="quizSubmitted" method="post">
			<label> 01. 5 + 5 = </label> <input type="hidden" name="question"
				value="1">
			<!-- 1번문제라는걸 넘겨줌 hidden으로 넘겨줘서 보이진 않는데 요청됐을떄 밸류값이 1로 들어가게됨 -->
			<input type="text" name="answer" />
			<button>Submit</button>
		</form>

		<form action="quizSubmitted" method="post">
			<label> 02. 10 + 10 = </label> <input type="hidden" name="question"
				value="2"> <input type="text" name="answer" />
			<button>Submit</button>
		</form>

		<form action="quizSubmitted" method="post">
			<label> 03. 15 + 15 = </label> <input type="hidden" name="question"
				value="3"> <input type="text" name="answer" />
			<button>Submit</button>
		</form>

		<form action="quizSubmitted" method="post">
			<label> 04. 20 + 20 = </label> <input type="hidden" name="question"
				value="4"> <input type="text" name="answer" />
			<button>Submit</button>
		</form>

		<form action="quizSubmitted" method="post">
			<label> 05. 25 + 25 = </label> <input type="hidden" name="question"
				value="5"> <input type="text" name="answer" />
			<button>Submit</button>
		</form>

		<form action="quizSubmitted" method="post">
			<label> 06. 30 + 30 = </label> <input type="hidden" name="question"
				value="6"> <input type="text" name="answer" />
			<button>Submit</button>
		</form>

		<form action="quizSubmitted" method="post">
			<label> 07. 35 + 35 = </label> <input type="hidden" name="question"
				value="7"> <input type="text" name="answer" />
			<button>Submit</button>
		</form>

		<form action="quizSubmitted" method="post">
			<label> 08. 40 + 40 = </label> <input type="hidden" name="question"
				value="8"> <input type="text" name="answer" />
			<button>Submit</button>
		</form>

		<form action="quizSubmitted" method="post">
			<label> 09. 45 + 45 = </label> <input type="hidden" name="question"
				value="9"> <input type="text" name="answer" />
			<button>Submit</button>
		</form>

		<form action="quizSubmitted" method="post">
			<label> 10. 50 + 50 = </label> <input type="hidden" name="question"
				value="10"> <input type="text" name="answer" />
			<button>Submit</button>
		</form>


		<!-- 비어있을때는 오답이라고하기로 설정햇는데 아무것도 전달안됐을때느 문제를 보여줘야함
			전달이 되면  -->

	</c:if>

	<c:if test="${!empty answer}">
		<!-- 비어있을때는 오답이라고하기로 설정햇는데 아무것도 전달안됐을때느 문제를 보여줘야함
			전달이 되면  -->
		<h1>정답입니다</h1>
	</c:if>
</body>
</html>