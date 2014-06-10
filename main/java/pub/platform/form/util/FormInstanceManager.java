//Source file: e:\\java\\zt\\platform\\form\\util\\FormInstanceManager.java

package pub.platform.form.util;

import java.util.*;
import java.io.Serializable;

/**
 * ����FORM��ʵ����,һ���Ự��session��һ��ʵ��
 *
 *
 * @author qingdao tec
 * @version 1.0
 */
public class FormInstanceManager
    implements Serializable {

    /**
     * FormInstance�����
     *
     * ����ǰ��ģʽ�������������ˣ�ɾ�����һ��ʵ������һ��Ԫ��Ϊ��ǰԪ��
     * ��ע��Ԫ�������ʱ�Ļ����������
     */
    private LinkedList manager = new LinkedList();

    /**
     * ʵ������ش�С
     */
    private int maxSize;

    /**
     * ʵ����Ų�������ÿ��ʵ����Ӧһ��Ψһ�����
     */
    private InstanceSequencer sequencer;

    /**
     * ʵ����FormInstanceManager������
     *
     * maxSize:ʵ���صĴ�С
     * @param maxSize
     * @roseuid 3F7215D90371
     */
    public FormInstanceManager(int p_maxSize)
    {
        this.maxSize = p_maxSize;
        sequencer = new InstanceSequencer();
    }

    /**
     * ����formidʵ����FORM,������ʵ�������
     * @param formid
     * @return String
     * @roseuid 3F7215F002FC
     */
    public String instanceForm(String formid)
    {
        //�ж���û�г������ֵ
        if (manager.size() >= maxSize)
            manager.removeFirst();
            //�����µ�instanceForm��������FormInstanceId
        String sequence = sequencer.getSequence();
        FormInstance fi = new FormInstance(formid, sequence);
        manager.add(fi);
        return sequence;
    }

    /**
     * ʵ����FORM,����ʼ��
     * @param formid
     * @param values
     * @return String
     * @roseuid 3F72B50D01E3
     */
    public String instanceForm(String formid, Map values)
    {
        if (manager.size() >= maxSize)
            manager.removeFirst();
        String sequence = sequencer.getSequence();
        FormInstance fi = new FormInstance(formid, sequence);
        fi.setValue(values);
        manager.add(fi);
        return sequence;
    }

    /**
     * ��ȡʵ��instanceid
     * @param instanceid
     * @return zt.platform.form.util.FormInstance
     * @roseuid 3F721605023E
     */
    public FormInstance getFormInstance(String instanceid)
    {
        //����manager�ҳ�instanceid��ͬ�ģ�ɾ����ͬʱ���ؾ����
        FormInstance fi;
        for (int i = manager.size() - 1; i >= 0; i--) {
            fi = (FormInstance) manager.get(i);
            if ( (fi.getInstanceid()).equals(instanceid))
                return (FormInstance) manager.get(i);
        }
        return null;
    }

    /**
     * �õ���ǰʵ��
     * @return zt.platform.form.util.FormInstance
     * @roseuid 3F7E1D3D0316
     */
    public FormInstance getFormInstance()
    {
        return (FormInstance) manager.getLast();
    }

    /**
     * ȡ��ʵ��instanceid����һ��FORMʵ��
     * @param instanceid
     * @return zt.platform.form.util.FormInstance
     * @roseuid 3F7284700101
     */
    public FormInstance getPreviousInstance(String instanceid)
    {
        //����manager�ҳ�instanceid��ͬ�ģ�ȡ��ǰһ���������˵��Сһ����ŵģ�ʵ�����أ�û�У��򷵻�null��
        FormInstance fi;
        for (int i = manager.size() - 1; i > 0; i--) {
            fi = (FormInstance) manager.get(i);
            if ( (fi.getInstanceid()).equals(instanceid))
                return (FormInstance) manager.get(i - 1);
        }
        return null;
    }

    /**
     * ȡ��ָ��FORMʵ������һ��ʵ��
     * @param instance
     * @return zt.platform.form.util.FormInstance
     * @roseuid 3F72848D01C1
     */
    public FormInstance getPreviousInstance(FormInstance instance)
    {
        //����manager�ҳ�instance��ͬ�ģ�ȡ��ǰһ���������˵��Сһ����ŵģ�ʵ�����أ�û�У��򷵻�null��
        FormInstance fi;
        for (int i = manager.size() - 1; i > 0; i--) {
            fi = (FormInstance) manager.get(i);
            if (fi.equals(instance))
                return (FormInstance) manager.get(i - 1);
        }
        return null;
    }

    /**
     * ��ȡ��ǰʵ������һ��ʵ��
     * @return zt.platform.form.util.FormInstance
     * @roseuid 3F7E1C960077
     */
    public FormInstance getPreviousInstance()
    {
        return getPreviousInstance(getFormInstance());
    }

    /**
     * ж��ʵ��instanceid
     * @param instanceid
     * @return FormInsatnce
     * @roseuid 3F72CD4A0325
     */
    public FormInstance removeInstance(String instanceid)
    {
        //����manager�ҳ�instanceid��ͬ�ģ�ɾ����ͬʱ���ؾ����
        FormInstance fi;
        for (int i = manager.size() - 1; i >= 0; i--) {
            fi = (FormInstance) manager.get(i);
            if ( (fi.getInstanceid()).equals(instanceid))
                return (FormInstance) manager.remove(i);
        }
        return null;
    }

    /**
     * ж��ʵ��instance
     * @param instance
     * @return zt.platform.form.util.FormInstance
     * @roseuid 3F72CE53004B
     */
    public FormInstance removeInstance(FormInstance instance)
    {
        //����manager�ҳ�instanceid��ͬ�ģ�ɾ����ͬʱ���ؾ����
        FormInstance fi;
        for (int i = manager.size() - 1; i >= 0; i--) {
            fi = (FormInstance) manager.get(i);
            if (fi.equals(instance))
                return (FormInstance) manager.remove(i);
        }
        return null;
    }

    public void print()
    {
        System.out.println("FormInstanceManager Pool size = " + manager.size());
        for (int i = 0; i < manager.size(); i++) {
            FormInstance fi = (FormInstance) manager.get(i);
            System.out.println(fi.getFormid());
        }
    }

    public static void main(String[] args)
    {
        LinkedList ll = new LinkedList();
        ArrayList al = new ArrayList();
        System.out.println(ll.size());
        ll.add("asdf1");
        ll.add("asdf2");
        ll.add("asdf3");
        al.add("asdf1");
        al.add("asdf2");
        al.add("asdf3");
        ll.remove(1);
        al.remove(1);
        System.out.println(ll.get(1));
    }
}