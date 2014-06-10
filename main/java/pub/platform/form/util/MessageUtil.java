
package pub.platform.form.util;

import pub.platform.advance.utils.PropertyManager;
import pub.platform.form.util.event.ErrorMessage;
/**
 * ��Ϣ������
 *
 * @author qingdao tec
 * @version 1.0
 */
public class MessageUtil {

    /**
     * ��msgת������Ϣ��
     *
     * ����ErrorMessage��ʵ��������PropertyMessages�õ�ת�������Ϣ��
     * @param msg
     * @return String
     * @roseuid 3F7E3FD90063
     */
    public static String getMessage(ErrorMessage msg) {
      String temp;
      if(msg.getType()==msg.CONSTANT_TYPE){
        temp= PropertyManager.getProperty(msg.getMessage());
        if(temp==null) temp=msg.getMessage();
      }
      else{
        temp= PropertyManager.getProperty(msg.getMessage(),msg.getArguments());
        if(temp==null) temp=msg.getMessage()+"<"+msg.getArguments()+">";
      }
      return temp;
    }
}
