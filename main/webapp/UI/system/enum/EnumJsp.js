//////��ʼ����Ϣ
function body_load() {
    ///��ʾ�������ݼ��߶ȵĶ��� ��ʽdivfd_ID
    //var divhight = document.body.clientHeight - 180 ;

    divfd_EnumTable.style.height = 220;
    divfd_detailTable.style.height = "150";
    EnumTable.fdwidth = "100%";
    detailTable.fdwidth = "100%";
    EnumTable.actionname = "sm0051";
    EnumTable.addmethodname = "addenum";
    EnumTable.editmethodname = "editenum";
    EnumTable.delmethodname = "delenum";

    initDBGrid("EnumTable");

    detailTable.actionname = "sm0054";
    detailTable.addmethodname = "addenum";
    detailTable.editmethodname = "editenum";
    detailTable.delmethodname = "delenum";

    initDBGrid("detailTable");


}


/////�����ݱ��TR��˫���¼�
function EnumTable_TRDbclick(el) {

    ///��ȡ������Ϣ
    var whArr = EnumTable.rows[EnumTable.activeIndex].getAttribute("whStr").split("&");
    ///�ֽ�������Ϣ�����ѯ����

    var whereStr = "and(EnuType = '" + whArr[2] + "') order by 1";
    ///���ӱ���Ӳ�ѯ����
    detailTable.whereStr = whereStr;

    ///ˢ�´ӱ��¼
    detailTable.AbsolutePage = "1";
    detailTable.RecordCount = "0";
    Table_Refresh("detailTable", true, body_load);


}


/////�����ݱ��TD�ĵ����¼�
function EnumTable_TDclick(el) {

    el = event.srcElement;
    var trobj = getOwnerTR(el);

    if (trobj.edit == "true") {
        ///������������������Ӷ���
        if (el.fieldtype == "text") {
            CreateText(el);
        }

    }


}

////////////ö�����һ����¼
function EnumTable_appendRecord_click(tab) {

    appendRecord(tab);
    while (detailTable.rows.length > 0) {
        detailTable.deleteRow(detailTable.rows.length - 1);
    }
}


/////�����ݱ��TD�ĵ����¼�
function detailTable_TDclick(el) {

    el = event.srcElement;

    var trobj = getOwnerTR(el);

    if (trobj.edit == "true") {

        if (el.fieldtype == "text") {
            CreateText(el);
        }
        if (el.fieldtype == "int") {
            CreateNumberText(el);
        }
    }


}

function detailTable_afterChecked(obj) {

    //     if (obj.checked){
    //
    //          obj.checked= false;
    //     }
}

/////////////����ö��
function detailTable_updateRecord_click(tab) {
    var whArr = EnumTable.rows[EnumTable.activeIndex].whStr.split("&");
    ///�ֽ�������Ϣ�����ѯ����

    var whereStr = "EnuType&" + whArr[1] + "&" + whArr[2];
    updateRecord(tab, whereStr);


}


////////��ѯ����
function queryClick() {

    var whereStr = "";

    if (trimStr(document.all["cationid"].value) != "")
        whereStr += " and ( EnuType like '%" + document.all.cationid.value + "%')";

    if (trimStr(document.all["actionclass"].value) != "")
        whereStr += " and ( EnuName like '%" + document.all.actionclass.value + "%')";

    if (trimStr(document.all["actiondes"].value) != "")
        whereStr += " and ( EnuDesc like '%" + document.all.actiondes.value + "%')";


    if (whereStr != document.all["EnumTable"].whereStr) {
        document.all["EnumTable"].whereStr = whereStr + " order by 1 ";
        document.all["EnumTable"].RecordCount = "0";
        document.all["EnumTable"].AbsolutePage = "1";


        Table_Refresh("EnumTable");
    }

}

