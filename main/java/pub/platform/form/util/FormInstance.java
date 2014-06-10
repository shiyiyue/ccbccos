//Source file: e:\\java\\zt\\platform\\form\\util\\FormInstance.java

package pub.platform.form.util;

import java.util.*;
import java.util.logging.*;

import java.io.Serializable;

import pub.platform.db.*;
import pub.platform.form.config.*;
import pub.platform.form.control.*;
import pub.platform.form.util.datatype.*;
import pub.platform.form.util.event.*;

/**
 * FORMʵ����
 *
 * @author qingdao tec
 * @version 1.0
 */
public class FormInstance implements Serializable{

  public static Logger logger = Logger.getLogger(
      "zt.platform.form.util.FormInstance");

  /**
   * ʵ��ID
   */
  private String instanceid;

  /**
   * FORM��ID
   */
  private String formid;

  /**
   * FormActions��ʵ�����ɿ�����Ա��չ��������
   */
  private FormActions formAction;

  /**
   * ʵ����״̬���ݲ�ά��
   */
  private int state;

  private boolean readonly;

  /**
   * FORMÿ��Ԫ�ض�Ӧ��ֵ���� �ֶ���-ֵ��FormElementValue��
   */
  private Map values = new HashMap();

  /**
   * FORM�����е�pk��sk
   */
  private ArrayList pkflds = new ArrayList();
  private ArrayList skflds = new ArrayList();

  /**
   * ���캯��
   *
   * 1.ʵ����FormActions
   * 2.����values������������Ԫ�ض�Ӧ��FormElementValue
   *
   * @param formid
   */
  public FormInstance(String p_formid, String p_instanceid) {
    this.formid = p_formid;
    this.instanceid = p_instanceid;
    FormBean formBean = FormBeanManager.getForm(formid);
    //ʵ����FormActions
    setFormAction(formBean.getProccls());
    //FormElementValue
    this.readonly = formBean.isReadonly();
    String[] elementKeys = formBean.getElementKeys();
    for (int i = 0; i < elementKeys.length; i++) {
      ElementBean elementBean = formBean.getElement(elementKeys[i]);
      //ʵ����values
      FormElementValue fev = new FormElementValue(elementBean.getDataType());
      fev.setReadonly(elementBean.isReadonly());
      fev.setIsNull(elementBean.isIsnull());
      fev.setVisible(elementBean.isVisible());
      fev.setDisabled(elementBean.isDisabled());
      values.put(elementKeys[i], fev);
      //�ҳ�pk��sk�ֶ�
      if (elementBean.isIsPrimaryKey()) {
        pkflds.add(elementKeys[i]);
      }
      if (elementBean.isIsSearchKey()) {
        skflds.add(elementKeys[i]);
      }
    }
  }

  /**
   * ��ʼ��values(���FormElementValue�ĵ�ǰֵ������Ĭ��ֵ)
   *
   * @roseuid 3F728CB501B0
   */
  public void initValues() {
    //���values;
    values.clear();
    //ȡ��Form�����е�Ԫ������
    FormBean formBean = FormBeanManager.getForm(formid);
    Object[] fldNames = formBean.getElementKeys();
    for (int i = 0; i < fldNames.length; i++) {
      //����Ԫ������
      String fldName = (String) fldNames[i];
      //Ԫ����������
      ElementBean eb = (ElementBean) formBean.getElement(fldName);
      int dataType = eb.getDataType();
      //�յ�FormElementValue
      FormElementValue fev = new FormElementValue(dataType);
      values.put(fldName, fev);
      fev.setReadonly(eb.isReadonly());
      fev.setIsNull(eb.isIsnull());
      fev.setVisible(eb.isVisible());
      fev.setDisabled(eb.isDisabled());
      //Ĭ��ֵ
      ElementBean elementBean = formBean.getElement(fldName);
      String defValue = elementBean.getDefaultValue();
      //�����γ�List
      ArrayList tmpVal = (ArrayList) convertValue(defValue, dataType);
      if (tmpVal == null) {
        continue;
      }
      //System.out.println(tmpVal.get(0));
      //List�ŵ�values�е�FormElementValue��
      fev.setValue(tmpVal);
    }
  }

