/**
 * Copyright 2003 ZhongTian, Inc. All rights reserved.
 *
 * qingdao tec PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * $Id: DBUtil.java,v 1.1 2006/05/17 09:19:30 wiserd Exp $
 * File:DBUtil.java
 * Date Author Changes
 * March 10 2003 wangdeliang Created
 */

package pub.platform.db;

import pub.platform.advance.utils.PropertyManager;
import pub.platform.form.config.SystemAttributeNames;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

/**
 * ���ݿ⹤�߰�
 *
 * @author <a href="mailto:wuyeyuan@tom.com">wuyeyuan</a>
 * @version $Revision: 1.1 $ $Date: 2006/05/17 09:19:30 $
 */

public class DBUtil {

    /**
     * ����������ת����SQL92�ı�׼
     *
     * @param date
     *            ����
     * @return
     */
    static Logger logger = Logger.getLogger("zt.platform.db.DBUtil");

    public static String formatWithSql92Date(Date date) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh : mm : ss");
            return "{ts '" + sdf.format(date) + "'}";
        } else {
            return "NULL";
        }
    }

    public static String formatDateTime(String DateTimeStr) {
        if (DateTimeStr != null) {
            return "{ts '" + DateTimeStr + "'}";
        } else {
            return null;
        }
    }

    /**
     * ��Ҫ��ת��
     *
     * @param content
     * @param fromEncoding
     * @param toEncoding
     * @return
     */
    public static String toDB(String content, String fromEncoding, String toEncoding) {
        // if ( content == null )
        // return null;
        // try {
        // byte[] tt;
        // if ( fromEncoding == null ) {
        // tt = content.getBytes();
        // } else {
        // //logger.info("formEncoding is "+fromEncoding);
        // tt = content.getBytes(fromEncoding);
        // }
        // if ( toEncoding == null )
        // return new String(tt);
        // return new String(tt,toEncoding);
        // } catch ( Exception e ) {
        // return content;
        // }

        return content;
    }

    /**
     * ��Ҫ��ת��
     *
     * @param content
     * @param fromEncoding
     * @param toEncoding
     * @return
     */
    public static String fromDB(String content, String fromEncoding, String toEncoding) {
        return toDB(content, fromEncoding, toEncoding);
    }

    /**
     * ��Ҫ��web����תΪDB����(by wxj) �������ļ��ж�ȡת��Ҫ�󣬽��ַ���ת�롣
     *
     * @param p_value
     * @return
     */
    public static String toDB(String p_value) {
        String encod1 = PropertyManager.getProperty(SystemAttributeNames.WEB_SERVER_ENCODING);
        String encod2 = PropertyManager.getProperty(SystemAttributeNames.DB_SERVER_ENCODING);
        return toDB(p_value, encod1, encod2);
    }

    /**
     * ��Ҫ��web����תΪDB����(by wxj) �������ļ��ж�ȡת��Ҫ�󣬽��ַ���ת�롣
     *
     * @param p_value
     * @return
     */
    public static String fromDB(String p_value) {
        String encod1 = PropertyManager.getProperty(SystemAttributeNames.DB_SERVER_ENCODING);
        String encod2 = PropertyManager.getProperty(SystemAttributeNames.WEB_SERVER_ENCODING);
        return fromDB(p_value, encod1, encod2);
    }

    /**
     * ����toSQLת������'ת��Ϊ''
     */
    public static final String toSql(String p_str) {
        if (p_str == null || p_str.length() < 1)
            return "";
        return p_str.replaceAll("'", "''");
    }

    /**
     * ����toHtmlת��
     */
    public static final String toHtml(String p_str) {
        if (p_str == null || p_str.length() == 0)
            return "";
        p_str = p_str.replaceAll("<", "&lt;");
        p_str = p_str.replaceAll(">", "&gt;");
        p_str = p_str.replaceAll("&", "&amp;");
        p_str = p_str.replaceAll("\"", "&quot;");
        // p_str=p_str.replaceAll("\r","<br>");
        return p_str;
    }

    public static String cutZero(String v) {
        if (v.indexOf(".") > -1) {
            while (true) {
                if (v.lastIndexOf("0") == (v.length() - 1)) {
                    v = v.substring(0, v.lastIndexOf("0"));
                } else {
                    break;
                }
            }
            if (v.lastIndexOf(".") == (v.length() - 1)) {
                v = v.substring(0, v.lastIndexOf("."));
            }
        }
        return v;
    }

    /**
     * ��doubleת��Ϊ�ַ�����ȥ����ѧ������ ���ٱ�����λ��ȥ��������
     */
    public static String doubleToStr(double d) {
        String rtn = "";
        java.text.DecimalFormat df = new java.text.DecimalFormat("###0.000000");
        rtn = df.format(d);
        return cutZero(rtn);
    }

    public static String doubleToStr1(double d) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("###,###,###,##0.00");
        return df.format(d);
    }

    public static String intToStr(int d) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("###########0");
        return df.format(d);
    }

    /**
     * ȡ����ĳһ�У�ĳһ�е����� �Լ�����ȡ����
     */
    public static String getCellValue(String p_table, String p_field, String p_where) {
        if (p_field == null || p_table == null || p_where == null)
            return null;
        ConnectionManager manager = ConnectionManager.getInstance();
        DatabaseConnection con = manager.getConnection();
        String sql = "select " + p_field;
        sql += " from " + p_table;
        sql += " where " + p_where;
        try {
            RecordSet rs = con.executeQuery(sql);
            if (rs.next()) {
                String tmp = rs.getString(0);
                if (tmp != null)
                    return tmp.trim();
            }
            rs.close();
            return null;
        } catch (Exception ex) {
            return null;
        } finally {
            manager.releaseConnection(con);
        }
    }

    /**
     * ȡ����ĳһ�У�ĳһ�е����� ʹ�ô�����������
     */
    public static String getCellValue(DatabaseConnection p_con, String p_table, String p_field, String p_where) {
        if (p_field == null || p_table == null || p_where == null)
            return null;
        String sql = "select " + p_field;
        sql += " from " + p_table;
        sql += " where " + p_where;
        try {
            RecordSet rs = p_con.executeQuery(sql);
            if (rs.next()) {
                String tmp = rs.getString(0);
                if (tmp != null)
                    return tmp.trim();
            }
            rs.close();
            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * ȡ�����ֶ�Ӧ��ƴ��
     *
     */
    public static String getSpell(DatabaseConnection con, String chsChar) {
        String strSpell = "";
        StringBuffer sbSpell = new StringBuffer();
        try {
            // �жϺ����Ƿ�Ϊ��
            if (!chsChar.equals("")) {
                RecordSet rs = null;
                char[] arrChar = chsChar.toCharArray();
                for (int i = 0; i < arrChar.length; i++) {
                    // ����Ƕ����֣���ȡ��һ������
                    rs = con.executeQuery(" select PY from ptpyk where HZ='" + arrChar[i]
                            + "' and rownum<=1 order by py");
                    while (rs.next()) {
                        sbSpell.append(rs.getString("PY"));
                    }
                }
                rs.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        strSpell = sbSpell.toString();
        return strSpell;
    }

    public static void main(String[] args) {
        ConnectionManager manager = ConnectionManager.getInstance();
        DatabaseConnection con = manager.getConnection();
        try {
            System.out.println(getSpell(con, "���˹�"));

        } catch (Exception e) {

        } finally {
            manager.releaseConnection(con);
        }
    }

    public static void main2(String[] args) {
        ConnectionManager manager = ConnectionManager.getInstance();
        DatabaseConnection con = manager.getConnection();
        try {
            RecordSet rs = con.executeQuery("select opername,operid from ptoper");
            while (rs.next()) {
                // System.out.println(getSpell(con, "���˹�"));
                String spell = getSpell(con, rs.getString("opername"));
                con.executeUpdate("update ptoper set operid='" + spell + ".qd' where operid='" + rs.getString("operid")
                        + "'");
                // System.out.println(spell);
            }
        } catch (Exception e) {

        } finally {
            manager.releaseConnection(con);
        }
    }
}
