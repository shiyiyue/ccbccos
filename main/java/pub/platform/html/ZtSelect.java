package pub.platform.html;

import org.apache.ecs.html.*;

import pub.platform.db.*;
import pub.platform.form.config.*;
import pub.platform.utils.DbUtil;

import java.util.*;

/**
 * ����ö�����ͱ������������˵�����.
 * ʾ����
 *     ZtSelect zs = new ZtSelect("test","02","03");
 *     zs.addAttr("onClick","javascript:alert('dddd')");
 *     zs.addOption("��","1234");
 *     System.out.println(zs);
 *
 * ���ؽ����
 *     <select name="test" onClick="javascript:alert('dddd')">
 *          <option value="1234">��
 *          <option value="01">����װ��ʩ����
 *          <option value="02">���߿�ͨʩ����
 *          <option selected value="03">���߱�ͣʩ����
 *          ....
 *     </select>
 *
 */

public class ZtSelect {
  String name = "";         //�����˵�����
  String enuType = "";      //ʹ�õ�ö������
  String defValue;          //Ĭ��ֵ
  Set nousedValue;           //ö��ֵ�е�ѡ��ֵ

  boolean displayAll;       //�Ƿ���ʾ��ֵ-���ƣ�
  HashMap attr = new HashMap();  //�Զ�������Լ���
  HashMap options = new HashMap(); //�Զ����ѡ��
  RecordSet rs ;            //���ɲ˵�ʹ�õĽ������Ϊֵ��������������
  String sqlString;         //�����SQL��䣬ͨ������ִ�в�����������ɲ˵�
  boolean isSelectNull = true;
  /**
   * ���췽��
   * @param name �����˵�����
   * @param enuType �����˵���Ӧ��ö������
   * @param defValue �����˵���Ĭ��ֵ
   */
  public ZtSelect(String name,String enuType,String defValue) {
    this.name = name;
    this.enuType = enuType;
    this.defValue = defValue;
    attr.put("fieldname",name);
    attr.put("fieldtype","select");
  }

  /**
   * ���췽��
   * @param name �����˵�����
   * @param enuType �����˵���Ӧ��ö������
   * @param defValue �����˵���Ĭ��ֵ
   */
  public ZtSelect(String name,String enuType) {
    this.name = name;
    this.enuType = enuType;
    attr.put("fieldname",name);
    attr.put("fieldtype","select");
  }

  /**
   * ���췽��
   *
   * @param name �����˵�����
   * @param rs �����˵���Ӧ�Ľ����(����ֵ����������)
   * @param defValue �����˵���Ĭ��ֵ
   */
  public ZtSelect(String name,RecordSet rs,String defValue) {
    this.name = name;
    this.rs = rs;
    this.defValue = defValue;
    attr.put("fieldname",name);
    attr.put("fieldtype","select");
  }

  public static void main(String[] args) {

    ZtSelect zs = new ZtSelect("busscode","citycode","03");
    zs.setSqlString("select itemcode,itemname from coreitemdefine where rownum<=5");
    zs.addAttr("onClick","javascript:alert('dddd')");
    zs.addOption("��","1234");
    zs.setDisplayAll(true);
    System.out.println(zs);
  }

  /**
   * ����Select������
   * @param attrName ��������
   * @param attrCont ���Ե������ַ���
   */
  public void addAttr(String attrName,String attrCont) {
	if (attrCont == null)
		attrCont = "";
    attr.put(attrName,attrCont);
  }

  /**
   * �ֹ����ѡ����
   * @param optName ѡ���������
   * @param optValue ѡ�����ֵ
   */
  public void addOption(String optName,String optValue) {
    options.put(optName,optValue);
  }

  /**
   * �Ƿ���ʾֵ
   * @param displayAll
   */
  public void setDisplayAll(boolean displayAll) {
    this.displayAll = displayAll;
  }

  private void setAttr(Select se) {
    se.addAttribute("id",name);
    Iterator attrit = attr.keySet().iterator();
    while(attrit.hasNext()) {
      Object object = attrit.next();
      se.addAttribute((String)object,(String)attr.get(object));
    }
  }

  private void setOption(Select se) {
    Iterator optit = options.keySet().iterator();
    while(optit.hasNext()) {
      Object object = optit.next();
      String[] enumArr=((String) object).split(";");

      Option option = new Option();
      if (displayAll)
        option.addElement((String) options.get(object) +" - "+enumArr[0]);
      else
        option.addElement(enumArr[0]);
      option.setValue( (String) options.get(object));

      if (enumArr.length >1)
           option.addAttribute("expand",enumArr[1]);

      if (object.equals(defValue))
        option.setSelected(true);
      se.addElement(option);
    }
  }

