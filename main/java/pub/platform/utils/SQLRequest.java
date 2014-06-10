package pub.platform.utils;

import org.jdom.*;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import pub.platform.utils.*;

import java.io.*;
import java.util.*;

public class SQLRequest {
     private Map ActionRequestNames; /////�����ƴ��������Ϊ����Ϣ
     private Vector ActionRequestInts; /////���������Ϊ����Ϣ
     private int ActionCount; ///������Ϊ����
     private Vector ActionNames; /////���������Ϊ������

     public  String ReqXML; /////�ӿͻ��˻�ȡ��XML����

     public SQLRequest() {
          ActionRequestNames = new HashMap();
          ActionRequestInts = new Vector();
          ActionNames = new Vector();

     }

     ////��ʼ����Ϣ ���� XML�ַ���

     public boolean setXMLStr(String XMLStr) {
          boolean retbool = false;

          ReqXML = XMLStr;

          try {
               Reader reader = new StringReader(XMLStr);
               SAXBuilder saxbd = new SAXBuilder();
               Document doc = saxbd.build(reader);

               Element rootNode = doc.getRootElement();

               List ActionChildList = rootNode.getChildren();

               ////ȡ��action �ĸ���
               ActionCount = ActionChildList.size();

               for(int i = 0; i < ActionChildList.size(); i++) {

                    ActionRequest actionRequest = new ActionRequest();

                    Element ActionCildNode = (Element)ActionChildList.get(i);

                    List recorderChildList = ActionCildNode.getChildren();

                    for(int j = 0; j < recorderChildList.size(); j++) {
                         Element recorderChildNode = (Element)recorderChildList.get(j);

                         List fieldChildList = recorderChildNode.getChildren();

                         Map recorderTamp = new HashMap();

                         for(int k = 0; k < fieldChildList.size(); k++) {
                              Element fieldChildNode = (Element)fieldChildList.get(k);

                              FieldRequest fieldRequest = new FieldRequest();
                              fieldRequest.setName(fieldChildNode.getAttributeValue("name").trim().toLowerCase());
                              fieldRequest.setType(fieldChildNode.getAttributeValue("type").trim().toLowerCase());

                              if(fieldChildNode.getAttribute("oldValue") != null)
                                   fieldRequest.setoldValue(Basic.decode(fieldChildNode.getAttributeValue("oldValue")).trim());
                              else
                                   fieldRequest.setoldValue("");





                              if(fieldChildNode.getAttribute("fieldLable") != null){

                                   fieldRequest.setfieldLable(Basic.decode(fieldChildNode.getAttributeValue("fieldLable")));
                              }
                             else
                                  fieldRequest.setfieldLable("");



                              fieldRequest.setValue(Basic.decode(fieldChildNode.getAttributeValue("value")).trim());
                              //�������
                              recorderTamp.put(fieldChildNode.getAttributeValue("name").trim().toLowerCase(), fieldRequest);
                         }
                         actionRequest.setRecorderInt(recorderTamp);

                         actionRequest.setTypeS(recorderChildNode.getAttributeValue("type").trim().toLowerCase());

                         if(j == 0) {
                              actionRequest.setRecorder(recorderTamp);
                              actionRequest.setType(recorderChildNode.getAttributeValue("type").trim().toLowerCase());
                         }

                    }
                    actionRequest.setRecorderCount(recorderChildList.size());
                    ActionRequestNames.put(ActionCildNode.getAttributeValue("actionname").trim().toLowerCase(), actionRequest);
                    ActionRequestInts.add(actionRequest);
                    ActionNames.add(ActionCildNode.getAttributeValue("actionname").trim().toLowerCase());


                    //////////////��ӷ�������
                    if (ActionCildNode.getAttribute("methodname") != null)
                        actionRequest.setmethodName(ActionCildNode.getAttributeValue("methodname").trim());

                    else
                         actionRequest.setmethodName("");


               }
               reader.close();
               retbool = true;
          } catch(Exception ex) {
               ex.printStackTrace();

               retbool = false;
          }
          return retbool;
     }

     ///ȡ����Ϊ�ĸ���
     public int getActionCount() {
          return ActionCount;
     }

     ///ȡ����Ϊ������
     public ActionRequest getActionRequest(String actionName) {
          return(ActionRequest)ActionRequestNames.get(actionName.toLowerCase().trim());
     }

     ///ȡ����Ϊ������
     public ActionRequest getActionRequest(int index) {
          return(ActionRequest)ActionRequestInts.get(index);
     }

     ///ȡ����Ϊ������
     public String getActionName(int index) {
          return(String)ActionNames.get(index);
     }

}
