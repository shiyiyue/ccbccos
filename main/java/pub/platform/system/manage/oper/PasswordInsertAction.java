package pub.platform.system.manage.oper;

import pub.auth.MD5Helper;
import pub.platform.form.control.Action;
import pub.platform.system.manage.dao.PtOperBean;

public class PasswordInsertAction extends Action {
     PtOperBean operbean = new PtOperBean();

     public int doBusiness() {
           
         //operbean.setOperpasswd(this.req.getFieldValue("newpwd"));
         operbean.setOperpasswd(MD5Helper.getMD5String(this.req.getFieldValue("newpwd")));

         this.res.setType(0);
           
           if (operbean.updateByWhere(" where (deptid='"+this.req.getFieldValue("deptid")+"')and(operid='"+this.req.getFieldValue("operid")+"')") <0){

             this.res.setResult(false);
             this.res.setMessage("��������ʧ�ܣ�");
             this.dc.close();
             return -1;
           }

         

          this.res.setResult(true);
          this.res.setMessage("��������ɹ���");
          this.dc.close();
          return 0;
     }


}