  /**
   * ����dataType��valueת��Ϊlist�в�ͬ���͵Ķ���
   * 1. String ��List����java.lang.String
   * 2. integer��List����java.lang.Integer
   * 3. decimal��List����java.lang.Double
   * 4. boolean��List����java.lang.String (0-false 1-true)
   * 5. Date   ��List����java.lang.String (��ʽMM/DD/YYYY)
   * 6. Enumeration��List����java.lang.Integer
   */
  private List convertValue(String[] p_values, int p_type) {
    if (p_values == null) {
      return null;
    }
    ArrayList al = new ArrayList();
    for (int i = 0; i < p_values.length; i++) {
      al.add(convertValueSingle(p_values[i], p_type));
    }
    return al;
  }

  private List convertValue(String p_value, int p_type) {
    if (p_value == null) {
      return null;
    }
    ArrayList al = new ArrayList();
    al.add(convertValueSingle(p_value, p_type));
    return al;
  }

  public static Object convertValueSingle(String p_value, int p_type) {
    if (p_value == null) {
      return null;
    }
    //�������ʹ������Ϊ�գ��������Сֵ
    if (p_type == DataType.INTEGER_TYPE || p_type == DataType.ENUMERATION_TYPE) {
      try {
        return new Integer(p_value);
      }
      catch (Exception ex) {
        return new Integer(Integer.MAX_VALUE);
      }
    }
    //ʵ�����ʹ������Ϊ�գ��������Сֵ
    else if (p_type == DataType.DECIAML_TYPE) {
      try {
        return new Double(p_value);
      }
      catch (Exception ex) {
        return new Double(Double.MAX_VALUE);
      }
    }
    //����Ϊ�յ����
    //else if (p_type == DataType.DATE_TYPE && p_value.trim().equals("")) {
      //return null;
    //}
    //�������
    else {
      return p_value;
    }
  }

  /**
   * ����Client���ݵĲ���������ʵ����ֵ
   */
  public void updateValue(SessionContext ctx) {
    //ȡ��Form�����е�Ԫ������
    Object[] fldNames = values.keySet().toArray();
    for (int i = 0; i < fldNames.length; i++) {
      //Ԫ������
      String fldName = (String) fldNames[i];
      //Ԫ����������
      int dataType = ( (FormElementValue) values.get(fldName)).getType();
      //��ctxȡ������
      String[] fldValue = ctx.getParameters(fldName);
      //�����γ�List
      ArrayList tmpVal = (ArrayList) convertValue(fldValue, dataType);
      if (tmpVal == null) {
        continue;
      }
      //List�ŵ�values�е�FormElementValue��
      ( (FormElementValue) values.get(fldName)).setValue(tmpVal);
    }
  }

  /**
   * ����rs����values
   *
   * @param rs
   */
  public void updateValue(RecordSet rs) {
    //initValues
    initValues();
    //ȡ��Form�����е�Ԫ������
    Object[] fldNames = values.keySet().toArray();
    FormBean formBean = FormBeanManager.getForm(formid);
    for (int i = 0; i < fldNames.length; i++) {
      //Ԫ������
      String fldName = (String) fldNames[i];
      ElementBean elementBean = formBean.getElement(fldName);
      //������ڴ��������������
      if (elementBean.getType() == elementBean.MEMORY_FIELD) {
        continue;
      }
      //�������
      int compType=elementBean.getComponetTp();
      //Ԫ����������
      int dataType = ( (FormElementValue) values.get(fldName)).getType();
      //��rsȡ�����ݣ���ת�룬rs�ڴ�����֮ǰ���Ѿ���λ����
      String fldValue;
      try {
        //�����double����,ȥ����ѧ������
        if (dataType == DataType.DECIAML_TYPE) {
          fldValue = DBUtil.doubleToStr(rs.getDouble(fldName));
        }
        //�����date����,ȥ��"-"(20031127)
        else if (dataType == DataType.DATE_TYPE) {
          fldValue = rs.getString(fldName);
          if(fldValue!=null){
            fldValue=fldValue.replaceAll("-","");
          }
        }
        else {
          fldValue = rs.getString(fldName);
          if(compType==ComponentType.TEXTAREA_TYPE || compType==ComponentType.TEXT_TYPE){
            fldValue=DBUtil.toHtml(fldValue);
          }
        }
        //�����null,ͳͳת��Ϊ""
        if (fldValue == null) {
          fldValue = "";
        }
        else {
          fldValue = fldValue.trim();
        }
        fldValue = DBUtil.fromDB(fldValue);
      }
      catch (Exception ex) {
        fldValue = null;
      }
      //�����γ�List
      ArrayList tmpVal = (ArrayList) convertValue(fldValue, dataType);
      if (tmpVal == null) {
        continue;
      }
      //List�ŵ�values�е�FormElementValue��
      ( (FormElementValue) values.get(fldName)).setValue(tmpVal);
    }
  }

