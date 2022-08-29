<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>

<!-- 상세화면 -->
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
	
	table{
		width: 100%;
    	border-collapse: collapse;
	}
	
	table th{
		text-align: center;
		border: 1px solid #ddd;
	}
	
	table td{
		border: 1px solid #ddd;
		padding : 10px;
	}
	
	.btn_area{
		margin-top : 20px;
		text-align: right;
	}
	
	.btn_1{
		border: none;
	    padding: 10px;
	    font-weight: bold;
	    background: #5959fd;
	    border-radius: 12px;
	    color: #fff;
	    cursor:pointer;
	}
	
	.btn_2{
		border: none;
	    padding: 10px;
	    font-weight: bold;
	    background: #eee74d;
	    border-radius: 12px;
	    color: #fff;
	    cursor:pointer;
	    float:left;
	}
	
	.txtarea{
		resize: none;
		width : 100%;
		height:200px;
	}
	
	#title{
		width : 100%;
	}
</style>

<div class="todo_body ">
<sec:authorize access="isAuthenticated()">
	<h1 class="w3-text-blue">처리 글</h1>
	<form id="listForm" method="GET" enctype="multipart/form-data">
		<input type="hidden" id="t_seq" name="t_seq2" value="${todo2.t_seq2}">
		<table>
			<colgroup>
				<col width="20%"/>
				<col width="80%"/>
			</colgroup>
			<tbody>
				<tr>
					<th>제목</th>
					<td>${todo2.t_title2}</td>
				</tr>
				<tr>
					<th>작성자</th>
					<td>${todo2.t_username}</td>
				</tr>
				<tr>
					<th>등록일</th>
					<td>${todo2.t_sdate}</td>
				</tr>
				<tr>
					<th>지역</th>
					<td>
						${todo2.t_place2}
					</td>
				</tr>
				<tr>
					<th>내용</th>
					<td>
						${todo2.t_content2}
					</td>
				</tr>
				<tr>
					<th>사진</th>
					<td>
						<div class="inputArea">
						   <img style="max-width:650px;" src="${rootPath}/static${todo2.gdsImg2}" class="oriImg" title="이미지"/>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
	
	<div class="btn_area">
		<input type="button" class="btn_2" onclick="go_list()" value="목록"/>
		<input type="button" class="btn_1" onclick="list_update()" value="수정"/>
	</div>
</sec:authorize>
</div>
<script>

	function list_update(){
		var listForm = document.getElementById("listForm");
		listForm.action = "/todo/todo/insertFrm2";
		listForm.submit();
	}
	
	function go_list(){
		var listForm = document.getElementById("listForm");
		listForm.action = "/todo/todo";
		listForm.submit();
	}
	
// 	$("#gdsImg").change(
// 			function() {
// 				if (this.files && this.files[0]) {
// 					var reader = new FileReader;
// 					reader.onload = function(data) {
// 						$(".select_img img").attr("src", data.target.result)
// 								.width(500);
// 					}
// 					reader.readAsDataURL(this.files[0]);
// 				}
// 			});
</script>