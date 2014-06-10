//Source file: D:\\zt\\platform\\form\\control\\ViewController.java

package pub.platform.form.control;

import pub.platform.form.config.*;
import pub.platform.form.util.*;
import pub.platform.form.util.event.*;

/**
 * ����FORMʵ������FORM��HTML����
 *
 * @author ���滻
 * @version 1.0
 */
public class ViewController {

    /**
     * ���ݴ������FORM�Ľű��ַ���
     *
     * �������£�
     * 1������ǲο��¼���������ο����
     *       return ReferenceGenerator.run()
     * 2��ȡʵ��IDinstanceid=event.getInstanceid()
     * 3�����FORMʵ��������
     * 4�����ʵ����ʶΪinstanceid��ʵ��formInstance
     * 5������formid���Formʵ��
     * 6�������form��List���ͣ�������Find�¼���
     *       if ( ��ѯ���� ) {
     *           ����msg��֯������Ϣ
     *           return ������Ϣ
     *       } else
     *            return ListGenerator.run()
     * 7�������ĵ���PageGenerator.run()�����ű�
     * @param ctx
     * @param event
     * @param msg
     * @param result
     * @return String
     * @roseuid 3F722D6E0372
     */
    public static String[] process(SessionContext ctx, Event event, ErrorMessages msgs, int result)
    {
        String[] rtnMsg = new String[2];
        if (event.getType() == EventType.REFERENCE_FIELD_EVENT_TYPE) {
            return ReferenceGenerator.run(ctx, event, msgs, result);
        }
        FormInstanceManager fiManager = (FormInstanceManager) ctx.getAttribute(SessionAttributes.
            SESSION_FORM_INSTANCE_MANAGER_NAME);
        String instanceid = event.getId();

        FormInstance fi = fiManager.getFormInstance(instanceid);

        FormBean fb = FormBeanManager.getForm(fi.getFormid());
        if (fb.getType() == fb.LIST_TYPE && event.getType() == EventType.FIND_EVENT_TYPE) {
            if (result < 0) {
                rtnMsg[0] = printErrorMsgs(msgs,result);
                return rtnMsg;
            }
            else {
                return ListGenerator.run(ctx, event,msgs,result);
            }
        } else if ( fb.getType() == fb.QUERY_TYPE ) {
            return QueryGenerator.run(ctx, event,msgs,result);
        } else {
            return PageGenerator.run(ctx, event, msgs, result);
        }
    }

    public static String printErrorMsgs(ErrorMessages msgs,int result)
    {
        return PageGenerator.getErrorString(msgs,result);
    }
}