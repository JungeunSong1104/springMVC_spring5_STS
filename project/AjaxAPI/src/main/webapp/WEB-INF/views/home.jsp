<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCType html>
<html>
<head>
<title>Home</title>
</head>
<meta charset="UTF-8">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<body>
	<h2>인천 국제 항공사 주차 정보</h2>
	<hr>
	<button id="btn1">정보출력</button>
	<br>
	<br>
	<table id="result1" border="1">
		<thead>
			<tr>
				<th>주차 현황 시간</th>
				<th>주차장 구분</th>
				<th>주차구역 주차수</th>
				<th>주차구역 총주차면수</th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
	<script>
		$(function() {
			$("#btn1").click(function() {
						$.ajax({
							url : "parking.do",//parking.do로 연결할수있는 컨트롤러를 만들어줘야함
							data : {},
							success : function(data) {//data로 제이슨 데이터가 들어옴
								const itemArr = data.response.body.items;
								//json형태로 받아오겠지 바디안의 items 목록으로 들어감

								let value = "";
								let items = {};
								for (let i in itemArr) {//itemArr에 잇는 items를 하나하나 출력해줌
									let item = itemArr[i];
									value += "<tr>"//테이블구문을 완성하기위한것
											+ "<td>:" + item.datetm + "</td>"
											+ "<td>:" + item.floor + "</td>"
											+ "<td>:" + item.parking + "</td>"
											+ "<td>:" + item.parkingarea + "</td>" 
											+ "</tr>";
								}
								$("#result1 tbody").html(value);
								//value에 코드를 완성해줌 이제 이걸 tbody에 넣어줘야함
							}
						})
					})
		})
	</script>
</body>
</html>
