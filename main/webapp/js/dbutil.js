//**************************************************//
//���ļ��Ǳ�ϵͳ�����ݿ⽻���Ĺ����ļ����κ��˲������Ը���	//
//���и����뱾����ϵ��������					        //
//**************************************************//

/*******************************************************************************
 * 
 * �ļ����� dbutil.js
 * 
 * �� �ã����ݰ���װ������
 * 
 * �� �ߣ� ������
 * 
 * ʱ �䣺 yyyy-mm-dd
 * 
 * �� Ȩ�� leonwoo
 * 
 ******************************************************************************/

// ///Excute�����ύ
function postExcute(XMLStr) {
  var retStr = ExecServerPrgm(g_jsContextPath+"/BI/util/SqlExcuteJsp.jsp", "POST", "sys_request_xml=" + XMLStr);
  return retStr;
}

// //////////////�������ݸ��²���////////////////////////////
// / formelement �ύ��form����
// / whereStr�����������
// / exctype ��������
// / postpath �ύ��̨����
// //////////////////////////////////////////////////////

function createExecutearr(fieldname, fieldvalue, fieldtype, exctype, actionName, methodname) {

  var xmlDoc = createDomDocument("<root/>");

  var rootNode = xmlDoc.documentElement;

  var actionNode = appendNode(xmlDoc, xmlDoc.documentElement, "action");
  appendAttri(xmlDoc, actionNode, "actionname", actionName);

  if (arguments.length == 6)
    appendAttri(xmlDoc, actionNode, "methodname", methodname);

  var recNode = appendNode(xmlDoc, xmlDoc.documentElement, "recorder");
  appendAttri(xmlDoc, recNode, "type", exctype);

  for ( var i = 0; i < fieldname.length; i++) {

    // if((fieldtype[i]=="text")||(fieldtype[i]=="int")||(fieldtype[i]=="date")||(fieldtype[i]=="datetime"))
    // {
    var fieldNode = createFieldNode(xmlDoc, fieldname[i], "text", fieldvalue[i]);
    recNode.appendChild(fieldNode);
    // }

  }

  if (recNode.childNodes.length > 0) {
    actionNode.appendChild(recNode);
    rootNode.appendChild(actionNode);
    retStr = ExecServerPrgm(g_jsContextPath+"/BI/util/SqlExcuteJsp.jsp", "POST", "sys_request_xml=" + xmlDoc.xml);

  }
  xmlDoc.free;
  return retStr;

}

// //////////////�������ݸ��²���////////////////////////////
// / formelement �ύ��form����
// / whereStr�����������
// / exctype ��������
// / postpath �ύ��̨����
// //////////////////////////////////////////////////////

function createExecuteform(formelement, exctype, actionName, methodname) {

  if (isfireuserEvent(formelement.id + "_beforePost")) {
    if (fireUserEvent(formelement.id + "_beforePost") == false)
      return false;
  }

  // marked by leonwoo 20100118 begin
  // if (checkForm(formelement)=="true")
  // marked by leonwoo 20100118 end
  // �Ѿ�����ҳ���飬�˴�����Ҫ�������
  if (true) {

    var xmlDoc = createDomDocument("<root/>");

    if (exctype == "insert") {
      if (createinsert(formelement, xmlDoc, actionName, methodname) == "false")
        return "false";

    }

    if (exctype == "update") {
      if (createupdate(formelement, xmlDoc, actionName, methodname) == "false")
        return "false";

    }
    if (exctype == "delete") {
      createdelete(xmlDoc, formelement, actionName, methodname);

    }

    if (xmlDoc.documentElement.childNodes.length > 0)

      // alert(xmlDoc.xml);
      // return;
      retStr = ExecServerPrgm(g_jsContextPath+"/BI/util/SqlExcuteJsp.jsp", "POST", "sys_request_xml=" + xmlDoc.xml);

    // retStr =
    // ExecServerPrgm_synsh(g_jsContextPath+"/BI/util/SqlExcuteJsp.jsp","POST","sys_request_xml="+xmlDoc.xml,"","","",fun_test);

    xmlDoc.free;

    return retStr;
  } else
    return "false";

}

