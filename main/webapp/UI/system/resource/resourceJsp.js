//////��ʼ����Ϣ
function body_load() {
  resourceTable.fdwidth = "1000px";
  divfd_resourceTable.style.height = document.body.offsetHeight - 130;
  resourceTable.actionname = "sm0071";
  // /MenuTable.addmethodname ="addenum";
  resourceTable.editmethodname = "editenum";
  resourceTable.delmethodname = "delenum";
  initDBGrid("resourceTable");
}

// ///�����ݱ��TD�ĵ����¼�
function resourceTable_TDclick(el) {

  el = event.srcElement;

  var trobj = getOwnerTR(el);

  if (trobj.edit == "true") {
    // /������������������Ӷ���
    if (el.fieldtype == "text") {
      CreateText(el);
    }
    if (el.fieldtype == "dropdown") {
      el.enumType = "restype";

      el.fieldTitle = "����,����";
      dropdown(el);
    }
  }

}
