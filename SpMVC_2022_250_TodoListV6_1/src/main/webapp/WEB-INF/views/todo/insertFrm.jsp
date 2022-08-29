<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
<!-- 작성   -->
<style>
div.todo_body {
	width: 60%;
	margin: 50px auto;
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
	border-collapse: collapse;
}

table th {
	text-align: center;
	border: 1px solid #ddd;
}

table td {
	border: 1px solid #ddd;
	padding: 10px;
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

.btn_2 {
	border: none;
	padding: 10px;
	font-weight: bold;
	background: #eee74d;
	border-radius: 12px;
	color: #fff;
	cursor: pointer;
	float: left;
}

.txtarea {
	resize: none;
	width: 100%;
	height: 200px;
}

#title {
	width: 100%;
}

.select_img img {
	margin: 20px 0;
}
</style>
<div class="todo_body ">
	<sec:authorize access="isAuthenticated()">
		<h1 class="w3-text-blue">LIST</h1>
		<form id="listForm" method="POST" enctype="multipart/form-data">
		<c:if test="${todoVO.t_seq != '' && todoVO.t_seq != null}">
			<input type="hidden" id="t_seq" name="t_seq" value="${todo.t_seq}" />
		</c:if>
			<table>
				<colgroup>
					<col width="20%" />
					<col width="80%" />
				</colgroup>
				<tbody>
					<tr>
						<th>제목</th>
						<td><input type="text" id="title" name="t_title"
							value="${todo.t_title}"></td>
					</tr>
					<tr>
						<th>지역</th>
						<td><select name="t_place">
								<option value="광산구"
									<c:if test="${todo.t_place eq '광산구' }">selected</c:if>>광산구</option>
								<option value="동구"
									<c:if test="${todo.t_place eq '동구' }">selected</c:if>>동구</option>
								<option value="서구"
									<c:if test="${todo.t_place eq '서구' }">selected</c:if>>서구</option>
								<option value="남구"
									<c:if test="${todo.t_place eq '남구' }">selected</c:if>>남구</option>
								<option value="북구"
									<c:if test="${todo.t_place eq '북구' }">selected</c:if>>북구</option>
						</select></td>
					</tr>
					<tr>
						<th>내용</th>
						<td><textarea name="t_content" class="txtarea">${todo.t_content}</textarea>
						</td>
					</tr>
				</tbody>
			</table>
		</form>

		<div class="btn_area">
			<input type="button" class="btn_2" onclick="go_list()" value="목록" />
			<input type="button" class="btn_1" onclick="list_write()" value="저장" />
		</div>

	</sec:authorize>
</div>
<script>
	function list_write() {
		 var listForm = document.getElementById("listForm");
		 if($("#t_seq").val() == null || $("#t_seq").val() == ""){
			listForm.action = "/todo/todo/insert";
		}else{
			listForm.action = "/todo/todo/update";
		}
		
		listForm.submit();
	}

	function go_list() {
		var listForm = document.getElementById("listForm");
		listForm.action = "/todo/todo";
		listForm.submit();
	}
</script>
