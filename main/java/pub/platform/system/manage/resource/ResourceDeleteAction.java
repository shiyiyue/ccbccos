package pub.platform.system.manage.resource;

import pub.platform.db.*;
import pub.platform.form.control.Action;


public class ResourceDeleteAction extends Action
{
    public int doBusiness() {
         for (int i=0; i<this.req.getRecorderCount();i++ ){

                   this.dc.executeUpdate(" delete from  PTRoleRes where (ResID='"+this.req.getFieldValue(i,"keycode")+"') ");

                   String SQLStr = "select  * from  PTRoleRes where (ResID='"+this.req.getFieldValue(i,"keycode")+"') ";


                  this.rs = this.dc.executeQuery(SQLStr);
                  if (!this.rs.next()){
                       SQLStr = "delete from PTResource where (ResID='" + this.req.getFieldValue(i,"keycode") + "')";
                       int retcount = this.dc.executeUpdate(SQLStr);
                       if(retcount < 0) {
                            this.res.setType(0);
                            this.res.setResult(false);
                            this.res.setMessage("ɾ����¼ʧ��");
                            return retcount;
                       }

                  }else{
                       this.res.setType(0);
                       this.res.setResult(false);
                       this.res.setMessage("��ɫ��Դɾ��ʧ�ܣ�");
                       return -1;

                  }

         }

         this.res.setType(0);
         this.res.setResult(true);
         this.res.setMessage("ɾ����¼�ɹ�");
         return 0;
    }
}
