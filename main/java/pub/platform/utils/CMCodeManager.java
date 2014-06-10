package pub.platform.utils ;

/**
 * ��Ӧ��CMCodeManager����
 * ͨ��key-value�ķ�ʽ����������Ϣ
 *
 * @author Mr. ZhouWei
 * $Date: 2006/05/17 09:19:43 $
 * @version 1.0
 */

import java.util.Date ;
import java.util.HashMap ;
import java.util.GregorianCalendar ;

import java.util.StringTokenizer;
import java.util.Calendar;

import pub.platform.db.*;

public class CMCodeManager {
     /**
      * ������еĹ���������Ϣ
      �� key - KEY_codtp_langtp_codsq
      */
     private static HashMap codes ;

     static {
          init();
     }
     public static void init() {
          //1. ��ʼ��codes
          codes = new HashMap() ;
          //2. ��ȡ���ݿ⣬�����ڴ�
          RecordSet rs = loadFromDb() ;
          parseToCodes(rs) ;
          rs.close() ;

     }
     public static boolean updateValue(String codetp,String langtp,int codesq,String value) {
          DatabaseConnection dc = ConnectionManager.getInstance().getConnection() ;
          try {
               //�õ����ò������е�����
               String key = "KEY"+"_"+codetp+"_"+langtp+"_"+codesq;
               String sSqlWhere = "where 1=1 ";
               if ( codetp != null ) {
                    sSqlWhere += " and codtp='"+codetp+"'";
               }
               if ( langtp != null ) {
                    sSqlWhere += " and langtp='"+langtp+"'";
               }
               sSqlWhere += " and codsq="+codesq+"";
               String sSql = "update cmcode set valstr='"+value+"' " + sSqlWhere;
               if ( dc.executeUpdate(sSql) > 0 ) {
                    if ( codes.containsKey(key) ) {
                         codes.put(key,value) ;
                         return true;
                    }
               }
               return false;
          } catch (Exception e) {
               return false;
          } finally {
               //Close Database connection.
               ConnectionManager.getInstance().releaseConnection(dc) ;
          }
     }
     /**
      * ����keyֵ��������---Long����
      * @param key
      * @return long
      * @roseuid 3F0A28590040
      */
     public static long getLong(String key) throws Exception {
          return Long.parseLong( (String)codes.get(key)) ;
     }

     /**
      * @param key
      * @return long
      * @roseuid 3F0A28A10329
      */
     public static String getString(String key) {
          return (String)codes.get(key) ;
     }

     /**
      * @param key
      * @return double
      * @roseuid 3F0A28B103AE
      */
     public static double getDouble(String key)  throws Exception {
          return Double.parseDouble( (String)codes.get(key)) ;
     }

     /**
      * @param key
      * @return float
      * @roseuid 3F0A28BD0383
      */
     public static float getFloat(String key)  throws Exception {
          return Float.parseFloat( (String)codes.get(key)) ;
     }

     /**
      * @param key
      * @return int
      * @roseuid 3F0A28D00222
      */
     public static int getInt(String key) throws Exception {
          return Integer.parseInt( (String)codes.get(key)) ;
     }

     /**
      * @param key
      * @return short
      * @roseuid 3F0A28DB0105
      */
     public static short getShort(String key) throws Exception {
          return Short.parseShort( (String)codes.get(key)) ;
     }

     /**
      * @param key
      * @return byte
      * @roseuid 3F0A28EF023B
      */
     public static byte getByte(String key) throws Exception {
          return Byte.parseByte( (String)codes.get(key)) ;
     }

     /**
      * @param key
      * @return char
      * @roseuid 3F0A291E0365
      */
     public static char getChar(String key)  throws Exception  {
          return (char)getByte(key) ;
     }

     /**
      * @param key
      * @return java.util.Date
      * @roseuid 3F0A298A0310
      */
     public static Date getDate(String key) throws Exception {
          return toDate( (String)codes.get(key)) ;
     }

     public static Calendar getCalendar(String key) throws Exception {
          return toCalendar( (String)codes.get(key)) ;
     }


     /**
      * �Թ��ò������ж�ȡ���ݣ����ؽ�����ϡ�
      * @return RecordSet
      */
     private static RecordSet loadFromDb() {
          RecordSet result = null;
          try {
               //�õ����ò������е�����
               DatabaseConnection dc = ConnectionManager.getInstance().get() ;
               //Compose Sql String.
               StringBuffer strBuf = new StringBuffer() ;

               strBuf.append("select * from cmcode") ;

               //System.out.println(strBuf.toString());

               //Execute sqlStr.
               result = dc.executeQuery(strBuf.toString()) ;

          } catch (Exception e) {
               result = null;
          } finally {
               //Close Database connection.
               ConnectionManager.getInstance().release() ;
          }
          return result ;
     }

     /**
      * ����������е����ݵ��ڴ���
      * @param rs RecordSet
      * @return int Result KEY_dt_1_1
      */
     private static int parseToCodes(RecordSet rs) {
          try {
               while (rs.next()) {
                    StringBuffer key = new StringBuffer("KEY") ;
                    key.append("_" + rs.getString("codtp")).append("_" + rs.getString("langtp")).append("_" + rs.getString("codsq")) ;

                    codes.put(key.toString(),rs.getString("valstr")) ;
               }
               return 0 ;
          } catch (Exception e) {
               return -1 ;
          }
     }

     /**
      * ���ݴ����ʱ���ַ����õ�ʱ�����
      *
      * �ַ�����ʽ�� YYYYMMDD
      * @param strin
      * @return
      */
     private static Date toDate(String strin) {
          if (!strin.trim().equals("")) {
               int yy = Integer.parseInt(strin.substring(0,4)) ;
               //System.out.println(yy);
               int mm = Integer.parseInt(strin.substring(4,6)) - 1 ;
               //System.out.println(mm);
               int dd = Integer.parseInt(strin.substring(6,8)) ;
               //System.out.println(dd);
               GregorianCalendar gc = new GregorianCalendar() ;
               gc.set(yy,mm,dd,0,0,0) ;
               return gc.getTime() ;
          } else
               return null ;
     }
     private static Calendar toCalendar(String strin) {
          if (!strin.trim().equals("")) {
               int yy = Integer.parseInt(strin.substring(0,4)) ;
               //System.out.println(yy);
               int mm = Integer.parseInt(strin.substring(4,6)) - 1 ;
               //System.out.println(mm);
               int dd = Integer.parseInt(strin.substring(6,8)) ;
               //System.out.println(dd);
               GregorianCalendar gc = new GregorianCalendar() ;
               gc.set(yy,mm,dd,0,0,0) ;
               return gc ;
          } else
               return null ;
     }


     public static void main(String[] args) {
        System.out.println(CMCodeManager.getString("KEY_D1_1_1"));
     }
}