function createExecuteform_synsh(formelement, exctype, actionName, methodname) {

  if (isfireuserEvent(formelement.id + "_beforePost")) {
    if (fireUserEvent(formelement.id + "_beforePost") == false)
      return false;
  }

  if (checkForm(formelement) == "true") {

    var xmlDoc = createDomDocument("<root/>");

    if (exctype == "insert") {
      if (createinsert(formelement, xmlDoc, actionName, methodname) == "false")
        return "false";

    }

    if (exctype == "update") {
      if (createupdate(formelement, xmlDoc, actionName, methodname) == "false")
        return "false";

    }
    if (exctype == "delete") {
      createdelete(xmlDoc, formelement, actionName, methodname);

    }

    if (xmlDoc.documentElement.childNodes.length > 0)

      // alert(xmlDoc.xml);
      // return;
      // retStr =
      // ExecServerPrgm(g_jsContextPath+"/BI/util/SqlExcuteJsp.jsp","POST","sys_request_xml="+xmlDoc.xml);

      retStr = ExecServerPrgm_synsh(g_jsContextPath+"/BI/util/SqlExcuteJsp.jsp", "POST", "sys_request_xml=" + xmlDoc.xml, "", "", "",
          fun_test);

    xmlDoc.free;

    return retStr;
  } else
    return "false";

}

// //////////////�������ݲ�����Ϣ////////////////////////////
// / formelement �ύ��form����
// //////////////////////////////////////////////////////
function createinsert(formelement, xmlDoc, actionName, methodname) {

  var rootNode = xmlDoc.documentElement;

  var actionNode = appendNode(xmlDoc, xmlDoc.documentElement, "action");
  appendAttri(xmlDoc, actionNode, "actionname", actionName);

  if (arguments.length == 4)
    appendAttri(xmlDoc, actionNode, "methodname", methodname);

  var recNode = appendNode(xmlDoc, xmlDoc.documentElement, "recorder");
  appendAttri(xmlDoc, recNode, "type", "insert");

  for ( var i = 0; i < formelement.length; i++) {
    var obj = formelement.item(i);
    if (obj.tagName.toUpperCase() == "INPUT") {

      if (obj.type == "text" || obj.type == "hidden" || obj.type == "password") {

        if (obj.fieldtype == "select") {
          var fieldNode = createFieldNode(xmlDoc, obj.id, "text", obj.attr);
        } else
          var fieldNode = createFieldNode(xmlDoc, obj.id, "text", obj.value);

        recNode.appendChild(fieldNode);

      }

      if (obj.type == "checkbox") {
        if (obj.checked) {
          var fieldNode = createFieldNode(xmlDoc, obj.id, "check", "1");

        } else
          var fieldNode = createFieldNode(xmlDoc, obj.id, "check", "0");

        recNode.appendChild(fieldNode);
      }

      if (obj.type == "radio") {
        if (obj.checked) {
          var fieldNode = createFieldNode(xmlDoc, obj.id, "radio", obj.value);

        }

        recNode.appendChild(fieldNode);
      }

    }

    if (obj.tagName.toUpperCase() == "TEXTAREA") {
      var fieldNode = createFieldNode(xmlDoc, obj.id, "text", obj.value);
      recNode.appendChild(fieldNode);
    }

    if (obj.tagName.toUpperCase() == "SELECT") {
      if (obj.selectedIndex >= 0) {
        var fieldNode = createFieldNode(xmlDoc, obj.id, "text", obj.value);
        recNode.appendChild(fieldNode);
      }
    }
  }

  if (recNode.childNodes.length > 0) {
    actionNode.appendChild(recNode);
    rootNode.appendChild(actionNode);

  }

}

// //////////////�������ݿ��������////////////////////////////
// / formelement �ύ��form����
// / whereStr�����������
// //////////////////////////////////////////////////////

