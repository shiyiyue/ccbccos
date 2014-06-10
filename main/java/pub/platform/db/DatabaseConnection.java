/**
 *  Copyright 2003 ZhongTian, Inc. All rights reserved. qingdao tec
 *  PROPRIETARY/CONFIDENTIAL. Use is subject to license terms. $Id: DatabaseConnection.java,v 1.2 2006/05/18 08:20:22 wiserd Exp $
 *  File:DatabaseConnection.java Date Author Changes March 5 2003 wangdeliang
 *  Created
 */

package pub.platform.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pub.platform.debug.BussDebug;
import pub.platform.debug.DebugInfo;
import pub.platform.utils.*;

/**
 * ����������ݿ�
 * 
 */

public class DatabaseConnection {
  private static final Log logger = LogFactory.getLog(DatabaseConnection.class);

  // ��ó���ʱ������
  protected Exception errorException;

  /**
   * ����ԴĬ�ϵ�����
   */
  public final static String DEFAULT_ENV_CONTEXT_URL = "java:comp/env";

  /**
   * Description of the Field
   */
  public final static String DEFAULT_DATASOURCE_URL = "ccb_pool";

  private Connection connect;

  /**
   * �����Ƿ��Զ��ύ(true-�Զ��ύ,false-�ֹ��ύ)
   * 
   */
  private boolean isAuto = true;

  /**
   * ����Ĳ��,��ʼΪ0
   */
  private int layers = 0;

  /**
   * �������ӣ�ʧ�����׳�����NoAvailableResourceException
   * 
   * @exception NoAvailableResourceException
   *              Description of the Exception
   * @roseuid 3E5B223E0372
   */
  public DatabaseConnection() throws NoAvailableResourceException {
    try {
      Hashtable prop = new Hashtable();
      // prop.put(Context.PROVIDER_URL,"t3://172.1.1.16:7001");
      Context initCtx = new InitialContext(prop);
      // Context envCtx = (Context) initCtx.lookup(DEFAULT_ENV_CONTEXT_URL);
      DataSource ds = (DataSource) initCtx.lookup(DEFAULT_DATASOURCE_URL);

      connect = ds.getConnection();
      // ��¼��������
      this.register();

    } catch (Exception e) {
      errorException = e;
      throw new NoAvailableResourceException(e.getMessage());
    }
  }

  /**
   * Constructor for the DatabaseConnection object
   * 
   * @param sDBDriver
   *          Description of the Parameter
   * @param sConnStr
   *          Description of the Parameter
   * @param user
   *          Description of the Parameter
   * @param passwd
   *          Description of the Parameter
   * @exception NoAvailableResourceException
   *              Description of the Exception
   */

  public DatabaseConnection(String sDBDriver, String sConnStr, String user, String passwd)
      throws NoAvailableResourceException {
    try {
      Class.forName(sDBDriver).newInstance();

    } catch (Exception ex) {
      errorException = ex;
      ex.printStackTrace();
    }

    try {
      connect = DriverManager.getConnection(sConnStr, user, passwd);
      // ��¼��������
      this.register();

    } catch (SQLException ex) {
      errorException = ex;
      ex.printStackTrace();
    }
  }

  /**
   * ��ʼ����
   */
  public void begin() {
    /*
     * isAuto:�Զ��ύΪtrue,����Ϊfalse.�����ø÷���ʱ�� isAuto������Ϊfalse��layers��1 @roseuid
     * 3E5B22540070
     */
    isAuto = false;
    try {
      if (connect.getAutoCommit())
        connect.setAutoCommit(false);
    } catch (SQLException sqle) {
    }
    layers++;
  }

  /**
   * �ύ����
   */
  public void commit() {
    /*
     * layers--,���layers����0,���ύ���� @roseuid 3E5B22D7024F
     */
    if (!isAuto) {
      layers--;
      if (layers == 0) {
        try {
          connect.commit();
          connect.setAutoCommit(true);
          isAuto = true;
        } catch (SQLException sqle) {
        }
      } // if
    }
  }

