<%@include file="/global.jsp" %>
<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="pub.platform.db.*"%>
<%@ page import="pub.platform.html.*" %>
<%@ page import="pub.platform.html.*,pub.platform.advance.utils.*" %>
<html>
<head>
<title>

</title>
	<LINK href="<%=contextPath%>/css/ccb.css" type="text/css" rel="stylesheet">
    <script language="javascript" src="<%=contextPath%>/js/basic.js"></script>
    <script language="javascript" src="<%=contextPath%>/js/xmlHttp.js"></script>
    <script language="javascript" src="<%=contextPath%>/js/dbgrid.js"></script>
	<script language="javascript" src="<%=contextPath%>/js/dropdownData.js"></script>
    <script language="javascript" src="<%=contextPath%>/js/dbutil.js"></script>
    <script language="javascript" src="lowerdept.js"></script>

</head>




<%



    DBGrid  dbGrid= new DBGrid();
    dbGrid.setGridID("userTable");
     dbGrid.setGridType("edit");
    dbGrid.setfieldSQL("select MOUDLEID,ERRORTYPE,ERRORINDEX,MESSAGE from PTERROR where  (1=1) ");
    dbGrid.setfieldcn("ģ����, ��������, ������ ,������Ϣ");
    dbGrid.setenumType("-1,-1,-1,0") ;
    dbGrid.setvisible("false,false,true,true");
    dbGrid.setfieldName("moudleid,errortype,errorindex,message");
    dbGrid.setfieldWidth("5,0,0,20,80");
    dbGrid.setfieldType("text,text,text,text");
    dbGrid.setfieldCheck(" isNull=false ; isNull=false ; isNull=false ;isNull=false ");
    dbGrid.setWhereStr(" and 1>1");

    dbGrid.setpagesize(10);
    dbGrid.setCheck(true);
    //////���ݼ���ť
    dbGrid.setdataPilotID("datapilot");
    dbGrid.setbuttons("default");
%>
<body bgcolor="#ffffff" onload="body_load()">

<table width="100%">
     <tr>
          <td>
               <table width="100%" rules="border" class="title1">
                 <tr><td><span id="title"></span></td>
                         <td align="right">
                             <%=dbGrid.getDataPilot()%>
                         </td></tr>
               </table>
		</td>
     </tr>

      <tr>
          <td>
               <table  width="100%" rules="border" class="title1" >
                   <tr><td>
                    <%=dbGrid.getDBGrid()%>
                   </td></tr>

               </table>
		</td>
     </tr>

       <tr>
          <td>
               <form id="feerateedit" >
               <table  align="center">
                         <tr>
                              <td>ҵ������:</td><td>
                                <%
                                   ZtSelect zscn = new ZtSelect("ywbh", "CM_YWLX");

                                   zscn.addAttr("style", "width: 100px");
                                   zscn.addOption("����","GGMK");
                                   zscn.addAttr("disabled","true");

                                   zscn.setDisplayAll(false);
                                   out.print(zscn);
                                   %>
                              </td>

                              <td>�������:</td><td>
                                   <%
                                    zscn = new ZtSelect("errortype", "");

                                   zscn.addAttr("style", "width: 100px");
                                    zscn.addOption("��ʾ","0");
                                   zscn.addOption("��ͨ","1");
                                   zscn.addOption("����","2");
                                   zscn.addAttr("disabled","true");
                                   zscn.setDisplayAll(false);
                                   out.print(zscn);
                                   %>
                              </td>

                                <td>������:</td><td><input  id="errorindex" class="text" type="text"   isNull=false  maxlength=20></td>


                         </tr>
                         <tr>
                              <td>������ʾ:</td><td  colspan="5"><textarea id="message"    isNull=false  rows="3" cols="60"></textarea></td>

                          </tr>

                    </table>

               </form>
		</td>
     </tr>
 </table>
</body>
</html>
