package pub.platform.advance.utils;

/**
 * <p>Title: ��̨ҵ�����</p>
 *
 * <p>Description: ��̨ҵ�����</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: ������Ϣ�����������ι�˾</p>
 *
 * @author leonwoo
 * @version 1.0
 */



import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import pub.platform.db.ConnectionManager;
import pub.platform.db.DatabaseConnection;
import pub.platform.form.control.*;

public abstract class BaseJob  {
  public void action(String param) throws Exception {
    boolean isOK = true;
    DatabaseConnection dc = null;

    try {
    	//�ڻ������ȡ����
      dc = ConnectionManager.getInstance().getConnection();
      dc.begin();
      Connection con = dc.getConnection();
      doing(con,param,dc);

    }
    catch (Exception e) {
      isOK = false;
      throw e;

    }
    finally {
      if (dc != null) {
        if (isOK) {
          dc.commit();
        }
        else {
          dc.rollback();
        }
        ConnectionManager.getInstance().releaseConnection(dc);
      }
    }
  }

  protected abstract void doing(Connection con, String param,DatabaseConnection dc) throws Exception;
}
