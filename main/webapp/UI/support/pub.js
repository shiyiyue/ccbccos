/***********************************************************************************************************************
 * �ļ���������js������ 
 * ���ߣ�by leonwoo
 * ���ܣ�
 **********************************************************************************************************************/

/** ---------------------------------------ϵͳ���й���JS��Ϣ--------------------------------------------------------- */
var MSG_SYSLOCK = "��������װ�ش����У�����ҵ���ֹ������\r\n����Լ����Ӻ��ٽ�����ز�����";
var MSG_NORECORD = "û�п��Բ����ļ�¼��";
var MSG_DELETE_CONFRIM = "ȷ��ɾ���ü�¼��";
var MSG_DEL_SUCCESS = "ɾ����¼�ɹ���";
var MSG_EXIT_CONFIRM = "ȷ���˳���ǰ������";
var MSG_ADDMORTINFO = "û�в�ѯ����ش����¼���Ƿ��������Ӹñʴ����¼��";
var MSG_LOANNULL = "�ñʵ�Ѻ��������Ӧ������Ϣ��";
var MSG_SUCCESS = "�����ɹ�...";
var MSG_CLM_CONFIRM = "ȷ������ñʴ�����";
var MSG_CLM_CANCEL = "ȷ���˻ظñʴ�����";
var MSG_BATCH_CLM_CONFIRM = "ȷ���������������";
var MSG_BATCH_CLM_CANCEL = "ȷ���˻ظ���������";
var MSG_BATCH_EXPRESS = "ȷ��������ݸ���������";
var MSG_ODSB_READ_CONFIRM = "ȷ����ODSB����������";
var MSG_ODSB_CHECK_CONFIRM = "ȷ������ODSB�����������";
var MSG_ODSB_CHECK_RESULT = "�����һ�¡�";
var MSG_ODSB_UPDATE_CONFIRM = "ȷ�����´���������";
/** ----------------------------------------------------------------------------------------------------------------- */

/** ------------------------------------------ϵͳ���й���ö�ٱ���---------------------------------------------------- */

// menu type �˵����� 010 ��Ѻ��Ϣ�Ǽ�<br>
/** ��Ѻ��Ϣ�Ǽ� */
var BUSINODE_010 = "010";
/** ��ݵ�Ѻ��Ϣ�Ǽ� */
var BUSINODE_020 = "020";
/** ���п�����δ����Ѻ�Ǽ� */
var BUSINODE_030 = "030";
/** ���п������ɱ���Ѻ�Ǽ� */
var BUSINODE_040 = "040";
/** δ�����Ѻԭ��Ǽ� */
var BUSINODE_050 = "050";
/** ȡ�û�ִ�Ǽ� */
var BUSINODE_060 = "060";
/** ��ݻ�֤�Ǽ� */
var BUSINODE_070 = "070";
/** ��Ѻ���Ǽ� */
var BUSINODE_080 = "080";
/** ��֤���õǼ� */
var BUSINODE_090 = "090";
/** ��֤�黹�Ǽ� */
var BUSINODE_100 = "010";
/** �������ȡ֤�Ǽ� */
var BUSINODE_110 = "110";
/** ��Ѻ����ͨ�� */
var BUSINODE_120 = "120";
/** 130 ������Ϣ���� */
var BUSINODE_130 = "130";
/** 140 �ͻ��������� */
var BUSINODE_140 = "140";
/** 150��������ͨ�� */
var BUSINODE_150 = "150";
/** 160������Ŀά�� */
var BUSINODE_160 = "160"
/** ----------------------------------------------------------------------------------------------------------------- */

/**
 * ��ʾ�Ի���
 */
function showDialog(width, height, url, arg, resizable) {
    // ���÷��
    if (resizable == undefined) {
        resizable = "no";
    }
    var sfeature = "dialogwidth:" + width + "px; dialogheight:" + height + "px;center:yes;help:no;resizable:"
            + resizable + ";scroll:no;status:no";
    var date = new Date();
    // ����URL��չ������IEû������ÿ��ˢ�£�������ʾ��ҳ������
    var time = getDateString(date) + " " + getTimeString(date);
    if (url.indexOf("?") > 0) {
        url += "&showDialogTime=" + time;
    } else {
        url += "?&showDialogTime=" + time;
    }
    return window.showModalDialog(url, arg, sfeature);
}

/**
 * ��ʾ�Ի���
 */
