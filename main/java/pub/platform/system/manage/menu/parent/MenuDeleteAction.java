package pub.platform.system.manage.menu.parent;

import pub.platform.form.control.Action;
import pub.platform.system.manage.dao.*;


public class MenuDeleteAction extends Action
{

    PtMenuBean  ptmenu = new PtMenuBean();

     public int doBusiness() {
         for (int i=0; i<this.req.getRecorderCount();i++ ){
              String SQLStr = "select  MenuID from  PTMenu where (ParentMenuID='"+this.req.getFieldValue(i,"menuid")+"') ";

              this.rs = this.dc.executeQuery(SQLStr);

              if (!this.rs.next()){

                   //////////////ɾ����ɫ��Դ��ϵ
                   this.dc.executeUpdate(" delete from  PTRoleRes where (ResID='m"+this.req.getFieldValue(i,"menuid")+"') ");

                    ///////////////ɾ����Դ
                    this.dc.executeUpdate(" delete from  ptresource where (ResID='m"+this.req.getFieldValue(i,"menuid")+"') ");

                   /////////////////////ɾ���˵�
                    if ( ptmenu.deleteByWhere("where (MenuID='"+this.req.getFieldValue(i,"menuid")+"')") <0){
                        this.res.setType(0);
                        this.res.setResult(false);
                        this.res.setMessage("ɾ����¼ʧ��");
                        return -1;
                  }
             }else{
                  this.res.setType(0);
                  this.res.setResult(false);
                  this.res.setMessage("�����¼��˵���Ϣ");
                  return -1;

             }
         }
         this.res.setType(0);
         this.res.setResult(true);
         this.res.setMessage("ɾ����¼�ɹ�");
         return 0;

    }

}
