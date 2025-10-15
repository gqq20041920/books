<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    if (session.getAttribute("userDB") == null){
        response.sendRedirect(request.getContextPath() + "/login.jsp");
    }    
%>
<!DOCTYPE HTML>
<html>
<head>
    <title>图书馆管理系统</title>
    <jsp:include page="common/css.jsp"></jsp:include>    
    <style>
        /* 背景优化 - 增加模糊和遮罩提升文字可读性 */
        body {
            background-image: url("static/images/03.jpg");
            background-size: cover;
            background-position: center;
            background-attachment: fixed;
            background-repeat: no-repeat;
            position: relative;
            min-height: 100vh;
        }
        
        body::before {
            content: "";
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background-color: rgba(255, 255, 255, 0.85); /* 白色遮罩提升文字清晰度 */
            z-index: -1;
        }
        
        /* 容器内边距调整 */
        .container {
            padding: 20px 0;
            position: relative;
            z-index: 1;
        }
        
        /* 面板美化 */
        .panel {
            border: none;
            border-radius: 8px;
            overflow: hidden;
            transition: all 0.3s ease;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
            margin-bottom: 25px;
        }
        
        .panel:hover {
            transform: translateY(-5px);
            box-shadow: 0 8px 16px rgba(0, 0, 0, 0.12);
        }
        
        /* 面板标题栏 */
        .panel-heading {
            background-color: #3f51b5; /* 图书馆主题蓝紫色 */
            color: white;
            padding: 15px 20px;
            border-bottom: none;
        }
        
        .text-muted.bootstrap-admin-box-title {
            color: white;
            font-size: 18px;
            font-weight: 500;
            display: flex;
            align-items: center;
        }
        
        .text-muted.bootstrap-admin-box-title::before {
            content: "";
            display: inline-block;
            width: 4px;
            height: 18px;
            background-color: #ffd740;
            margin-right: 10px;
            border-radius: 2px;
        }
        
        /* 面板内容区 */
        .bootstrap-admin-panel-content {
            padding: 20px;
            background-color: white;
        }
        
        /* 列表样式优化 */
        .bootstrap-admin-panel-content ul {
            padding-left: 20px;
            margin-bottom: 0;
        }
        
        .bootstrap-admin-panel-content li {
            line-height: 1.8;
            color: #555;
            padding: 6px 0;
            position: relative;
            padding-left: 12px;
        }
        
        .bootstrap-admin-panel-content li::before {
            content: "•";
            color: #3f51b5;
            font-weight: bold;
            position: absolute;
            left: 0;
        }
        
        /* 响应式调整 */
        @media (max-width: 768px) {
            .col-md-6 {
                margin-bottom: 15px;
            }
            
            .panel-heading {
                padding: 12px 15px;
            }
            
            .text-muted.bootstrap-admin-box-title {
                font-size: 16px;
            }
        }
        
        /* 顶部导航栏与内容区间距 */
        .row {
            margin-top: 15px;
        }
    </style>
</head>

<body class="bootstrap-admin-with-small-navbar">
    <jsp:include page="common/top.jsp"></jsp:include>
    <div class="container">
        <div class="row">
            <c:if test="${userDB.role == 1}">
                <jsp:include page="common/user_left.jsp"></jsp:include>            
            </c:if>
            <c:if test="${userDB.role == 2}">
                <jsp:include page="common/left.jsp"></jsp:include>            
            </c:if>
            <!-- content -->
            <div class="col-md-10">
                <div class="row">
                    <div class="col-md-6">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <div class="text-muted bootstrap-admin-box-title">图书查询</div>
                            </div>
                            <div class="bootstrap-admin-panel-content">
                                <ul>
                                    <li>根据图书名称、作者、分类查询图书信息</li>
                                    <li>可查询图书的编号、名称、分类、作者、在馆数量等</li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <div class="text-muted bootstrap-admin-box-title">借阅信息</div>
                            </div>
                            <div class="bootstrap-admin-panel-content">
                                <ul>
                                    <li>展示所借图书的基本信息，借阅日期、截止还书日期等</li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-md-6">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <div class="text-muted bootstrap-admin-box-title">热门推荐</div>
                            </div>
                            <div class="bootstrap-admin-panel-content">
                                <ul>
                                    <li>展示每一本书的借阅量，包括图书基本信息</li>
                                    <li>可以在当前界面直接借阅，并且可以查询书籍借阅量</li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <div class="text-muted bootstrap-admin-box-title">借阅历史</div>
                            </div>
                            <div class="bootstrap-admin-panel-content">
                                <ul>
                                    <li>查询借阅历史，借阅时长等具体信息</li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-md-6">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <div class="text-muted bootstrap-admin-box-title">最佳读者</div>
                            </div>
                            <div class="bootstrap-admin-panel-content">
                                <ul>
                                    <li>展示每一位已知读者的借阅量，以及读者的基本信息</li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <div class="text-muted bootstrap-admin-box-title">问题反馈</div>
                            </div>
                            <div class="bootstrap-admin-panel-content">
                                <ul>
                                    <li>有问题请留下您的联系方式，我们会记录在库并及时反馈</li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="common/userInfo.jsp"></jsp:include>
    <jsp:include page="common/js.jsp"></jsp:include>
</body>
</html>