function dialog(url, arg, sfeature) {

    // openWin(url);
    // return;

    var sfeature = sfeature;
    var date = new Date();
    // ����URL��չ������IEû������ÿ��ˢ�£�������ʾ��ҳ������
    var time = getDateString(date) + " " + getTimeString(date);
    if (url.indexOf("?") > 0) {
        url += "&showDialogTime=" + time;
    } else {
        url += "?&showDialogTime=" + time;
    }
    // alert(url);
    return window.showModalDialog(url, arg, sfeature);
    // return window.open(url);
}

/**
 * �¿�һ����
 */
function openWin(url) {

    var date = new Date();
    // ����URL��չ������IEû������ÿ��ˢ�£�������ʾ��ҳ������
    var time = getDateString(date) + " " + getTimeString(date);
    if (url.indexOf("?") > 0) {
        url += "&showDialogTime=" + time;
    } else {
        url += "?&showDialogTime=" + time;
    }
    // alert(url);
    return window.open(url);
    // return window.open(url);
}

/**
 * ��ȡ����
 */
function getDateString(date) {
    var years = date.getFullYear();
    var months = date.getMonth() + 1;
    var days = date.getDate();

    if (months < 10)
        months = "0" + months;
    if (days < 10)
        days = "0" + days;

    return years + "-" + months + "-" + days;
}

/**
 * ��ȡʱ��
 */
function getTimeString(date) {
    var hours = date.getHours();
    var minutes = date.getMinutes();
    var seconds = date.getSeconds();

    if (hours < 10)
        hours = "0" + hours;
    if (minutes < 10)
        minutes = "0" + minutes;
    if (seconds < 10)
        seconds = "0" + seconds;

    return hours + ":" + minutes + ":" + seconds;
}

// /��Ž�����������������dbgrid tableBubbleSort��������
// ��table �С��о���0��ʼ���� by wuyeyuan
function itemTab_afterSort2() {
    var tab = document.all["itemTab"];
    if (tab != null) {
        var pageSize = tab.pageSize;
        // var totalPage=tab.TotalPage;
        var curPage = tab.AbsolutePage;
        var seq = (curPage - 1) * pageSize;
        for ( var i = 0; i < pageSize; i++) {
            // var xh=tab.rows[i].cells[0].innerText;
            var j = new Number(i + seq) + 1;
            if (tab.rows[i])// �ж����ҳ�����Ƿ���ڣ��������д��š����ǵ����һҳ���ݲ���pagesizeʱ��
                // �޸�ÿ�еĵ�2������
                if (tab.rows[i].firstChild.firstChild.type == "checkbox")
                    tab.rows[i].cells[1].innerText = j;
                else
                    tab.rows[i].cells[0].innerText = j;
        }
    }
}

// ѭ��ʹ��form���ݲ����޸�
function readFun(userTab) {
    for ( var i = 0; i < userTab.length; i++) {
        var obj = userTab.item(i);
        // if(obj.type=="text"||obj.type=="textarea"||obj.type=="button"){
        if (obj.type == "text" || obj.type == "textarea") {
            obj.disabled = true;
            // obj.className = "inputReadonly";
            // obj.readOnly = true;
        } else if (obj.type == "select-one") {
            obj.disabled = true;
            // obj.className = "inputReadonly";
            // obj.readOnly = true;
        }
        // else{
        // obj.readOnly = true;
        // obj.className = "inputReadonly";
        // }
    }
}

//
// ///�����ݱ��TD�ĵ����¼�
function itemTab_TDclick(el) {
    // alert();
    el = event.srcElement;
    var trobj = getOwnerTR(el);
    if (trobj.edit == "true") {
        // /������������������Ӷ���
        if (el.fieldtype == "text" || el.fieldtype == "date") {

            CreateText(el);
        }

        if (el.fieldtype == "number" || el.fieldtype == "money" || el.fieldtype == "percent") {
            CreateNumberText(el);
        }

        if (el.fieldtype == "dropdown") {
            switch (el.fieldname) {
            case "pub_name": {
                // el.dropwidth="220px"
                el.enumType = "pub_name";
                el.fieldTitle = "����,����";
                dropdown(el);
                break;
            }
            default:
                break;
            }

        }
    }
}
/*******************************************************************************
 * �����������ѡ��select�ؼ�ʹ�õĺ��� ��listȡ��ֵ������textbox date 2008-5-12 selectId listboxID
 * textId textboxID
 * 
 ******************************************************************************/
