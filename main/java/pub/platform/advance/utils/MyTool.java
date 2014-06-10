package pub.platform.advance.utils;

import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;

import java.sql.*;
import java.util.*;
import java.text.SimpleDateFormat;

import pub.platform.db.*;

import pub.platform.utils.BusinessDate;

public class MyTool {

  /*
   * ����excel��ʱ���Ĭ�����ڸ�ʽת��Ϊyyyy-mm-dd hh:mm:ss
   */
  public static String jxlCellDate(Cell temp) {
    String crtdate = temp.getContents();

    if (crtdate.equalsIgnoreCase("NULL"))
      crtdate = "";

    if (temp.getType() == CellType.DATE) {
      SimpleDateFormat format = new SimpleDateFormat(
          "yyyy-MM-dd hh:mm:ss");
      DateCell datec = (DateCell) temp;
      crtdate = format.format(datec.getDate());

    }
    return crtdate;
  }

  /*
   * ����ҵ����Ҫ��Ψһ���
   */

  public static String getSeq(String seqName) {

    DatabaseConnection dc = null;
    String seq = "";

    dc = ConnectionManager.getInstance().getConnection();
    Connection conn = dc.getConnection();
    Statement st = null;
    ResultSet rs = null;
    StringBuffer sb = null;
    // ��ǰ��š����λ��
    int dqbh;
    int bhws = 4;
    String rq = "";
    String[] rq2 = null;
    try {
      st = conn.createStatement();
      sb = new StringBuffer();
      rs = st
          .executeQuery("select DQBH,BHWS,RQ from sys_seq where bhlx='"
              + seqName + "'");

      rs.next();
      dqbh = rs.getInt("DQBH");
      bhws = rs.getInt("BHWS");
      rq2 = rs.getString("RQ").toString().trim().split(" ");
      rq = rq2[0];
      // yyyy-mm-dd format
      if (BusinessDate.getToday().equals(rq)) {
        int len = String.valueOf(dqbh).length();
        if (len < bhws) {
          for (int i = 0; i < (bhws - len); i++) {
            sb.append("0");
          }
        }
        seq = BusinessDate.getNoFormatToday() + sb.toString()
            + String.valueOf(dqbh);
        dqbh += 1;
      } else {
        dqbh = 1;
        seq = BusinessDate.getNoFormatToday() + "0001";
        dqbh += 1;
      }

      dc.executeUpdate("update sys_seq set dqbh=" + dqbh
          + ",rq=to_date('" + BusinessDate.getToday()
          + "','yyyy-mm-dd') where bhlx='" + seqName + "'");

    } catch (SQLException ex) {
      // System.out.println(ex.getSQLState());
    } finally {
      ConnectionManager.getInstance().releaseConnection(dc);
    }
    return seq;
  }

  /*
   * ��ѡ���������id����text
   */

  public static String getText(String selType, String strValue) {

    DatabaseConnection dc = null;
    String retValue = "";

    dc = ConnectionManager.getInstance().getConnection();
    Connection conn = dc.getConnection();
    if (strValue.equals(""))
      return "";
    String[] listV = strValue.split(",");
    String enuType = "";
    try {
      if (selType.equals("rzmc")) {
        enuType = "RZMC";
      } else if (selType.equals("mylb")) {
        enuType = "MYLB";
      } else if (selType.equals("mygb") || selType.equals("sckt")) {
        enuType = "GBDQ";
      }
      for (int i = 0; i < listV.length; i++) {
        RecordSet rs = dc
            .executeQuery("select enuitemlabel from ptenudetail where enutype='"
                + enuType
                + "' and enuitemvalue='"
                + listV[i]
                + "'");
        while (rs.next()) {
          if (retValue.equals(""))
            retValue = rs.getString("enuitemlabel");
          else
            retValue += "," + rs.getString("enuitemlabel");
        }
      }
      conn.close();
    } catch (Exception ex) {
      // System.out.println(ex.getSQLState());
    } finally {
      ConnectionManager.getInstance().releaseConnection(dc);
    }
    return retValue;
  }
  
  //�жϵ����Ƿ�Ϊ���һ����ߵ��µ�һ�������
  //�����ϱ�����ʹ�ã�����Ǳ������һ��������ϱ����µ����ݣ�����
  //�¸��µ�ǰ5��Ҳ�����ϱ����µ����� 
  public static String judgeDay(){
    String msg="";
//    String nowDay=BusinessDate.getToday();
//    nowDay=nowDay.substring(5,7);
//    int iNowDay=Integer.parseInt(nowDay);
    //�ж��Ƿ��������һ��
    Calendar cal = Calendar.getInstance(); 
    int seqDay=cal.get(Calendar.DAY_OF_MONTH);
    //int year=cal.get(Calendar.YEAR);
    //int month=cal.get(Calendar.MONTH);
    int days=cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    //msg=String.valueOf(seqDay);
    if(seqDay==days)
      msg="endTrue";
    else {
      if(seqDay==1||seqDay==2||seqDay==3||seqDay==4||seqDay==5)
        msg="beginTrue";
      else
        msg="other";
    }
    
    return msg;
  }
  
  public static void main(String[] args){
    System.out.println(judgeDay());   
  }
}
