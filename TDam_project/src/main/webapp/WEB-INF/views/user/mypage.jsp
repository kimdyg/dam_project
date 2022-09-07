<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="rootPath" value="${pageContext.request.contextPath}" />
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
<style>
div.todo_body {
	width: 60%;
	margin: 50px auto;
    color: white;
}

div.todo_content {
	cursor: pointer;
}

div.complete {
	text-decoration: red line-through wavy;
	color: green;
	background-color: aqua;
}

table {
	width: 100%;
	text-align: center;
	border-collapse: collapse;
}

table th {
	border: 1px solid #ddd;
}

table td {
	border: 1px solid #ddd;
}

table tbody tr:hover {
	background: #ddd;
}

.btn_area {
	margin-top: 20px;
	text-align: right;
}

.btn_1 {
	border: none;
	padding: 10px;
	font-weight: bold;
	background: #5959fd;
	border-radius: 12px;
	color: #fff;
	cursor: pointer;
}
/* 탭 메뉴 css */

ul.tabs{
	margin: 2%;
	padding: 0px;
	list-style: none;
	display: flex;
	justify-content: center;
    font-weight: bolder;
    font-size: 2rem;
}

ul.tabs li{
  	display: inline-block;
    background: #077e73;
	color: #fff;
	padding: 2% 10%;
	cursor: pointer;
	border-radius: 9px;
}
ul.tabs li:hover {
	background-color: #a0c9c7dd;
}

ul.tabs li.current{
	background: #1dcdbc;
	color: white;
	border-radius: 9px;
}

.tab-content{
  	display: none;
	background: #fff;
	padding: 12px;
	color:#000 !important;
}

.tab-content.current{
	display: inherit;
}
</style>
<script>
	function listView(seq){
		var listForm = document.getElementById("listForm");
		document.getElementById("t_seq").value = seq;
		listForm.action = "/tdam/user/view";
		listForm.submit();
	}
	function listView2(seq){
		var listForm = document.getElementById("listForm2");
		document.getElementById("t_seq2").value = seq;
		listForm.action = "/tdam/user/view2";
		listForm.submit();
		
	}
	/* 탭 메뉴 스크립트 */
	$(document).ready(function(){
		if($("#tabValue").val() == "2"){
			$("#tab-1").removeClass('current');
			$("#tab-2").addClass('current');
			
			$("#tabmenu1").removeClass('current');
			$("#tabmenu2").addClass('current');
			
		}
		
		$('ul.tabs li').click(function(){
			var tab_id = $(this).attr('data-tab');

			$('ul.tabs li').removeClass('current');
			$('.tab-content').removeClass('current');

			$(this).addClass('current');
			$("#"+tab_id).addClass('current');
		})
	});
</script>

<div class="container">
	<ul class="tabs">
		<li id="tabmenu1" class="tab-link current" data-tab="tab-1">나의 제보 목록</li>
		<li id="tabmenu2" class="tab-link" data-tab="tab-2">나의 처리 목록</li>
	</ul>
	<input type="hidden" id="tabValue"  value="${tabValue}"/>
	<div id="tab-1" class="todo_body tab-content current">
		<sec:authorize access="isAuthenticated()">
			<h1>나의 제보 목록</h1>
			<form id="listForm" method="GET">
				<input type="hidden" id="t_seq" name="t_seq" value="">
				<table>
					<colgroup>
						<col width="10%" />
						<col width="10%" />
						<col width="50%" />
						<col width="10%" />
						<col width="20%" />
					</colgroup>
					<thead>
						<tr>
							<th>순번</th>
							<th>지역</th>
							<th>제목</th>
							<th>작성자</th>
							<th>작성일</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${TODOS}" var="TODO" varStatus="status">
							<tr onclick="listView('${TODO.t_seq}')">
								<td>${fn:length(TODOS) - status.index}</td>
								<td>${TODO.t_place}</td>
								<td>${TODO.t_title}</td>
								<td>${TODO.t_username}</td>
								<td>${TODO.t_sdate}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</form>
		</sec:authorize>
	</div>
	
	<div id="tab-2" class="todo_body tab-content">
		<sec:authorize access="isAuthenticated()">
			<h1>나의 처리 목록</h1>
			<form id="listForm2" method="get">
				<input type="hidden" id="t_seq2" name="t_seq2" value="">
				<table>
					<colgroup>
						<col width="10%" />
						<col width="10%" />
						<col width="50%" />
						<col width="10%" />
						<col width="20%" />
					</colgroup>
					<thead>
						<tr>
							<th>순번</th>
							<th>지역</th>
							<th>제목</th>
							<th>작성자</th>
							<th>작성일</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${TODOS2}" var="TODO" varStatus="status">
							<tr onclick="listView2('${TODO.t_seq2}')"> 
								<td>${fn:length(TODOS2) - status.index}</td>
								<td>${TODO.t_place2}</td>
								<td>${TODO.t_title2}</td>
								<td>${TODO.t_username}</td>
								<td>${TODO.t_sdate}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</form>
		</sec:authorize>
	</div>
</div>