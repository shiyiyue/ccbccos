//////��ʼ����Ϣ
var menu;

function body_load()
{

    divfd_ActionTable.style.height = document.body.clientHeight - 180 + "px";
	 //divfd_ActionTable.style.height="100%";
	 ActionTable.fdwidth="100%";
     ActionTable.actionname     ="sm0011";
     ActionTable.addmethodname  ="addenum";
     ActionTable.editmethodname ="editenum";
     ActionTable.delmethodname  ="delenum";

    initDBGrid("ActionTable");
}


/////�����ݱ��TD�ĵ����¼�
function ActionTable_TDclick(el){


	el = event.srcElement;

	var trobj =getOwnerTR(el);

	if (trobj.edit=="true")
	{
		///������������������Ӷ���
		if (el.fieldtype=="text")
		{

               CreateText(el);
		}
		if (el.fieldType=="number")
		{
			CreateNumberText(el);
		}
	}


}


////////��ѯ����
function queryClick(){

	var  whereStr =" 1=1 ";

	if (trimStr(document.all["cationid"].value) != "")
		whereStr += " and ( LogicCode like '%"+document.all.cationid.value+"%')";

	if (trimStr(document.all["actionclass"].value) != "")
		whereStr += " and ( LogicClass like '%"+document.all.actionclass.value+"%')";

	if (trimStr(document.all["actiondes"].value) != "")
		whereStr += " and ( LogicDesc like '%"+document.all.actiondes.value+"%')";

	

	if (whereStr !=document.all["ActionTable"].whereStr){
		document.all["ActionTable"].setAttribute("whereStr", whereStr +" order by 1 ");
//		document.all["ActionTable"].whereStr=whereStr +" order by 1 ";
		document.all["ActionTable"].setAttribute("RecordCount","0");
		document.all["ActionTable"].setAttribute("AbsolutePage","1");


//        var tab = document.all["ActionTable"];
//        alert(tab.getAttribute("whereStr"));
		Table_Refresh("ActionTable");
//		Table_Refresh_asy("ActionTable");
    }

}
