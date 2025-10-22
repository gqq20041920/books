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
    <!-- 引入Font Awesome图标 -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <style>
        :root {
            --primary-color: #3f51b5;
            --primary-light: #757de8;
            --primary-dark: #002984;
            --accent-color: #ff5722;
            --light-color: #f5f5f5;
            --dark-color: #333;
            --success-color: #4caf50;
            --info-color: #2196f3;
            --warning-color: #ff9800;
            --danger-color: #f44336;
            --shadow-light: 0 2px 10px rgba(0,0,0,0.08);
            --shadow-medium: 0 5px 15px rgba(0,0,0,0.1);
            --shadow-heavy: 0 10px 25px rgba(0,0,0,0.15);
            --transition: all 0.3s ease;
        }
        
        /* 背景优化 */
        body {
            background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
            background-attachment: fixed;
            min-height: 100vh;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        
        /* 主容器 */
        .container {
            padding: 20px 0;
            max-width: 1200px;
        }
        
        /* 内容区域 */
        .content-area {
            background: white;
            border-radius: 12px;
            box-shadow: var(--shadow-medium);
            padding: 25px;
            margin-left: 15px;
        }
        
        /* 欢迎横幅 */
        .welcome-banner {
            background: linear-gradient(to right, var(--primary-color), var(--primary-light));
            color: white;
            border-radius: 10px;
            padding: 20px;
            margin-bottom: 30px;
            box-shadow: var(--shadow-light);
            display: flex;
            align-items: center;
            justify-content: space-between;
        }
        
        .welcome-text h2 {
            margin: 0 0 5px 0;
            font-weight: 600;
        }
        
        .welcome-text p {
            margin: 0;
            opacity: 0.9;
        }
        
        .welcome-icon {
            font-size: 40px;
            opacity: 0.8;
        }
        
        /* 功能卡片网格 */
        .features-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
            gap: 25px;
        }
        
        /* 功能卡片 */
        .feature-card {
            background: white;
            border-radius: 12px;
            overflow: hidden;
            transition: var(--transition);
            box-shadow: var(--shadow-light);
            border: 1px solid #eaeaea;
            height: 100%;
            display: flex;
            flex-direction: column;
        }
        
        .feature-card:hover {
            transform: translateY(-8px);
            box-shadow: var(--shadow-heavy);
        }
        
        .card-header {
            background: var(--primary-color);
            color: white;
            padding: 18px 20px;
            display: flex;
            align-items: center;
            justify-content: space-between;
        }
        
        .card-title {
            margin: 0;
            font-size: 18px;
            font-weight: 600;
            display: flex;
            align-items: center;
        }
        
        .card-title i {
            margin-right: 10px;
            font-size: 20px;
        }
        
        .card-badge {
            background: rgba(255, 255, 255, 0.2);
            border-radius: 20px;
            padding: 4px 12px;
            font-size: 12px;
            font-weight: 500;
        }
        
        .card-content {
            padding: 20px;
            flex-grow: 1;
            display: flex;
            flex-direction: column;
        }
        
        .card-content ul {
            padding: 0;
            margin: 0 0 15px 0;
            list-style: none;
        }
        
        .card-content li {
            padding: 8px 0;
            position: relative;
            padding-left: 20px;
            line-height: 1.5;
            color: #555;
        }
        
        .card-content li::before {
            content: "•";
            color: var(--primary-color);
            font-weight: bold;
            position: absolute;
            left: 0;
        }
        
        .card-footer {
            margin-top: auto;
            padding-top: 15px;
            border-top: 1px solid #f0f0f0;
            display: flex;
            justify-content: flex-end;
        }
        
        .card-action {
            color: var(--primary-color);
            text-decoration: none;
            font-weight: 500;
            font-size: 14px;
            display: flex;
            align-items: center;
            transition: var(--transition);
        }
        
        .card-action:hover {
            color: var(--primary-dark);
        }
        
        .card-action i {
            margin-left: 5px;
            font-size: 12px;
        }
        
        /* 统计数据卡片 */
        .stats-container {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 20px;
            margin-bottom: 30px;
        }
        
        .stat-card {
            background: white;
            border-radius: 10px;
            padding: 20px;
            box-shadow: var(--shadow-light);
            display: flex;
            align-items: center;
            transition: var(--transition);
        }
        
        .stat-card:hover {
            transform: translateY(-5px);
            box-shadow: var(--shadow-medium);
        }
        
        .stat-icon {
            width: 60px;
            height: 60px;
            border-radius: 10px;
            display: flex;
            align-items: center;
            justify-content: center;
            margin-right: 15px;
            font-size: 24px;
            color: white;
        }
        
        .stat-info h3 {
            margin: 0;
            font-size: 24px;
            font-weight: 700;
        }
        
        .stat-info p {
            margin: 5px 0 0 0;
            color: #666;
            font-size: 14px;
        }
        
        /* 响应式调整 */
        @media (max-width: 992px) {
            .content-area {
                margin-left: 0;
                margin-top: 20px;
            }
            
            .features-grid {
                grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
            }
        }
        
        @media (max-width: 768px) {
            .welcome-banner {
                flex-direction: column;
                text-align: center;
            }
            
            .welcome-icon {
                margin-top: 10px;
            }
            
            .features-grid {
                grid-template-columns: 1fr;
            }
            
            .stats-container {
                grid-template-columns: repeat(2, 1fr);
            }
        }
        
        @media (max-width: 576px) {
            .stats-container {
                grid-template-columns: 1fr;
            }
            
            .content-area {
                padding: 15px;
            }
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
            
            <!-- 主要内容区域 -->
            <div class="col-md-10">
                <div class="content-area">
                    <!-- 欢迎横幅 -->
                    <div class="welcome-banner">
                        <div class="welcome-text">
                            <h2>欢迎回来，${userDB.name}！</h2>
                            <p>今天是 <span id="current-date"></span>，祝您在图书馆度过愉快的一天</p>
                        </div>
                        <div class="welcome-icon">
                            <i class="fas fa-book-reader"></i>
                        </div>
                    </div>
                    
                    <!-- 统计数据 -->
                    <div class="stats-container">
                        <div class="stat-card">
                            <div class="stat-icon" style="background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);">
                                <i class="fas fa-book"></i>
                            </div>
                            <div class="stat-info">
                                <h3>1,254</h3>
                                <p>馆藏图书</p>
                            </div>
                        </div>
                        <div class="stat-card">
                            <div class="stat-icon" style="background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);">
                                <i class="fas fa-users"></i>
                            </div>
                            <div class="stat-info">
                                <h3>586</h3>
                                <p>注册读者</p>
                            </div>
                        </div>
                        <div class="stat-card">
                            <div class="stat-icon" style="background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);">
                                <i class="fas fa-exchange-alt"></i>
                            </div>
                            <div class="stat-info">
                                <h3>324</h3>
                                <p>本月借阅</p>
                            </div>
                        </div>
                        <div class="stat-card">
                            <div class="stat-icon" style="background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%);">
                                <i class="fas fa-chart-line"></i>
                            </div>
                            <div class="stat-info">
                                <h3>92%</h3>
                                <p>借阅满意度</p>
                            </div>
                        </div>
                    </div>
                    
                    <!-- 功能卡片网格 -->
                    <div class="features-grid">
                        <!-- 图书查询 -->
                        <div class="feature-card">
                            <div class="card-header">
                                <h3 class="card-title"><i class="fas fa-search"></i> 图书查询</h3>
                                <span class="card-badge">常用功能</span>
                            </div>
                            <div class="card-content">
                                <ul>
                                    <li>根据图书名称、作者、分类查询图书信息</li>
                                    <li>可查询图书的编号、名称、分类、作者、在馆数量等</li>
                                    <li>支持高级筛选和模糊搜索</li>
                                </ul>
                                <div class="card-footer">
                                    <a href="#" class="card-action">
                                        立即查询 <i class="fas fa-arrow-right"></i>
                                    </a>
                                </div>
                            </div>
                        </div>
                        
                        <!-- 借阅信息 -->
                        <div class="feature-card">
                            <div class="card-header">
                                <h3 class="card-title"><i class="fas fa-book-open"></i> 借阅信息</h3>
                                <span class="card-badge">个人中心</span>
                            </div>
                            <div class="card-content">
                                <ul>
                                    <li>展示所借图书的基本信息，借阅日期、截止还书日期等</li>
                                    <li>一键续借功能，方便延长借阅时间</li>
                                    <li>逾期提醒和罚款计算</li>
                                </ul>
                                <div class="card-footer">
                                    <a href="#" class="card-action">
                                        查看详情 <i class="fas fa-arrow-right"></i>
                                    </a>
                                </div>
                            </div>
                        </div>
                        
                        <!-- 热门推荐 -->
                        <div class="feature-card">
                            <div class="card-header">
                                <h3 class="card-title"><i class="fas fa-fire"></i> 热门推荐</h3>
                                <span class="card-badge">热门</span>
                            </div>
                            <div class="card-content">
                                <ul>
                                    <li>展示每一本书的借阅量，包括图书基本信息</li>
                                    <li>可以在当前界面直接借阅，并且可以查询书籍借阅量</li>
                                    <li>个性化推荐基于您的阅读历史</li>
                                </ul>
                                <div class="card-footer">
                                    <a href="#" class="card-action">
                                        查看推荐 <i class="fas fa-arrow-right"></i>
                                    </a>
                                </div>
                            </div>
                        </div>
                        
                        <!-- 借阅历史 -->
                        <div class="feature-card">
                            <div class="card-header">
                                <h3 class="card-title"><i class="fas fa-history"></i> 借阅历史</h3>
                            </div>
                            <div class="card-content">
                                <ul>
                                    <li>查询借阅历史，借阅时长等具体信息</li>
                                    <li>统计阅读偏好和频率</li>
                                    <li>导出借阅记录功能</li>
                                </ul>
                                <div class="card-footer">
                                    <a href="#" class="card-action">
                                        查看历史 <i class="fas fa-arrow-right"></i>
                                    </a>
                                </div>
                            </div>
                        </div>
                        
                        <!-- 最佳读者 -->
                        <div class="feature-card">
                            <div class="card-header">
                                <h3 class="card-title"><i class="fas fa-trophy"></i> 最佳读者</h3>
                            </div>
                            <div class="card-content">
                                <ul>
                                    <li>展示每一位已知读者的借阅量，以及读者的基本信息</li>
                                    <li>月度/年度读者排行榜</li>
                                    <li>优秀读者奖励和特权</li>
                                </ul>
                                <div class="card-footer">
                                    <a href="#" class="card-action">
                                        查看排名 <i class="fas fa-arrow-right"></i>
                                    </a>
                                </div>
                            </div>
                        </div>
                        
                        <!-- 问题反馈 -->
                        <div class="feature-card">
                            <div class="card-header">
                                <h3 class="card-title"><i class="fas fa-comments"></i> 问题反馈</h3>
                            </div>
                            <div class="card-content">
                                <ul>
                                    <li>有问题请留下您的联系方式，我们会记录在库并及时反馈</li>
                                    <li>多种反馈渠道：在线表单、邮箱、电话</li>
                                    <li>常见问题解答和自助服务</li>
                                </ul>
                                <div class="card-footer">
                                    <a href="#" class="card-action">
                        提交反馈 <i class="fas fa-arrow-right"></i>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="common/userInfo.jsp"></jsp:include>
    <jsp:include page="common/js.jsp"></jsp:include>
    
    <script>
        // 设置当前日期
        function setCurrentDate() {
            const now = new Date();
            const options = { year: 'numeric', month: 'long', day: 'numeric', weekday: 'long' };
            document.getElementById('current-date').textContent = now.toLocaleDateString('zh-CN', options);
        }
        
        // 页面加载完成后执行
        document.addEventListener('DOMContentLoaded', function() {
            setCurrentDate();
            
            // 为卡片添加点击效果
            const cards = document.querySelectorAll('.feature-card');
            cards.forEach(card => {
                card.addEventListener('click', function(e) {
                    if (e.target.tagName !== 'A') {
                        const link = this.querySelector('.card-action');
                        if (link) {
                            window.location.href = link.href;
                        }
                    }
                });
            });
        });
    </script>
</body>
</html>