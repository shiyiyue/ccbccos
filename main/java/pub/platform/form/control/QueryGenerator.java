
package pub.platform.form.control;

import java.util.*;

import pub.platform.advance.utils.PropertyManager;
import pub.platform.db.*;
import pub.platform.form.config.*;
import pub.platform.form.util.*;
import pub.platform.form.util.datatype.*;
import pub.platform.form.util.event.*;

/**
 * @author ���滻
 * @version 1.0
 */
public class QueryGenerator {

    public static final String TARGET_TEMPLATE = "/templates/defaultform.jsp";

    /**
     * 1�����FORMʵ�������
     * 2�����FORMʵ�壨event.getInstanceid()��
     * 3�����ֵ��ctx.getRequestAttribute(SessionAttributes.REQUEST_FIND_RESULT_NAME)
     * 4��ȡ������action=form.getUrlocate()
     *       ����body=""
     *       body += "<table class='form_list'>"
     *       body += "<form method='post' action='$action$?instanceid=$instanceid$'>"
     * 5�����LISTͷ����ÿ���ֶε�TITLE
     * 6����ÿһ����¼ִ�����²���
     *       <tr>
     *       ��ÿһ���ֶ�ִ�����²���
     *         <td width= heiht= align= ����>
     *              ���������formatcls,��ʵ������ִ��format������������FieldFormat��
     *             ����ֱ��ת���ɴ����
     *         </td>
     *       </tr>
     * 7��body += "</form>";
     *       body += "</table>";
     * @param ctx
     * @param event
     * @return String
     * @roseuid 3F73DED000E0
     */
    public static String[] run(SessionContext ctx, Event event,ErrorMessages msgs,int result)
    {
        FormInstanceManager fiManager = (FormInstanceManager)ctx.getAttribute(SessionAttributes.SESSION_FORM_INSTANCE_MANAGER_NAME);
        FormInstance fi = fiManager.getFormInstance(event.getId());
        FormBean fb = FormBeanManager.getForm(fi.getFormid());
        String body = "";
        RecordSet rs = (RecordSet)ctx.getRequestAttribute(SessionAttributes.REQUEST_FIND_RESULT_NAME);
        String pageno = (String) ctx.getRequestAttribute(SessionAttributes.REQUEST_LIST_PAGENO_NAME);
        String pagecount = (String) ctx.getRequestAttribute(SessionAttributes.REQUEST_LIST_PAGECOUNT_NAME);
        boolean readonly = fi.isReadonly();
        String sysButton = "";

        int iPageno = 0;
        int iPageCount = 0;

        try {
            iPageno = Integer.parseInt(pageno);
        }
        catch (Exception e) {
            iPageno = 0;
        }
        try {
            iPageCount = Integer.parseInt(pagecount);
        }
        catch (Exception e) {
            iPageCount = 0;
        }
        int tblwidth = fb.getWidth();
        if ( tblwidth != 0 && tblwidth < 300 )
            tblwidth = 300;
        String instanceid = event.getId();
        int pagesize = fb.getRows();
        if (pagesize == 0)
            pagesize = PropertyManager.getIntProperty(SessionAttributes.REQUEST_LIST_PAGESIZE_NAME);

        String action = fb.getUrl();
        if ( action == null ) {
            action = PageGenerator.DEFAULT_URL_LOCATE;
        }
        if ( msgs.size() > 0 ) {
            body += PageGenerator.getErrorString(msgs, result);
        }
        body += "<script src='" + ctx.getUrl("/js/querybutton.js") + "' type='text/javascript'></script>";
        body += "<script src='" + ctx.getUrl("/js/meizzDate.js") + "' type='text/javascript'></script>";

        try {
            body += "<table><tr><td>";
            body += getQueryForm(ctx, fb, fi);
            body += "<form id='querywinform' method='post' action='" + ctx.getUrl(action) +
                "' target='_self'>";
            body += "<table class='list_form_table' width='" + tblwidth + "'>";

            String[] fldnames = fb.getElementKeys();
            int row = 0;
            if (fldnames != null) {
                //1.���̧ͷ
                body += "<tr class='list_form_title_tr'>";
                for (int i = 0; i < fldnames.length; i++) {
                    ElementBean eb = fb.getElement(fldnames[i]);
                    if (eb == null || !eb.isVisible() ||
                        eb.getComponetTp() == ComponentType.SYS_BUTTON ||
                        eb.getDisplayType() == FormBean.DISPLAY_QUERY)
                        continue;
                    int tdwidth = eb.getWidth();
                    if (tdwidth > 0) {
                        body += "<td width='" + tdwidth + "' class='list_form_title_td' nowrap>";
                    }
                    else {
                        body += "<td class='list_form_title_td' nowrap>";
                    }

                    String caption = eb.getCaption();
                    if (eb.getName() != null && eb.getName().toLowerCase().equals("checkbox")) {
                        caption =
                            "<input type='checkbox' class='delete_checkbox' name='allcheck' id='all' onclick='selectAll();'>";
                    }
                    else {
                        if (caption == null || caption.trim().length() == 0) {
                            caption = "&nbsp;";
                        }
                    }
                    body += caption.trim();

                    body += "</td>";
                }
                body += "</tr>";

                //2.�������
                if (rs != null) {
                    while (rs.next()) {
                        row++;
                        body += "<tr class='list_form_tr'>";
                        for (int i = 0; i < fldnames.length; i++) {
                            try {
                                ElementBean eb = fb.getElement(fldnames[i]);
                                if (eb == null || !eb.isVisible() ||
                                    eb.getComponetTp() == ComponentType.SYS_BUTTON ||
                                    eb.getDisplayType() == FormBean.DISPLAY_QUERY)
                                    continue;
                                if (eb.getDataType() == ElementBean.DATATYPE_DECIMAL ||
                                    eb.getDataType() == ElementBean.DATATYPE_INTEGER ) {
                                    body += "<td class='list_form_td' align='right' nowrap>";
                                }
                                else {
                                    body += "<td class='list_form_td' nowrap>";
                                }
                                FieldFormat ff = eb.getFormatcls();
                                String content = ff.format(ctx, eb, fi, rs);
                                if (content == null || content.trim().length() <= 0)
                                    content = "&nbsp;";
                                body += content;
                                body += "</td>";
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        body += "</tr>";
                    }
                }

                for (; /*row < 8 &&*/ row < pagesize; row++) {
                    body += "<tr class='list_form_tr'>";
                    for (int i = 0; i < fldnames.length; i++) {
                        ElementBean eb = fb.getElement(fldnames[i]);
                        if (eb == null || !eb.isVisible() ||
                            eb.getComponetTp() == ComponentType.SYS_BUTTON ||
                            eb.getDisplayType() == FormBean.DISPLAY_QUERY)
                            continue;

                        body += "<td class='list_form_td'>&nbsp;</td>";
                    }
                    body += "</tr>";
                }

            }

            body += "</table>";

            //�γɲ�ѯ����

            String commText = "<input type='hidden' name='" +
                SessionAttributes.REQUEST_INSATNCE_ID_NAME +
                "' value='" + event.getId() + "'>";
            commText += "<input type='hidden' name='" + SessionAttributes.REQUEST_EVENT_ID_NAME +
                "' value='" + EventType.FIND_EVENT_TYPE + "'>";
            String[] names = fb.getElementKeys();
            if (names != null) {
                for (int i = 0; i < names.length; i++) {
                    String name = names[i];
                    String[] values = ctx.getParameters(name);
                    if (values != null) {
                        for (int j = 0; j < values.length; j++) {
                            if (values[j] != null) {
                                commText += "<input type='hidden' name='" + name + "' value='" +
                                    values[j] + "'>";
                            }
                        }
                    }
                }
            }

            body += commText;
            body += "<input type='hidden' name='" + SessionAttributes.REQUEST_BUTTON_EVENT_NAME +
                "' value='" + SessionAttributes.REQUEST_BUTTON_DELETE_NAME + "'>";
            body += "</form>";
            body += "</td></tr></table>";
            sysButton = getSysButton(ctx,fb,event,iPageno,iPageCount,commText,readonly);
//            sysButton += getQueryForm(ctx, fb, fi);

        } catch ( Exception e ) {
            e.printStackTrace();
        }

        String[] rtnMsg = new String[2];
        rtnMsg[0] = body;
        rtnMsg[1] = sysButton;

        return rtnMsg;
    }

    private static String getSysButton(SessionContext ctx,FormBean fb,Event event,int iPageno,int iPageCount,String commText,boolean readonly) {
        String body = "";

        boolean useDelete = fb.isUseDelete();
        boolean useAdd    = fb.isUseAdd();
//�����޼�¼ʱ����ʾ�˵�
//        if ( iPageCount == 0 && !useDelete ) {
//            return body;
//        }
        //2003-11-5
        body += "<form id='listform' method='post' action='" + ctx.getUrl(TARGET_TEMPLATE) + "'>";
        body += commText;
        body += "<input type='hidden' name='" + SessionAttributes.REQUEST_LIST_PAGENO_NAME + "' value=''>";
        body += "<input type='hidden' name='" + SessionAttributes.REQUEST_LIST_PAGECOUNT_NAME + "' value=''>";
        body += "</form>";
        //2003-11-5
//        body += "<table class='blank_table'></table>";
        body += "<table class='list_button_tbl'>";
        body += "<tr class='list_button_tbl_tr'>";

        body += "<td class='list_button_tbl_td'>";
        body += "<input type=\"button\" name=\"submit6\" class=\"list_button_active\" value=\"��һ��\" onClick=\"history.go(-1);\">";
        body += "</td>";

        List sysButton = fb.getSysButton();
        for ( int i = 0 ; i < sysButton.size() ; i++ ) {
            ElementBean ebt = (ElementBean)sysButton.get(i);

            body += "<td class='list_button_tbl_td'>";
            body +=
                    "<input class='list_button_active' type='button' name='delete' value='" + ebt.getCaption() + "' " +
                    " onClick='pressSelfButton(\""+ ebt.getName() + "\");'>";
            body += "</td>";
        }
        //�������Ӱ�ť
        if ( useAdd ) {
            body += "<td class='list_button_tbl_td'>";
            if ( readonly ) {
                body +=
                    "<input class='list_button_disabled' type='button' name='delete' value=' ���� ' disabled='true'>";
            } else {
                body +=
                    "<input class='list_button_active' type='button' name='delete' value=' ���� ' " +
                    " onClick='pressAdd();'>";
            }
            body += "</td>";
        }
        if ( useDelete ) {
            body += "<td class='list_button_tbl_td'>";
            if ( readonly || iPageCount < 0 ) {
                body +=
                    "<input class='list_button_disabled' type='button' name='delete' value=' ɾ�� ' disabled='true'>";
            } else {
                body +=
                    "<input class='list_button_active' type='button' name='delete' value=' ɾ�� ' onClick='pressDelete();'>";
            }
            body += "</td>";
        }

        //������ҳ��ť
        body += "<td class='list_button_tbl_td'>";
        if (iPageno == 0) {
            body += "<input type='button' name='submit1' class='list_button_disabled' value=' ��ҳ ' disabled='true'>";
        }
        else {
            body += "<input type=\"button\" name=\"submit1\" class=\"list_button_active\" value=\" ��ҳ \" onClick=\"buttonClick('0','"+iPageCount+"');\">";
        }

        body += "</td>";

        //������һҳ��ť
        body += "<td class='list_button_tbl_td'>";
        if (iPageno <= 0) {
            body += "<input type='button' name='submit2' class='list_button_disabled' value='��һҳ' disabled='true'>";
        }
        else {
            body += "<input type=\"button\" name=\"submit2\" class=\"list_button_active\" value=\"��һҳ\" onClick=\"buttonClick('"+(iPageno - 1)+"','"+iPageCount+"');\">";
        }
        body += "</td>";
        //������һҳ��ť
        body += "<td class='list_button_tbl_td'>";
        if (iPageno >= iPageCount) {
            body += "<input type='submit' name='submit3' class='list_button_disabled' value='��һҳ' disabled='true'>";
        }
        else {
            body += "<input type=\"button\" name=\"submit3\" class=\"list_button_active\" value=\"��һҳ\" onClick=\"buttonClick('"+(iPageno + 1)+"','"+iPageCount+"');\">";
        }

        body += "</td>";
        //����βҳ��ť
        body += "<td class='list_button_tbl_td'>";
        if (iPageno >= iPageCount) {
            body += "<input type='submit' name='submit4' class='list_button_disabled' value=' βҳ ' disabled='true'>";
        }
        else {
            body += "<input type=\"button\" name=\"submit4\" class=\"list_button_active\" value=\" βҳ \" onClick=\"buttonClick('" + iPageCount +"','" + iPageCount + "');\">";
        }

        body += "</td>";

        body += "<td class='list_button_tbl_td'>";
//        body += "<input type=\"button\" name=\"submit5\" class=\"list_button_active\" value=\" ˢ�� \" onClick=\"window.location.reload();\">";
        body += "<input type=\"button\" name=\"submit5\" class=\"list_button_active\" value=\" ˢ�� \" onClick=\"buttonClick('"+iPageno+"','"+iPageCount+"');\">";

        body += "</td>";

        body += "</td>";

        body += "<td class='list_button_tbl_td'>";
        body += "<input type=\"button\" name=\"submit7\" class=\"list_button_active\" value=\" ���� \" onClick=\"window.close();\">";

        body += "</td>";



        //���ɵ�����ҳ��ť
/*
        body += "<td class='list_button_tbl_td'>";
        body += "<form id='winform5' method='post' action='" + ctx.getUrl(TARGET_TEMPLATE) + "'>";
        body += commText;
        body += "<input type='hidden' name='" + SessionAttributes.REQUEST_LIST_PAGECOUNT_NAME +
            "' value='" + iPageCount + "'>";
        body += commText;
        body += "����<input type='text' name='" + SessionAttributes.REQUEST_LIST_PAGENO_NAME +
            "' value='" + (iPageno + 1) + "' size='3'>ҳ"+(iPageno + 1)+"/"+(iPageCount+1)+"ҳ";
        body += "<input class='list_button_submit' type='submit' name='submit' class='list_button_go' value=' G O '>";

        body += "</form>";

        body += "</td>";
*/

        body += "</tr>";
        body += "</table>";

        return body;
    }
/*
    private static String getQueryForm(SessionContext ctx,FormBean fb,FormInstance fi) {
        String body = "";

        String action = fb.getUrl();
        if ( action == null ) {
            action = PageGenerator.DEFAULT_URL_LOCATE;
        }
        try {
            body += "<table><tr><td>";
            String onclick = "if ( filter.style.visibility == 'hidden' ) { filter.style.visibility ='';} else { filter.style.visibility = 'hidden';}";
            body += "<a href=\"#\" onClick=\""+onclick+"\">��ѯ</a>";
            body += "</td></tr></table>";

            body += "<form id='winform' method='post' action='" + ctx.getUrl(action) + "'>";
            body += "<div id='filter' style='visibility:hidden'>";
            body += "<table class='query_table'>";
            body += "<tr class='query_tr'>";
            body += "<td class='query_td'>";
            List queryFlds = fb.getQueryField();
            int rows = 0;

            if (queryFlds.size() > 0) {
                body += "<table class='query_form_table'>";
                int i = 0;
                for (; i < queryFlds.size(); i++) {
                    ElementBean ebt = (ElementBean) queryFlds.get(i);
//                    if (i % 2 == 0) {
                    body += "<tr class='query_form_tr' nowrap>";
                    rows++;
//                    }
                    try {
                        FormElementValue fev = fi.getValue(ebt.getName());
                        ebt = (ElementBean) ebt.clone();
//                        ebt.setIsnull(true);
//                        body += PageGenerator.getElemetScript(ctx, fi.getInstanceid(), ebt, fev, false, false);
                        if (ebt.getComponetTp() == ComponentType.HIDDEN_TYPE) {
                            if (ebt != null) {
                                ebt.setComponetTp(ComponentType.TEXT_TYPE);
                            }
                            else {
                                ebt = fb.getElement(ebt.getName());
                            }
                        }
                        ebt.setDefaultValue("");
                        ebt.setIsnull(true);
                        ebt.setVisible(true);

                        ebt.setReadonly(false);

                        body += PageGenerator.getViewElemetScript(ctx, fi.getInstanceid(), ebt, fev, false, false);
                        if ( ebt.getComponetTp() == ComponentType.TEXT_TYPE || ebt.getComponetTp() == ComponentType.DATE_TYPE ||
                             ebt.getComponetTp() == ComponentType.REFERENCE_TEXT_TYPE ) {
                            ebt.setName(ebt.getName()+"large");
                            ebt.setCaption(" �� ");
                            body += PageGenerator.getViewElemetScript(ctx, fi.getInstanceid(), ebt, fev, false, false);
                        }

                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
//                    if (i % 2 == 1)
                        body += "</tr>";
                }
//                if (i % 2 == 1) {
//                    body += "<td>&nbsp;</td><td>&nbsp;</td></tr>";
//                }
                List queryHiddens = fb.getQueryHiddenFlds();
                for (i = 0; i < queryHiddens.size(); i++) {
                    ElementBean ebt = (ElementBean) queryHiddens.get(i);
                    FormElementValue fev = fi.getValue(ebt.getName());
                    String[] values = fev.getValueArray();
                    if (values != null && values.length > 0) {
                        body += "<input type='hidden' name='" + ebt.getName() + "' value='" +
                            values[0] +
                            "'>";
                    }
                    else {
                        body += "<input type='hidden' name='" + ebt.getName() + "' value=''>";
                    }
                }
                body += "<input type='hidden' name='" + SessionAttributes.REQUEST_INSATNCE_ID_NAME +
                    "' value='" + fi.getInstanceid() + "'>";

                body += "<input type='hidden' name='" + SessionAttributes.REQUEST_EVENT_ID_NAME +
                    "' value='" + EventType.FIND_EVENT_TYPE + "'>";

                body += "</table>";
                body += "</td>";
                body += "<td class='query_td'>";
                body += "<table class='page_form_button_table'>";
                body += "<tr>";
                body += "<td nowrap valign='top'>";
                body += "<input type='submit' class='query_button' name='submit' value=' �� �� '>";
                body += "</td>";
                body += "</tr>";
                body += "<tr>";
                body += "<td nowrap valign='top'>";
                body += "<input type='reset' class='query_button' name='reset' value=' �� �� '>";
                body += "</td>";
                body += "</tr>";

                body += "</table>";
            }
            else {
                queryFlds = fb.getSearchKeys();
                if (queryFlds.size() > 0) {
                    body += "<table class='query_form_table'>";
                    int i = 0;
                    for (; i < queryFlds.size(); i++) {
                        ElementBean ebt = (ElementBean) queryFlds.get(i);
//                        if (i % 2 == 0) {
                            body += "<tr class='query_form_tr' nowrap>";
                            rows++;
//                        }
                        try {
                            FormElementValue fev = fi.getValue(ebt.getName());
                            ebt = (ElementBean) ebt.clone();
                            ebt.setIsnull(true);
                            ebt.setVisible(true);
                            if (ebt.getComponetTp() == ComponentType.HIDDEN_TYPE)
                                ebt.setComponetTp(ComponentType.TEXT_TYPE);
                            body += PageGenerator.getElemetScript(ctx, fi.getInstanceid(), ebt, fev, false, false);
                            if ( ebt.getComponetTp() == ComponentType.TEXT_TYPE || ebt.getComponetTp() == ComponentType.DATE_TYPE ||
                                 ebt.getComponetTp() == ComponentType.REFERENCE_TEXT_TYPE ) {
                                ebt.setName(ebt.getName()+"large");
                                ebt.setCaption(" �� ");
                                body += PageGenerator.getViewElemetScript(ctx, fi.getInstanceid(), ebt, fev, false, false);
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
//                        if (i % 2 == 1)
                            body += "</tr>";
                    }
//                    if (i % 2 == 1) {
//                        body += "<td>&nbsp;</td><td>&nbsp;</td></tr>";
//                    }
                    List queryHiddens = fb.getQueryHiddenFlds();
                    for (i = 0; i < queryHiddens.size(); i++) {
                        ElementBean ebt = (ElementBean) queryHiddens.get(i);
                        if (ebt.isIsSearchKey())
                            continue;
                        FormElementValue fev = fi.getValue(ebt.getName());
                        String[] values = fev.getValueArray();
                        if (values != null && values.length > 0) {
                            body += "<input type='hidden' name='" + ebt.getName() + "' value='" +
                                values[0] +
                                "'>";
                        }
                        else {
                            body += "<input type='hidden' name='" + ebt.getName() + "' value=''>";
                        }
                    }
                    body += "<input type='hidden' name='" +
                        SessionAttributes.REQUEST_INSATNCE_ID_NAME +
                        "' value='" + fi.getInstanceid() + "'>";

                    body += "<input type='hidden' name='" + SessionAttributes.REQUEST_EVENT_ID_NAME +
                        "' value='" + EventType.FIND_EVENT_TYPE + "'>";

                    body += "</table>";
                    body += "</td>";
                    body += "<td class='query_td'>";
                    body += "<table class='page_form_button_table'>";
                    body += "<tr>";
                    body += "<td nowrap valign='top'>";
                    body +=
                        "<input type='submit' class='query_button' name='submit' value=' �� �� '>";
                    body += "</td>";
                    body += "</tr>";
                    body += "<tr>";
                    body += "<td nowrap valign='top'>";
                    body += "<input type='reset' class='query_button' name='reset' value=' �� �� '>";
                    body += "</td>";
                    body += "</tr>";

                    body += "</table>";
                }
            }

            body += "</td>";
            body += "</tr>";
            body += "</table>";
            body += "</div>";
            body += "</form>";
        } catch ( Exception e ) {
            e.printStackTrace();
        }

        return body;
    }*/
    private static String getQueryForm(SessionContext ctx,FormBean fb,FormInstance fi) {
        String body = "";
        String queryStr = "";

        String action = fb.getUrl();
        if (action == null) {
            action = PageGenerator.DEFAULT_URL_LOCATE;
        }
        try {
            body += "<form id=\"winform\" method=\"post\" action=\"" + ctx.getUrl(action) + "\">";
            body += "<table class=\"query_table\">";
            body += "<tr class=\"query_tr\">";
            body += "<td class=\"query_td\">";
            List queryFlds = fb.getQueryField();
            int rows = 0;

            if (queryFlds.size() > 0) {
                body += "<table class=\"query_form_table\">";
                int i = 0;
                for (; i < queryFlds.size(); i++) {
                    ElementBean ebt = (ElementBean) queryFlds.get(i);
                    body += "<tr class=\"query_form_tr\" nowrap>";
                    rows++;
                    try {
                        FormElementValue fev = fi.getValue(ebt.getName());
                        ebt = (ElementBean) ebt.clone();
                        if (ebt.getComponetTp() == ComponentType.HIDDEN_TYPE) {
                            if (ebt != null) {
                                ebt.setComponetTp(ComponentType.TEXT_TYPE);
                            }
                            else {
                                ebt = fb.getElement(ebt.getName());
                            }
                        }
                        ebt.setDefaultValue("");
                        ebt.setIsnull(true);
                        ebt.setVisible(true);

                        ebt.setReadonly(false);

                        body += PageGenerator.getViewElemetScript(ctx, fi.getInstanceid(), ebt, fev, false, false);
                        if (ebt.getComponetTp() == ComponentType.TEXT_TYPE ||
                            ebt.getComponetTp() == ComponentType.DATE_TYPE ||
                            ebt.getComponetTp() == ComponentType.REFERENCE_TEXT_TYPE) {
                            //�γɷ�Χ�ֶΣ���ԭ�����ֶλ��������largelarge
                            ebt.setName(ebt.getName()+"largelarge");
                            ebt.setCaption(" �� ");
                            body +=
                                PageGenerator.getViewElemetScript(ctx, fi.getInstanceid(), ebt, fev, false, false);
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    body += "</tr>";
                }
                List queryHiddens = fb.getQueryHiddenFlds();
                for (i = 0; i < queryHiddens.size(); i++) {
                    ElementBean ebt = (ElementBean) queryHiddens.get(i);
                    FormElementValue fev = fi.getValue(ebt.getName());
                    String[] values = fev.getValueArray();
                    if (values != null && values.length > 0) {
                        body += "<input type=\"hidden\" name=\"" + ebt.getName() + "\" value=\"" +
                            values[0] +
                            "\">";
                    }
                    else {
                        body += "<input type=\"hidden\" name=\"" + ebt.getName() + "\" value=\"\">";
                    }
                }
                body += "<input type=\"hidden\" name=\"" + SessionAttributes.REQUEST_INSATNCE_ID_NAME +
                    "\" value=\"" + fi.getInstanceid() + "\">";

                body += "<input type=\"hidden\" name=\"" + SessionAttributes.REQUEST_EVENT_ID_NAME +
                    "\" value=\"" + EventType.FIND_EVENT_TYPE + "\">";

                body += "</table>";
                body += "</td>";
                body += "<td class=\"query_td\">";
                body += "<table class=\"page_form_button_table\">";
                body += "<tr>";
                body += "<td nowrap valign=\"top\">";
                body += "<input type=\"submit\" class=\"query_button\" name=\"submit\" value=\" �� �� \">";
                body += "</td>";
                body += "</tr>";
                body += "<tr>";
                body += "<td nowrap valign=\"top\">";
                body += "<input type=\"reset\" class=\"query_button\" name=\"reset\" value=\" �� �� \">";
                body += "</td>";
                body += "</tr>";

                body += "</table>";
            }
            else {
                queryFlds = fb.getSearchKeys();
                if (queryFlds.size() > 0) {
                    body += "<table class=\"query_form_table\">";
                    int i = 0;
                    for (; i < queryFlds.size(); i++) {
                        ElementBean ebt = (ElementBean) queryFlds.get(i);
                        body += "<tr class=\"query_form_tr\" nowrap>";
                        rows++;
                        try {
                            FormElementValue fev = fi.getValue(ebt.getName());
                            ebt = (ElementBean) ebt.clone();
                            ebt.setIsnull(true);
                            ebt.setVisible(true);
                            if (ebt.getComponetTp() == ComponentType.HIDDEN_TYPE)
                                ebt.setComponetTp(ComponentType.TEXT_TYPE);
                            body += PageGenerator.getElemetScript(ctx, fi.getInstanceid(), ebt, fev, false, false);
                            if (ebt.getComponetTp() == ComponentType.TEXT_TYPE ||
                                ebt.getComponetTp() == ComponentType.DATE_TYPE ||
                                ebt.getComponetTp() == ComponentType.REFERENCE_TEXT_TYPE) {
                                ebt.setName(ebt.getName()+"largelarge");
                                ebt.setCaption(" �� ");
                                body +=
                                    PageGenerator.getViewElemetScript(ctx, fi.getInstanceid(), ebt,
                                    fev, false, false);
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                        body += "</tr>";
                    }
                    List queryHiddens = fb.getQueryHiddenFlds();
                    for (i = 0; i < queryHiddens.size(); i++) {
                        ElementBean ebt = (ElementBean) queryHiddens.get(i);
                        if (ebt.isIsSearchKey())
                            continue;
                        FormElementValue fev = fi.getValue(ebt.getName());
                        String[] values = fev.getValueArray();
                        if (values != null && values.length > 0) {
                            body += "<input type=\"hidden\" name=\"" + ebt.getName() + "\" value=\"" +
                                values[0] +
                                "\">";
                        }
                        else {
                            body += "<input type=\"hidden\" name=\"" + ebt.getName() + "\" value=\"\">";
                        }
                    }
                    body += "<input type=\"hidden\" name=\"" +
                        SessionAttributes.REQUEST_INSATNCE_ID_NAME +
                        "\" value=\"" + fi.getInstanceid() + "\">";

                    body += "<input type=\"hidden\" name=\"" + SessionAttributes.REQUEST_EVENT_ID_NAME +
                        "\" value=\"" + EventType.FIND_EVENT_TYPE + "\">";

                    body += "</table>";
                    body += "</td>";
                    body += "<td class=\"query_td\">";
                    body += "<table class=\"page_form_button_table\">";
                    body += "<tr>";
                    body += "<td nowrap valign=\"top\">";
                    body +=
                        "<input type=\"submit\" class=\"query_button\" name=\"submit\" value=\" �� �� \">";
                    body += "</td>";
                    body += "</tr>";
                    body += "<tr>";
                    body += "<td nowrap valign=\"top\">";
                    body += "<input type=\"reset\" class=\"query_button\" name=\"reset\" value=\" �� �� \">";
                    body += "</td>";
                    body += "</tr>";

                    body += "</table>";
                }
            }

            body += "</td>";
            body += "</tr>";
            body += "</table>";
            body += "</form>";
            body = "<script language='javascript'> var content='"+body+"';</script>";
            queryStr += body;
            queryStr += "<table id='detail'><tr><td>";
            String onclick = "if ( dd.props == '' ) { dd.props = '1'; dd.title='����˴����ز�ѯ������';addQueryItem(); } else { dd.props = ''; dd.title='����˴���ѯ����';delQueryItem(detail);}";
//            queryStr += "<a id='dd' href=\"#\" props='' onClick=\"" + onclick + "\"><image src='"+ctx.getUrl("/images/menu-images/HaveSub.gif")+"'></image></a>";
            queryStr += "<image id='dd' props='' title='����˴���ѯ����' onClick=\"" + onclick + "\" src='"+ctx.getUrl("/images/menu-images/HaveSub.gif")+"'></image>";
            queryStr += "</td></tr></table>";

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return queryStr;
    }

}
