//Source file: e:\\java\\zt\\platform\\form\\config\\EnumerationType.java

package pub.platform.form.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pub.platform.db.ConnectionManager;
import pub.platform.db.DBUtil;
import pub.platform.db.DatabaseConnection;
import pub.platform.db.RecordSet;

/**
 *  ö������ �����������ö��ʵ������static�����дӱ�sysenuinfomain��sysenuinfodetl�м����� �е�ö������ʵ�����塣
 *
 *@author     ���滻
 *@created    2003��10��11��
 *@version    1.0
 */
public class EnumerationType {
    private static Map types = Collections.synchronizedMap(new HashMap());

    private static final Log logger = LogFactory.getLog("zt.platform.form.config.EnumerationType");

    static {
        init(true);
    }


    /**
     *  ��ֵ֤value�Ƿ���ö������type��ȡֵ��Χ��
     *
     *@param  type
     *@param  value
     *@return        boolean
     *@roseuid       3F71657501F8
     */
    public static boolean validate(String type, String value) {
        EnumerationBean eb = getEnu(type);
        if (eb == null) {
            return false;
        } else {
            Object o = eb.getValue(value);
            if (o == null) {
                return false;
            } else {
                return true;
            }
        }
    }

    public static String getEnu(String type,String value) {
      EnumerationBean eb = getEnu(type);
      if (eb == null)
        return value;
      return (String)eb.getValue(value);
    }

    /**
     * ��ȡö������ linyw add
     *
     * @param type String ö������
     * @param value String ö��ֵ
     * @return String
     */
    public static String getEnumName(String type, String value){
         EnumerationBean enumerationBean = (EnumerationBean) types.get(type.toUpperCase());
         if(enumerationBean == null){
              return null;
         }else{
              String values = (String)enumerationBean.getValue(value);
              if(values == null){
                   return null;
              }else{
                   return values.split("\\;")[0];
              }
         }
    }

    /**
     * ��ȡö������ yuanwzh add
     *
     * @param type String ö������
     * @param value String ö��ֵ
     * @return String
     */
    public static String getEnumExtend(String type, String value){
         EnumerationBean enumerationBean = (EnumerationBean) types.get(type.toUpperCase());
         if(enumerationBean == null){
              return null;
         }else{
              String values = (String)enumerationBean.getValue(value);
              if(values == null){
                   return null;
              }else{
                   return values.split("\\;")[1];
              }
         }
    }

    /**
     *  �õ�ö������type��ʵ�� ���Է���ÿ��ö��ֵ
     *
     *@param  type
     *@return       zt.platform.form.config.EnumerationBean
     *@roseuid      3F7165A503D7
     */
    public static EnumerationBean getEnu(String type) {
        return (EnumerationBean) types.get(type.toUpperCase());
   }


   /**
    * ����װ��ö�����ݣ��޸�ö��ʱ����
    */
   public static void reload() {
        types.clear();
        init(false);
   }

    /**
     *  Description of the Method
     */
    public static void init(boolean autoClose) {

         String enuitemName="";

         DatabaseConnection con = null;
         try {
              if(autoClose) //�Զ��ر�����ʱʹ�÷��߳����ݿ�
                   con = ConnectionManager.getInstance().getConnection();
              else //���Զ��ر�����ʱʹ���߳����ݿ�
                   con = ConnectionManager.getInstance().get();
              String str = "select * from ptenumain";
              RecordSet rs = con.executeQuery(str);
              while(rs.next()) {
                   String enuid = DBUtil.fromDB(rs.getString("EnuType"));
                   String enuDesc = rs.getString("EnuName");

                   enuid = DBUtil.fromDB(enuid.trim());
                   if(enuDesc == null) {
                        enuDesc = "";
                   } else {
                        enuDesc = DBUtil.fromDB(enuDesc.trim());
                   }
                   EnumerationBean eb = new EnumerationBean(enuid);
                   eb.setEnudesc(enuDesc);
                   types.put(enuid.toUpperCase(), eb);
                   
                   //logger.debug(enuid+"-->"+enuDesc);
              }

              String enuBeanStr = "select * from ptenudetail where enutype in ( "
                                 +"select enutype from ptenumain "
                                 +") order by enutype,DISPNO ";
              RecordSet enuRs = con.executeQuery(enuBeanStr);
              while (enuRs.next()) {
                  EnumerationBean eb = (EnumerationBean) types.get(enuRs.getString("enutype").toUpperCase());
                  enuitemName = "";
                  if (enuRs.getString("ENUITEMEXPAND") != null)
                      enuitemName = enuRs.getString("EnuItemLabel").trim() + ";" + enuRs.getString("ENUITEMEXPAND").trim();
                  else
                      enuitemName = enuRs.getString("EnuItemLabel").trim() + "; ";

                  eb.add(enuRs.getString("EnuItemValue"), DBUtil.fromDB(enuitemName));
                  //logger.debug(enuRs.getString("enutype")+":"+enuRs.getString("EnuItemValue")+"-->"+DBUtil.fromDB(enuitemName));
              }
              logger.error("��ʼ��ö��ֵ�ɹ�!");
         } catch(Exception e) {
        	 logger.error("��ʼ��ö��ֵʧ�ܣ�",e);
         } finally {
              if(con != null && autoClose) //ֻ�����Զ��ر����ӵ����
                   con.close();
         }

    }

    public static void main(String[] args) {
        System.out.println(EnumerationType.getEnu("GG_BUSS_TYPE","XKZ_KY"));
      }
    
}