  private String selectNull() {
       Script script = new Script();
       script.setLanguage("javascript");
       script.addElement("document.all(\""+name+"\").value='"+defValue+"';");

       if (this.defValue!=null)
            return script.toString();
       else
            return "";
  }

  /**
   * ͨ�õ����������˵��ķ���
   * ��Ҫ��˴�SQL���ķ�ʽ
   *          ������ķ�ʽ
   * @return String
   */
  public String toString() {
    //��SQL���
    if (sqlString != null){
    	 String rtn="";
    	 rtn=this.getRsSelect();
    	 //System.out.println("ddd:"+rtn);    	 
         return this.getRsSelect();
    }
    //������ķ�ʽ
    if (rs!=null)
         return this.getRsSelect();
    //��ͨ��ͨ��ö�ٱ�����ʽ
    Select se = new Select(name);
    setAttr(se);     //�����Զ�������
    setOption(se);   //�����Զ���ѡ��

    //����ö�ٱ�������ѡ��
    EnumerationBean eb = EnumerationType.getEnu(enuType);
    if (eb == null)
         return se.toString();
    Collection tmpKey = eb.getKeys();
    Object[] keys = tmpKey.toArray();
    for ( int i = 0 ; i < keys.length ; i++ ) {
      Object object = keys[i];
      //���������ѡ��ֵ����ֻ��ѡ����ֵ����ʾ
      if (nousedValue!=null && nousedValue.contains(object))
           continue;
      Option option = new Option();

       String[] enumArr=((String)eb.getValue(object)).split(";");

      if (displayAll)
        option.addElement((String)object +" - " +enumArr[0]);
      else
        option.addElement(enumArr[0]);
      option.setValue((String)object);

      if (enumArr.length >1)
           option.addAttribute("expand",enumArr[1]);

      if (object.equals(defValue))
        option.setSelected(true);
      se.addElement(option);
    }
    if(this.isSelectNull){    	 
         return se.toString() + selectNull();
      
    }else{
         return se.toString();
    }
  }


  public String optionToString() {
       String opts = "";
       EnumerationBean eb = EnumerationType.getEnu(enuType);
       Iterator it = eb.getKeys().iterator();
       while(it.hasNext()) {
            Object object = it.next();
            Option option = new Option();

             String[] enumArr=((String)eb.getValue(object)).split(";");
            if(displayAll)
                 option.addElement((String)object + " - " +enumArr[0]);
            else
                 option.addElement(enumArr[0]);
            option.setValue((String)object);

            if (enumArr.length >1)
                 option.addAttribute("expand",enumArr[1]);

            if(object.equals(defValue))
                 option.setSelected(true);
            opts += option.toString();
       }

       return opts;
  }

  /**
   *  ����SQL����ѯ���������˵�
   *  �������SQL��䣬��ִ�����ɽ����
   *  ����ֱ��ʹ�ý����
   * @return String
   */
  public String getRsSelect() {
    Select se = new Select(name);
    se.addAttribute("id",name);

    setAttr(se);
    setOption(se);

    if(sqlString != null)
         rs = DbUtil.getRecord(sqlString);

    while (rs!=null && rs.next()) {
      String value = rs.getString(0);
      String name = rs.getString(1);

        Option option = new Option();
        if (displayAll)
          option.addElement(  value+ " - " + name);
        else
          option.addElement( name);
        option.setValue( value);
        String retStr ="";
       if ( rs.getfieldCount()>2){
            for (int i=2; i< rs.getfieldCount() ; i++ ){
                 retStr = rs.getString(i);
                 if (retStr == null)
                      retStr ="";
               option.addAttribute(rs.getFieldName(i),retStr);
            }


       }

//        if (value.equals(defValue))
//          option.setSelected(true);
        se.addElement(option);
    }

    if(this.isSelectNull){
         return se.toString() + selectNull();
    }else{
         return se.toString();
    }

  }

  public void setDefValue(String defValue) {
    this.defValue = defValue;
  }
  public String getDefValue() {
    return defValue;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public void setSelectNull(boolean isselectnull){
       this.isSelectNull = isselectnull;
  }
     public void setSqlString(String sqlString) {
          this.sqlString = sqlString;
     }
     public void setNosedValue(Set nousedValue) {
          this.nousedValue = nousedValue;
     }

}
