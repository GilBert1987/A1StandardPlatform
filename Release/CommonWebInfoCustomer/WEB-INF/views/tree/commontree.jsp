<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=8,chrome=1" />
<link rel="stylesheet" type="text/css" href="../../css/tree.css" />
<link rel="stylesheet" type="text/css" href="../../css/jquery-webox.css" />
<link href="../../assets/css/bootstrap.min.css" rel="stylesheet"/>
<link href="../../css/style.css?v=4.1.0" rel="stylesheet">
<link rel="shortcut icon" href="../../assets/img/favicon.png" type="image/x-icon">
<!--Basic Styles-->
<link id="bootstrap-rtl-link" href="" rel="stylesheet">
<link href="../../assets/css/font-awesome.min.css" rel="stylesheet">
<link href="../../assets/css/weather-icons.min.css" rel="stylesheet">
<!--Fonts-->
<link href="../../css/usesofont.css" rel="stylesheet" type="text/css">
<!--Beyond styles-->
<link href="../../assets/css/demo.min.css" rel="stylesheet">
<link href="../../assets/css/typicons.min.css" rel="stylesheet">
<link href="../../assets/css/animate.min.css" rel="stylesheet">
<link href="../../css/tree.css" rel="stylesheet">
<link id="skin-link" href="" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../../js/jquery.js"></script>
<script type="text/javascript" src="../../js/jquery-webox.js"></script>
<script type="text/javascript" src="../../js/jquery.url.js"></script>
<script type="text/javascript" src="../../js/common.js?ver=1.4"></script>
<script type="text/javascript" src="../../js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
<script type="text/javascript" src="../../js/treemain.js?ver=1.02"></script>
<script type="text/javascript" src="../../js/frame.js?ver=1.01"></script>
<title>通用树</title>
</head>
<!-- onload="initTreeMain('${requestScope._viewpath}');" -->
<body onload="initTreeMain('${requestScope._viewpath}');">
	<table border="0" width="100%" height="100%" cellspacing="0"
		cellpadding="0">
		<tr>
			<td style="vertical-align:top;">
            <iframe id="${requestScope._viewpath}_frameleft" 
					style="BORDER-RIGHT: buttonface 1px dashed;height:100%;" name="Tree"
					frameborder="0" src="${requestScope._urlInfo}" scrolling="auto"
					></iframe>
            </td>
			<td width="2px"></td>
			<td style="vertical-align:top;text-align:left;">
            	<iframe id="${requestScope._viewpath}_Main" name="${requestScope._viewpath}_Main" frameborder="0" src="../tree/empty"
					scrolling="auto" style="width:100%;height:100%;"></iframe>
            </td>
		</tr>
	</table>
</body>
</html>