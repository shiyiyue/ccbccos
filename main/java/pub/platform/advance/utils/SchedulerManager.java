package pub.platform.advance.utils;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import com.ccb.dao.SYSSCHEDULER;

public class SchedulerManager extends HttpServlet {

	static{
		System.out.println("////////////////////////////////////////");
		System.out.println("loading SchedulerManager.class");
		System.out.println("////////////////////////////////////////");
	}

    public static Scheduler scheduler;
    public static Map jobInfoMap = new HashMap();

	// ��ʼ��
    public void init() throws ServletException {
		//System.out.println();
		printLine();
		System.out.println("���ڼ��ص�����ҵ������Ϣ......");
		try{
			// ������ҵ��Ϣ			
			SchedulerManager.loadSchedulerInfo();
		}catch(Exception e){
			e.printStackTrace();
		}

		System.out.println("���ص�����ҵ������Ϣ������");
		printLine();
	}

	// ����
    public void destroy() {
		System.out.println();
		printLine();
		System.out.println("���ڹر����е�����ҵ����......");

		try{
			// �رյ�����ҵ
			SchedulerManager.shutdownScheduler();
		}catch(Exception e){
			e.printStackTrace();
		}

		System.out.println("�رյ�����ҵ������Ϣ������");
		printLine();
	}

	// ���¼�����ҵ��Ϣ
	public static void reload(){
		System.out.println();
		printLine();
		System.out.println("��ʼ���¼�����ҵ��Ϣ......");
		try {
			SchedulerManager.loadSchedulerInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("���¼�����ҵ��Ϣ������");
		printLine();
	}

	public static void shutdown(){
		System.out.println();
		printLine();
		System.out.println("��ʼֹͣ������ҵ......");
		try {
			SchedulerManager.shutdownScheduler();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("ֹͣ������ҵ������");
		printLine();
	}

	// ������ҵ��Ϣ
	private static synchronized void loadSchedulerInfo()throws Exception{

		SchedulerManager.shutdownScheduler();

		SchedulerFactory schedulerFactory = new StdSchedulerFactory();
               // System.out.println("ddd");
		SchedulerManager.scheduler = schedulerFactory.getScheduler();

		List list = SYSSCHEDULER.find("", true);


		for(int i=0; i<list.size(); i++){
			SYSSCHEDULER scheuler = (SYSSCHEDULER)list.get(i);
			JobInfo job = new JobInfo(scheuler);
		}

		SchedulerManager.scheduler.start();
	}

	// �رյ�����ҵ
	private static synchronized void shutdownScheduler()throws Exception{
		if(SchedulerManager.scheduler != null){
			SchedulerManager.scheduler.shutdown(false);
			SchedulerManager.jobInfoMap.clear();
		}
	}

    public void doPost(HttpServletRequest request, HttpServletResponse response)
    	throws ServletException, IOException {
    	doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
    	throws ServletException, IOException {
    	if("reload".equals(request.getParameter("action"))){
    		System.out.println("********  reload  *********");
    		SchedulerManager.reload();

    	}else if("shutdown".equals(request.getParameter("action"))){
    		System.out.println("********  shutdown  **********");
    		SchedulerManager.shutdown();
    	}

    	response.sendError(HttpServletResponse.SC_FORBIDDEN);
    }

	private static void printLine(){
		System.out.println("//////////////////////////" + new Date() + "//////////////////////////");
	}

}