  /**
   * �ع�����
   */
  public void rollback() {
    /*
     * ִ������ع���������,layers = 0 @roseuid 3E5B22E40258
     */
    if (!isAuto) {
      layers = 0;
      try {
        connect.rollback();
        isAuto = true;
      } catch (SQLException sqle) {
      }
    } // if
  }

  /**
   * /** ִ�����ݿ�������
   * 
   * @param sql
   *          sql���
   * @return int �����Ƿ�ɹ�
   * @roseuid 3E5B22F70179
   */
  public int executeUpdate(String sql) {
    if (sql == null || sql.trim().length() == 0) {
      logger.error("DatabaseConnection.executeUpdate's sql parameter is null!!!");
      return -1000;
    }

    // ��ӡsql
    printsql(sql);

    try {
      Statement st = connect.createStatement();
      int result = st.executeUpdate(sql);
      st.close();
      return result;
    } catch (SQLException sqle) {
      errorException = sqle;

      logger.error("���´���", sqle);
      if (sqle.getErrorCode() < 0) {
        return sqle.getErrorCode();
      } else {
        return sqle.getErrorCode() * (-1);
      }
    }
  }

  /**
   * ִ��sql��ѯ���
   * 
   * @param sql
   * @return com.zt.db.RecordSet
   * @roseuid 3E5B230E0281
   */
  public RecordSet executeQuery(String sql) {
    if (sql == null || sql.trim().length() == 0) {
      logger.error("DatabaseConnection.executeQuery's sql parameter is null!!!");
      return null;
    }

    // ////////��ӡsql
    printsql(sql);

    try {
      Statement st = connect.createStatement();

      ResultSet rs = st.executeQuery(sql);
      RecordSet records = new RecordSet(rs);

      rs.close();
      st.close();
      return records;
    } catch (SQLException sqle) {
      errorException = sqle;
      logger.error("��ѯ����", sqle);
      return null;
    }
  }

  public ResultSet executeResultSetQuery(String sql) {
    if (sql == null || sql.trim().length() == 0) {
      logger.error("DatabaseConnection.executeQuery's sql parameter is null!!!");
      return null;
    }

    // ////////��ӡsql
    printsql(sql);

    try {
      Statement st = connect.createStatement();
      ResultSet rs = st.executeQuery(sql);
      return rs;
    } catch (SQLException sqle) {
      errorException = sqle;
      logger.error("��ѯ����", sqle);
      return null;
    }
  }

  /**
   * Description of the Method
   * 
   * @param sql
   *          Description of the Parameter
   * @param beginIndex
   *          Description of the Parameter
   * @param resultNo
   *          Description of the Parameter
   * @return Description of the Return Value
   */
  public RecordSet executeQuery(String sql, int beginIndex, int resultNo) {

    if (sql == null || sql.trim().length() == 0) {
      logger.error("DatabaseConnection.executeQuery's sql parameter is null!!!");
      return new RecordSet();
    }

    // ////////��ӡsql
    printsql(sql);

    try {
      String pageSql = "";

      Statement st = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      if (Basic.getDbType().equals("DB2")) {
        pageSql = " select * from (SELECT ta.*, ROWNUMBER() OVER ()  rn FROM ( " + sql + ") ta FETCH FIRST "
            + (beginIndex - 1 + resultNo) + " ROW ONLY) tb where tb.rn>" + (beginIndex - 1);

      } else
        pageSql = " select * from ( select t1.*, rownum rnum from ( " + sql + " ) t1 where rownum<= "
            + (beginIndex - 1 + resultNo) + " ) t2 where t2.rnum> " + (beginIndex - 1);

      // //////// ��ӡsql
      printsql("page sql:" + pageSql);
      ResultSet rs = st.executeQuery(pageSql);
      RecordSet records = new RecordSet(rs);

      rs.close();
      st.close();
      return records;
    } catch (SQLException sqle) {
      errorException = sqle;
      logger.error("��ѯ����", sqle);
      return new RecordSet();
    }
  }

  /**
   * �жϼ�¼�Ƿ����
   * 
   * @param sql
   * @return
   */
  public boolean isExist(String sql) {
    try {
      Statement st = connect.createStatement();
      ResultSet rs = st.executeQuery(sql);
      boolean exist = rs.next();
      st.close();
      return exist;
    } catch (SQLException sqle) {
      errorException = sqle;
      logger.error("��ѯ����", sqle);
      return false;
    }
  }

