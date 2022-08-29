<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
<!-- 글쓰기 -->
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
    background: #117823;
	color: #222;
	padding: 2% 10%;
	cursor: pointer;
}

ul.tabs li.current{
	background: #29e112;
	color: white;
	text-decoration: underline blue;
}

.tab-content{
  	display: none;
	background: #29e112;
	padding: 12px;
}

.tab-content.current{
	display: inherit;
}
h1 {
	text-decoration: underline blue;
}
</style>
<script>
document.addEventListener("DOMContentLoaded", () => {
	  const div_body = document.querySelector("div.todo_body");
	  div_body?.addEventListener("click", (e) => {
	    const target = e.target;
	    if(target.tagName == "DIV" && target.classList?.contains("todo_content")) {
	    	const seq = target.dataset.seq
	    	if(target?.classList.contains("complete")) {
	    		alert("완료된 항목은 수정할 수 없음")
	    		return false
	    	}
	    	document.location.href = "${rootPath}/todo/update?t_seq=" + seq
	    			
	    } else if(target.tagName == "SPAN" && target.classList?.contains("todo_content")) {
	    	
			const parentDiv = target.closest("DIV")
	    	const seq = parentDiv?.dataset.seq
	    	document.location.href = "${rootPath}/todo/comp?t_seq=" + seq
	    }
	  })
	})
	
	function list_write(){
		var listForm = document.getElementById("listForm");
		listForm.action = "/todo/todo/insertFrm";
		listForm.submit();
	}
	
	function listView(seq){
		var listForm = document.getElementById("listForm");
		document.getElementById("t_seq").value = seq;
		listForm.action = "/todo/todo/view";
		listForm.submit();
	}
	
	/* 탭 메뉴 스크립트 */
	$(document).ready(function(){
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
		<li class="tab-link current" data-tab="tab-1">제보 목록</li>
		<li class="tab-link" data-tab="tab-2">처리 목록</li>
	</ul>
	<div id="tab-1" class="todo_body tab-content current">
		<sec:authorize access="isAuthenticated()">
			<h1>제보 목록</h1>
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
						<c:forEach items="${TODOS}" var="TODO">
							<tr onclick="listView('${TODO.t_seq}')">
								<td>${TODO.t_seq}</td>
								<td>${TODO.t_place}</td>
								<td>${TODO.t_title}</td>
								<td>${TODO.t_username}</td>
								<td>${TODO.t_sdate}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</form>
			<div class="btn_area">
				<input type="button" class="btn_1" onclick="list_write()" value="글쓰기" />
			</div>
		</sec:authorize>
	</div>
	
	<div id="tab-2" class="todo_body tab-content">
		<sec:authorize access="isAuthenticated()">
			<h1>처리 목록</h1>
			<form id="listForm" method="get">
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
						<c:forEach items="${TODOS2}" var="TODO">
							<tr onclick="listView('${TODO.t_seq2}')">
								<td>${TODO.t_seq2}</td>
								<td>${TODO.t_place2}</td>
								<td>${TODO.t_title2}</td>
								<td>${TODO.t_username}</td>
								<td>${TODO.t_sdate}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</form>
			<div class="btn_area">
				<input type="button" class="btn_1" onclick="list_write()" value="글쓰기" />
			</div>
		</sec:authorize>
	</div>
</div>