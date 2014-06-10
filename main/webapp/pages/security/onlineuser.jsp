<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="pub.platform.security.OnLineOpersManager" %>
<%@ page import="pub.platform.security.OperatorManager" %>
<%@ page import="pub.platform.system.manage.dao.PtOperBean" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Map" %>
<%
    HashMap<String,OperatorManager> operMaps = OnLineOpersManager.getAllOperMaps(application);
%>
<html>
<head>
    <title>�����û��б�</title>
    <link href="../../css/ccb.css" rel="stylesheet" type="text/css">
</head>
<body>
<div style='margin-left:15px;margin-right:35px; overflow:auto;height:520px;top:0px;width:100%;position:absolute;'>
    <table align="center" width="100%">
        <tr align="left">
            <td height="30">&nbsp;����������<font color="red" size="1"><%=operMaps.size()%>
            </font></td>
        </tr>
        <tr align="center">
            <td>
                <table width='100%'>
                    <tr>
                        <td>
                            <table width='100%' class="borderGroove">
                                <tr class="borderGrooveB">
                                    <td width='13%' align="center" class="borderGrooveBR">��������</td>
                                    <td width='12%' align="center" class="borderGrooveBR">����Ա����</td>
                                    <td width='10%' align="center" class="borderGrooveBR">����Ա��ʶ</td>
                                    <td width='35%' align="center" class="borderGrooveBR">��¼��Ϣ</td>
                                    <td width='20%' align="center" class="borderGrooveBR">SessionUser</td>
                                    <%--<td width='10%' align="center" class="borderGrooveBR">����</td>--%>
                                </tr>
                                <%
                                        Iterator iter = operMaps.entrySet().iterator();
                                        while (iter.hasNext()) {
                                        Map.Entry<String,OperatorManager> entry = (Map.Entry<String,OperatorManager>) iter.next();
                                        String sessionKey = entry.getKey();
                                        OperatorManager om = entry.getValue();
                                        PtOperBean onLineOper = om.getOperator();
                                        %>
                                  <tr >
                                  <td align="center" class="borderGrooveBR" ><%=onLineOper.getPtDeptBean().getDeptname()%></td>
                                  <td align="center" class="borderGrooveBR" ><%=onLineOper.getOpername()%></td>
                                  <td align="center" class="borderGrooveBR"><%=onLineOper.getOperid()%></td>
                                  <td class="borderGrooveBR" ><%=onLineOper.getFillstr600()%> </td>
                                  <td align="center" class="borderGrooveBR" ><%=sessionKey%></td>
<%--
                                      <td align="center" class="borderGrooveBR"><a href="killlineuser.jsp?killsession=<%=sessionId%>">ǿ������</a>
--%>
                                </tr>
                                <%
                                        }
                                %>
                            </table>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</div>
</body>
</html>