  /**
   * �ж������Ƿ��Զ��ύ
   * 
   * @return boolean
   * @roseuid 3E5B244903E2
   */
  public boolean isAuto() {
    return isAuto;
  }

  /**
   * ����������ύ��ʽ
   * 
   * @param isAuto
   *          boolean����
   * @roseuid 3E5EA680009C
   */
  public void setAuto(boolean isAuto) {
    try {
      connect.setAutoCommit(isAuto);
    } catch (SQLException sqle) {
      errorException = sqle;
    }
    this.isAuto = isAuto;
  }

  /**
   * �ر����ݿ�����,ʹ����֮��һ�����ø÷����ͷ����ݿ�����,����������Դ���˷�
   * 
   * @roseuid 3E5B3532018E
   */
  public void close() {
    try {
      connect.close();
      // ��¼�ر�����
      this.remove();

    } catch (SQLException ex) {
      errorException = ex;
      ex.printStackTrace();
    }
  }

  /**
   * Gets the preparedStatement attribute of the DatabaseConnection object
   * 
   * @param str
   *          Description of the Parameter
   * @return The preparedStatement value
   */
  public PreparedStatement getPreparedStatement(String str) {
    try {

      PreparedStatement st = connect.prepareStatement(str, ResultSet.TYPE_SCROLL_INSENSITIVE,
          ResultSet.CONCUR_READ_ONLY);
      return st;
    } catch (SQLException ex) {
      errorException = ex;
      return null;
    }
  }

  /**
   * Description of the Method
   * 
   * @param pst
   *          Description of the Parameter
   * @return Description of the Return Value
   */
  public RecordSet executeQuery(PreparedStatement pst) {
    if (pst == null) {
      return new RecordSet();
    }
    try {
      ResultSet rs = pst.executeQuery();
      ConnectionHealth.end(this);
      RecordSet records = new RecordSet(rs);

      pst.close();
      return records;
    } catch (SQLException sqle) {
      errorException = sqle;
      logger.error("��ѯ����", sqle);
      return new RecordSet();
    }
  }

  /**
   * Description of the Method
   * 
   * @param pst
   *          Description of the Parameter
   * @param beginIndex
   *          Description of the Parameter
   * @param resultNo
   *          Description of the Parameter
   * @return Description of the Return Value
   */
  public RecordSet executeQuery(PreparedStatement pst, int beginIndex, int resultNo) {
    if (pst == null) {
      System.out.println("DatabaseConnection.executeQuery's pst parameter is null!!!");
      return new RecordSet();
    }

    try {

      pst.setMaxRows(beginIndex - 1 + resultNo);

      ResultSet rs = pst.executeQuery();
      if (beginIndex != 1) {
        rs.absolute(beginIndex - 1);
      }

      RecordSet records = new RecordSet(rs, resultNo);

      rs.close();
      pst.close();
      return records;
    } catch (SQLException sqle) {
      errorException = sqle;
      logger.error("��ѯ����", sqle);
      return new RecordSet();
    }
  }

  /**
   * Gets the connection attribute of the DatabaseConnection object
   * 
   * @return The connection value
   */
  public Connection getConnection() {
    return connect;
  }

  public String getErrorMsg() {
    return "";
  }

  public void setErrorMsg(String errorMsg) {
    if (errorMsg != null) {
      if (errorMsg.indexOf("SQL") > 0) {
        errorMsg = errorMsg.substring(errorMsg.indexOf("SQL"), errorMsg.length());
        errorMsg = DBUtil.fromDB(errorMsg);
      }
    }
  }

  public Exception getErrorException() {
    return errorException;
  }

  private void printsql(String sql) {
    logger.debug(sql);
  }

  // �Ƿ���������
  private boolean debug = false;

  private void register() {
    if (debug)
      ConnectionManager.badconn.put(this.hashCode() + "", new DebugInfo(BussDebug.inferCaller()));
  }

  private void remove() {
    if (debug)
      ConnectionManager.badconn.remove(this.hashCode() + "");
  }
}
