package pub.platform.advance.utils;

import pub.platform.db.RecordSet;
import pub.platform.utils.*;

/**
 * Javaʵ����ͳһ������
 * 
 * �����淶��������ȥ���»��ߣ�ȫ����д��+Bean
 * 
 * @author wu $Date: 2006/04/18 06:51:20 $
 * @version 1.0
 * 
 *          ��Ȩ���ൺ��˾
 */

public class DAOGenerator {
  // Ĭ�ϵİ���
//  private static final String schme = "CCB";
  private static final String schme = "GWK";
  public static final String PACKAGE = "com.ccb.dao";
  static JavaBeanGenerator jbg = new JavaBeanGenerator();

  /**
   * ����ָ���������������ļ�
   * 
   * @param tableName
   *          String
   */
  public static void generateTable(String tableName) {
    if (hasTable(tableName))
      jbg.generate(PACKAGE, getBeanName(tableName), tableName);
    else
      System.out.println("����ı�����" + tableName + "�������ݿ��в����ڣ�����ϸ��飡����");
  }

  /**
   * ����CIMS�������еı�������ļ�
   */
  public static void generateAllTables() {
    RecordSet rs = DbUtil.getRecord("select * from sys.all_tables where owner='" + schme + "'");

    while (rs.next()) {
      generateTable(rs.getString("table_name"));
    }
  }

  /**
   * �ж��Ƿ��б���
   * 
   * @param tableName
   *          String
   * @return boolean
   */
  public static boolean hasTable(String tableName) {
    RecordSet rs = DbUtil.getRecord("select * from sys.all_tables where owner='" + schme + "' and table_name='"
        + tableName.toUpperCase() + "'");

    while (rs.next()) {
      return true;
    }

    return false;
  }

  /**
   * j ���ݱ������������ļ��� ȥ���»��ߣ�ȫ����д
   * 
   * @param tableName
   *          String
   * @return String
   */
  private static String getBeanName(String tableName) {
    tableName = tableName.replaceAll("_", "");
    tableName = tableName.toUpperCase();

    return tableName;

  }

  public static void main(String[] argv) {
    // ���ɵ�������ļ�
//    generateAllTables();
//     generateTable("ls_taskinfo");
//     generateTable("LS_PERSONALINFO");
//     generateTable("LS_BDGAGENCY_H");
//     generateTable("LS_CARDBASEINFO");
//     generateTable("LS_CONSUMEINFO");
      generateTable("LS_CARDSTATUS");
  }

}
