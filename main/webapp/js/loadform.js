function load_form() {

}

//���ߺ�������
function initFormElement(elename,elevalue) {
	var formElement = document.getElementById(elename);
    	if (formElement!=null) {
        eleval = decode(elevalue);
        formElement.oldvalue = eleval;
        
      //  alert(formElement.type);
        
        if ( formElement.type == "text") {
          initInput(elename,eleval);
         //�����ֶ�
        } else if ( formElement.type == "hidden") {
        	initInput(elename,eleval);
         //�����ֶ�
        } else if ( formElement.type == "password") {
        	initInput(elename,eleval);
        //��ѡ������
        }        //�����ı�������
        else if ( formElement.type == "textarea") {
	        initInput(elename,eleval);
        //��ѡ������
        } else if ( formElement.type == "checkbox" ) {
           	initCheckBox(elename,eleval);
        //��ѡ������
        } else if ( formElement.type == "radio" ) {
            	initRadio(elename,eleval);
        //�����˵�����
        } else if ( formElement.type == "select-one" ) {
		initSelect_One(elename,eleval);
        }
        //�б�˵�����
        else if ( formElement.type == "select-multiple" ) {

        }else if(formElement.type=="int"){
        	initInput(elename,eleval);
        }else if(formElement.type=="money"){
	        initInput(elename,eleval);
        }

    	}
}

//���ݶ�ѡ�������,��ʼֵ(��,�ָ�)�趨��ѡ���ֵ
function initCheckBox(cbname,val) {
     	tts = val.split(',');
        ft = document.all(cbname);
    	for (var i = 0 ; i < ft.length ; i++ ) {
              for (var j = 0; j < tts.length ; j++ ) {
               	  if (ft[i].value== tts[j]) {
               		ft[i].checked=true;
                        break;
                  }
            	}
        }
}

//���ݵ�ѡ������ƺ�ֵ��ʼ��ֵ
function initRadio(rdname,val) {
    	ft = document.all(rdname);
	for (var i = 0 ; i < ft.length ; i++ ) {
               	if (ft[i].value== val) {
              		ft[i].checked=true;
                       	break;
               	}
	}
}

//��ʼ���ı���
function initInput(ipname,val) {
        
    	ft = document.all(ipname);
    	if (ft!=null) {
                ft.value = val;
    	}
}

//��ʼ��������
function initSelect_One(soname,val) {
    	ft = document.all(soname);
    	if (ft!=null) {
                ft.value = val;
    	}
}