<!--
/*********************************************************************
* ��������: ��ѯ����ģ��
***********************************************************************/
-->
<%@page contentType="text/html; charset=GBK" %>
<%@include file="/global.jsp" %>
<html>
<head>
    <META http-equiv="Content-Type" content="text/html; charset=GBK">
    <title></title>
    <script language="javascript" src="queryTemplate.js"></script>
    <script language="javascript" src="<%=contextPath%>/UI/support/pub.js"></script>
    <script language="javascript" src="<%=contextPath%>/UI/support/pub.js"></script>
    <script language="javascript" src="<%=contextPath%>/UI/support/common.js"></script>
    <script language="javascript" src="<%=contextPath%>/UI/support/DataWindow.js"></script>
    <LINK href="<%=contextPath%>/css/newccb.css" type="text/css" rel="stylesheet">

</head>
<body onload="formInit()" bgcolor="#ffffff" class="Bodydefault">

<fieldset style="padding:40px 25px 0px 25px;margin:0px 20px 0px 20px">

    <legend>��ѯ����</legend>
    <form id="queryForm" name="queryForm">
        <table border="0" cellspacing="0" cellpadding="0" width="100%" style="margin:20px 0px 0px">
            <input type="hidden" value="miscRpt03" id="rptType" name="rptType"/>
            <tr>
                <td width="25%" nowrap="nowrap" class="lbl_right_padding">����</td>
                <td width="20%" nowrap="nowrap" class="data_input"><input type="text" id="MORTEXPIREDATE"
                                                                          name="MORTEXPIREDATE" onClick="WdatePicker()"
                                                                          fieldType="date" size="20"></td>
                <td width="5%" nowrap="nowrap" class="lbl_right_padding">��</td>
                <td width="50%" nowrap="nowrap" class="data_input"><input type="text" id="MORTEXPIREDATE2"
                                                                          name="MORTEXPIREDATE2" onClick="WdatePicker()"
                                                                          fieldType="date" size="20"></td>
            </tr>
            <tr>
                <td colspan="4" nowrap="nowrap" align="center" style="padding:20px 0px 10px">
                    <input name="expExcel" class="buttonGrooveDisableExcel" type="button"
                           onClick="exportExcel_click()" value="����excel">
                    <input type="reset" value="����" class="buttonGrooveDisable">
                </td>
            </tr>
        </table>
    </form>
</fieldset>

<br/>
<br/>
<br/>

<div class="help-window">
    <DIV class=formSeparator>
        <H2>[XX��ѯͳ�Ʊ�]</H2>
    </DIV>
    <div class="help-info">
        <ul>
            <li>��ѯ����Ϊ��.</li>
            <li>��ѯȨ�޿��Ʒ�ʽ.</li>
            <li>��ѯ����ע������.</li>
        </ul>
    </div>
</div>


</body>
</html>
