package pub.platform.system.manage.action;

import pub.platform.form.control.Action;
import pub.platform.system.manage.dao.*;


public class ActionDeleteAction extends Action
{
    public int doBusiness() {

         PtLogicAct  ptlogic = new PtLogicAct();

         for (int i=0; i<this.req.getRecorderCount();i++ ){

              if ( ptlogic.deleteByWhere("where (LogicCode='"+this.req.getFieldValue(i,"keycode")+"')") <0){
                       this.res.setType(0);
                       this.res.setResult(false);
                       this.res.setMessage("ɾ����¼ʧ��");
                       return -1;
              }


         }

         //��������Action����
         pub.platform.form.control.ActionConfig.getInstance().reload();

         this.res.setType(0);
         this.res.setResult(true);
         this.res.setMessage("ɾ����¼�ɹ�");
         return 0;

    }

}
