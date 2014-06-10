package pub.platform.utils;


import pub.platform.system.manage.dao.*;

public class LogManager {


     public static final String  ERR_TYPE_HINT = "0";///////��Ϣ��ʾ
     public static final String  ERR_TYPE_COMMON = "1";//////��ͨ������Ϣ
     public static final String  ERR_TYPE_BAD = "2";////////// ���ش���



     private Log log;

     private String moudleID;
     private String errorType;
     private String errorindex;
     private Exception exp;
     private String message;
     private boolean isWriteLog =false;




     public LogManager(String moudleID,String errorType,String errorindex,boolean isWriteLog,Exception exp) {

           log  = new Log();

          setMessage(moudleID,errorType,errorindex,isWriteLog,exp);

    }

     public LogManager(String moudleID,String errorType,String errorindex,boolean isWriteLog) {
         log  = new Log();

          setMessage(moudleID,errorType,errorindex,isWriteLog);
     }

     public LogManager(String moudleID,String errorType,String errorindex) {

          log  = new Log();

          setMessage(moudleID,errorType,errorindex);

     }

     public LogManager() {
          log  = new Log();
     }


     public void setMessage(String moudleID,String errorType,String errorindex,boolean isWriteLog,Exception exp){


          this.exp = exp;

          setMessage(moudleID,errorType,errorindex,isWriteLog);
     }

     public void setMessage(String moudleID,String errorType,String errorindex,boolean isWriteLog){

          this.isWriteLog =isWriteLog;

           setMessage(moudleID,errorType,errorindex);

     }

     public void setMessage(String moudleID,String errorType,String errorindex){
         this.moudleID =moudleID;
         this.errorType =errorType;
         this.errorindex =errorindex;

         getMessage();
    }


     public void writeLog(){
          if( message == null || message.equals(""))
               return;

          if (exp ==null)
               log.getLogger().error(message);
          else
               log.getLogger().error(message,exp);

     }


     public String getMessage(){
          if (moudleID == null && moudleID.equals(""))
              message ="û��ģ���ţ�";
          else if (errorType == null && errorType.equals(""))
              message ="û���������ͣ�";
          else if (errorindex == null && errorindex.equals(""))
              message ="û��������룡";
          else{
               PterrorBean pterror = new PterrorBean();

               pterror = (PterrorBean)pterror.findFirstByWhere(" where MOUDLEID = '"+moudleID+"' and ERRORTYPE= '"+errorType+"' and ERRORINDEX ='"+errorindex+"'");

               if (pterror != null)
                    message = pterror.getMessage();
               else
                    message ="������Ϣû�ж��壡";

          }



          ////////////////////д��־
         if (isWriteLog)
              writeLog();






           if (errorType.equals(ERR_TYPE_HINT)){

                return message ;

          }else if (errorType.equals(ERR_TYPE_COMMON)||errorType.equals(ERR_TYPE_BAD)){

               StringBuffer  StrBuf = new StringBuffer();

               StrBuf.append("<error ");

               StrBuf.append(" moudleid =\"" + moudleID + "\"");
               StrBuf.append(" errortype =\"" + errorType + "\"");

               StrBuf.append(" errorindex =\"" + errorindex + "\"");
               StrBuf.append(" message =\"" + message + "\"");
               StrBuf.append("  > ");
               
               if (exp !=null){
               	  StrBuf.append("<trace>  " + exp.getMessage()+" </trace>");
                    for (int i=0; i< exp.getStackTrace().length; i++){
                          StrBuf.append("<trace>  " + exp.getStackTrace()[i].toString()+" </trace>");
                    }
               }

                StrBuf.append("</error>");

                return StrBuf.toString();

          }else{
               return message ;

          }



     }


      public String getErrorType(){
           return this.errorType;
      }
}
