package pub.platform.system.manage.role;

import pub.platform.form.control.Action;

public class RoleUpdateAction extends Action
{
    public int doBusiness() {


		for (int i=0; i<this.req.getRecorderCount();i++ ){

               if (this.req.getFieldValue(i,"RoleID").trim().toLowerCase().equals(this.req.getFieldValue(i,"keycode").trim().toLowerCase()))
               {
                    String SQLStr = "update PTRole set RoleDesc='"+this.req.getFieldValue(i,"RoleDesc")+"'  where (RoleID='"+this.req.getFieldValue(i,"keycode")+"')";

                    System.out.print(SQLStr);
                    int retcount = this.dc.executeUpdate(SQLStr);

                    if (retcount <0){
                       this.res.setType(0);
                       this.res.setResult(false);
                       this.res.setMessage("���¼�¼ʧ��");
                       return retcount;
                  }
             }else{
                  String SQLStr = "select  * from  PTOperRole where (RoleID='"+this.req.getFieldValue(i,"keycode")+"') ";
                  System.out.print(SQLStr);

                  this.rs = this.dc.executeQuery(SQLStr);

                  if (this.rs.next()){
                       this.res.setType(0);
                       this.res.setResult(false);
                       this.res.setMessage("�ý�ɫ�ѷ�����Ա�޷�����������");
                       return -1;

                  }

                  SQLStr = "select  * from  PTRoleRes where (RoleID='"+this.req.getFieldValue(i,"keycode")+"') ";
                 System.out.print(SQLStr);

                 this.rs = this.dc.executeQuery(SQLStr);

                 if (this.rs.next()){
                      this.res.setType(0);
                      this.res.setResult(false);
                      this.res.setMessage("�ý�ɫ�ѷ�����Դ�޷�����������");
                      return -1;

                 }
                 SQLStr = "update PTRole set RoleID='"+this.req.getFieldValue(i,"RoleID")+"' , RoleDesc='"+this.req.getFieldValue(i,"RoleDesc")+"'  where (RoleID='"+this.req.getFieldValue(i,"keycode")+"')";

                   System.out.print(SQLStr);
                   int retcount = this.dc.executeUpdate(SQLStr);

                   if (retcount <0){
                      this.res.setType(0);
                      this.res.setResult(false);
                      this.res.setMessage("���¼�¼ʧ��");
                      return retcount;
                 }
             }
        }

       this.res.setType(0);
       this.res.setResult(true);
       this.res.setMessage("���¼�¼�ɹ�");

       return 0;
   }


}