function createupdate(formelement, xmlDoc, actionName, methodname) {

  var rootNode = xmlDoc.documentElement;

  var actionNode = appendNode(xmlDoc, xmlDoc.documentElement, "action");
  appendAttri(xmlDoc, actionNode, "actionname", actionName);

  if (arguments.length == 4)
    appendAttri(xmlDoc, actionNode, "methodname", methodname);

  var recNode = appendNode(xmlDoc, xmlDoc.documentElement, "recorder");
  appendAttri(xmlDoc, recNode, "type", "update");

  var obj;
  var fieldNode;

  for ( var i = 0; i < formelement.length; i++) {
    obj = formelement.item(i);

    if (obj.tagName.toUpperCase() == "INPUT") {

      if (obj.type == "text" || obj.type == "hidden" || obj.type == "password" || obj.type == "file") {

        if (obj.fieldtype == "select") {
          var fieldNode = createFieldNode(xmlDoc, obj.id, "text", obj.attr, obj.oldvalue);
        } else
          var fieldNode = createFieldNode(xmlDoc, obj.id, "text", obj.value, obj.oldvalue);
        recNode.appendChild(fieldNode);

      }

      if (obj.type == "checkbox") {
        if (obj.checked) {
          var fieldNode = createFieldNode(xmlDoc, obj.id, "check", "1", obj.oldvalue);

        } else
          var fieldNode = createFieldNode(xmlDoc, obj.id, "check", "0", obj.oldvalue);

        recNode.appendChild(fieldNode);
      }

      if (obj.type == "radio") {
        if (obj.checked) {
          var fieldNode = createFieldNode(xmlDoc, obj.id, "radio", obj.value, obj.oldvalue);

        }

        recNode.appendChild(fieldNode);
      }

    }

    if (obj.tagName.toUpperCase() == "TEXTAREA") {
      var fieldNode = createFieldNode(xmlDoc, obj.id, "text", obj.value, obj.oldvalue);
      recNode.appendChild(fieldNode);
    }

    if (obj.tagName.toUpperCase() == "SELECT") {
      if (obj.selectedIndex >= 0) {
        var fieldNode = createFieldNode(xmlDoc, obj.id, "text", obj.value, obj.oldvalue);
        recNode.appendChild(fieldNode);
      }
    }

  }

  if (recNode.childNodes.length > 0) {
    actionNode.appendChild(recNode);
    rootNode.appendChild(actionNode);

  }

}

// //////////////�������ݿ�ɾ������////////////////////////////
// / whereStr�����������
// //////////////////////////////////////////////////////

function createdelete(xmlDoc, formelement, actionName, methodname) {

  var rootNode = xmlDoc.documentElement;

  var actionNode = appendNode(xmlDoc, xmlDoc.documentElement, "action");
  appendAttri(xmlDoc, actionNode, "actionname", actionName);

  if (arguments.length == 4)
    appendAttri(xmlDoc, actionNode, "methodname", methodname);

  var recNode = appendNode(xmlDoc, xmlDoc.documentElement, "recorder");
  appendAttri(xmlDoc, recNode, "type", "delete");

  for ( var i = 0; i < formelement.length; i++) {
    var obj = formelement.item(i);

    if (obj.tagName.toUpperCase() == "INPUT") {

      if (obj.type == "text" || obj.type == "hidden" || obj.type == "password") {

        if (obj.fieldtype == "select") {
          var fieldNode = createFieldNode(xmlDoc, obj.id, "text", obj.attr);
        } else
          var fieldNode = createFieldNode(xmlDoc, obj.id, "text", obj.value);
        recNode.appendChild(fieldNode);

      }

      if (obj.type == "checkbox") {
        if (obj.checked) {
          var fieldNode = createFieldNode(xmlDoc, obj.id, "check", "1");

        } else
          var fieldNode = createFieldNode(xmlDoc, obj.id, "check", "0");

        recNode.appendChild(fieldNode);
      }

      if (obj.type == "radio") {
        if (obj.checked) {
          var fieldNode = createFieldNode(xmlDoc, obj.id, "radio", obj.value);

        }

        recNode.appendChild(fieldNode);
      }
    }

    if (obj.tagName.toUpperCase() == "TEXTAREA") {
      var fieldNode = createFieldNode(xmlDoc, obj.id, "text", obj.value);
      recNode.appendChild(fieldNode);
    }

    if (obj.tagName.toUpperCase() == "SELECT") {
      if (obj.selectedIndex >= 0) {
        var fieldNode = createFieldNode(xmlDoc, obj.id, "text", obj.value);
        recNode.appendChild(fieldNode);
      }
    }

  }

  if (recNode.childNodes.length > 0) {
    actionNode.appendChild(recNode);
    rootNode.appendChild(actionNode);
  }

}

