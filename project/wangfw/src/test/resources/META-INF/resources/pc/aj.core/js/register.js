// JavaScript Document

function check() {
	
	var aName = document.register.aName.value;
	var limit = /^[0-9a-zA-Z\u4E00-\u9FA5]+$/;
	var namelimit = aName.match(limit);
	
	if (aName == "") {
		alert("�û�������Ϊ�գ�����������");
		document.register.aName.focus();
		return false;
	}else
	if (namelimit == null){
		alert("�û���ֻ�������֡���ĸ���֣�����������");
		document.register.aName.focus();
		return false;
	}else
	if (aName.length < 4) {
		alert("�û�����������4���ַ�������������");
		document.register.aName.focus();
		return false;
	}
	
	var aPwd = document.register.aPwd.value;
	var reAPwd = document.register.reAPwd.value;
	var passwordlimit = /^[0-9a-zA-Z\u4E00-\u9FA5&,.!#]+$/;
	var matchreg1 = aPwd.match(passwordlimit);
	var matchreg2 = reAPwd.match(passwordlimit);
	
	if (aPwd == "") {
		alert("���벻��Ϊ��");
		document.register.aPwd.focus();
		return false;
	}else 
	if (matchreg1 == null) {
		alert("���벻�ܰ��������ַ�");
		document.register.aPwd.focus();
		return false;
	}else
	if (aPwd.length < 6) {
		alert("���벻������6���ַ�");
		document.register.aPwd.focus();
		return false;
	} else {
		
			if (aPwd != reAPwd) {
				alert("�������벻ƥ�䣬���������룡");
				document.register.reAPwd.focus();
			return false;
			}
		
	}
	var aPhone = document.register.aPhone.value;
	var aPhonereg = /^[0-9]{11}$/;
	var aPhoneMatch = aPhone.match(aPhonereg);
	if (aPhone == "" || aPhoneMatch == null) {
		alert("�ֻ�����ֻ����11λ����");
		document.register.aPhone.focus();
		return false;
	}
	
	var aQq = document.register.aQq.value;
	var aQqreg = /^[0-9]{4,}$/;
	var aPhoneMatch = aQq.match(aQqreg);
	if (aQq == "" || aQqreg == null) {
		alert("QQ����ֻ����4λ��������");
		document.register.aQq.focus();
		return false;
	}
	
	var email = document.register.email.value;
	var emailreg = /^(.+)@(.+)$/;
	var match = email.match(emailreg);
	if (email == "" || match == null) {
		alert("����д�Ϸ����ʼ���ַ'");
		document.register.email.focus();
		return false;
	}
	
}


//var xmlHttp;

//����ʵ��ajax�첽������֤��ˢ��
   function createXMLHttpRequest() {

		//��ʾ��ǰ���������ie,��ns,firefox

		if(window.XMLHttpRequest) {
		
		xmlHttp = new XMLHttpRequest();
		//alert("ie");
		
		} else if (window.ActiveXObject) 
		{
			//alert("��ie");
		
		xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
		
		}

    }

     //�ͻ����¼�������     

  function validate(){
         
 //����XMLHttpRequest

		 createXMLHttpRequest() ;
		
		 var url="http://localhost:8081/cloudclick/verifyCode";
		
		 //alert(url);
		
		 xmlHttp.open("GET", url, true);
		
		 //������ַ��������ɺ��Զ����ã��ص���
		
		 xmlHttp.onreadystatechange=callback ;
		
		 xmlHttp.send(null);//���������͵�Ajax����
		
		 //alert("callback��");
		  //alert(document.getElementById("verifyImg"));
		 //document.getElementById("verifyImg").innerHTML = "<img border=0 src='verifyCode'>";          

  }

//������ز�����

function callback()

 {       

	 //alert(xmlHttp.readyState);
	
	 if(xmlHttp.readyState==4){ //Ajax�����ʼ��
	
	 if(xmlHttp.status==200){ //httpЭ��ɹ�
	
	 //alert(xmlHttp.responseText);
	$("#verifyImg").html(new File(xmlHttp.responseText)).show();
	
	 //document.getElementById("verifyImg").innerHTML = "" + xmlHttp.responseText;
	
	 }else
	
	{
	
		//alert("����ʧ�ܣ�������="+xmlHttp.status);
	
		}                
	
	 }

 }
 
 //$("#verifyImg").live("click", function () {
				//validate();						 
// document.getElementById("verifyImg").innerHTML = "<img border=0 src='verifyCode'>";
// 
//}); 


