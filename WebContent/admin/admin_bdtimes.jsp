<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE HTML>
<html class="ax-vertical-centered">
<head>
	<title>热门书籍</title>
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
			<jsp:include page="../common/left.jsp"></jsp:include>

			<!-- content -->
			<div class="col-md-10">
				<div class="row">
					<div class="col-lg-12">
						<div class="panel panel-default bootstrap-admin-no-table-panel">
							<div class="panel-heading">
								<div class="text-muted bootstrap-admin-box-title">热门书籍推荐</div>
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
									<th>图书名称</th>
									<th>借阅次数</th>
									<th>图书类型</th>
									<th>作者名称</th>
									<th>出版社</th>
									<th>总数量</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${bList}" var="book" varStatus="status">
									<tr>
										<td>第${start + status.index + 1}名</td>
										<td>${book.bookName}</td>
										<td>${book.times}次</td>
										<td>${book.typeName}</td>
										<td>${book.author}</td>
										<td>${book.press}</td>
										<td>${book.num}</td>
									</tr>
								</c:forEach>
							</tbody>
							<tfoot>
								<tr>
									<td colspan="7">
										${pagation}
									</td>
								</tr>
							</tfoot>
						</table>
						
					</div>
				</div>
			</div>
		</div>

	<jsp:include page="../common/userInfo.jsp"></jsp:include>
	<jsp:include page="../common/js.jsp"></jsp:include>
</body>
</html>