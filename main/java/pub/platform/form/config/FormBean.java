//Source file: e:\\java\\zt\\platform\\form\\config\\FormBean.java

package pub.platform.form.config;
import java.util.*;

import pub.platform.form.util.datatype.ComponentType;

/**
 * FORM�࣬���FORM�Ķ�����Ϣ
 *
 * Notes:
 * 1)�����ÿ�����Ե�setter��getter����
 * 2)ע��fields�ǰ�xposition��yposition�������д���elements�����е�
 * @author sun
 * @version 1.0
 */
public class FormBean {

    /**
     * FORM��Ψһ��ʶ
     */
    private String Id;

    /**
     * URL����Ĭ��ֵ/template/defaultform.jsp
     */
    private String url;

    /**
     * FORM��Ӧ����ȫ·����
     */
    private String proccls;

    /**
     * CSS��ȫ·������
     */
    private String style;

    /**
     * ��Ӧ�ı�����
     */
    private String tbl;

    /**
     * FORM����ͷ
     */
    private String title;

    /**
     * FORM�����ͣ�0������ģʽ��1�����ģʽ
     */
    private int type;

    /**
     * �Ƿ�ֻ��
     */
    private boolean readonly;

    /**
     * LIST���͵Ĳ�ѯSQL���
     */
    private String listsql;
    private String description;
    private int cols;
    private int rows;
    private String[] elementKeys;
    public static final int PAGE_TYPE = 0;
    public static final int LIST_TYPE = 1;
    public static final int QUERY_TYPE = 2;

    public static final int DISPLAY_LIST  = 0;
    public static final int DISPLAY_QUERY = 1;
    public static final int DISPLAY_LIST_AND_QUERY   = 2;

    private Map elements=new HashMap();
    private boolean useAdd;
    private boolean useDelete;
    private boolean useSearch;
    private boolean useSave;
    private boolean useReset;
    private boolean useEdit;
    private String countsql;
    private int width;
    private String scriptFile;

    /**
     * ȡ�����е�ElementBeanʵ����������seqno��������
     * @return zt.platform.form.config.ElementBean[]
     * @roseuid 3F73FE200050
     */

    /**
     * �������±���ȡԪ��
     * @param idx
     * @return zt.platform.form.config.ElementBean
     * @roseuid 3F812E37031E
     */
//    public ElementBean getElement(int idx) {
//     return null;
//    }

//    public String[] getElementKeys(){
//        return
//    }

    /**
     * ��Ԫ������ȡԪ��
     * @param name
     * @return zt.platform.form.config.ElementBean
     * @roseuid 3F812E4603DE
     */
    public ElementBean getElement(String name) {
     return (ElementBean)this.elements.get(name);
    }

    /**
     * �����꣨x,y����ȡԪ��
     * @param x
     * @param y
     * @return zt.platform.form.config.ElementBean
     * @roseuid 3F812E580254
     */
    public ElementBean getElement(int x, int y) {
        for (Iterator ies = this.elements.values().iterator(); ies.hasNext(); ) {
            ElementBean eb = (ElementBean)ies.next();
            if(eb.getXposition()==x&&eb.getYposition()==y&&eb.getComponetTp()!=ComponentType.SYS_BUTTON&&eb.getComponetTp()!=ComponentType.HIDDEN_TYPE){
                return eb;
            }
        }
        return null;
    }
    public boolean hasElement(int x,int y) {
        for (Iterator ies = this.elements.values().iterator(); ies.hasNext(); ) {
            ElementBean eb = (ElementBean)ies.next();
            if(eb.getXposition()==x && eb.getYposition()!=y && eb.getComponetTp()!=ComponentType.SYS_BUTTON&&eb.getComponetTp()!=ComponentType.HIDDEN_TYPE){
                return true;
            }
        }
        return false;
    }


    public List getSearchKeys() {
        List ebs = new ArrayList();
        for (Iterator ies = this.elements.values().iterator(); ies.hasNext(); ) {
            ElementBean eb = (ElementBean) ies.next();
            if (eb.isIsSearchKey()) {
                ebs.add(eb);
            }
        }
        Collections.sort(ebs);
        return ebs;
    }

    public List getQueryField() {
        List ebs = new ArrayList();
        for (Iterator ies = this.elements.values().iterator(); ies.hasNext(); ) {
            ElementBean eb = (ElementBean) ies.next();
            if ( (eb.getDisplayType() == this.DISPLAY_QUERY || eb.getDisplayType() == this.DISPLAY_LIST_AND_QUERY ) && eb.getComponetTp() != ComponentType.HIDDEN_TYPE) {
                ebs.add(eb);
            }
        }
        Collections.sort(ebs);
        return ebs;
    }

