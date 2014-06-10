package pub.platform.system.manage.button;

import pub.platform.form.control.Action;
import pub.platform.system.manage.dao.*;
import pub.platform.utils.*;

public class ParentButtonAction extends Action
{

      PtButtonBean ptbut = new PtButtonBean();
     PtResourceBean ptresBean = new PtResourceBean();

     public int doBusiness() {

          String maxCoun ="1";


             for (int i=0; i<this.req.getRecorderCount();i++ ){


                  ////////////////����˵�����
                  maxCoun = Util.getFieldMax(this.dc,"BUTTONINDEX","ptbutton");

                  ptbut.setButtonindex(Integer.parseInt(maxCoun));
                  ptbut.setButtonid("modulebuttonstype");
                  ptbut.setButtondesc(this.req.getFieldValue(i,"buttondesc"));
                  ptbut.setButtonlabel(this.req.getFieldValue(i,"buttonlab"));
                  ptbut.setParentbuttonid(this.req.getFieldValue(i,"parentbuttonid"));



                   if (ptbut.insert() <0){
                       this.res.setType(0);
                       this.res.setResult(false);
                       this.res.setMessage("���Buttonʧ��");
                       return -1;
                   }


                    /////////////////�����Դ��Ϣ
                     ptresBean.setResid("b"+maxCoun);
                     ptresBean.setResdesc(this.req.getFieldValue("buttonlab"));
                     ptresBean.setParentresid("b"+this.req.getFieldValue("parentbuttonid"));
                     ptresBean.setResname(maxCoun);
                     ptresBean.setRestype("5");

                     if (ptresBean.insert() <0){
                         this.res.setType(0);
                         this.res.setResult(false);
                         this.res.setMessage("�����Դʧ��");
                         return -1;
                    }

             }

             this.res.setType(0);
             this.res.setResult(true);
             this.res.setMessage("��Ӽ�¼�ɹ�");

             return 0;
    }

    ///////////////�޸�
    public int editenum(){

           for (int i=0; i<this.req.getRecorderCount();i++ ){
                ptbut.setButtondesc(this.req.getFieldValue(i,"buttondesc"));
                ptbut.setButtonlabel(this.req.getFieldValue(i,"buttonlab"));


                   if (ptbut.updateByWhere(" where (buttonindex='"+this.req.getFieldValue(i,"keycode")+"')") <0){
                        this.res.setType(0);
                        this.res.setResult(false);
                        this.res.setMessage("���¼�¼ʧ��");
                        return -1;
                   }

                    /////////////////������Դ��Ϣ
                     ptresBean.setResdesc(this.req.getFieldValue(i,"menulabel"));
                     ptresBean.setParentresid("b"+this.req.getFieldValue(i,"parentmenuid"));

                     if (ptresBean.updateByWhere(" where (ResID='b"+this.req.getFieldValue(i,"keycode")+"')") <0){
                          this.res.setType(0);
                          this.res.setResult(false);
                          this.res.setMessage("������Դʧ��");
                          return -1;
                     }

        }

        this.res.setType(0);
        this.res.setResult(true);
        this.res.setMessage("���¼�¼�ɹ�");

        return 0;
    }

    //////////////ɾ��
    public int delenum(){
         for (int i=0; i<this.req.getRecorderCount();i++ ){

               //////////////ɾ����ɫ��Դ��ϵ
             this.dc.executeUpdate(" delete from  PTRoleRes where (ResID='b"+this.req.getFieldValue(i,"keycode")+"') ");

                ///////////////ɾ����Դ
             this.dc.executeUpdate(" delete from  ptresource where (ResID='b"+this.req.getFieldValue(i,"keycode")+"') ");


               //////////////////ɾ���˵�
              String SQLStr = "delete from PTButton where (buttonindex='"+this.req.getFieldValue(i,"keycode")+"')";
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
    ///////////////////////ѡ��ģ����Ϣ
    public int getbuttons(){




          String SQLStr = "select  buttonindex,Buttonlabel,Buttondesc,(select  count(buttonindex) as a from ptbutton   where (buttonid ='modulebuttonstype' ) and Parentbuttonid=me.buttonid) as menucount from ptbutton  me where (buttonid='modulebuttonstype') and Parentbuttonid='" +
                      this.req.getFieldValue("parentid") + "'";

          this.rs = this.dc.executeQuery(SQLStr);

          this.res.setFieldName("buttonindex;Buttonlabel;buttondesc;menucount");
          this.res.setFieldType("text;text;text;text");
          this.res.setEnumType("0;0;0;0");
          this.res.setType(1);

          if (this.rs == null) {
               this.res.setResult(false);
               this.res.setMessage("�޷�����Ϣ��¼");
               return -1;

          } else {
               this.res.setResult(true);
               this.res.setRecordset(this.rs);
          }

          return 0;
     }



 }
