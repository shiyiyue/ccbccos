//////��ʼ����Ϣ
function body_load()
{
	///��ʾ�������ݼ��߶ȵĶ��� ��ʽdivfd_ID

	 divfd_roleTable.style.height="100%";
	 roleTable.fdwidth="100%";
     roleTable.actionname     ="sm0063";
     ///MenuTable.addmethodname  ="addenum";
     roleTable.editmethodname ="editenum";
     roleTable.delmethodname  ="delenum";

     initDBGrid("roleTable");

}


/////�����ݱ��TD�ĵ����¼�
function roleTable_TDclick(el){

	el = event.srcElement;

	var trobj =getOwnerTR(el);

	if (trobj.edit=="true")
	{
		///������������������Ӷ���
		if (el.fieldtype=="text"){
			CreateText(el);
		}

	}
}

