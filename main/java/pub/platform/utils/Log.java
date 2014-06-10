package pub.platform.utils;

import org.apache.log4j.*;
import java.io.InputStream;
import java.io.*;
import java.util.*;

/**
 * <p>Title: �������</p>
 * <p>Description: ��־</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: �ൺ����Ƽ�</p>
 * @author �����
 * @version 1.0
 */

public  class Log {

    public static final int OFF = 0;
    public static final int FATAL = 1;
    public static final int ERROR = 2;
    public static final int WARN = 3;
    public static final int INFO = 4;
    public static final int DEBUG = 5;
    public static final int ALL = 6;

    private static Logger logger = null;

    private static final String  fileName="/log4j.properties";



    private void getlog(){
        //��ʼ��Log4j������Ϣ
        try {
           //���������ļ�������
           InputStream input = getClass().getResourceAsStream(fileName);

           Properties props = new Properties();

            props.load(input);
            input.close();

            PropertyConfigurator.configure(props);
            logger = Logger.getRootLogger();


       } catch (Exception ex) {
           // ������־�ļ�����ʱ��������־�������־���
           if (fileName.indexOf("log") > 0) {
               System.out.println("��ȡ" + fileName + "�����ļ�����");
           } else {
               getLogger().error("��ȡ" + fileName + "�����ļ�����", ex);
           }
       }

    }

    /**
     * ��ȡ��־
     * @return
     */
    public  Logger getLogger() {
         if (logger ==null)
              getlog();

        return logger;
    }

    /**
     * ��ȡ��־����
     * @return
     */
    public static String getLogLevel() {
        return logger.getLevel().toString();
    }


    /**
     * ������־����
     * @param level
     */
    public static void setLevel(int level) {
        switch (level) {

        case OFF:
            logger.setLevel(Level.OFF);
            break;

        case FATAL:
            logger.setLevel(Level.FATAL);
            break;

        case ERROR:
            logger.setLevel(Level.ERROR);
            break;

        case WARN:
            logger.setLevel(Level.WARN);
            break;

        case INFO:
            logger.setLevel(Level.INFO);
            break;

        case DEBUG:
            logger.setLevel(Level.DEBUG);
            break;

        case ALL:
            logger.setLevel(Level.ALL);
            break;

        default:
            break;
        }
    }


}