function onListChanged(selectId, textId) {
    try {
        if (document.getElementById(selectId).selectedIndex > -1) {
            var colVar = document.getElementById(selectId).options[document.getElementById(selectId).selectedIndex].text;
            document.getElementById(textId).value = colVar;
        }
    } catch (Exception) {
    }
}

/**
 * ȡ��ϵͳ��״̬
 * 
 * @param lockstatus
 *            text
 * @param input
 *            type
 * @param input
 *            value
 * @param action
 *            name
 * @param action
 *            method name
 */
function getSysLockStatus() {
    var lockStatus = "";
    // var rtnXml = createselectArr("lockStatus", "text", "1,", "sys01",
    // "getSysLockStatus");
    var rtnXml = createExecuteform(queryForm, "update", "sys01", "getSysLockStatus")
    if (rtnXml != "false") {
        var dom = createDomDocument();
        dom.loadXML(rtnXml);
        var fieldList = dom.getElementsByTagName("record")[0];
        for ( var i = 0; i < fieldList.childNodes.length; i++) {
            if (fieldList.childNodes[i].nodeType == 1) {
                oneRecord = fieldList.childNodes[i];
                attrName = oneRecord.getAttribute("name");
                if (attrName == "sysLockStatus") {
                    lockStatus = decode(oneRecord.getAttribute("value"));
                }
            }
        }
    }

    return lockStatus;
}

/**
 * Focus inʱ�༭ֵ
 * 
 * @param strValue
 *            ��Ҫ�༭��ֵ
 * @return �༭���ֵ
 */
function formatCommaWhenFocusIn(strValue) {
    if (strValue == null) {
        return "";
    }
    var re = /,/g;
    strValue = strValue.replace(re, "");
    return strValue;
}
/**
 * FocusOutǧ��λ׷��
 * 
 * @param strValue
 *            ��Ҫ�༭��ֵ
 * @return �༭���ֵ
 */
function formatCommaWhenFocusOut(strValue) {
    if (isDigit(strValue)) {
        var dotBeforeString = strValue.substring(0, strValue.indexOf("."));
        var dotAfterString = strValue.substring(strValue.indexOf("."));
        if (strValue.indexOf(".") == -1) {
            dotBeforeString = dotAfterString;
            dotAfterString = "";
        }
        var re = /(-?\d+)(\d{3})/;
        while (re.test(dotBeforeString)) {
            dotBeforeString = dotBeforeString.replace(re, "$1,$2");
        }
        strValue = dotBeforeString + dotAfterString;
    }
    return strValue;
}

/**
 * Number�ж�
 * 
 * @param obj
 */
function isDigit(str) {
    var patrn = /^-?[0-9]{1,38}.*$/;
    if (!patrn.test(str)) {
        return false;
    } else {
        return true;
    }
}

/**
* FocusOutǧ��λ׷�� 
* @param obj
*/
function Txn_LostFocus(obj) {
    if (obj.tagName.toUpperCase() == "INPUT") {
        var strValue = obj.value;
        if ((strValue == null) || (strValue == "")) {
            obj.value = "";
            return;
        }
        var arr = strValue.split(".");
        if (arr.length > 2) {
            return;
        }
        arr = strValue.split(",");
        strValue = "";
        for (var i = 0; i < arr.length; i++) {
            strValue = strValue + arr[i];
        }
        if (isDigit(strValue)) {
            var intValue = parseFloat(strValue, 10);
            strValue = intValue + "";
            var dotBeforeString = strValue.substring(0, strValue.indexOf("."));
            var dotAfterString = strValue.substring(strValue.indexOf("."));
            if (strValue.indexOf(".") == -1) {
                dotBeforeString = dotAfterString;
                dotAfterString = "";
            }
            var re = /(-?\d+)(\d{3})/;
            while (re.test(dotBeforeString)) {
                dotBeforeString = dotBeforeString.replace(re, "$1,$2");
            }
            obj.value = dotBeforeString + dotAfterString;
        } else if (strValue == "") {
            obj.value = strValue;
        }
    }
}
/**
* Focusin ǧ��λɾ��
* @param obj
*/
function Txn_GotFocus(obj) {
    if (obj.tagName.toUpperCase() == "INPUT") {
        var strValue = obj.value;
        var re = /,/g;
        obj.value = strValue.replace(re, "");
        //obj.select();
    }
}