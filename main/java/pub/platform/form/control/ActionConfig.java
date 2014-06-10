package pub.platform.form.control;

import java.util.Hashtable;

import pub.platform.db.*;

/**
 * <p>Title: �����ݿ��ж���Action��������Ϣ</p>
 * <p>Description: ��Ƴɵ�̬ģʽ���ṩ��ʼ����ˢ�·���</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Qingdao qingdao tec Technology Co.Ltd </p>
 * @author zhouwei
 * @version 1.0
 */

public class ActionConfig {
    private Hashtable acs = new Hashtable();
    private static ActionConfig instance;

    private ActionConfig() {
       if (!load(true))
           System.out.println("Failed to load action config!!!");
    }

    public static ActionConfig getInstance() {

        if (instance == null)
            instance = new ActionConfig();
        return instance;
    }

    public boolean load(boolean autoClose) {
         boolean result = true;
         DatabaseConnection dc = null;
         try {
              if(autoClose) //�Զ��ر�����ʱʹ�÷��߳����ݿ�
                   dc = ConnectionManager.getInstance().getConnection();
              else //���Զ��ر�����ʱʹ���߳����ݿ�
                   dc = ConnectionManager.getInstance().get();
              RecordSet rs = dc.executeQuery("select * from ptlogicact");
              while(rs.next()) {
                   LogicAct la = new LogicAct();
                   la.logicCode = rs.getString("logicCode").trim();
                   la.logicClass = rs.getString("logicClass");
                   la.logicMethod = rs.getString("logicMethod");
                   la.logicDesc = rs.getString("logicDesc");
                   la.logicEnabled = rs.getString("logicEnabled");

                   acs.put(la.logicCode, la);
              }
         } catch(Exception e) {
              result = false;
         } finally {
              if(dc != null && autoClose) //ֻ�����Զ��ر����ӵ����
                   dc.close();
         }
         return result;
    }

    public boolean reload() {
        acs.clear();
        return load(false);
    }

    public LogicAct getActionConfig(String logicCode) {
        return (LogicAct)acs.get(logicCode);
    }

    public static void main(String[] args) {
        ActionConfig ac = ActionConfig.getInstance();
        System.out.println(ac.getActionConfig("sc0011").logicClass);
    }

    public class LogicAct {
        String logicCode;
        public String logicClass;
        String logicMethod;
        String logicDesc;
        String logicEnabled;
    }
}
