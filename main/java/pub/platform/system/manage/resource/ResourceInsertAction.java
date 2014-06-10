package pub.platform.system.manage.resource;

import pub.platform.form.control.Action;
import pub.platform.system.manage.dao.*;

public class ResourceInsertAction extends Action
{
    PtResourceBean  prresbean = new PtResourceBean();

     public int doBusiness() {

       for (int i=0; i<this.req.getRecorderCount();i++ ){

            prresbean.setResid(this.req.getFieldValue(i,"ResID"));
            prresbean.setParentresid(this.req.getFieldValue(i,"ParentResID"));
            prresbean.setRestype(this.req.getFieldValue(i,"ResType"));
            prresbean.setResname(this.req.getFieldValue(i,"ResName"));
            prresbean.setResdesc(this.req.getFieldValue(i,"ResDesc"));


             if (prresbean.insert() <0){
                 this.res.setType(0);
                 this.res.setResult(false);
                 this.res.setMessage("��Ӽ�¼ʧ��");
                 return -1;
             }
       }

       this.res.setType(0);
       this.res.setResult(true);
       this.res.setMessage("��Ӽ�¼�ɹ�");

       return 0;
   }

  public int editenum(){

       for (int i=0; i<this.req.getRecorderCount();i++ ){

               if (this.req.getFieldValue(i,"ResID").trim().toLowerCase().equals(this.req.getFieldValue(i,"keycode").trim().toLowerCase()))
               {



                     prresbean.setParentresid(this.req.getFieldValue(i,"ParentResID"));
                     prresbean.setRestype(this.req.getFieldValue(i,"ResType"));
                     prresbean.setResname(this.req.getFieldValue(i,"ResName"));
                     prresbean.setResdesc(this.req.getFieldValue(i,"ResDesc"));

                    if (prresbean.updateByWhere(" where (ResID='"+this.req.getFieldValue(i,"keycode")+"')") <0){
                       this.res.setType(0);
                       this.res.setResult(false);
                       this.res.setMessage("���¼�¼ʧ��");
                       return -1;
                    }
             }else{


                 String SQLStr = "select  * from  PTRoleRes where (ResID='"+this.req.getFieldValue(i,"keycode")+"') ";


                 this.rs = this.dc.executeQuery(SQLStr);

                 if (this.rs.next()){
                      this.res.setType(0);
                      this.res.setResult(false);
                      this.res.setMessage("�ý�ɫ�ѷ�����Դ�޷�����������");
                      return -1;

                 }

                    prresbean.setResid(this.req.getFieldValue(i,"ResID"));
                    prresbean.setParentresid(this.req.getFieldValue(i,"ParentResID"));
                    prresbean.setRestype(this.req.getFieldValue(i,"ResType"));
                    prresbean.setResname(this.req.getFieldValue(i,"ResName"));
                    prresbean.setResdesc(this.req.getFieldValue(i,"ResDesc"));

                   if (prresbean.updateByWhere(" where (ResID='"+this.req.getFieldValue(i,"keycode")+"')") <0){
                      this.res.setType(0);
                      this.res.setResult(false);
                      this.res.setMessage("���¼�¼ʧ��");
                      return -1;
                   }


             }
        }

       this.res.setType(0);
       this.res.setResult(true);
       this.res.setMessage("���¼�¼�ɹ�");

       return 0;


  }

  public int delenum(){

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