// //////////////���ɶ������ݿ��¼ɾ��////////////////////////////
// / whereArr �������ݿ��¼����
// //////////////////////////////////////////////////////

function createDelArray(whereArr, actionName, methodname) {
  var xmlDoc = createDomDocument("<root/>");
  var rootNode = xmlDoc.documentElement;

  if (whereArr == undefined)
    return;

  var actionNode = appendNode(xmlDoc, xmlDoc.documentElement, "action");
  appendAttri(xmlDoc, actionNode, "actionname", actionName);

  if (arguments.length == 3)
    appendAttri(xmlDoc, actionNode, "methodname", methodname);

  for ( var i = 0; i < whereArr.length; i++) {
    if (whereArr[i] != undefined) {
      var recNode = createDeleteRecord(whereArr[i], xmlDoc)

      if (recNode.childNodes.length > 0) {
        actionNode.appendChild(recNode);

      }
    }

  }

  rootNode.appendChild(actionNode);

  var retStr = ExecServerPrgm(g_jsContextPath+"/BI/util/SqlSelectJsp.jsp", "POST", "sys_request_xml=" + xmlDoc.xml);

  xmlDoc.free;

  return analyzeReturnXML(retStr);

}

// ///����ɾ����¼

function createDeleteRecord(whereArr, xmlDoc) {
  var recNode = appendNode(xmlDoc, xmlDoc.documentElement, "recorder");
  appendAttri(xmlDoc, recNode, "type", "delete");

  var whereSArr = whereArr.split("*");

  for ( var j = 0; j < whereSArr.length; j++) {
    if (whereSArr[j] != undefined) {
      var whArr = whereSArr[j].split("&");

      var fieldNode = createFieldNode(xmlDoc, whArr[0], whArr[1], whArr[2]);
      recNode.appendChild(fieldNode);

    }
  }
  return recNode;
}
// //////////////���������������ݿ��ѯ����////////////////////////////
// / whereStr��ѯ�������
// //////////////////////////////////////////////////////

function createselectArr(fieldNameS, fieldTypeS, fieldValueS, actionName, methodname) {
  var xmlDoc = createDomDocument("<root/>");
  var rootNode = xmlDoc.documentElement;

  var fieldname = fieldNameS.split(",");
  var fieldtype = fieldTypeS.split(",");
  
  var fieldvalue = fieldValueS.split(",");
  
  var actionNode = appendNode(xmlDoc, xmlDoc.documentElement, "action");
  appendAttri(xmlDoc, actionNode, "actionname", actionName);

  if (arguments.length == 5)
    appendAttri(xmlDoc, actionNode, "methodname", methodname);

  var recNode = appendNode(xmlDoc, xmlDoc.documentElement, "recorder");
  appendAttri(xmlDoc, recNode, "type", "select");

  for ( var i = 0; i < fieldname.length; i++) {
    var fieldNode = createFieldNode(xmlDoc, fieldname[i], fieldtype[i], fieldvalue[i]);
    recNode.appendChild(fieldNode);

  }

  if (recNode.childNodes.length > 0) {
    actionNode.appendChild(recNode);
    rootNode.appendChild(actionNode);

    var retStr = ExecServerPrgm(g_jsContextPath+"/BI/util/SqlSelectJsp.jsp", "POST", "sys_request_xml=" + xmlDoc.xml);
  }

  xmlDoc.free;
  return analyzeReturnXML(retStr);
}

// //////////////���������������ݿ��ѯ����////////////////////////////
// / whereStr��ѯ�������
// //////////////////////////////////////////////////////

