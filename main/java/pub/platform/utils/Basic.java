package pub.platform.utils;

import java.util.*;

import pub.platform.advance.utils.PropertyManager;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class Basic
{
    private static String Db_Type = null;
	
	public Basic()
    {
    }

    //�ַ����ļ��뷽�������ַ�������ĳ�ָ�ʽת����ʮ�����Ƶ��ַ���
    public static String encode(String b)
    {
        if ( b == null ) {
             return b;
        }
        String s        = "";
        String temp     = "";
        String flag     = "_";

        int    asciiNum = 0;
        int    n        = 0;
        if ((b ==null)||(b.equals("")))
             return "";

        char[] by=b.toCharArray();

        while (n < by.length)
        {

           asciiNum = by[n];
           temp = Long.toHexString(asciiNum);


           if (asciiNum > 255 ||(asciiNum <0))
           {

               temp = fillLeftWithZero(temp,4);
               if (flag.equals( "_" ))
               {
                   flag = "#";
                   s += "[#" + temp ;
                }
                else if (flag.equals("~"))
                {
                    flag = "#";
                    s += "#" + temp;
                }
                else if (flag.equals("#"))
                {
                    s += temp ;
                }
            }
            else if ((asciiNum < 48) || (asciiNum > 57&&asciiNum < 65)||(asciiNum > 90&&asciiNum < 97)||(asciiNum >122))
            {
                temp = fillLeftWithZero(temp,2);

                if (flag.equals("_"))
                {
                    flag = "~";
                    s += "[~" + temp;
                }
                else if (flag.equals("~"))
                {
                    s += temp;
                }
                else if (flag.equals("#"))
                {
                    flag = "~";
                    s += "~" + temp;
                }

            }
            else
            {
                if ((flag.equals("#"))||(flag.equals("~")))
                {
                    flag = "_" ;
                    s += "]" + (char)asciiNum;
                }
                else
                {
                    s += (char)asciiNum;
                }

            }

            n ++;
        }

        return s;
    }

        //��ĳ�ָ�ʽ��ʮ�����Ƶ��ַ���ת������ͨ���ַ���
    public static String decode(String Str){
        try {
            String temp = "" ;
            String OutStr = "";


            String flag = "_";

            int    asciiNum = 0;
            int n = 0;

            if ((Str ==null)||(Str.equals("")))
             return "";

            for( int i=0; i< Str.length();i++)
            {
                temp = Str.substring(i,i+1);

                if (temp.equals("[") )
                {
                   i ++;
                   temp = Str.substring(i,i+1);

                }
                if (temp.equals("]"))
                {
                    flag = "_";
                    continue;

                }
                if (temp.equals("~"))
                {
                    flag = "~";

                    i ++;

                }
                if (temp.equals("#"))
                {
                    flag = "#";

                    i ++;

                }

                if (flag.equals("~"))
                {
                        temp=Str.substring(i,i+2);
                        asciiNum= Integer.parseInt(temp,16);
                        OutStr += String.valueOf( (char)asciiNum );
                        i++;
                }

                if (flag.equals("#"))
                {
                        temp=Str.substring(i,i+4);
                        if (temp.toUpperCase().equals("FFFF")){
                            i+=4;
                            temp=Str.substring(i,i+4);
                        }
                        asciiNum= Integer.parseInt(temp,16);
                        OutStr +=String.valueOf( (char)asciiNum );
                        i+=3;

                }
                if (flag.equals("_")){
                        OutStr +=temp ;

                }


            }

            return OutStr;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return  e.getMessage();
        }
    }

    ///�ַ�������߲�0
    public static String fillLeftWithZero(String inStr,int len){
        int count= inStr.length();
        String outStr = inStr;
        while(count <len){
            outStr ="0"+ outStr;
            count= outStr.length();
        }
        return outStr;
    }

    //��ĳ���ַ������ݰ������ض��ַ����ֳ��ַ�������
    public static String[] splite(String spliteStr,String subStr){

        int count = subStr.length(); //ȡ�÷ָ��ַ����ĳ��ȣ�

        int i = 100;                  //�ָ��ַ����ڱ��ָ��ַ�����λ��

       String SpStr = spliteStr;     //���ָ���ַ���
       String[] returnStr;           // =new String[100];//=new String[]{"dd","ewew","sdfsd"} ;          //�ָ����ַ�������

       Vector  StrVec = new Vector();

       while(i>0){
           i =spliteStr.indexOf(subStr);

           if (i<0)
               break;

            StrVec.add(spliteStr.substring(0,i));

            spliteStr= spliteStr.substring(i+count);
        }

        StrVec.add(spliteStr);

        returnStr = new String[StrVec.size()];

        for (int j=0 ;j < StrVec.size();j++)
            returnStr[j] =StrVec.get(j).toString();


        return returnStr;
    }
    
    //��ȫ��ר����
    public static String ABCToDBC(String changeStr)
    {
    	char[] by=changeStr.toCharArray();
    	int charNum = 0;
    	
    	String retStr ="";
    	
    	for (int i=0 ; i< by.length; i++)
    	{
    		charNum = by[i];
    		
    		if (charNum > 65280 && charNum < 65375)
    		{
    			charNum = charNum - 65248;
    		}
    		if (charNum == 183)
    			charNum = 46;
    		
    		retStr += String.valueOf( (char)charNum);
    	}
    	
    	return retStr;
    }

    //html��ʽת��
    public static String htmEncode( String s )
    {
        StringBuffer stringbuffer = new StringBuffer();
        int j = s.length();
        for ( int i = 0; i < j; i++ )
        {
            char c = s.charAt( i );
            switch ( c )
            {
                case 60: // '<'
                    stringbuffer.append( "&lt;" );
                    break;

                case 62: // '>'
                    stringbuffer.append( "&gt;" );
                    break;

                case 38: // '&'
                    stringbuffer.append( "&amp;" );
                    break;

                case 34: // '"'
                    stringbuffer.append( "&quot;" );
                    break;

                case 169:
                    stringbuffer.append( "&copy;" );
                    break;

                case 174:
                    stringbuffer.append( "&reg;" );
                    break;

                case 165:
                    stringbuffer.append( "&yen;" );
                    break;

                case 8364:
                    stringbuffer.append( "&euro;" );
                    break;

                case 8482:
                    stringbuffer.append( "&#153;" );
                    break;

                case 13: // '\r'
                    if ( i < j - 1 && s.charAt( i + 1 ) == '\n' )
                    {
                        stringbuffer.append( "<br>" );
                        i++;
                    }
                    break;

                case 32: // ' '
                    if ( i < j - 1 && s.charAt( i + 1 ) == ' ' )
                    {
                        stringbuffer.append( " &nbsp;" );
                        i++;
                        break;
                    }
                    // fall through

                default:
                    stringbuffer.append( c );
                    break;
            }
        }

        return new String( stringbuffer.toString() );
    }
    
    
    public static String getDbType()
    {
    	if (Db_Type == null)
    	{	
    		if (PropertyManager.getProperty("DB_TYPE")== null)
    			Db_Type = "ORCALE";
    		else
    			Db_Type = PropertyManager.getProperty("DB_TYPE");
    	}
    	
    	return Db_Type;
    		
    }
    
    
    

/*
    /// <summary>
    /// ��ʽһ�����봮Ϊ�����ڸ�ʽ
    /// </summary>
    /// <param name="InputDate">���봮</param>
    /// <returns>�����ڸ�ʽ��</returns>
    public static String FormatDate(String InputDate){
      Date date= new Date(InputDate);
      return FormatDate(date);
    }

    /// <summary>
    /// ��ʽһ������ DateTime Ϊ�����ڸ�ʽ
    /// </summary>
    /// <param name="InputDate">���� DateTime</param>
    /// <returns>�����ڸ�ʽ��</returns>
    public static String FormatDate(Date InputDate)
    {
       return Integer.toString(InputDate.getYear()) + "/" + Integer.toString(InputDate.getMonth()) + "/" + Integer.toString(InputDate.getDay());
      }

      /// <summary>
      /// ��ʽһ�����봮Ϊ��ʱ���ʽ
      /// </summary>
      /// <param name="InputTime">���봮</param>
      /// <returns>��ʱ���ʽ��</returns>
      public static String FormatTime(String InputTime){
        Date date= new Date(InputTime);
        return FormatTime(date);
      }

      /// <summary>
      /// ��ʽһ������ DateTime Ϊ��ʱ���ʽ
      /// </summary>
      /// <param name="InputTime">���� DateTime</param>
      /// <returns>��ʱ���ʽ��</returns>
      public static String FormatTime(Date InputTime){
        return InputTime.getHours() + ":" + InputTime.getMinutes() + ":" + InputTime.getSeconds();
      }

      /// <summary>
      /// ��ʽһ�����봮Ϊ������ʱ���ʽ
      /// </summary>
      /// <param name="InputDateTime">���봮</param>
      /// <returns>������ʱ���ʽ��</returns>
      public static String FormatDateTime(String InputDateTime)
      {
        Date date= new Date(InputDateTime);
        return FormatTime(date);

      }

      /// <summary>
      /// ��ʽһ������ DateTime Ϊ������ʱ���ʽ
      /// </summary>
      /// <param name="InputDateTime">���� DateTime</param>
      /// <returns>������ʱ���ʽ��</returns>
      public static String FormatDateTime(Date InputDateTime)
      {
        return FormatDate(InputDateTime) + " " + FormatTime(InputDateTime);
      }
<<<<<<< Basic.java
*/
}
