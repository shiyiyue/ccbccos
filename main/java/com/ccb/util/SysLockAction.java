package com.ccb.util;

import pub.platform.advance.utils.PropertyManager;
import pub.platform.db.RecordSet;
import pub.platform.form.control.Action;

/**
 * <p>
 * Title: ��̨ҵ�����
 * </p>
 * 
 * <p>
 * Description: ��̨ҵ�����
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2010
 * </p>
 * 
 * <p>
 * Company: ��˾
 * </p>
 * 
 * @author leonwoo
 * @version 1.0
 */
public class SysLockAction extends Action {

  /***
   * ��ѯϵͳ������
   * 
   * @return
   */
  public int getSysLockStatus() {
    String sysLock = "0";
    try {
      RecordSet rs = dc.executeQuery("select syslockstatus from sys_lock ");
      while (rs.next()) {
        sysLock = rs.getString("syslockstatus");
        break;
      }
    } catch (Exception e) {
      this.res.setType(0);
      this.res.setResult(false);
      this.res.setMessage(PropertyManager.getProperty("302"));
      return -1;
    }
    this.res.setFieldName("sysLockStatus");
    this.res.setFieldType("text");
    this.res.setEnumType("0");
    this.res.setFieldValue(sysLock);
    this.res.setType(4);
    this.res.setResult(true);
    return 0;
  }
}
