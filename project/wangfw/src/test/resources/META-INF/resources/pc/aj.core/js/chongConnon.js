// JavaScript Document

function check() {
	
var uName = document.login.uName.value;
	var limit = /^[0-9a-zA-Z\u4E00-\u9FA5]+$/;
	var namelimit = uName.match(limit);
	
	if (uName == "") {
		alert("�û�������Ϊ�գ�����������");
		document.login.uName.focus();
		return false;
	}else
	if (namelimit == null){
		alert("�û���ֻ�������֡���ĸ���֣�����������");
		document.login.uName.focus();
		return false;
	}else
	if (uName.length < 4) {
		alert("�û�����������4���ַ�������������");
		document.login.uName.focus();
		return false;
	}
	
	var uPwd = document.login.uPwd.value;
	var reuPwd = document.login.reuPwd.value;
	var passwordlimit = /^[0-9a-zA-Z\u4E00-\u9FA5&,.!#]+$/;
	var matchreg1 = uPwd.match(passwordlimit);
	var matchreg2 = reuPwd.match(passwordlimit);
	
	if (uPwd == "") {
		alert("���벻��Ϊ��");
		document.login.uPwd.focus();
		return false;
	}else 
	if (matchreg1 == null) {
		alert("���벻�ܰ��������ַ�");
		document.login.uPwd.focus();
		return false;
	}else
	if (uPwd.length < 6) {
		alert("���벻������6���ַ�");
		document.login.uPwd.focus();
		return false;
	} else {
		
			if (uPwd != reuPwd) {
				alert("�������벻ƥ�䣬���������룡");
				document.login.reuPwd.focus();
			return false;
			}
		
	}
}