/**
 * ��ϵͳ�����ļ��ж�ȡ����Ϣ������ִ�С�
 *
 * @author=yujj;
 * 2004-10-20
 *
 *
 */


package pub.platform.control.autorun;

import java.util.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

import pub.platform.utils.BusinessDate;

import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.logging.*;
import java.io.*;

public class SchedulerTask
     extends TimerTask {

     public boolean isDebug = true;

     HashMap hourMap = new HashMap();
     HashMap miniteMap = new HashMap();
     HashMap classNameMap = new HashMap();

     public SchedulerTask() {
          DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
          String CONFIG_FILE_NAME = "autorunconfig.xml";
          String txnCode = "";
          try {
               DocumentBuilder db = dbf.newDocumentBuilder();
               Document dom = db.parse(new File(CONFIG_FILE_NAME));
               if(dom == null) {
                    System.out.println(" is null ");
               }
               Element root = dom.getDocumentElement();

               NodeList nodes = root.getElementsByTagName("transaction");

               int length = nodes.getLength();
               for(int i = 0; i < length; i++) {
                    Node node = nodes.item(i); //<transaction>
                    if(node.getNodeType() != Node.ELEMENT_NODE) {
                         continue;
                    }
                    Element el = (Element)node;
                    txnCode = el.getAttribute("txncode");
                    System.out.print(txnCode);
                    //1.ȡ�������ֶ�<inputfields>
                    NodeList nodeList = el.getElementsByTagName("runtime");
                    if(nodeList.getLength() > 0) {
                         Element elrun = (Element)nodeList.item(0);
                         hourMap.put(txnCode, elrun.getAttribute("itemhour"));
                         miniteMap.put(txnCode, elrun.getAttribute("itemminite"));
                    }

                    //3.ȡҵ����
                    NodeList procNode = el.getElementsByTagName("processclass");
                    if(procNode.getLength() > 0) {
                         Element procEl = (Element)procNode.item(0);
                         classNameMap.put(txnCode, procEl.getAttribute("classname"));
                    }
               }

             //  System.out.println(hourMap.get("9001").toString() + miniteMap.get("9001").toString() + classNameMap.get("9001").toString());
            //   System.out.println(hourMap.get("9002").toString() + miniteMap.get("9002").toString() + classNameMap.get("9002").toString());
          } catch(Exception ex) {
               ex.printStackTrace();
          }

     }

     public void run() {
          // String paydate=BusinessDate.getNoFormatToday();
          Class cls;
          Object obj;
          BasicAutoApplication appRun;
          String className = "";
          try {
               Iterator iterator = classNameMap.values().iterator();
               while(iterator.hasNext()) {
                    className = iterator.next().toString();
                 //   System.out.println("className==="+className);
                    cls = Class.forName(className);
                    obj = cls.newInstance();
                    appRun = (BasicAutoApplication)obj;
                    appRun.doBussiness();

               }

          } catch(Exception ex) {
               //���������Ϣ�����־�ļ���
               if(isDebug) {
                    try {
//                         Handler handler = new FileHandler("autorun.log");
//
//                         Logger logger = Logger.getLogger(" zt.catv.bank.util");
//                         logger.addHandler(handler);
//                         logger.warning(ex.getMessage());

                    } catch(Exception ex1) {
                         System.out.println("���ܼ�¼��־��");
                    }

               }
               System.out.println(ex.getMessage());
          }

     }

}
