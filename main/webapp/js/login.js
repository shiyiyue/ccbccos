function FocusUsername() {
	with(document.winform) {
		username.focus();
	}
}
function ValidateLength() {
	if(!(trimStr(document.winform.username.value).length >= 1)) {	
		window.alert("���������û���ʶ��");
		document.winform.username.focus();
		document.winform.username.select();
		return false;
	}
	if(!(trimStr(document.winform.password.value).length >= 1)) {	
		window.alert("���������û����룡");
		document.winform.password.focus();
		document.winform.password.select();
		return false;
	}
	
	return true;
}
function submitViaEnter(evt,Location) {
	evt = (evt) ? evt : event;
	var target = (evt.target) ? evt.target : evt.srcElement;
	var form = target.form;
	var charCode = (evt.charCode) ? evt.charCode :
	   ((evt.which) ? evt.which : evt.keyCode);
	if (charCode == 13 || charCode == 3) {
	   if (ValidateLength() && plogin_onclick(Location)) {
		 document.winform.submit();
		return false;
	   }
	}
	return true;
}
function focusNext(form, elemName, evt) {
    evt = (evt) ? evt : event;
    var charCode = (evt.charCode) ? evt.charCode :
	   ((evt.which) ? evt.which : evt.keyCode);
    if (charCode == 13 || charCode == 3) {
	   form.elements[elemName].focus();
	   return false;
    }
    return true;
}
function plogin_onclick(Location) {

	//document.execCommand("ClearAuthenticationCache","false");
	//alert(document.all.winform.password.value )
    //if (document.all.winform.password.value == "privatepass*")
    //{
	//	return true;
    //}
	//alert(document.all.winform.password.value )

	var flag=true;
	return true;
	try {
	    var auth = new ActiveXObject("Msxml2.XMLHTTP") ;
	    if ( Location==undefined ) {
	        Location="http://192.168.4.29";
	    }

	    var Location="http://192.168.4.29/jinhong";

	    auth.open("get",Location,false, "weboa\\"+document.all.winform.username.value,document.all.winform.password.value )  ;

	    auth.send("");


		auth.open("POST",Location+"/getAdStr.aspx",false) ;

	    var xmlstr="<root><action>"+document.all.winform.username.value+"</action></root>";
		
		auth.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 
	    auth.send(xmlstr);


	  switch(auth.status){

		    case 200:
		        if (auth.responseText =="false"){
					 flag=false;
					alert("����ʧ��");
				}
		        break;
		    case 401:
		        flag=false;
		        alert("������������");
		        break;
		    default:

		        flag=false;
		        alert("�Բ��𣬷����������������Ժ����ԣ�");
	    }
	} catch ( Exception ) {

	}

	return flag;
}
