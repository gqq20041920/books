<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE HTML>
<html class="ax-vertical-centered">
<head>
	<title>用户排行</title>
	<jsp:include page="../common/css.jsp"></jsp:include>
	<style>
	body {
		background-image: url("static/images/03.jpg");
	}
	</style>
</head>

<body class="bootstrap-admin-with-small-navbar">
	<jsp:include page="../common/top.jsp"></jsp:include>

	<div class="container">
		<div class="row">
			<jsp:include page="../common/left.jsp"></jsp:include>
			
			<!-- content -->
			<div class="col-md-10">
				<div class="row">
					<div class="col-lg-12">
						<div class="panel panel-default bootstrap-admin-no-table-panel">
							<div class="panel-heading">
								<div class="text-muted bootstrap-admin-box-title">读者借阅量</div>
							</div>
							
						</div>
					</div>
				</div>

				<div class="row">
					<div class="col-lg-12">
						<table id="data_list" class="table table-hover table-bordered"
							cellspacing="0" width="100%">
							<thead>
								<tr>
								<th>排名</th>
									<th>借阅量</th>
									<th>ID</th>
									<th>姓名</th>
									<th>账号</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${uList}" var="user" varStatus="status">
									<tr>
									   <td>第${start + status.index + 1}名</td>
									  
										<td>${user.times}次</td>
										<td>${user.uid}</td>
										<td>${user.name}</td>
										<td>${user.account}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						${requestScope.pagation};
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--------------------------------------添加的模糊框------------------------>

	<jsp:include page="../common/userInfo.jsp"></jsp:include>
	<jsp:include page="../common/js.jsp"></jsp:include>
</body>
</html>