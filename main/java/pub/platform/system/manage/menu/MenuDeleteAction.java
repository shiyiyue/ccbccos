package pub.platform.system.manage.menu;

import pub.platform.form.control.Action;


public class MenuDeleteAction extends Action
{
    public int doBusiness() {
         for (int i=0; i<this.req.getRecorderCount();i++ ){

              //////////////ɾ����ɫ��Դ��ϵ
            this.dc.executeUpdate(" delete from  PTRoleRes where (ResID='m"+this.req.getFieldValue(i,"keycode")+"') ");

               ///////////////ɾ����Դ
            this.dc.executeUpdate(" delete from  ptresource where (ResID='m"+this.req.getFieldValue(i,"keycode")+"') ");


              //////////////////ɾ���˵�
             String SQLStr = "delete from PTMenu where (MenuID='"+this.req.getFieldValue(i,"keycode")+"')";
             int retcount =this.dc.executeUpdate(SQLStr);
             if (retcount <0){
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
