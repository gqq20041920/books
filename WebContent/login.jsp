<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
        + request.getContextPath() + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
    <base href="<%=basePath%>" />
    <title>图书馆管理系统</title>
    <jsp:include page="common/css.jsp"></jsp:include>
    <style>
        body {
            background-image: url("static/images/02.jpg");
        }
        .alert {
            margin: 0 auto 20px;
            text-align: center;
            width:450px;
        }
        .error-message {
            color: #a94442;
            background-color: #f2dede;
            border-color: #ebccd1;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid transparent;
            border-radius: 4px;
        }
    </style>
</head>

<body class="bootstrap-admin-without-padding">
    <div class="container">
        <div class="row">
            <div class="col-lg-12" style="margin-top: 100px">
                <!-- 系统欢迎信息 -->
                <div class="alert alert-info">
                    <a class="close" data-dismiss="alert" href="#">&times;</a>
                    欢迎登录图书馆管理系统
                </div>
                
                <!-- 错误提示容器 -->
                <% 
                    String errorMsg = (String) session.getAttribute("msg");
                    if (errorMsg != null) {
                %>
                <div class="error-message">
                    <%= errorMsg %>
                    <% session.removeAttribute("msg"); // 显示后立即清除 %>
                </div>
                <% } %>

                <!-- 登录表单 -->
                <form class="bootstrap-admin-login-form" method="post" 
                    action="<%=basePath%>login?method=login" onsubmit="return validateForm()">
                    
                    <div class="form-group">
                        <label class="control-label" for="username">账&nbsp;号</label> 
                        <input
                            type="text" 
                            class="form-control" 
                            id="username" 
                            name="account"
                            required="required" 
                            placeholder="请输入账号" 
                            oninput="clearError()"
                        />
                    </div>
                    
                    <div class="form-group">
                        <label class="control-label" for="password">密&nbsp;码</label> 
                        <input
                            type="password" 
                            class="form-control" 
                            id="password"
                            name="password" 
                            required="required" 
                            placeholder="请输入密码"
                            oninput="clearError()"
                        />
                    </div>

                    <label class="control-label" for="password">
                        没有账号请<a href="<%=basePath%>register.jsp" style="color: blue;">注册</a>
                    </label>
                    <br> 
                    
                    <input
                        type="submit" 
                        class="btn btn-lg btn-primary btn-block"
                        value="登&nbsp;&nbsp;&nbsp;&nbsp;录" 
                    />
                </form>
            </div>
        </div>
    </div>

    <script src="static/jQuery/jquery-3.1.1.min.js"></script>
    <script src="static/js/bootstrap.min.js"></script>
    <script>
        // 前端表单验证
        function validateForm() {
            const account = document.getElementById("username").value.trim();
            const password = document.getElementById("password").value.trim();
            
            // 非空检查
            if (account === "" || password === "") {
                showError("账号或密码不能为空！");
                return false;
            }
            
            return true;
        }

        // 显示错误信息
        function showError(message) {
            const errorDiv = document.createElement("div");
            errorDiv.className = "error-message";
            errorDiv.innerHTML = message;
            document.querySelector(".bootstrap-admin-login-form").prepend(errorDiv);
        }

        // 输入时清除错误提示
        function clearError() {
            const errorMessages = document.querySelectorAll(".error-message");
            errorMessages.forEach(msg => msg.remove());
        }

        // 自动隐藏错误提示（3秒后）
        window.onload = function() {
            const errorMsg = document.querySelector(".error-message");
            if (errorMsg) {
                setTimeout(() => {
                    errorMsg.remove();
                }, 3000);
            }
        }
    </script>
</body>
</html>