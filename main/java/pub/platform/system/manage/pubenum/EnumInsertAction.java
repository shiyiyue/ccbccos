package pub.platform.system.manage.pubenum;

import pub.platform.form.control.Action;
import pub.platform.system.manage.dao.*;

public class EnumInsertAction extends Action
{
    PtEnuMainBean ptmainbean = new PtEnuMainBean();
     public int doBusiness() {
       return 0;
     }

   //////////////���һ��ö��
   public int addenum(){


          for (int i=0; i<this.req.getRecorderCount();i++ ){

                  ptmainbean.setEnutype(this.req.getFieldValue(i,"EnuType"));
                  ptmainbean.setEnuname(this.req.getFieldValue(i,"EnuName"));
                  ptmainbean.setValuetype(this.req.getFieldValue(i,"ValueType"));
                  ptmainbean.setEnudesc(this.req.getFieldValue(i,"EnuDesc"));


                if (ptmainbean.insert() <0){
                    this.res.setType(0);
                    this.res.setResult(false);
                    this.res.setMessage("��Ӽ�¼ʧ��");
                    return -1;
                }
          }

          //��������ö������
          pub.platform.form.config.EnumerationType.reload();

          this.res.setType(0);
          this.res.setResult(true);
          this.res.setMessage("��Ӽ�¼�ɹ�");

          return 0;

   }

   /////////////�޸�һ��ö��
   public int editenum(){
        for (int i=0; i<this.req.getRecorderCount();i++ ){


              ptmainbean.setEnutype(this.req.getFieldValue(i,"EnuType"));
              ptmainbean.setEnuname(this.req.getFieldValue(i,"EnuName"));
              ptmainbean.setValuetype(this.req.getFieldValue(i,"ValueType"));
              ptmainbean.setEnudesc(this.req.getFieldValue(i,"EnuDesc"));



             if (ptmainbean.updateByWhere(" where (EnuType='"+this.req.getFieldValue(i,"keycode")+"')") <0){
                  this.res.setType(0);
                  this.res.setResult(false);
                  this.res.setMessage("���¼�¼ʧ��");
                  return -1;
             }
       }

       //��������ö������
       pub.platform.form.config.EnumerationType.reload();

       this.res.setType(0);
       this.res.setResult(true);
       this.res.setMessage("���¼�¼�ɹ�");

       return 0;

   }
   ///////////////////ɾ��һ��ö��
   public int delenum(){
        String SQLStr = "select  EnuType from  ptEnuDetail where (EnuType='"+this.req.getFieldValue("keycode")+"') ";


        this.rs = this.dc.executeQuery(SQLStr);

        if (!this.rs.next()){
             SQLStr = "delete from ptEnuMain where (EnuType='"+this.req.getFieldValue("keycode")+"')";
             int retcount =this.dc.executeUpdate(SQLStr);
             if (retcount <0){
                  this.res.setType(0);
                  this.res.setResult(false);
                  this.res.setMessage("ɾ����¼ʧ��");
                  return retcount;
             } else {

                  //��������ö������
                  pub.platform.form.config.EnumerationType.reload();

                  this.res.setType(0);
                  this.res.setResult(true);
                  this.res.setMessage("ɾ����¼�ɹ�");
                  return 0;
             }
        }else{
             this.res.setType(0);
             this.res.setResult(false);
             this.res.setMessage("�����¼�ö����Ϣ");
             return -1;

        }

   }


}