function createselectArrNOCheck(fieldNameS, fieldTypeS, fieldValueS, actionName, methodname) {
  var xmlDoc = createDomDocument("<root/>");
  var rootNode = xmlDoc.documentElement;

  var fieldname = fieldNameS.split(",");
  var fieldtype = fieldTypeS.split(",");
  var fieldvalue = fieldValueS.split(",");

  var actionNode = appendNode(xmlDoc, xmlDoc.documentElement, "action");
  appendAttri(xmlDoc, actionNode, "actionname", actionName);

  if (arguments.length == 5)
    appendAttri(xmlDoc, actionNode, "methodname", methodname);

  var recNode = appendNode(xmlDoc, xmlDoc.documentElement, "recorder");
  appendAttri(xmlDoc, recNode, "type", "select");

  for ( var i = 0; i < fieldname.length; i++) {
    var fieldNode = createFieldNode(xmlDoc, fieldname[i], fieldtype[i], fieldvalue[i]);
    recNode.appendChild(fieldNode);

  }

  if (recNode.childNodes.length > 0) {
    actionNode.appendChild(recNode);
    rootNode.appendChild(actionNode);

    var retStr = ExecServerPrgm(g_jsContextPath+"/BI/util/SqlSelectJsp.jsp", "POST", "sys_request_xml=" + xmlDoc.xml);

    xmlDoc.free;
    return retStr;
  }
  return "true";

}
// //////////////�������ݿ��ѯ����////////////////////////////
// / whereStr��ѯ�������
// //////////////////////////////////////////////////////

function createselect(formelement, actionName, methodname) {
  var xmlDoc = createDomDocument("<root/>");
  var rootNode = xmlDoc.documentElement;

  var actionNode = appendNode(xmlDoc, xmlDoc.documentElement, "action");
  appendAttri(xmlDoc, actionNode, "actionname", actionName);

  if (arguments.length == 3)
    appendAttri(xmlDoc, actionNode, "methodname", methodname);

  var recNode = appendNode(xmlDoc, xmlDoc.documentElement, "recorder");
  appendAttri(xmlDoc, recNode, "type", "select");

  for ( var i = 0; i < formelement.length; i++) {
    var obj = formelement.item(i);

    if (obj.tagName.toUpperCase() == "INPUT") {

      if (obj.type == "text" || obj.type == "hidden" || obj.type == "password") {

        if (obj.fieldtype == "select") {
          var fieldNode = createFieldNode(xmlDoc, obj.id, "text", obj.attr);
        } else
          var fieldNode = createFieldNode(xmlDoc, obj.id, "text", obj.value);
        recNode.appendChild(fieldNode);

      }

      if (obj.type == "checkbox") {
        if (obj.checked) {
          var fieldNode = createFieldNode(xmlDoc, obj.id, "check", "1");

        } else
          var fieldNode = createFieldNode(xmlDoc, obj.id, "check", "0");

        recNode.appendChild(fieldNode);
      }

      if (obj.type == "radio") {
        if (obj.checked) {
          var fieldNode = createFieldNode(xmlDoc, obj.id, "radio", obj.value);

        }

        recNode.appendChild(fieldNode);
      }
    }

    if (obj.tagName.toUpperCase() == "TEXTAREA") {
      var fieldNode = createFieldNode(xmlDoc, obj.id, "text", obj.value);
      recNode.appendChild(fieldNode);
    }

    if (obj.tagName.toUpperCase() == "SELECT") {
      if (obj.selectedIndex >= 0) {
        var fieldNode = createFieldNode(xmlDoc, obj.id, "text", obj.value);
        recNode.appendChild(fieldNode);
      }
    }

  }

  if (recNode.childNodes.length > 0) {
    actionNode.appendChild(recNode);
    rootNode.appendChild(actionNode);

    var retStr = ExecServerPrgm(g_jsContextPath+"/BI/util/SqlSelectJsp.jsp", "POST", "sys_request_xml=" + xmlDoc.xml);

    xmlDoc.free;
    return retStr;

    // fillform(formelement,retStr);
  }

}

// ////������ͨ���ݰ�����ֵ

