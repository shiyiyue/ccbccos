package pub.platform.system.manage.role;

import pub.platform.db.*;
import pub.platform.form.control.Action;


public class RoleDeleteAction extends Action
{
    public int doBusiness() {
         for (int i=0; i<this.req.getRecorderCount();i++ ){

              this.dc.executeUpdate("delete from  PTOperRole where (RoleID='"+this.req.getFieldValue(i,"keycode")+"')" );

              this.dc.executeUpdate("delete from  PTRoleRes where (RoleID='"+this.req.getFieldValue(i,"keycode")+"')" );

              String SQLStr = "select  * from  PTOperRole where (RoleID='"+this.req.getFieldValue(i,"keycode")+"') ";


              this.rs = this.dc.executeQuery(SQLStr);

              if (!this.rs.next()){

                   SQLStr = "select  * from  PTRoleRes where (RoleID='"+this.req.getFieldValue(i,"keycode")+"') ";


                  this.rs = this.dc.executeQuery(SQLStr);
                  if (!this.rs.next()){
                       SQLStr = "delete from PTRole where (RoleID='" + this.req.getFieldValue(i,"keycode") + "')";
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
                       this.res.setMessage("��ɫ��Դɾ��ʧ��");
                       return -1;

                  }

             }else{
                  this.res.setType(0);
                  this.res.setResult(false);
                  this.res.setMessage("��Ա��ɫɾ��ʧ��");
                  return -1;

             }

         }

         this.res.setType(0);
         this.res.setResult(true);
         this.res.setMessage("ɾ����¼�ɹ�");
         return 0;
    }
}
