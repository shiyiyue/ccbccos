//////��ʼ����Ϣ
function body_load() {
	if (parent.window.paramValue) {
		m_objParam = parent.window.paramValue.value;
	} else {
		m_objParam = "";
	}
	var tab = document.all("MenuTable");
	var whereStr = "and (parentmenuid='" + m_objParam + "') order by Levelidx";
	tab.whereStr = whereStr;
	MenuTable.actionname = "sm0021";
	divfd_MenuTable.style.height=document.body.offsetHeight - 130;
	MenuTable.fdwidth="100%";
//	MenuTable.fdwidth="800px";
     ///MenuTable.addmethodname  ="addenum";
	MenuTable.editmethodname = "editenum";
	MenuTable.delmethodname = "delenum";
	Table_Refresh("MenuTable", false);
	initDBGrid("MenuTable");
}



/////�����ݱ��TD�ĵ����¼�
function MenuTable_TDclick(el) {
	el = event.srcElement;
	var trobj = getOwnerTR(el);
	if (trobj.edit == "true") {
		///������������������Ӷ���
		if (el.fieldtype == "text") {
			CreateText(el);
		}
		if (el.fieldtype == "number") {
			CreateNumberText(el);
		}
		if (el.fieldtype == "dropdown") {
			switch (el.fieldname) {
			  case "targetMachine":
				el.enumType = "matchinetype";
				el.fieldTitle = "\u7f16\u7801,\u540d\u79f0";
				break;
			  case "OpenWindow":
				el.enumType = "openwindow";
				el.fieldTitle = "\u7f16\u7801,\u540d\u79f0";
				break;
			}
			dropdown(el);
		}
	}
}

////////////ö�����һ����¼
function MenuTable_updateRecord_click(tab) {
	updateRecord(tab, "ParentMenuID&text&" + parent.window.paramValue.value);
}

