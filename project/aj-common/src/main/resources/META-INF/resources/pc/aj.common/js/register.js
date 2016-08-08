// JavaScript Document

function check() {
	
	var aName = document.register.aName.value;
	var limit = /^[0-9a-zA-Z\u4E00-\u9FA5]+$/;
	var namelimit = aName.match(limit);
	
	if (aName == "") {
		alert("用户名不能为空，请重新输入");
		document.register.aName.focus();
		return false;
	}else
	if (namelimit == null){
		alert("用户名只能是数字、字母或汉字，请重新输入");
		document.register.aName.focus();
		return false;
	}else
	if (aName.length < 4) {
		alert("用户名不能少于4个字符，请重新输入");
		document.register.aName.focus();
		return false;
	}
	
	var aPwd = document.register.aPwd.value;
	var reAPwd = document.register.reAPwd.value;
	var passwordlimit = /^[0-9a-zA-Z\u4E00-\u9FA5&,.!#]+$/;
	var matchreg1 = aPwd.match(passwordlimit);
	var matchreg2 = reAPwd.match(passwordlimit);
	
	if (aPwd == "") {
		alert("密码不能为空");
		document.register.aPwd.focus();
		return false;
	}else 
	if (matchreg1 == null) {
		alert("密码不能包含特殊字符");
		document.register.aPwd.focus();
		return false;
	}else
	if (aPwd.length < 6) {
		alert("密码不能少于6个字符");
		document.register.aPwd.focus();
		return false;
	} else {
		
			if (aPwd != reAPwd) {
				alert("两次密码不匹配，请重新输入！");
				document.register.reAPwd.focus();
			return false;
			}
		
	}
	var aPhone = document.register.aPhone.value;
	var aPhonereg = /^[0-9]{11}$/;
	var aPhoneMatch = aPhone.match(aPhonereg);
	if (aPhone == "" || aPhoneMatch == null) {
		alert("手机号码只能是11位数字");
		document.register.aPhone.focus();
		return false;
	}
	
	var aQq = document.register.aQq.value;
	var aQqreg = /^[0-9]{4,}$/;
	var aPhoneMatch = aQq.match(aQqreg);
	if (aQq == "" || aQqreg == null) {
		alert("QQ号码只能是4位以上数字");
		document.register.aQq.focus();
		return false;
	}
	
	var email = document.register.email.value;
	var emailreg = /^(.+)@(.+)$/;
	var match = email.match(emailreg);
	if (email == "" || match == null) {
		alert("请填写合法的邮件地址'");
		document.register.email.focus();
		return false;
	}
	
}


//var xmlHttp;

//下面实现ajax异步处理验证码刷新
   function createXMLHttpRequest() {

		//表示当前浏览器不是ie,如ns,firefox

		if(window.XMLHttpRequest) {
		
		xmlHttp = new XMLHttpRequest();
		//alert("ie");
		
		} else if (window.ActiveXObject) 
		{
			//alert("非ie");
		
		xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
		
		}

    }

     //客户端事件触发：     

  function validate(){
         
 //创建XMLHttpRequest

		 createXMLHttpRequest() ;
		
		 var url="http://localhost:8081/cloudclick/verifyCode";
		
		 //alert(url);
		
		 xmlHttp.open("GET", url, true);
		
		 //方法地址。处理完成后自动调用，回调。
		
		 xmlHttp.onreadystatechange=callback ;
		
		 xmlHttp.send(null);//将参数发送到Ajax引擎
		
		 //alert("callback后");
		  //alert(document.getElementById("verifyImg"));
		 //document.getElementById("verifyImg").innerHTML = "<img border=0 src='verifyCode'>";          

  }

//结果返回操作：

function callback()

 {       

	 //alert(xmlHttp.readyState);
	
	 if(xmlHttp.readyState==4){ //Ajax引擎初始化
	
	 if(xmlHttp.status==200){ //http协议成功
	
	 //alert(xmlHttp.responseText);
	$("#verifyImg").html(new File(xmlHttp.responseText)).show();
	
	 //document.getElementById("verifyImg").innerHTML = "" + xmlHttp.responseText;
	
	 }else
	
	{
	
		//alert("请求失败，错误码="+xmlHttp.status);
	
		}                
	
	 }

 }
 
 //$("#verifyImg").live("click", function () {
				//validate();						 
// document.getElementById("verifyImg").innerHTML = "<img border=0 src='verifyCode'>";
// 
//}); 


