/*
 * �������� 2005-1-24
 */
package pub.platform.advance.utils;

import java.util.Iterator;
import java.util.Vector;

/**
 * @author zhang
 */
/**
 * @author zhang
 */
public class TabPane
{
	//TabPane ID : ��Ӧ�����ɵ�DIV����id��ClassName="tab"��
	private String  Id;

	//JavaScript object Name����Ӧ��������JS�в����Ķ�����
	private String  objectName;

	//use Cookie?
	private boolean useCookie;

	//param url����������URL
	private String  paramUrl;

	//PageInfo������ҳ����Ϣ����������ҳ��
	private Vector  pageVector;

	//view? �Ƿ�ֻ�ǲ鿴ҳ�棿
	private boolean view;
	/**
	 * @param id tab����ID
	 * @param objectName �����ͻ�������������JS����ID
	 * @param useCookie �Ƿ�ʹ��Cookie���浱ǰҳ����Ϣ��һ�㲻�ã����false
	 */
	public TabPane(String id, String objectName, boolean useCookie)
	{
		super();
		Id = id;
		this.objectName = objectName;
		this.useCookie = useCookie;
		this.pageVector = new Vector();
		this.paramUrl = "";
		this.view=false;	//ȱʡ�ǲ鿴״̬
	}

	public TabPane(String id, String objectName, boolean useCookie, boolean view)
	{
		this(id, objectName, useCookie );
		this.view=view;
	}


	/**
	 * @return ���� Id��
	 */
	public String getId()
	{
		return Id;
	}

	/**
	 * @param Id Ҫ���õ� Id��
	 */
	public void setId(String Id)
	{
		this.Id = Id;
	}

	/**
	 * @return ���� objectName��
	 */
	public String getObjectName()
	{
		return objectName;
	}

	/**
	 * @param objectName Ҫ���õ� objectName��
	 */
	public void setObjectName(String objectName)
	{
		this.objectName = objectName;
	}

	/**
	 * @return ���� paramUrl��
	 */
	public String getParamUrl()
	{
		return paramUrl;
	}

	/**
	 * @param paramUrl Ҫ���õ� paramUrl��
	 */
	public void setParamUrl(String paramUrl)
	{
		this.paramUrl = paramUrl;
	}

	/**
	 * @return ���� useCookie��
	 */
	public boolean isUseCookie()
	{
		return useCookie;
	}

	/**
	 * @param useCookie Ҫ���õ� useCookie��
	 */
	public void setUseCookie(boolean useCookie)
	{
		this.useCookie = useCookie;
	}

	/**
	 * ����һ��Tabҳ
	 *
	 * @param tabPageId ����tabҳ��ID����JS�����п���ʹ�ø�ID����TABҳ
	 * @param tabTitle ����tabҳ����ʾ����
	 * @param useIFrame �Ƿ�ʹ��IFrame�л�ҳ�档�����ʹ��IFrame����������������Ч
	 * @param Url ʹ��IFrame��ʱ�򣬱�Tabҳ��ʾ��ҳ��Url
	 * @param initLoad ��ҳ���Ƿ���ҳ��򿪵�ʱ�����
	 * @param autoUnload ��ҳ���Ƿ���ҳ���л����Զ�Unload��Unload����Խ�Լ�ͻ����ڴ棬�������ݲ����潫��ʧ��
	 */
	public void addPage(String tabPageId, String tabTitle, boolean useIFrame,
			String Url, boolean initLoad, boolean autoUnload)
	{
		if (pageVector != null)
		{
			TabPage tabPage = new TabPage(tabPageId, tabTitle, useIFrame, Url,
					initLoad, autoUnload);
			pageVector.add(tabPage);
		}
	}

	/**
	 * ����HTML����
	 *
	 * @return ���ɵ�HTML���룬д��ҳ����
	 */
	public String getTabPane()
	{
		StringBuffer strBuf = new StringBuffer();
		//TabPane��Ϣ
		strBuf.append("<div class=\"tabContainer\" id=\""+this.Id+"Container\">\n");
		strBuf.append("	<div class = \"tab-pane\" id = \"" + this.Id + "\">\n");
		strBuf.append("		<SCRIPT LANGUAGE = \"JavaScript\">\n");
		strBuf.append("			"+this.objectName+" = new WebFXTabPane(document.getElementById(\""+this.Id+"\"), "+this.isUseCookie()+", "+this.isView()+");\n");
		strBuf.append("			"+this.objectName+".setParamUrl( \"" + paramUrl + "\");\n");
		strBuf.append("		</SCRIPT>\n");
		strBuf.append("	<div class=\"tabContentContainer\" id=\""+this.Id+"ContentContainer\"><marquee id="+this.Id+"__message isvisible=1  scrollamount=3 behavior=alternate direction=right"+
			" style=\"position: absolute; visibility: visible; width:100%; height: 22; background-color: #d4d0c8; font-size: 12; border: 1 solid silver; padding-top:3; z-index: 10000; filter:alpha(opacity=70)\">���ڶ�ȡ���ݣ����Ժ򡭡�</marquee>\n");
		if (pageVector != null)
		{
			Iterator it = pageVector.iterator();
			while (it.hasNext())
			{
				TabPage tabPage = (TabPage) it.next();
				strBuf
						.append("		<div class = \"tab-page\" id = \""
								+ tabPage.getId() + "\" url=\""
								+ tabPage.getUrl() + "\" initLoad=\""
								+ tabPage.isInitLoad() + "\" autoUnload=\""
								+ tabPage.isAutoUnload() + "\">\n");
				strBuf.append("			<font class = \"tab\">" + tabPage.getTitle()
						+ "</font>\n");
				strBuf.append("			<script type = \"text/javascript\">\n");
				strBuf.append("				" + this.getObjectName()
						+ ".addTabPage(document.getElementById(\""
						+ tabPage.getId() + "\"));\n");
				strBuf.append("			</script>\n");
				strBuf.append("			<IFRAME SRC = \"\" NAME = \"frame_"
						+ tabPage.getId() + "\" id = \"frame_"
						+ tabPage.getId()
						+ "\" class=\"tabPageFrame\"></IFRAME>\n");
//                        + "\" class=\"tabPageFrame\" frameBorder=\"0\"></IFRAME>\n");
				strBuf.append("		</div>\n");
			}
		}
		strBuf.append("	</div>\n");
		strBuf.append("	</div>\n");
		strBuf.append("</div>");
		pageVector.clear();
		pageVector=null;
		return strBuf.toString();
	}

	/**
	 * �����õĴ���
	 *
	 * @param args
	 */
	public static void main(String[] args)
	{
		TabPane tabPane = new TabPane("tab", "tabPane1", false);
		tabPane.addPage("jb", "������Ϣ", true, "jb.html", false, true);
		tabPane.addPage("sp", "�����ļ�", true, "sp.html", false, true);
		tabPane.addPage("fzr", "���˴���", true, "fzr.html", false, true);
		tabPane.addPage("fr", "���˹ɶ�", true, "fr.html", false, true);
		tabPane.addPage("zr", "��Ȼ�˹ɶ�", true, "zr.html", false, true);
		tabPane.addPage("yj", "��Ա���", true, "yj.html", false, true);
		System.out.println(tabPane.getTabPane());
	}

	public boolean isView()
	{
		return view;
	}

	public void setView(boolean view)
	{
		this.view = view;
	}
}