package pub.platform.system.manage.resRole;

import pub.platform.form.control.Action;

public class ResRoleDeleteAction extends Action{
    public int doBusiness() {

       for (int i=0; i<this.req.getRecorderCount();i++ ){
           String  SQLStr = "delete from PTRoleRes where (ResID='" + this.req.getFieldValue(i,"ResID") + "')and(RoleID='" + this.req.getFieldValue(i,"RoleID") + "')";
            int retcount = this.dc.executeUpdate(SQLStr);

            if(retcount < 0) {
                 this.res.setType(0);
                 this.res.setResult(false);
                 this.res.setMessage("ɾ����¼ʧ��");
                 return retcount;
          }

     }

     this.res.setType(0);
     this.res.setResult(true);
     this.res.setMessage("ɾ����¼�ɹ�");

     return 0;
   }
}