  /**
   * ���ֵ
   *
   * @param name
   */
  public void clearValue(String name) {
    //ȡ��Form�����е�Ԫ������
    ((FormElementValue) values.get(name)).clear();
  }

  /**
   * ����FORMԪ��ֵ�ĺϷ���
   * EventType:
   * View����true
   * Delete �� true
   * Insert��Edit - ȫ�� ����ElementBean��validate������ʧ�����ô�����Ϣ
   * Find - true
   *
   * @param eventid
   * @param msgs
   * @return boolean
   * @roseuid 3F72628A0171
   */
  public boolean validate(int eventid, ErrorMessages msgs) {
    boolean flg = true;
    //ֻ��Insert��Edit�¼����Ͳż��
    if (eventid == EventType.EDIT_EVENT_TYPE ||
        eventid == EventType.INSERT_EVENT_TYPE) {
      FormBean formBean = FormBeanManager.getForm(formid);
      String[] eleNames = formBean.getElementKeys();
      for (int i = 0; i < eleNames.length; i++) {
        ElementBean elementBean = formBean.getElement(eleNames[i]);
        FormElementValue fev = (FormElementValue)this.values.get(eleNames[i]);
        if (elementBean.validate(fev.getValue())) {
          flg = false;
          msgs.add(eleNames[i] + "�ֶδ���!");
        }
      }
    }
    return flg;
  }

  /**
   * �����������õ�sql
   */
  public String getRefSQL(SessionContext p_ctx, String p_fieldname) {
    if (p_ctx == null || p_fieldname == null) {
      return null;
    }
    String sql;
    String refTable;
    FieldBean[] skBeans;
    String[] skNames, skValues;
    /*1�����select����*/
    //��λ��FormInstance����Ӧ��TableBean
    FormBean frmbean = FormBeanManager.getForm(this.formid);
    TableBean tblbean = TableBeanManager.getTable(frmbean.getTbl());
    //��λ��p_fieldname����Ӧ��FieldBean
    FieldBean fldbean = tblbean.getField(p_fieldname);
    //ͨ��FieldBean��ȡrefTable��Refnamefld��Refvaluefld�����sql��select����
    refTable = fldbean.getReftbl();
    if (refTable == null || refTable.length() < 1) {
      return null;
    }
    sql = "select " + fldbean.getRefnamefld() + "," + fldbean.getRefvaluefld() +
        " from " +
        refTable + " ";
    /*2�����where����*/
    //��λskBeans
    TableBean reftblbean = TableBeanManager.getTable(refTable);
    if (reftblbean == null) {
      return sql;
    }
    skBeans = reftblbean.getSearchKey();
    if (skBeans == null || skBeans.length < 1) {
      return null;
    }
    //��p_ctx��ȡ��sk����Ӧ��ֵ����ͬ���where����
    sql += "where ";
    for (int i = 0; i < skBeans.length; i++) {
      String tmpVal = p_ctx.getParameter(skBeans[i].getName());
      if (tmpVal == null || tmpVal.length() < 1) {
        continue;
      }
      tmpVal = DBUtil.toDB(tmpVal.trim());
      sql += skBeans[i].getName();
      sql += " like ";
      sql +=
          SqlAssistor.crtWhereValue(tmpVal, skBeans[i].getDatatype(),
                                    OperatorType.LIKE_OPERATOR_TYPE) +
          " and ";
    }
    if (sql.endsWith("where ")) {
      sql = sql.substring(0, sql.length() - 6);
    }
    else {
      sql = sql.substring(0, sql.length() - 5);
    }
    logger.info(sql);
    return sql;
  }

