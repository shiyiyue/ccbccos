<%@ page contentType="text/html; charset=GBK"%>
<%@include file="/global.jsp" %>
<%@ page import="pub.platform.db.*"%>
<html>
  <head>
    <title></title>
    <LINK href="<%=contextPath%>/css/ccb.css" type="text/css" rel="stylesheet">
      <%--
          <script language="javascript" src="<%=contextPath%>/js/basic.js"></script>
          <script language="javascript" src="<%=contextPath%>/js/xmlHttp.js"></script>
          <script language="javascript" src="<%=contextPath%>/js/dbgrid.js"></script>
          <script language="javascript" src="<%=contextPath%>/js/dropdownData.js"></script>
          <script language="javascript" src="<%=contextPath%>/js/dbutil.js"></script>
            <script language="javascript" src="<%=contextPath%>/js/menu.js"></script>
      --%>
      <script language="javascript" src="ActionJsp.js"></script>
  </head>
  <%
        DBGrid dbGrid = new DBGrid();
        dbGrid.setGridID("ActionTable");
        //dbGrid.setGridTitleVisible(false);

        //dbGrid.setGridBottomVisible(false);

        dbGrid.setGridType("edit");
        dbGrid
            .setfieldSQL("select LogicCode as keycode, LogicCode,LogicClass,LogicMethod,LogicDesc from PTLogicAct where  ");
        dbGrid.setenumType("-1,0,0,0,0");
        dbGrid.setvisible("false,true,true,true,true");
        dbGrid.setfieldcn("����,���ܱ��,������,���ܷ���,��������");

        dbGrid.setfieldWidth("3,0,15,40,15,20");
        dbGrid.setfieldName("keycode,LogicCode,LogicClass,LogicMethod,LogicDesc");

        dbGrid.setfieldType("text,text,text,text,text");
        dbGrid.setfieldCheck(";isNull=false,textLength=20;isNull=false,textLength=200;textLength=50;textLength=200");
        dbGrid.setpagesize(30);
        dbGrid.setCheck(true);
        dbGrid.setWhereStr("  1=2 ");

        //////���ݼ���ť
        dbGrid.setdataPilotID("datapilot");
        dbGrid.setbuttons("default");
  %>
  <body onload="body_load()" onresize="body_load()" class="Bodydefault">
    <fieldset>
      <legend>
        ��ѯ����
      </legend>
      <table style=" width:100%" border="0" cellspacing="0" cellpadding="0">
        <form id="deptform">
        <tr height="20">
          <td width="80" class="lbl_right_padding">
            ���ܱ��
          </td>
          <td width="80" class="data_input">
            <input id="cationid" type="text">
          </td>
          <td width="80" class="lbl_right_padding">
            ������
          </td>
          <td width="60" class="data_input">
            <input id="actionclass" type="text">
          </td>
          <td width="80" class="lbl_right_padding">
            ��������
          </td>
          <td width="80" class="data_input">
            <input id="actiondes" type="text">
          </td>
          <td>
            &nbsp;
          </td>
          <td>
            <input id="savebut" class="buttonGrooveDisable" onmouseover="button_onmouseover()" onmouseout="button_onmouseout()" type="button" value="��ѯ" onclick="queryClick();">
            <input name="" class="buttonGrooveDisable" type="reset" value="����" onmouseover="button_onmouseover()" onmouseout="button_onmouseout()">
          </td>
        </tr>
        </form>
      </table>
    </fieldset>
    <fieldset>
      <legend>
        �Ự�б�
      </legend>
      <table width="100%">
        <tr>
          <td>
            <%=dbGrid.getDBGrid()%>
          </td>
        </tr>
      </table>
    </fieldset>
    <fieldset>
      <legend>
        ����
      </legend>
      <table width="100%" rules="border" class="title1">
        <tr>
          <td>
            <span id="title"></span>
          </td>
          <td align="right">
            <%=dbGrid.getDataPilot()%>
          </td>
        </tr>
      </table>
    </fieldset>
  </body>
</html>
