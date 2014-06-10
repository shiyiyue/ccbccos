package pub.platform.system.manage.menu.parent;

import pub.platform.db.*;
import pub.platform.form.control.Action;
import pub.platform.system.manage.dao.*;

public class MenuUpdateAction extends Action {

    private PtMenuBean ptmenu;

    public int doBusiness() {

        ptmenu = new PtMenuBean();
        PtResourceBean ptresBean = new PtResourceBean();

        int menulevel = 1;

        for (int i = 0; i < this.req.getRecorderCount(); i++) {


            /////////////////ȡ�����ڵ���
            ptmenu = (PtMenuBean) ptmenu.findFirstByWhere("where (MenuID='" + this.req.getFieldValue(i, "parentmenuid") + "')");
            if (ptmenu != null)
                menulevel = ptmenu.getMenulevel() + 1;

            ptmenu = new PtMenuBean();

            ptmenu.setMenulabel(this.req.getFieldValue(i, "MenuLabel"));
            ptmenu.setParentmenuid(this.req.getFieldValue("parentmenuid"));

            ptmenu.setMenulevel(menulevel);

            if (this.req.getFieldValue(i, "levelindex") != null)
                ptmenu.setLevelidx(Integer.parseInt(this.req.getFieldValue(i, "levelindex")));
            else
                ptmenu.setLevelidx(0);

            ptmenu.setMenudesc(this.req.getFieldValue(i, "MenuDesc"));

            if (ptmenu.updateByWhere(" where (MenuID='" + this.req.getFieldValue(i, "menuid") + "')") < 0) {
                this.res.setType(0);
                this.res.setResult(false);
                this.res.setMessage("���¼�¼ʧ��");
                return -1;
            }

            /////////////�޸ĺ��ӽڵ�  level
            if (updatechild(this.req.getFieldValue(i, "menuid"), menulevel) < 0) {
                this.res.setType(0);
                this.res.setResult(false);
                this.res.setMessage("���¼�¼ʧ��");
                return -1;
            }

            String target =  this.req.getFieldValue(i, "targetmachine");
            //ptmenu.setTargetmachine(target);

            //�޸��ӽڵ� targetmachine ���ڲ˵�����
            if (updatechild(this.req.getFieldValue(i, "menuid"), target) < 0) {
                this.res.setType(0);
                this.res.setResult(false);
                this.res.setMessage("���¼�¼ʧ��");
                return -1;
            }

            /////////////////������Դ��Ϣ
            ptresBean.setResdesc(this.req.getFieldValue(i, "menulabel"));
            ptresBean.setParentresid("m" + this.req.getFieldValue(i, "parentmenuid"));

            if (ptresBean.updateByWhere(" where (ResID='m" + this.req.getFieldValue(i, "menuid") + "')") < 0) {
                this.res.setType(0);
                this.res.setResult(false);
                this.res.setMessage("������Դʧ��");
                return -1;
            }

        }

        this.res.setType(0);
        this.res.setResult(true);
        this.res.setMessage("���¼�¼�ɹ�");

        return 0;
    }

    private int updatechild(String menuid, int level) {
        if (ptmenu == null)
            ptmenu = new PtMenuBean();


        RecordSet rsset = ptmenu.findRecordSetByWhere("where Parentmenuid='" + menuid + "'");

        while (rsset.next()) {
            ptmenu = new PtMenuBean();

            ptmenu.setMenulevel(level + 1);
            if (ptmenu.updateByWhere(" where (MenuID='" + rsset.getString("menuid") + "')") < 0) {

                return -1;
            }

            updatechild(rsset.getString("menuid"), level + 1);
        }


        return 0;
    }

    /**
     * 20100820 zhanrui
     * �޸��Ӳ˵���targetmachine
     * @param menuid
     * @return
     */
    private int updatechild(String menuid, String target) {
        if (ptmenu == null)
            ptmenu = new PtMenuBean();

        RecordSet rsset = ptmenu.findRecordSetByWhere(" start with menuid = '" + menuid + "' connect by prior menuid = parentmenuid ");

        while (rsset.next()) {
            ptmenu = new PtMenuBean();

            ptmenu.setTargetmachine(target);
            if (ptmenu.updateByWhere(" where (MenuID='" + rsset.getString("menuid") + "')") < 0) {
                return -1;
            }
        }

        return 0;
    }
}
