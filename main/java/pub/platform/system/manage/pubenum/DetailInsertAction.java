package pub.platform.system.manage.pubenum;

import pub.platform.form.control.Action;
import pub.platform.system.manage.dao.*;

public class DetailInsertAction extends Action
{
    PtEnuDetailBean ptdetai = new PtEnuDetailBean();

     public int doBusiness() {

          return 0;
   }

    public int addenum(){

         for (int i=0; i<this.req.getRecorderCount();i++ ){
                ptdetai.setEnutype(this.req.getFieldValue(i,"EnuType"));
                ptdetai.setEnuitemvalue(this.req.getFieldValue(i,"EnuItemValue"));
                ptdetai.setEnuitemlabel(this.req.getFieldValue(i,"Enuitemlabel"));
                ptdetai.setEnuitemdesc(this.req.getFieldValue(i,"EnuItemDesc"));
                ptdetai.setEnuitemexpand(this.req.getFieldValue(i,"enuitemexpand"));
                try{
                if (this.req.getFieldValue(i,"dispno") != null ){
                     ptdetai.setDispno(Integer.parseInt(this.req.getFieldValue(i,"dispno")));
                }
                }catch(Exception e){

                }

                 if (ptdetai.insert() <0){
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


    public int editenum(){

         for (int i=0; i<this.req.getRecorderCount();i++ ){


                    ptdetai.setEnutype(this.req.getFieldValue(i,"EnuType"));
                    ptdetai.setEnuitemvalue(this.req.getFieldValue(i,"EnuItemValue"));
                    ptdetai.setEnuitemlabel(this.req.getFieldValue(i,"Enuitemlabel"));
                    ptdetai.setEnuitemdesc(this.req.getFieldValue(i,"EnuItemDesc"));
                    ptdetai.setEnuitemexpand(this.req.getFieldValue(i,"enuitemexpand"));
                    try{
                          if (this.req.getFieldValue(i,"dispno") != null ){
                               ptdetai.setDispno(Integer.parseInt(this.req.getFieldValue(i,"dispno")));
                          }
                     }catch(Exception e){

                     }


                  if (ptdetai.updateByWhere("where (EnuType='"+this.req.getFieldValue(i,"EnuType")+"')and(EnuItemValue='"+this.req.getFieldValue(i,"keycode")+"')") <0){
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
    public int delenum(){
         for (int i=0; i<this.req.getRecorderCount();i++ ){
              String SQLStr = "delete from ptEnuDetail where (EnuType='"+this.req.getFieldValue(i,"EnuType")+"')and(EnuItemValue='"+this.req.getFieldValue(i,"keycode")+"')";
              int retcount =this.dc.executeUpdate(SQLStr);
              if (retcount <0){
               this.res.setType(0);
               this.res.setResult(false);
               this.res.setMessage("ɾ����¼ʧ��");
               return retcount;
              }
         }

         //��������ö������
         pub.platform.form.config.EnumerationType.reload();

         this.res.setType(0);
         this.res.setResult(true);
          this.res.setMessage("ɾ����¼�ɹ�");

          return 0;

    }
}