  /**
   * �����������������õ�sql
   */
  public String getRefCountSQL(SessionContext p_ctx, String p_fieldname) {
    if (p_ctx == null || p_fieldname == null) {
      return null;
    }
    String sql;
    String refTable;
    FieldBean[] skBeans;
    String[] skNames, skValues;
    /*1�����select����*/
    //��λ��FormInstance����Ӧ��TableBean
    FormBean frmbean = FormBeanManager.getForm(this.formid);
    TableBean tblbean = TableBeanManager.getTable(frmbean.getTbl());
    //��λ��p_fieldname����Ӧ��FieldBean
    FieldBean fldbean = tblbean.getField(p_fieldname);
    //ͨ��FieldBean��ȡrefTable��Refnamefld��Refvaluefld�����sql��select����
    refTable = fldbean.getReftbl();
    if (refTable == null || refTable.length() < 1) {
      return null;
    }
    sql = "select count(*) from " + refTable + " ";
    /*2�����where����*/
    //��λskBeans
    TableBean reftblbean = TableBeanManager.getTable(refTable);
    if (reftblbean == null) {
      return sql;
    }
    skBeans = reftblbean.getSearchKey();
    if (skBeans == null || skBeans.length < 1) {
      return null;
    }
    //��p_ctx��ȡ��sk����Ӧ��ֵ����ͬ���where����
    sql += "where ";
    for (int i = 0; i < skBeans.length; i++) {
      String tmpVal = p_ctx.getParameter(skBeans[i].getName());
      if (tmpVal == null || tmpVal.length() < 1) {
        continue;
      }
      tmpVal = DBUtil.toDB(tmpVal.trim());
      sql += skBeans[i].getName();
      sql += " like ";
      sql +=
          SqlAssistor.crtWhereValue(tmpVal, skBeans[i].getDatatype(),
                                    OperatorType.LIKE_OPERATOR_TYPE) +
          " and ";
    }
    if (sql.endsWith("where ")) {
      sql = sql.substring(0, sql.length() - 6);
    }
    else {
      sql = sql.substring(0, sql.length() - 5);
    }
    logger.info(sql);
    return sql;
  }

  /**
   * �����ֶ�������ȡ�������͵���ֵ
   *
   * @param name
   * @return
   */
  public FormElementValue getValue(String name) {
    FormElementValue fev = (FormElementValue) values.get(name);
    return fev;
  }

  public Object getObjectValue(String name) {
    return (Object) values.get(name);
  }

  public String getStringValue(String name) {
    FormElementValue fev = (FormElementValue) values.get(name);
    ArrayList al = (ArrayList) fev.getValue();
    if (al.size() > 0) {
      String temp=al.get(0).toString();
      //INTEGER_TYPE,DECIAML_TYPE���������ֵʱת��""
      if(SqlAssistor.isMaxValue(temp,fev.getType())){
        return "";
      }
      return temp;
    }
    else {
      return null;
    }
  }

  public int getIntValue(String name) {
    String tmp = getStringValue(name);
    if (tmp == null) {
      return 0;
    }
    return new Integer(tmp).intValue();
  }

  public double getDoubleValue(String name) {
    String tmp = getStringValue(name);
    if (tmp == null) {
      return 0;
    }
    return new Double(tmp).doubleValue();
  }

  public boolean getBooleanValue(String name) {
    String tmp = getStringValue(name);
    if (tmp == null) {
      return false;
    }
    if (tmp.equals("1")) {
      return true;
    }
    return false;
  }

  /**
   * �����ֶ����������������͵���ֵ����ֵ��ʽ�������Ȼ�����
   *
   * @param name
   * @param value
   */
  public void setValue(String name, FormElementValue value) {
    FormElementValue fev = (FormElementValue) values.get(name);
    initElementProperties(name, fev);
    fev = value;
  }

  public void setValue(String name, String value) {
    FormElementValue fev = (FormElementValue) values.get(name);
    fev.setValue(convertValue(value, fev.getType()));
  }

  public void setValue(String name, int value) {
    FormElementValue fev = (FormElementValue) values.get(name);
    fev.setValue(convertValue(value + "", DataType.INTEGER_TYPE));
  }

  public void setValue(String name, double value) {
    FormElementValue fev = (FormElementValue) values.get(name);
    fev.setValue(convertValue(value + "", DataType.DECIAML_TYPE));
  }

  public void setValue(Map p_values) {
    this.values = p_values;
  }

  public void setValue(String name, boolean value) {
    String tmp = "0";
    if (value) {
      tmp = "1";
    }
    setValue(name, tmp);
  }

  public void setValue(String name, Object value) {
    FormElementValue fev = (FormElementValue) values.get(name);
    fev.setValue( (List) value);
  }

  /**
   * �����ֶ�����׷�Ӹ������͵���ֵ��ԭ������ֵ���ֲ���
   * @param name
   * @param value
   */

  public void addValue(String name, String value) {
    FormElementValue fev = (FormElementValue) values.get(name);
    fev.add(value);
  }

  public void addValue(String name, int value) {
    FormElementValue fev = (FormElementValue) values.get(name);
    fev.add(new Integer(value));
  }

  public void addValue(String name, double value) {
    FormElementValue fev = (FormElementValue) values.get(name);
    fev.add(new Double(value));
  }

  public void addValue(String name, boolean value) {
    FormElementValue fev = (FormElementValue) values.get(name);
    String tmp = "0";
    if (value) {
      tmp = "1";
    }
    fev.add(tmp);
  }

