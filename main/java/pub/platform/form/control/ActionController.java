
package pub.platform.form.control;

import pub.platform.advance.utils.PropertyManager;
import pub.platform.db.ConnectionManager;
import pub.platform.db.DatabaseConnection;
import pub.platform.form.config.FormBean;
import pub.platform.form.config.FormBeanManager;
import pub.platform.form.config.SystemAttributeNames;
import pub.platform.form.util.FormInstance;
import pub.platform.form.util.FormInstanceManager;
import pub.platform.form.util.SessionAttributes;
import pub.platform.form.util.event.ErrorMessages;
import pub.platform.form.util.event.Event;
import pub.platform.form.util.event.EventManager;
import pub.platform.form.util.event.EventType;
import pub.platform.utils.ErrorCode;

/**
 * FORM����������������
 *
 * ���������е�FORM���󣬸��������ID���¼�ID���Ȳ�ͬ�����Խ��д������ľ��幤��
 * ��ControllerAssistor���
 *
 * @author ���滻
 * @version 1.0
 */
public class ActionController {
    private SessionContext ctx;
    public static final int POOL_MIN_SIZE = 20;

    /**
     * @param ctx
     * @roseuid 3F724F850093
     */
    public ActionController(SessionContext ctx)
    {
        this.ctx = ctx;
    }

    /**
     * FORM�����пش���
     *
     * �ɹ����� 0
     * ʧ�ܷ��� �������(<0)
     *
     * ִ���߼����£�
     * 1�����FORMʵ����������FormInstanceManager��formInstanceManager
     *
     * formInstanceManager=ctx.getAttribute(SessionAttribute.SESSION_FORM_INSTANCE_MANA
     * GER_NAME);
     *      if ( formInstanceManager == null ) {
     *          formInstanceManager = new FormInstanceManager();
     *          ctx.setAttribute();
     *      }
     * 2����ȡ�����Formid��Instanceid��eventId
     *     instanceid=ctx.getParameter(SessionAtribute.REQUEST_INSTANCE_ID_NAME)
     *     formid=ctx.getParameter(SessionAtribute.REQUEST_FORM_ID_NAME)
     *     eventtp=ctx.getParameter(SessionAttribute.REQUEST_EVENT_ID_NAME)
     * 3�������¼�����������������һ�������¼�
     *     if ( instanceid!=null ) {
     *         if ( instanceid not exist ) {
     *              ctx.setHead("�������")
     *              ctx.setBody("ʵ��ID="+instanceid+"������,�Ƿ���FORM����")
     *              ctx.setTail("")
     *              return;
     *         }
     *         if ( eventid ���Ϸ�) {
     *              ctx.setHead("�������")
     *              ctx.setBody("�¼�ID="+eventid+"������,�Ƿ���EVENT����")
     *              ctx.setTail("")
     *              return;
     *          }
     *          id = instanceid;
     *          isInstance = true;
     *      } else if ( formid!=null) {
     *          if ( formid not exist ) {
     *               ctx.setHead("�������")
     *               ctx.setBody("FORM ID="+formid+"������,�Ƿ���FORM����")
     *               ctx.setTail("")
     *               return;
     *          }
     *          if ( eventid ���Ϸ� ) {
     *               ctx.setHead("�������")
     *               ctx.setBody("�¼�ID="+eventid+"������,�Ƿ���EVENT����")
     *               ctx.setTail("")
     *               return;
     *          }
     *          id = formid;
     *          isInstance = false;
     *      } else {
     *          ctx.setHead("�������")
     *          ctx.setBody("ʵ��ID�ʹ���IDΪ�գ��Ƿ���FORM����")
     *          ctx.setTail("")
     *          return;
     *      }
     *
     *      manager = New EventManager()
     *      Event firstEvent = new Event(id, eventtp,isInstance)
     *      manager.trigger(firstEvent)
     *      errorMessages = new EventMessages()
     *      Event event
     *      int  result=0;
     *      �����ʵ���������������ֵ���浽ʵ������
     * 4���������ݿ�����
     * 5�������¼�
     *       while ( manager.hasMoreEvent() ) {
     *
     *           5����ʼ����
     *           event = manager.nextEvent();
     *           if ( event == null )
     *               break;
     *           result = ControllerAssistor.process(ctx,event,errorMessages,manager) ;
     *           if ( result >= 0 ) �ύ���� sysEvent=event��else
     * �ع����ݿ�����sysEvent=firstEvent
     *           if (result < 0 || ��break���Ե��¼�)
     *               break;
     *       }
     *
     * 6�����ctx.getHead==null,��ctx.setHead(FORM�����Title)
     * 7������Form����
     *       String body=ViewController.process(ctx,sysEvent,errorMessages,result)
     * 8����FORM���屣��
     *       ctx.setBody(body);
     * @return int
     * @roseuid 3F7226D502D3
     */
    public int run()
    {
        try {
            //1.���FormInstance������
            FormInstanceManager fiManager = (FormInstanceManager) ctx.getAttribute(
                SessionAttributes.SESSION_FORM_INSTANCE_MANAGER_NAME);
            if (fiManager == null) {
                int size = PropertyManager.getIntProperty(SystemAttributeNames.
                    FORM_INSTANCE_POOL_SIZE);
                if (size < POOL_MIN_SIZE) {
                    size = POOL_MIN_SIZE;
                }
                fiManager = new FormInstanceManager(size);
                ctx.setAttribute(SessionAttributes.SESSION_FORM_INSTANCE_MANAGER_NAME, fiManager);
            }
            //2.���촥���¼�
            Event firstEvent = findEvent(fiManager);
            if (firstEvent == null)
                return ErrorCode.ERROR_NOT_EVENT;

            EventManager eManager = new EventManager();
            eManager.trigger(firstEvent);

            ErrorMessages errMsgs = new ErrorMessages();
            Event event = null ;
            int result = 0;
            if ( firstEvent.isInstance() ) {
                FormInstance fi = fiManager.getFormInstance(firstEvent.getInstanceid());
                if ( fi != null ) {
                    fi.updateValue(ctx);
                }
            }
            //3.�������ݿ���Դ
            ConnectionManager manager = ConnectionManager.getInstance();
            DatabaseConnection con = manager.getConnection();
            try {
                //4.�����¼�
                while (eManager.hasMoreEvent() && result >= 0) {
                    event = eManager.nextEvent();
                    con.begin();
                    result = ControllerAssistor.process(ctx, event, errMsgs, eManager, con);
                    if (result < 0) {
                        con.rollback();
                    }
                    else {
                        con.commit();
                    }

                    if (event.getBranchType() == event.BRANCH_BREAK_TYPE)
                        break;
                }
            } catch ( Exception e ) {
                e.printStackTrace();
                con.rollback();
            } catch ( Throwable t ) {
                System.out.println("=====================������������==========================");
                t.printStackTrace();
                System.out.println("=====================������������==========================");
                con.rollback();
            }

            manager.releaseConnection(con);

            if ( ctx.needForward() ) {
                return 0;
            }

            if ( event.getType() == EventType.LOAD_EVENT_TYPE && result < 0 ) {
                ctx.setHead("����ʧ��");
                if ( errMsgs.size() > 0 ) {
                    ctx.setBody(PageGenerator.getErrorString(errMsgs,result));
                }
//                ctx.setTarget();
                return -1;
            }

            //5.����FORM��Title
            if ( ctx.getHead() == null && event != null ) {
                 FormBean formBean = FormBeanManager.getForm(fiManager.getFormInstance(event.getInstanceid()).getFormid());
                 ctx.setHead(formBean.getTitle());
            }
            //6.�γ�Form������
            String[] body = ViewController.process(ctx,event,errMsgs,result);
            if ( body != null ) {
                if ( body.length >= 1 ) {
                    ctx.setBody(body[0]);
                }
                if ( body.length >= 2 ) {
                    ctx.setSysButton(body[1]);
                }
            }

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            ctx.setHead("��������");
            ctx.setBody("ϵͳ��Դæ���Ժ����");
            ctx.setTail("");
            return ErrorCode.EXCEPTION_THROWN;
        }
    }

