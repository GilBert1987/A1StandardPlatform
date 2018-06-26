<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>登录</title>
	<link href="css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="css/font-awesome.css?v=4.4.0" rel="stylesheet">

    <link href="css/animate.css" rel="stylesheet">
    <link href="css/style.css?v=4.1.0" rel="stylesheet">
    <!--[if lt IE 9]>
    <![endif]-->
    
</head>

<body class="gray-bg">

    <div class="middle-box text-center loginscreen  animated fadeInDown">
        <div>
            <div>

                <h1 class="logo-name">
			
		</h1>

            </div>
            <h3>欢迎使用A1开发平台系统</h3>

            <form class="m-t" role="form"  method="post" id="formID" action="login">
                <div class="form-group">
                    <input type="text" name="username" class="form-control" placeholder="用户名" required>
                </div>
                <div class="form-group">
                    <input  name="password" type="password" class="form-control" placeholder="密码" required>
                </div>
                <button type="submit" name="loginBtn" id="login" class="btn btn-primary block full-width m-b">登 录</button>


               

            </form>
        </div>
    </div>

    <!-- 全局js -->
    <script src="js/jquery.js?v=2.1.4"></script>
    <script src="js/bootstrap.min.js?v=3.3.6"></script>
    <script type="text/javascript" src="js/login.js"></script>
    

</body>

</html>
