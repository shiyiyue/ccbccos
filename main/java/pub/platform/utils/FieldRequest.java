package pub.platform.utils;

import java.io.Serializable;

public class FieldRequest {

     private String name; ///�ֶ�����
     private String type; ////�ֶ�����
     private String value; ////�ֶ�ֵ
     private String oldValue; ///�ɵ��ֶ�ֵ
     private String fieldLable;////�ֶ����Ľ���

     public FieldRequest() {
     }

     public void setName(String name) {
          this.name = name;
     }

     public String getName() {
          return name;
     }

     public void setType(String type) {
          this.type = type;
     }

     public String geType() {
          return type;

     }

     public void setValue(String value) {
          this.value = value;
     }

     public String getValue() {
          return value;

     }

     public String getoldValue() {
          return oldValue;
     }

     public void setoldValue(String oldValue) {
          this.oldValue = oldValue;
     }

     public String getfieldLable() {
         return fieldLable;
    }

    public void setfieldLable(String fieldLable) {
         this.fieldLable = fieldLable;
    }

}