  public void addValue(String name, Object value) {
    FormElementValue fev = (FormElementValue) values.get(name);
    fev.add(value);
  }

  /**
   * @return String
   * @roseuid 3F7217070086
   */
  public String getInstanceid() {
    return this.instanceid;
  }

  /**
   * @param id
   * @roseuid 3F7217140017
   */
  public void setInstanceid(String id) {
    this.instanceid = id;
  }

  /**
   * @return String
   * @roseuid 3F721721032D
   */
  public String getFormid() {
    return this.formid;
  }

  /**
   * @param id
   * @roseuid 3F721727017D
   */
  public void setFormid(String id) {
    this.formid = id;
  }

  /**
   * @return zt.platform.form.control.FormActions
   * @roseuid 3F72172D0262
   */
  public FormActions getFormAction() {
    return this.formAction;
  }

  /**
   * @param actionΪclassȫ·������Ҫʵ����Ϊ���󣬸���formAction����
   * ��Ҫ����ClassNotFind�쳣��
   * @roseuid 3F72173F0344
   */
  public void setFormAction(String action) {
    if (action == null || action.length() < 1) {
      this.formAction = null;
      return;
    }
    try {
      this.formAction = (FormActions) Class.forName(action).newInstance();
    }
    catch (Exception ex) {
      this.formAction = null;

    }
  }

  /**
   * @return int
   * @roseuid 3F72174C03B1
   */
  public int getState() {
    return this.state;
  }

  /**
   * @param state
   * @roseuid 3F721755015B
   */
  public void setState(int state) {
    this.state = state;
  }

  public boolean isFk(String p_fldName) {
    for (int i = 0; i < pkflds.size(); i++) {
      if ( ( (String) pkflds.get(i)).equals(p_fldName)) {
        return true;
      }
    }
    return false;
  }

  public boolean isSk(String p_fldName) {
    for (int i = 0; i < skflds.size(); i++) {
      if ( ( (String) skflds.get(i)).equals(p_fldName)) {
        return true;
      }
    }
    return false;
  }

  public boolean setFieldReadonly(String fldname, boolean readonly) {
    FormElementValue fev = (FormElementValue) values.get(fldname);
    //System.out.println(fldname + "===set====" + readonly);
    if (fev != null) {
      fev.setReadonly(readonly);
      return true;
    }
    else {
      return false;
    }
  }

  public boolean isFieldReadonly(String fldname) {
    FormElementValue fev = (FormElementValue) values.get(fldname);
    if (fev != null) {
      return fev.isReadonly();
    }
    else {
      return false;
    }
  }

  public boolean setFieldIsNull(String fldname, boolean p_isnull) {
    FormElementValue fev = (FormElementValue) values.get(fldname);
    if (fev != null) {
      fev.setIsNull(p_isnull);
      return true;
    }
    else {
      return false;
    }
  }

  public boolean isFieldIsNull(String fldname) {
    FormElementValue fev = (FormElementValue) values.get(fldname);
    if (fev != null) {
      return fev.isIsNull();
    }
    else {
      return false;
    }
  }

  public boolean setFieldVisible(String fldname, boolean p_visible) {
    FormElementValue fev = (FormElementValue) values.get(fldname);
    if (fev != null) {
      fev.setVisible(p_visible);
      return true;
    }
    else {
      return false;
    }
  }

  public boolean isFieldVisible(String fldname) {
    FormElementValue fev = (FormElementValue) values.get(fldname);
    if (fev != null) {
      return fev.isVisible();
    }
    else {
      return false;
    }
  }

  public boolean setFieldDisabled(String fldname, boolean p_disabled) {
    FormElementValue fev = (FormElementValue) values.get(fldname);
    if (fev != null) {
      fev.setDisabled(p_disabled);
      return true;
    }
    else {
      return false;
    }
  }

  public boolean isFieldDisabled(String fldname) {
    FormElementValue fev = (FormElementValue) values.get(fldname);
    if (fev != null) {
      return fev.isDisabled();
    }
    else {
      return false;
    }
  }



  public boolean isReadonly() {
    return readonly;
  }

  public void setReadonly(boolean readonly) {
    this.readonly = readonly;
  }

  private void initElementProperties(String name, FormElementValue fev) {
    try {
      FormBean formBean = FormBeanManager.getForm(formid);
      ElementBean eb = formBean.getElement(name);
      fev.setReadonly(eb.isReadonly());
      fev.setIsNull(eb.isIsnull());
      fev.setVisible(eb.isVisible());
      fev.setDisabled(eb.isDisabled());
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
}