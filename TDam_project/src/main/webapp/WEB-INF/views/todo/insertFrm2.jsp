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
		<h1 class="w3-text-blue">처리</h1>
		<form id="listForm" method="POST" enctype="multipart/form-data">
		<c:if test="${todo2.t_seq2 != '' && todo2.t_seq2 != null}">
			<input type="hidden" id="t_seq" name="t_seq2" value="${todo2.t_seq2}" />
		</c:if>
			<table>
				<colgroup>
					<col width="20%" />
					<col width="80%" />
				</colgroup>
				<tbody>
					<tr>
						<th>제목</th>
						<td><input type="text" id="title" name="t_title2"
							value="${todo2.t_title2}"></td>
					</tr>
					<tr>
						<th>지역</th>
						<td><select name="t_place2">
								<option value="광산구"
									<c:if test="${todo2.t_place2 eq '광산구' }">selected</c:if>>광산구</option>
								<option value="동구"
									<c:if test="${todo2.t_place2 eq '동구' }">selected</c:if>>동구</option>
								<option value="서구"
									<c:if test="${todo2.t_place2 eq '서구' }">selected</c:if>>서구</option>
								<option value="남구"
									<c:if test="${todo2.t_place2 eq '남구' }">selected</c:if>>남구</option>
								<option value="북구"
									<c:if test="${todo2.t_place2 eq '북구' }">selected</c:if>>북구</option>
						</select></td>
					</tr>
					<tr>
						<th>내용</th>
						<td><textarea name="t_content2" class="txtarea">${todo2.t_content2}</textarea>
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
			<div class="inputArea">
				<label for="gdsImg">이미지</label> <input type="file" id="gdsImg"
					name="file" />
				<div class="select_img">
					<img src="" />
				</div>
			</div>
		</form>

		<div class="btn_area">
			<input type="button" class="btn_2" onclick="go_list()" value="취소" />
			<input type="button" class="btn_1" onclick="list_write()" value="저장" />
		</div>

	</sec:authorize>
</div>
<script>
	function list_write() {
		 var listForm = document.getElementById("listForm");
		 console.log($("#t_seq").val());
		 if($("#t_seq").val() == null || $("#t_seq").val() == ""){
			listForm.action = "/tdam/todo/insert2";
			listForm.submit();
		}else{
			listForm.action = "/tdam/todo/update2";
			listForm.submit();
			var listConfirm = confirm("나의 목록에서 보기-확인\n전체 목록에서 보기-취소");
			if(listConfirm){
				location= "/tdam/user/mypage?tabValue=2";
			} else {
				location= "/tdam/todo?tabValue=2";
			}
		}
	}

	function go_list(){
		var link =  document.location.href;
		if(link == "http://localhost:8080/tdam/todo/insertFrm2?t_seq2=" || 
				link == "http://localhost:8080/tdam/todo/insertFrm2?t_seq2=${todo2.t_seq2}"){
			location = "/tdam/todo?tabValue=2";
		} else {
			location = "/tdam/user/mypage?tabValue=2";
		}
	}
	$("#gdsImg").change(
			function() {
				if (this.files && this.files[0]) {
					var reader = new FileReader;
					reader.onload = function(data) {
						$(".select_img img").attr("src", data.target.result)
								.width(500);
					}
					reader.readAsDataURL(this.files[0]);
				}
			});
</script>
