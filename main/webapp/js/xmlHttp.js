//************************************************************//
//���ļ��Ǳ�ϵͳ�ͻ��˵�xml������ϵͳ�����ļ����κ��˲������Ը���	  //
//���и����뱾����ϵ��������		                              //
//************************************************************//

/********************************************************************************
 *
 *      �ļ�����xmlHttp.js
 *
 *      ��  �ã� xml��װ�����ݴ��䡣
 *
 *      ��  �ߣ� ������
 *
 *      ʱ  �䣺  yyyy-mm-dd
 *
 *      ��  Ȩ��  leonwoo
 *
 ********************************************************************************/


//����Automation����
function createObject(strName, strDescription) {
    try {
        var stm = new ActiveXObject(strName);

        return stm;
    }
    catch (e) {
        var strMsg = "���ļ����û�а�װ" + strName;

        if (strDescription)
            strMsg += ", " + strDescription;
        throw strMsg;
    }
}

//�ж�һ�������Ƿ�Ϊ����
function paramIsArray(v) {
    try {
        var a = v[0];
        return typeof(a) != "undefined";
    }
    catch (e) {
        return false;
    }
}

//���ӽڵ��ֵת��Ϊ����
function transformNode(xmlNode, strNewNodeName) {
    var xmlDoc = createDomDocument("<" + strNewNodeName + "/>");
    var root = xmlDoc.documentElement;

    var node = xmlNode.firstChild;

    while (node) {
        root.setAttribute(node.nodeName, node.text);
        node = node.nextSibling;
    }

    return xmlDoc;
}

//��ĳһ�����ڵ���ӽڵ㸴�Ƶ���һ�����ڵ���
function cloneNodes(rootDest, rootSrc, bRecursive) {
    var node = rootSrc.firstChild;

    while (node) {
        rootDest.appendChild(node.cloneNode(bRecursive));

        node = node.nextSibling;
    }
}

function cloneXmlNode(rootDest, rootSrc, strXPath, bRecursive) {
    var node = rootSrc.selectSingleNode(strXPath);
    var nodeResult = null;

    if (node) {
        nodeResult = rootDest.appendChild(node.cloneNode(bRecursive));
    }

    return nodeResult;
}

//��ָ���ڵ�������һ���ڵ�
function appendNode(xmlDoc, root, strNodeName) {
    var nodeText = "";

    if (arguments.length > 3)
        nodeText = arguments[3];

    var node = xmlDoc.createNode(1, strNodeName, "");

    if (nodeText.toString().length > 0)
        node.text = nodeText;

    root.appendChild(node);

    return node;
}

//��ָ���ڵ�������һ������
function appendAttr(xmlDoc, node, strAttrName) {
    var nodeText = "";

    if (arguments.length > 3)
        nodeText = arguments[3];

    var attr = xmlDoc.createAttribute(strAttrName);

    if (nodeText != "")
        attr.value = nodeText;

    node.attributes.setNamedItem(attr);

    return attr;
}

//��ָ���ڵ�������һ������
function appendCDAT(xmlDoc, node, dataValue) {
    var nodeText = "";

    var CDATASection = xmlDoc.createCDATASection(dataValue);

    node.appendChild(CDATASection);

}


function appendAttri(xmlDoc, node, strAttrName, nodeText) {

    var attr = xmlDoc.createAttribute(strAttrName);

    if (nodeText != "" && nodeText != null)
        attr.value = nodeText;

    node.attributes.setNamedItem(attr);

    return attr;
}

//����һ���ڵ������ֵ�����û��������ԣ��򷵻ؿմ�
function getAttrValue(node, strAttrName) {
    var attr = node.attributes.getNamedItem(strAttrName);

    if (attr)
        return attr.value;
    else
        return "";
}

//����һ���ڵ�����ݣ�����ڵ�Ϊ�գ��򷵻�ȱʡֵ
function getNodeText(node, strDefault) {
    var strResult = "";

    if (node)
        strResult = node.text;
    else if (strDefault)
        strResult = strDefault;

    return strResult;
}

function getNodeAttribute(node, strAttr) {
    var attrText = node.getAttribute(strAttr);

    if (!attrText)
        attrText = "";

    return attrText;
}

function getSingleNodeText(nodeRoot, strPath, strDefault) {
    var node = nodeRoot.selectSingleNode(strPath);

    return getNodeText(node, strDefault);
}

var innerXmlHttp = null;
var innerHandleStateChange = null;
var innerParam = null;


/******************************************************
 ������    ִ�з������˵ĳ����ļ�
 ���أ�    �ɹ��򷵻س���ִ�н����ʧ���򷵻�-1
 ������    strPrgmURL    �����ļ�·��
 strMethod    ���ͷ�����POST��GET��
 strParamString POST����ʱ�Ĳ����ַ���
 ******************************************************/

var xmlhttp = null;
var resText = "";
var returntableID;
var retdateRefresh;

function ExecServerPrgm_synsh(strPrgmURL, strMethod, strParamString, strUserID, strPwd, tableID, dateRefresh) {
    var agent = window.navigator.userAgent.toLowerCase();
    //alert(agent);

    returntableID = tableID;
    retdateRefresh = dateRefresh;

    xmlhttp = new ActiveXObject("Msxml2.XmlHttp");

    if (strUserID != "" && strUserID != null) {
        xmlhttp.Open(strMethod, strPrgmURL, true, strUserID, strPwd);
        if (arguments.length == 7)
            xmlhttp.onreadystatechange = readystatechange;
    } else {
        xmlhttp.Open(strMethod, strPrgmURL, true);
        if (arguments.length == 7)
            xmlhttp.onreadystatechange = readystatechange;
    }
    if (strMethod.toUpperCase() == 'POST') {
        xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xmlhttp.send(strParamString);
    } else {
        xmlhttp.send();
    }
    //alert(xmlhttp.readyState);
    return resText;
}

function readystatechange() {

    if (xmlhttp.readyState == 4) {
        //alert(xmlhttp.responseText);
        retdateRefresh(returntableID, unescape(xmlhttp.responseText));
    }
}


function ExecServerPrgm(strPrgmURL, strMethod, strParamString, strUserID, strPwd) {


    xmlhttp = new ActiveXObject("Msxml2.XmlHttp");

    if (strUserID != "" && strUserID != null) {
        xmlhttp.Open(strMethod, strPrgmURL, false, strUserID, strPwd);
    }
    else {
        xmlhttp.Open(strMethod, strPrgmURL, false);

    }
    if (strMethod.toUpperCase() == 'POST') {
        xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

        xmlhttp.send(strParamString);
    } else {
        xmlhttp.send();
    }


    if (xmlhttp.status == 200) {

        resText = unescape(xmlhttp.responseText);
    } else {

        resText = xmlhttp.status;
    }


    return resText;
}


//����xmlDocument����
function createDomDocument() {
    var xmlData;

    try {
        xmlData = createObject("Msxml2.DOMDocument");
    }
    catch (e) {
        xmlData = createObject("Msxml.DOMDocument");
    }

    xmlData.async = false;

    if (arguments.length > 0)
        xmlData.loadXML(arguments[0]);

    return xmlData;
}




