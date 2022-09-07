<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<c:set value="${pageContext.request.contextPath}" var="rootPath" />   
<%@ taglib uri="http://www.springframework.org/security/tags"  prefix="sec"%> 
<%@ taglib uri="http://www.springframework.org/tags/form"  prefix="form"%>
   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="_csrf_header" th:content="${_csrf.headerName}">
<meta name="_csrf" th:content="${_csrf.token}">

<title>Insert title here</title>
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<style>
	* {
		box-sizing: border-box;
		margin: 0;
		padding: 0;
	}
	html {
		width: 100vw;
		height: 100vh;
	}
	body {
		display: flex;
		width: 100%;
		height: 100%;
		flex-direction: column;
	}
	header {
		padding:2rem;
		display: flex;
		justify-content: center;
		align-items: center;
	}
	nav {
		background-color: #009688;
		color: white;
		font-size: large;
	}
	nav ul {
		margin: 0 20%;
		display: flex;
		list-style: none;
		
	}
	nav li {
		padding: 16px 12px;
		cursor: pointer;
	}
	nav a {
		text-decoration: none;
		color: inherit;
		margin: 5px 0;
		padding: 0 12px;
		border-bottom: 3px solid transparents;
		transition : 1s;
	}
	nav a:hover {
		border-bottom: 3px solid #ddd
	}
	nav li:nth-of-type(4) {
		margin-left: auto;
	}
	nav li:nth-of-type(1) {
		margin-left: 20px;
	}
	nav li:last-of-type {
		margin-right: 30px;
	}
	section.main {
		flex: 1;
	}
	article.welcome {
		height:100%;
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
	}
	div.img-t {
		display: flex;
	    align-items: center;
	    justify-content: center;
	    padding: 5% 0;
	}
	
</style>
</head>
<body>
<header>
<img src="${rootPath}/static/logo.PNG" width="10%"/>
</header>
	<nav>
		<ul>
			<li><a href="${rootPath}/">HOME</a></li>
			<li><a href="${rootPath}/todo">List</a></li>
			<li><a href="${rootPath}/about2">쓰담차트</a></li>
			
			<sec:authorize access="isAnonymous()">
			<li><a href="${rootPath}/user/login">로그인</a></li>
			<li><a href="${rootPath}/user/join">회원가입</a></li>
			</sec:authorize>
			
			<sec:authorize access="isAuthenticated()">
			<li class="logout">로그아웃</li>
			<li><a href="${rootPath}/user/mypage">myPage</a></li>
			</sec:authorize>
		</ul>
	</nav>
	<section class="main">
		<c:choose>
		<c:when test="${LAYOUT == 'JOIN' }">
			<%@ include file="/WEB-INF/views/user/join.jsp"%>
		</c:when>
		<c:when test="${LAYOUT == 'LOGIN' }">
			<%@ include file="/WEB-INF/views/user/login.jsp"%>
		</c:when>
		<c:when test="${LAYOUT == 'TODO_LIST' }">
			<%@ include file="/WEB-INF/views/todo/list.jsp"%>
		</c:when>
		<c:when test="${LAYOUT == 'TODO_WRITE' }">
			<%@ include file="/WEB-INF/views/todo/insertFrm.jsp"%>
		</c:when>
		<c:when test="${LAYOUT == 'TODO_WRITE2' }">
			<%@ include file="/WEB-INF/views/todo/insertFrm2.jsp"%>
		</c:when>
		<c:when test="${LAYOUT == 'TODO_VIEW' }">
			<%@ include file="/WEB-INF/views/todo/view.jsp"%>
		</c:when>
		<c:when test="${LAYOUT == 'TODO_VIEW2' }">
			<%@ include file="/WEB-INF/views/todo/view2.jsp"%>
		</c:when>
		<c:when test="${LAYOUT == 'MY_PAGE' }">
			<%@ include file="/WEB-INF/views/user/mypage.jsp"%>
		</c:when>
		<c:when test="${LAYOUT == 'CHART_LIST' }">
			<%@ include file="/WEB-INF/views/chart/about2.jsp"%>
		</c:when>
			<c:otherwise>
				<div class="img-t">
					<img src="${rootPath}/static/logo.PNG" width="30%"/>
				</div>
			</c:otherwise>
		</c:choose>
	</section>
		<script>
		const logout = document.querySelector("li.logout")
		logout?.addEventListener("click",()=>{
			document.querySelector("form.logout").submit()
		})
		
	</script>

	<form:form class="logout" action="${rootPath}/logout">
	</form:form>

</body>
</html>