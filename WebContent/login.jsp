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
        :root {
            --primary-color: #4a6fa5;
            --primary-dark: #385d8a;
            --accent-color: #ff7e5f;
            --light-color: #f8f9fa;
            --dark-color: #343a40;
            --shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
            --transition: all 0.3s ease;
        }
        
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        
        body {
            background: linear-gradient(135deg, #6a11cb 0%, #2575fc 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 20px;
        }
        
        .login-container {
            width: 100%;
            max-width: 420px;
            margin: 0 auto;
        }
        
        .login-card {
            background: white;
            border-radius: 16px;
            box-shadow: var(--shadow);
            overflow: hidden;
            transition: var(--transition);
        }
        
        .login-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 12px 25px rgba(0, 0, 0, 0.15);
        }
        
        .login-header {
            background: var(--primary-color);
            color: white;
            padding: 25px 30px;
            text-align: center;
            position: relative;
        }
        
        .login-header::after {
            content: '';
            position: absolute;
            bottom: -10px;
            left: 50%;
            transform: translateX(-50%);
            width: 0;
            height: 0;
            border-left: 10px solid transparent;
            border-right: 10px solid transparent;
            border-top: 10px solid var(--primary-color);
        }
        
        .login-header h1 {
            font-size: 24px;
            font-weight: 600;
            margin-bottom: 5px;
        }
        
        .login-header p {
            font-size: 14px;
            opacity: 0.9;
        }
        
        .login-body {
            padding: 30px;
        }
        
        .form-group {
            margin-bottom: 20px;
            position: relative;
        }
        
        .form-label {
            display: block;
            margin-bottom: 8px;
            font-weight: 500;
            color: var(--dark-color);
            font-size: 14px;
        }
        
        .form-control {
            width: 100%;
            padding: 12px 15px;
            border: 1px solid #e1e5eb;
            border-radius: 8px;
            font-size: 15px;
            transition: var(--transition);
            background-color: #f8f9fa;
        }
        
        .form-control:focus {
            outline: none;
            border-color: var(--primary-color);
            box-shadow: 0 0 0 3px rgba(74, 111, 165, 0.2);
            background-color: white;
        }
        
        .input-icon {
            position: absolute;
            right: 15px;
            top: 38px;
            color: #adb5bd;
        }
        
        .btn-login {
            background: var(--primary-color);
            color: white;
            border: none;
            border-radius: 8px;
            padding: 14px;
            font-size: 16px;
            font-weight: 600;
            width: 100%;
            cursor: pointer;
            transition: var(--transition);
            margin-top: 10px;
        }
        
        .btn-login:hover {
            background: var(--primary-dark);
            transform: translateY(-2px);
        }
        
        .btn-login:active {
            transform: translateY(0);
        }
        
        .register-hint {
            text-align: center;
            margin-top: 20px;
            font-size: 14px;
            color: #6c757d;
        }
        
        .register-hint a {
            color: var(--primary-color);
            text-decoration: none;
            font-weight: 500;
        }
        
        .register-hint a:hover {
            text-decoration: underline;
        }
        
        .error-message {
            background: #f8d7da;
            color: #721c24;
            padding: 12px 15px;
            border-radius: 8px;
            margin-bottom: 20px;
            border-left: 4px solid #dc3545;
            font-size: 14px;
            display: flex;
            align-items: center;
        }
        
        .error-message i {
            margin-right: 8px;
        }
        
        .alert {
            background: #d1ecf1;
            color: #0c5460;
            padding: 12px 15px;
            border-radius: 8px;
            margin-bottom: 20px;
            border-left: 4px solid #17a2b8;
            font-size: 14px;
            display: flex;
            align-items: center;
        }
        
        .alert i {
            margin-right: 8px;
        }
        
        .library-icon {
            font-size: 40px;
            margin-bottom: 15px;
            color: white;
        }
        
        @media (max-width: 480px) {
            .login-card {
                border-radius: 12px;
            }
            
            .login-header, .login-body {
                padding: 20px;
            }
        }
    </style>
    <!-- 引入Font Awesome图标 -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</head>

<body>
    <div class="login-container">
        <div class="login-card">
            <div class="login-header">
                <div class="library-icon">
                    <i class="fas fa-book-reader"></i>
                </div>
                <h1>图书馆管理系统</h1>
                <p>欢迎登录，请使用您的账号密码</p>
            </div>
            
            <div class="login-body">
                <!-- 系统欢迎信息 -->
                <div class="alert">
                    <i class="fas fa-info-circle"></i>
                    欢迎登录图书馆管理系统
                </div>
                
                <!-- 错误提示容器 -->
                <% 
                    String errorMsg = (String) session.getAttribute("msg");
                    if (errorMsg != null) {
                %>
                <div class="error-message">
                    <i class="fas fa-exclamation-triangle"></i>
                    <%= errorMsg %>
                    <% session.removeAttribute("msg"); // 显示后立即清除 %>
                </div>
                <% } %>

                <!-- 登录表单 -->
                <form class="login-form" method="post" 
                    action="<%=basePath%>login?method=login" onsubmit="return validateForm()">
                    
                    <div class="form-group">
                        <label class="form-label" for="username">
                            <i class="fas fa-user"></i> 账号
                        </label> 
                        <input
                            type="text" 
                            class="form-control" 
                            id="username" 
                            name="account"
                            required="required" 
                            placeholder="请输入您的账号" 
                            oninput="clearError()"
                        />
                        <div class="input-icon">
                            <i class="fas fa-user"></i>
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label class="form-label" for="password">
                            <i class="fas fa-lock"></i> 密码
                        </label> 
                        <input
                            type="password" 
                            class="form-control" 
                            id="password"
                            name="password" 
                            required="required" 
                            placeholder="请输入您的密码"
                            oninput="clearError()"
                        />
                        <div class="input-icon">
                            <i class="fas fa-lock"></i>
                        </div>
                    </div>

                    <button type="submit" class="btn-login">
                        <i class="fas fa-sign-in-alt"></i> 登 录
                    </button>
                    
                    <div class="register-hint">
                        没有账号？请联系系统管理员
                    </div>
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
            errorDiv.innerHTML = '<i class="fas fa-exclamation-triangle"></i> ' + message;
            document.querySelector(".login-form").prepend(errorDiv);
            
            // 3秒后自动移除
            setTimeout(() => {
                errorDiv.remove();
            }, 3000);
        }

        // 输入时清除错误提示
        function clearError() {
            const errorMessages = document.querySelectorAll(".error-message");
            errorMessages.forEach(msg => {
                if (!msg.querySelector('i.fa-exclamation-triangle')) {
                    msg.remove();
                }
            });
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