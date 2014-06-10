package pub.platform.system.manage.action;

import pub.platform.form.control.Action;
import pub.platform.system.manage.dao.*;

public class ActionInsertAction extends Action
{

     PtLogicAct  ptlogic = new PtLogicAct();

     public int doBusiness() {
          return 0;

         

     }



   public int addenum(){
        for (int i=0; i<this.req.getRecorderCount();i++ ){
                 ptlogic.setLogiccode(this.req.getFieldValue(i,"LogicCode"));
                 ptlogic.setLogicclass(this.req.getFieldValue(i,"LogicClass"));
                 ptlogic.setLogicmethod(this.req.getFieldValue(i,"LogicMethod"));
                 ptlogic.setLogicdesc(this.req.getFieldValue(i,"LogicDesc"));


                 if (ptlogic.insert() <0){
                     this.res.setType(0);
                     this.res.setResult(false);
                     this.res.setMessage("��Ӽ�¼ʧ��");
                     return -1;
                 }
           }

           //��������Action����
           pub.platform.form.control.ActionConfig.getInstance().reload();

           this.res.setType(0);
           this.res.setResult(true);
           this.res.setMessage("��Ӽ�¼�ɹ�");

           return 0;

   }

   public int editenum(){
        for (int i=0; i<this.req.getRecorderCount();i++ ){

                    ptlogic.setLogiccode(this.req.getFieldValue(i,"LogicCode"));
                    ptlogic.setLogicclass(this.req.getFieldValue(i,"LogicClass"));
                    ptlogic.setLogicmethod(this.req.getFieldValue(i,"LogicMethod"));
                    ptlogic.setLogicdesc(this.req.getFieldValue(i,"LogicDesc"));


                  if (ptlogic.updateByWhere("where (LogicCode='"+this.req.getFieldValue(i,"keycode")+"')") <0){
                       this.res.setType(0);
                       this.res.setResult(false);
                       this.res.setMessage("���¼�¼ʧ��");
                       return -1;
                  }
            }

            //��������Action����
            pub.platform.form.control.ActionConfig.getInstance().reload();

            this.res.setType(0);
            this.res.setResult(true);
            this.res.setMessage("���¼�¼�ɹ�");

            return 0;

     }

     public int delenum(){

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
