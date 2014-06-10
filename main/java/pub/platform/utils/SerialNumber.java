package pub.platform.utils;

/**
 * ��ˮ�Ź�����
 *
 * û�п��ǲ�����������������������Լ�ͬ����־
 *
 * 1.�������ݿ��Sequenceʵ�֡�
 * 2.�������\�ر����ݿ⣨�������̷߳�ʽ������Ϊ�п��ܵ���ʹ�á�
 *
 * @author zhouwei
 * $Date: 2006/05/17 09:19:45 $
 * @version 1.0
 *
 * ��Ȩ��leonwoo
 */

import pub.platform.db.*;

public class SerialNumber {

    /**
     * ��õ�ǰ����ˮ��
     * ʧ��ʱ����null
     * @param serialName
     * @return
     */
    public static String getNextSerialNo(String serialName) {
        DatabaseConnection dc = null;
        String rtn = null;
        try {
            dc = ConnectionManager.getInstance().getConnection();
            dc.begin();
            RecordSet rs = dc.executeQuery("select " + serialName + ".nextval from dual");
            while (rs.next())
                rtn = rs.getString(0);
            dc.rollback();
        } catch (Exception e) {
            System.out.println("�õ����к�ʧ��:"+e);
        } finally {
            if (dc != null)
                dc.close();
        }
        return rtn;
    }

    /**
     * �����һ����ˮ��
     * ʧ��ʱ����null
     * @param serialName
     * @return
     */
    public static String getCurrSerialNo(String serialName) {
        DatabaseConnection dc = null;
        String rtn = null;
        try {
            dc = ConnectionManager.getInstance().getConnection();
            RecordSet rs = dc.executeQuery("select " + serialName + ".currval from dual");
            while(rs.next())
                rtn = rs.getString(0);
        } catch (Exception e) {
            System.out.println("�õ����к�ʧ��:"+e);
        } finally {
            if (dc != null)
                dc.close();
        }
        return rtn;
    }

    /**
     * ����һ�����к�������
     * @param serialName
     * @return
     */
    public static int createSerial(String serialName) {
        DatabaseConnection dc = null;
        int rtn = -1;
        try {
            dc = ConnectionManager.getInstance().getConnection();
            rtn = dc.executeUpdate(serialName);
        } catch (Exception e) {
            System.out.println("��������ʧ��:"+e);
        } finally {
            if (dc != null)
                dc.close();
        }
        return rtn;
    }

    /**
     * ɾ��һ�����к�������
     * @param serialName
     * @return
     */
    public static int removeSerial(String serialName) {
        DatabaseConnection dc = null;
        int rtn = -1;
        try {
            dc = ConnectionManager.getInstance().getConnection();
            rtn = dc.executeUpdate(serialName);
        } catch (Exception e) {
            System.out.println("ɾ������ʧ��:"+e);
        } finally {
            if (dc != null)
                dc.close();
        }
        return rtn;
    }

  public static void main(String[] args) {
      //System.out.println(SerialNumber.createSerial("create sequence aa minvalue 1 maxvalue 9999 start with 1 increment by 1 cache 15 cycle order"));
      //System.out.println(SerialNumber.getNextSerialNo("aa"));
      //System.out.println(SerialNumber.getCurrSerialNo("aa"));
      //System.out.println(SerialNumber.removeSerial("drop sequence aa"));
      System.out.println(getNextSerialNo("usercredentialseq"));
  }
}