function analyzeReturnXML(XMLStr) {
  // alert(XMLStr);

  if (XMLStr == "false")
    return "false";

  var xmlDoc = createDomDocument();

  xmlDoc.loadXML(XMLStr);

  var rootNode = xmlDoc.documentElement.firstChild;

  if (rootNode == null)
    return;
  
  if (rootNode.getAttribute("type") == "1" || rootNode.getAttribute("type") == "3") {
    if (rootNode.getAttribute("result") == "true") {
      retXML = rootNode.xml;
      xmlDoc.free;
      return retXML;
    } else {

      showErrorPage(rootNode.firstChild.xml);
      xmlDoc.free;
      return "false";
    }
  } else if (rootNode.getAttribute("type") == "0") {
    // ��������Ա��ʱҳ��
    if (rootNode.getAttribute("result") == "true") {

      xmlDoc.free;
      return "true";
    } else {
      alert(decode(rootNode.text));

      xmlDoc.free;
      return "false";
    }

  } else {
    if (rootNode.getAttribute("result") == "true") {

      retXML = rootNode.text;
      xmlDoc.free;
      return retXML;
    } else {
      showErrorPage(rootNode.firstChild.xml);
      xmlDoc.free;
      return "false";
    }

  }
}

// ////////////////////////��ʾ����ҳ/////////////////////////////////////////////////
function showErrorPage(errorMsg) {
  var sfeature = "dialogwidth:500px; Dialogheight:108px;center:yes;help:no;resizable:yes;scroll:no;status:no";
  var spath = g_jsContextPath+"/UI/system/public/errorShow.jsp";
  var arg = new Object();
  arg.errorMsg = errorMsg;

  var goupstr = window.showModalDialog(spath, arg, sfeature);

}
// //////////////��������������////////////////////////////
// / formelement ����form����
// / postpath ����������Ϣ
// //////////////////////////////////////////////////////

function fillform(formelement, xmlStr) {

  var xmlDoc = createDomDocument();
  xmlDoc.loadXML(xmlStr);

  var rootNode = xmlDoc.documentElement;

  if (rootNode.getAttribute("result") == "true") {
    for ( var i = 0; i < formelement.length; i++) {
      var obj = formelement.item(i);

      for ( var j = 0; j < rootNode.firstChild.childNodes.length; j++) {
        var node = rootNode.firstChild.childNodes[j];

        if (node.getAttribute("name") == obj.id) {
          if (obj.tagName.toUpperCase() == "TD") {
            if (decode(node.getAttribute("value")) != "null")
              obj.innerText = decode(node.getAttribute("value"));
          }
          if (obj.tagName.toUpperCase() == "INPUT") {
            if (obj.type == "text" || obj.type == "hidden" || obj.type == "password") {
              if (obj.fieldtype == "select") // ������ؼ�
              {
                if (decode(node.getAttribute("attr")) != "null") // attr��ֵ����Ϊ�գ�
                {
                  obj.dropOldValue = decode(node.getAttribute("attr"));
                  obj.oldvalue = decode(node.getAttribute("attr"));
                  obj.attr = obj.oldvalue;
                  obj.defvalue = obj.attr;

                  obj.value = decode(node.getAttribute("value"));
                  obj.expand = decode(node.getAttribute("expand"));
                }
              } else {
                if (obj.type == "hidden" && node.getAttribute("attr") != null) // attr��ֵ��dropdown���ͣ���ʹ��attr����value
                {
                  if (decode(node.getAttribute("attr")) != "null") // ֵ��Ч������null
                  {
                    obj.oldvalue = decode(node.getAttribute("attr"));
                    obj.value = decode(node.getAttribute("attr"));
                  }
                } else if (decode(node.getAttribute("value")) != "null") {
                  obj.oldvalue = decode(node.getAttribute("value"));
                  obj.value = decode(node.getAttribute("value"));
                }
              }
            }
            if (obj.type == "checkbox" || obj.type == "radio") {
              obj.oldvalue = decode(node.getAttribute("value"));
              if (obj.oldvalue == "1")
                obj.checked = true;
              else
                obj.checked = false;

            }
          }
          if (obj.tagName.toUpperCase() == "TEXTAREA") {
            if (decode(node.getAttribute("value")) != "null") {
              obj.oldvalue = decode(node.getAttribute("value"));
              obj.value = decode(node.getAttribute("value"));
            }
          }
          if (obj.tagName.toUpperCase() == "SELECT") {
            if (node.getAttribute("attr") != null) // dropdown���ͣ�ʹ��attrֵ����
            {
              obj.oldvalue = decode(node.getAttribute("attr"));
              obj.value = obj.oldvalue;
              if (obj.value == "")
                obj.selectedIndex = -1;
            } else {
              if (decode(node.getAttribute("value")) != "null") {
                obj.oldvalue = decode(node.getAttribute("value"));
                obj.value = obj.oldvalue;
                if (obj.value == "")
                  obj.selectedIndex = -1;
              }
            }
          }
        }
      }
    }

  } else
    alert(rootNode.text);

  xmlDoc.free;
}

