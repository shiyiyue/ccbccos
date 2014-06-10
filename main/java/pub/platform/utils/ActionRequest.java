package pub.platform.utils;

import org.jdom.*;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import pub.platform.utils.*;

import java.io.*;
import java.util.*;

public class ActionRequest {
   private Map recorder;          /////��ŵ�һ����Ϣ
   private String SQLType;        ////��������

   private Map recorderNames;     /////�����ƴ���������͵���Ϣ
   private Vector recorderInts;   /////����������͵���Ϣ
   private Map SQLTypeNames;     /////�����ƴ���������͵���Ϣ������
   private Vector SQLTypeInts;   /////����������͵���Ϣ������
   private int recorderCount;    ///�������͸���
   private String MethodName;   //////////������������


    public ActionRequest() {

        recorder= new HashMap();
        recorderNames= new HashMap();
        SQLTypeNames= new HashMap();
        recorderInts = new Vector();
        SQLTypeInts =  new  Vector();

        MethodName ="";
    }

    /////��������ֵ
    public void setmethodName(String methodname){
         MethodName =methodname;
    }

    ///////���ط�������
    public String getmethodName(){
       return MethodName;
   }

    ///���ز�������
    public String getType(){
        return SQLType;
    }
    ///���������͸�ֵ
   public void setType(String SQLType){
       this.SQLType = SQLType;
   }

  ////����ֶ���
   public void setRecorder(Map recorder)
   {
       this.recorder =recorder;

   }

    ////�����ֶ�����
    public String getFieldType(String fieldName)
    {
         FieldRequest fieldRequest =  (FieldRequest)recorder.get(fieldName.trim().toLowerCase());
         return fieldRequest.geType();
    }


    ///�����ֶ�ֵ
    public String getFieldValue(String fieldName)
   {
        FieldRequest fieldRequest =  (FieldRequest)recorder.get(fieldName.trim().toLowerCase());
        if ( fieldRequest != null )
             return fieldRequest.getValue();
        else
             return null;
   }



   ///���ؾ��ֶ�ֵ
   public String getFieldOldValue(String fieldName)
  {
       FieldRequest fieldRequest =  (FieldRequest)recorder.get(fieldName.trim().toLowerCase());
       if ( fieldRequest != null )
            return fieldRequest.getoldValue();
       else
            return null;
  }

  ///�����ֶα�ʾ
  public String getFieldLable(String fieldName)
 {
      FieldRequest fieldRequest =  (FieldRequest)recorder.get(fieldName.trim().toLowerCase());
      if ( fieldRequest != null )
           return fieldRequest.getfieldLable();
      else
           return null;
 }




  /* ////���ݲ���������Ӳ�������
  public void setTypeS(String name,String SQLType){
     SQLTypeNames.put(name.trim().toLowerCase(),SQLType);
  }

   ////���ݲ������Ʒ��ز�������
   public String getTypeS(String name){
      return  (String)SQLTypeNames.get(name.trim().toLowerCase());
   }
*/
   /////���ݲ���˳�򷵻ز�������
   public void setTypeS(String SQLType){
      SQLTypeInts.add(SQLType);
   }

   /////���ݲ���˳�򷵻ز�������
   public String getTypeS(int index){
      return  (String)SQLTypeInts.get(index);
   }

   /////���ݲ������ͷ��ز���˳��
   public int getIndexOfTypeS(String SQLType){
      return  SQLTypeInts.indexOf(SQLType);
   }

 /*  /////���ݲ���������Ӳ���
   public void setRecorderName(String name,Map recorder)
    {
       recorderNames.put(name.trim().toLowerCase(),recorder);

    }*/
    /////������Ӳ���
   public void setRecorderInt(Map recorder)
    {
       recorderInts.add(recorder);

    }
/*
   /////���ݲ������ƺ��ֶ����Ʒ����ֶ�����
   public String getFieldType(String name,String fieldName)
    {
        Map recorderName  = (Map)recorderNames.get(name.trim().toLowerCase());

        FieldRequest fieldRequest =  (FieldRequest)recorderName.get(fieldName.trim().toLowerCase());
        return fieldRequest.geType();
    }

    /////���ݲ������ƺ��ֶ����Ʒ����ֶ�ֵ
   public String getFieldValue(String name,String fieldName)
    {
        Map recorderName  = (Map)recorderNames.get(name.trim().toLowerCase());

        FieldRequest fieldRequest =  (FieldRequest)recorderName.get(fieldName.trim().toLowerCase());
        return fieldRequest.getValue();
    }

*/
    /////���ݲ���˳����ֶ����Ʒ����ֶ�ֵ
    public String getFieldValue(int index,String fieldName) {
        Map recorderInt  = (Map)recorderInts.get(index);
        FieldRequest fieldRequest =  (FieldRequest)recorderInt.get(fieldName.trim().toLowerCase());
        if ( fieldRequest != null ) {
             return fieldRequest.getValue();
        } else {
             return null;
        }
   }

   /**
    * ��ȡ������ֵ
    * @param index int
    * @param fieldName String
    * @return int
    *
    * linyw add
    */
   public int getFieldIntValue(int index,String fieldName){
        String value = getFieldValue(index, fieldName);
        if(value == null || value.trim().length() == 0){
             return 0;
        }else{
             return Integer.parseInt(value);
        }
   }



   ///ȡ���ֶ���������
   public Object[] getFieldNameArr(int index){
         Map recorderInt  = (Map)recorderInts.get(index);
         return recorderInt.keySet().toArray();

   }


   /////���ݲ���˳����ֶ����Ʒ����ֶξ�ֵ
   public String getFieldOldValue(int index,String fieldName) {
       Map recorderInt  = (Map)recorderInts.get(index);
       FieldRequest fieldRequest =  (FieldRequest)recorderInt.get(fieldName.trim().toLowerCase());
       if ( fieldRequest != null ) {
            return fieldRequest.getoldValue();
       } else {
            return null;
       }
  }

  /////���ݲ���˳����ֶ����Ʒ����ֶα�ʾ
  public String getFieldLable(int index,String fieldName) {
      Map recorderInt  = (Map)recorderInts.get(index);
      FieldRequest fieldRequest =  (FieldRequest)recorderInt.get(fieldName.trim().toLowerCase());
      if ( fieldRequest != null ) {
           return fieldRequest.getfieldLable();
      } else {
           return null;
      }
 }


   /////���ݲ���˳����ֶ����Ʒ����ֶ�����
   public String getFieldType(int index,String fieldName)
  {

       Map recorderInt  = (Map)recorderInts.get(index);
       FieldRequest fieldRequest =  (FieldRequest)recorderInt.get(fieldName.trim().toLowerCase());
       return fieldRequest.geType();
  }


    ///���ز�����������
    public int getRecorderCount(){
        return recorderInts.size();
    }

    ///��Ӳ�����������
   public void setRecorderCount(int recorderCount){
       this.recorderCount = recorderCount;
   }

}
