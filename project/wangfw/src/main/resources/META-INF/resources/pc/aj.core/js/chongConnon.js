// JavaScript Document

function check() {
	
var uName = document.login.uName.value;
	var limit = /^[0-9a-zA-Z\u4E00-\u9FA5]+$/;
	var namelimit = uName.match(limit);
	
	if (uName == "") {
		alert("用户名不能为空，请重新输入");
		document.login.uName.focus();
		return false;
	}else
	if (namelimit == null){
		alert("用户名只能是数字、字母或汉字，请重新输入");
		document.login.uName.focus();
		return false;
	}else
	if (uName.length < 4) {
		alert("用户名不能少于4个字符，请重新输入");
		document.login.uName.focus();
		return false;
	}
	
	var uPwd = document.login.uPwd.value;
	var reuPwd = document.login.reuPwd.value;
	var passwordlimit = /^[0-9a-zA-Z\u4E00-\u9FA5&,.!#]+$/;
	var matchreg1 = uPwd.match(passwordlimit);
	var matchreg2 = reuPwd.match(passwordlimit);
	
	if (uPwd == "") {
		alert("密码不能为空");
		document.login.uPwd.focus();
		return false;
	}else 
	if (matchreg1 == null) {
		alert("密码不能包含特殊字符");
		document.login.uPwd.focus();
		return false;
	}else
	if (uPwd.length < 6) {
		alert("密码不能少于6个字符");
		document.login.uPwd.focus();
		return false;
	} else {
		
			if (uPwd != reuPwd) {
				alert("两次密码不匹配，请重新输入！");
				document.login.reuPwd.focus();
			return false;
			}
		
	}
}