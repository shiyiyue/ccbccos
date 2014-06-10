package pub.service.dao;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 2010-8-24
 * Time: 17:27:29
 * To change this template use File | Settings | File Templates.
 */

import java.io.Serializable;
import java.util.List;


public interface DaoSupport {

    
    public Serializable insert(Object vo);


    public void update(Object vo);


    public void update(List list);


    public void delete(Object vo);

    /**
     * ��������ֵɾ��һ��ֵ����
     *
     * @param c ֵ�������
     * @param s ����ֵ
     */
    public void delete(Class c, Serializable s);


    public void delete(List list);

    /**
     * ����������ѯ��������
     *
     * @param selectClause ��ѯ�����from֮ǰ�Ĳ���(����select),�����(select *),��д��Ϊnull.
     * @param className    ֵ���������
     * @param properties   ��ѯ�������������б�
     * @param operators    ��ѯ�����Ĳ������б������ѯ�����д��ڲ�Ϊ<b>=</b>�Ĳ���������Ҫ��д���б�����Ϊnull
     * @param values       ��ѯ������ֵ�б����б�Ӧ���������б�һһ��Ӧ
     * @param firstRow     ���ز�ѯ�������ʼ�У��������Ҫ��������Ϊ0
     * @param maxRows      ���ز�ѯ���������������������Ҫ��������Ϊ0
     * @return ��ѯ������б�
     */
    public List query(String selectClause, String className, String[] properties, String[] operators, Object[] values, int firstRow, int maxRows);

    /**
     * ��������ֵ�õ�һ��ֵ����

     */
    public Object load(Class c, Serializable s);
}