    public List getQueryHiddenFlds() {
        List ebs = new ArrayList();
        for (Iterator ies = this.elements.values().iterator(); ies.hasNext(); ) {
            ElementBean eb = (ElementBean) ies.next();
            if (  (eb.getDisplayType() == this.DISPLAY_QUERY || eb.getDisplayType() == this.DISPLAY_LIST_AND_QUERY) && eb.getComponetTp() == ComponentType.HIDDEN_TYPE ) {
                ebs.add(eb);
            }
        }
        return ebs;
    }

    public List getSysButton() {
        List ebs = new ArrayList();
        for (Iterator ies = this.elements.values().iterator(); ies.hasNext(); ) {
            ElementBean eb = (ElementBean) ies.next();
            if ( eb.getComponetTp() == ComponentType.SYS_BUTTON ) {
                ebs.add(eb);
            }
        }
        Collections.sort(ebs);
        return ebs;
    }

    public List getHidden() {
        List ebs = new ArrayList();
        for (Iterator ies = this.elements.values().iterator(); ies.hasNext(); ) {
            ElementBean eb = (ElementBean) ies.next();
            if ( eb.getComponetTp() == ComponentType.HIDDEN_TYPE ) {
                ebs.add(eb);
            }
        }
        return ebs;
    }


    /**
     * �Ƿ�ֻ��
     * @return boolean
     * @roseuid 3F73FE280188
     */
    public boolean isReadonly() {
     return this.readonly;
    }
    public String getDescription() {
        return description;
    }
    public int getCols() {
        return cols;
    }
    public void setCols(int cols) {
        this.cols = cols;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setElementKeys(String[] elements) {
        this.elementKeys = elements;
    }
    public void setListsql(String listsql) {
        this.listsql = listsql;
    }
    public void setId(String Id) {
        this.Id = Id;
    }
    public String getId() {
        return Id;
    }
    public String getListsql() {
        return listsql;
    }
    public String getProccls() {
        return proccls;
    }
    public void setProccls(String proccls) {
        this.proccls = proccls;
    }
    public void setReadonly(boolean readonly) {
        this.readonly = readonly;
    }
    public void setRows(int rows) {
        this.rows = rows;
    }
    public int getRows() {
        return rows;
    }
    public String getStyle() {
        return style;
    }
    public void setStyle(String style) {
        this.style = style;
    }
    public String getTbl() {
        return tbl;
    }
    public void setTbl(String tbl) {
        this.tbl = tbl;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    //formid,urlocate,formproc,formstyle,formtbl
    //title,formtype,readonly,rows,cols,enabled,listsql,description
    public String toString(){
        return ":"+getId()+":"+getUrl()+":"+getProccls()+":"+getStyle()+":"+getTbl()
                  +":"+getTitle()+":"+getType()+":"+isReadonly()+":"+getRows()+":"+getCols()+":"+getListsql()+":"+getDescription();
    }
//    public java.util.Map getElements() {
//        return elements;
//    }
    public void setElements(Map elements) {
        this.elements = elements;
    }

    public void addElement(Object key,Object value){
        this.elements.put(key,value);
    }

//    public ElementBean[] getElements(){
//        Object o[]=elements.values().toArray();
//        ElementBean[] e=new ElementBean[o.length];
//        for (int i = 0; i < o.length; i++) {
//            e[i]=(ElementBean)o[i];
//        }
//        return e;
//    }

    public String[] getElementKeys(){
        return this.elementKeys;
    }
    public boolean isUseAdd() {
        return useAdd;
    }
    public void setUseAdd(boolean useAdd) {
        this.useAdd = useAdd;
    }
    public boolean isUseDelete() {
        return useDelete;
    }
    public void setUseDelete(boolean useDelete) {
        this.useDelete = useDelete;
    }
    public boolean isUseSearch() {
        return useSearch;
    }
    public void setUseSearch(boolean useSearch) {
        this.useSearch = useSearch;
    }
    public boolean isUseSave() {
        return useSave;
    }
    public void setUseSave(boolean useSave) {
        this.useSave = useSave;
    }
    public boolean isUseReset() {
        return useReset;
    }
    public void setUseReset(boolean useReset) {
        this.useReset = useReset;
    }
    public boolean isUseEdit() {
        return useEdit;
    }
    public void setUseEdit(boolean useEdit) {
        this.useEdit = useEdit;
    }
    public String getCountsql()
    {
        return countsql;
    }
    public void setCountsql(String countsql)
    {
        this.countsql = countsql;
    }
    public int getWidth()
    {
        return width;
    }
    public void setWidth(int width)
    {
        this.width = width;
    }
    public String getScriptFile() {
        return scriptFile;
    }
    public void setScriptFile(String scriptFile) {
        this.scriptFile = scriptFile;
    }

}
