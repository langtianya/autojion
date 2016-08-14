<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="user-scalable=no, width=device-width, initial-scale=1.0, maximum-scale=1.0"/>
<link rel="stylesheet" href="${contextPath}/error-rs/styles/style.css">
</head>
<script type="text/javascript">
$(function() {
    var lang = '${locale}';
    if (lang == 'zh_CN') {
        $("#meserr").text("404:资源未找到！");
    } else if (lang == 'zh_TW') {
        $("#meserr").text("404:資源未找到！");
    } else {
        $("#meserr").text("404:Resource not found!");
    }
    var reason = "${requestScope['javax.servlet.error.message']}";
    if (reason != '') {
        if (reason.length > 4 && reason.substr(0, 4) == 'key:') {
            var tmp = $.i18n.get(reason.substr(4));
            if (tmp == null || tmp == undefined) {
                tmp = reason;
            }
            $("#reason").text(tmp);
        } else {
            $("#reason").text(reason);
        }
    }
    $("#container").i18n();
});
</script>
<body>
	<div id="error-container">
		<div id="error" class="err404">
            <div class="pacman left"></div>
            <div class="pacman right pacman_eats"></div>
        </div>
		<div id="container">
			<div id="title">
				<h1 id="meserr"></h1>
			</div>
			<div id="content">
				<div class="center">
					<p class="no-top" i18n="error.reason">可能原因:</p>
					<ul>
						<li id="reason">失效连接</li>
						<li i18n="error.reason.badlink">URL输入错误</li>
					</ul>
					<div class="clearfix"></div>
				</div>
				<div class="clearfix"></div>
			</div>
		</div>
	</div>
</body>
</html>
