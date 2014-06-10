package pub.platform.security;

import javax.servlet.http.*;

import pub.platform.form.config.*;

import java.util.*;

public class Check {
     private HttpSession session = null;

     public Check(){
     }
     public void addSession(HttpSession session){
          this.session = session;
     }
     public boolean hasRight(String strUrl) throws Exception{
          if (this.session == null ){
               throw new Exception ("�������  û�д���session����!");
          }

          OperatorManager om = (OperatorManager)session.getAttribute(SystemAttributeNames.USER_INFO_NAME);
          if(om == null){
               throw new Exception ("����Ա��ʱ��������ǩ����");
          }
          List list = om.getJspList();
          int length = list.size();
          for (int i = 0; i < length ; i ++ ){
               String tmpStr = (String)list.get(i);
               if ( tmpStr.indexOf(strUrl) >= 0 ) {
                    return true;
               }
          }
          return false; 
     }
}