    private Event findEvent(FormInstanceManager manager)
    {
        String formid = ctx.getParameter(SessionAttributes.REQUEST_FORM_ID_NAME);
        String instanceid = ctx.getParameter(SessionAttributes.REQUEST_INSATNCE_ID_NAME);
        String eventid = ctx.getParameter(SessionAttributes.REQUEST_EVENT_ID_NAME);
        String initParameters = ctx.getParameter(SessionAttributes.
                                                 REQUEST_FORM_INIT_PARAMETERS_NAME);

        String id = "";
        int eventType = EventType.LOAD_EVENT_TYPE;
        boolean isInstance = false;
        try {
            if (instanceid != null && instanceid.trim().length() > 0) {
                instanceid = instanceid.trim();
                if (manager.getFormInstance(instanceid) == null) {
                    ctx.setHead("�������");
                    ctx.setBody("ʵ��ID=" + instanceid + "������,�Ƿ���FORM����");
                    ctx.setTail("");
                    return null;
                }
                if (eventid == null || !EventType.validate(eventid)) {
                    ctx.setHead("�������");
                    ctx.setBody("�¼�ID=" + eventid + "������,�Ƿ���EVENT����");
                    ctx.setTail("");
                    return null;
                }
                id = instanceid;
                isInstance = true;
            }
            else if (formid != null) {
                formid = formid.trim();
                if (FormBeanManager.getForm(formid) == null) {
                    ctx.setHead("�������");
                    ctx.setBody("FORM ID=" + formid + "������,�Ƿ���FORM����");
                    ctx.setTail("");
                    return null;
                }
                if (eventid == null || !eventid.equals("" + EventType.LOAD_EVENT_TYPE)) {
                    ctx.setHead("�������");
                    ctx.setBody("�¼�ID=" + eventid + "������,�Ƿ���EVENT����");
                    ctx.setTail("");
                    return null;
                }
                id = formid;
                isInstance = false;
            }
            else {
                ctx.setHead("�������");
                ctx.setBody("ʵ��ID�ʹ���IDΪ�գ��Ƿ���FORM����");
                ctx.setTail("");
                return null;
            }

            eventType = Integer.parseInt(eventid);
        } catch (Exception e) {
            return null;
        }

        return new Event(id,eventType,Event.BRANCH_CONTINUE_TYPE, isInstance, initParameters);
    }
}