// ����document����
function createDoc() {
  var xmlDoc = createDomDocument("<root/>");

  return xmlDoc;

}

// �������ڵ�
function createRootNode(xmlDoc) {
  return rootNode = xmlDoc.documentElement

}

// ���action�ڵ�
function createActionNode(xmlDoc, actionName) {
  var recorderNode = appendNode(xmlDoc, xmlDoc.documentElement, "action");
  appendAttri(xmlDoc, recorderNode, "actionname", actionName);
  return recorderNode;
}

// ���recorder�ڵ�
function createRecorderNode(xmlDoc, type) {
  var recorderNode = appendNode(xmlDoc, xmlDoc.documentElement, "recorder");
  appendAttri(xmlDoc, recorderNode, "type", type);
  return recorderNode;
}

// ���field�ڵ�
function createFieldNode(xmlDoc, name, type, value, oldValue, fieldLable) {
  var fieldNode = appendNode(xmlDoc, xmlDoc.documentElement, "field");
  appendAttri(xmlDoc, fieldNode, "name", name);
  appendAttri(xmlDoc, fieldNode, "type", type);
  appendAttri(xmlDoc, fieldNode, "value", encode(value));

  if (arguments.length >= 5)
    appendAttri(xmlDoc, fieldNode, "oldValue", encode(oldValue));

  if (arguments.length >= 6)
    appendAttri(xmlDoc, fieldNode, "fieldLable", encode(fieldLable));

  return fieldNode;

}

// ���field�ڵ�
function createFieldStrNode(xmlDoc, whereStr) {

  var whArr = whereStr.split("&");

  var fieldNode = createFieldNode(xmlDoc, whArr[0], whArr[1], whArr[2]);

  return fieldNode;

}

// //////////////����Tab ���б�ѡ�еļ�¼ ������ɾ��////////////////////////////
// / tabName ����
// //inputStr �ⲿ�������
// //actionName ����
// //////////////////////////////////////////////////////

function createTabCheckXML(tabName, inputStr, actionName, refresh, methodname) {
  var xmlDoc = createDoc();

  var tab = document.all(tabName);

  var rootNode = createRootNode(xmlDoc);

  var actionNode = createActionNode(xmlDoc, actionName);

  if (arguments.length == 5)
    appendAttri(xmlDoc, actionNode, "methodname", methodname);

  var wherrstr = "";
  for ( var i = 0; i < tab.rows.length; i++) {
    if (tab.rows[i].operate != "insert") {
      if (tab.rows[i].cells[0].children[0].checked) {
        wherrstr = tab.rows[i].whStr;
        if (inputStr != "") {
          wherrstr = wherrstr + "*" + inputStr;
        }
        var reorderNode = createDeleteRecord(wherrstr, xmlDoc);
        actionNode.appendChild(reorderNode);
      }
    }
  }

  // û�й�ѡʱ
  if (wherrstr == "" && tab.rows.length >= 1) { // >=1����¼ʱ
    // ȡ��ǰ��ļ�¼��
    if (tab.rows[tab.activeIndex].operate != "insert") {
      var wherrstr = tab.rows[tab.activeIndex].whStr;
      if (inputStr != "") {
        wherrstr = wherrstr + "*" + inputStr;
      }
      var reorderNode = createDeleteRecord(wherrstr, xmlDoc);
      actionNode.appendChild(reorderNode);
    }

  }

  rootNode.appendChild(actionNode);

  var retStr = ExecServerPrgm(g_jsContextPath+"/BI/util/SqlExcuteJsp.jsp", "POST", "sys_request_xml=" + xmlDoc.xml);

  if (analyzeReturnXML(retStr) == "true") {
    tab.AbsolutePage = "1";
    tab.RecordCount = "0";
    if (refresh == undefined || refresh)
      Table_Refresh(tab.id);
  }

}
