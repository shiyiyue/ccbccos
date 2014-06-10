package pub.platform.advance.utils;

public class TabPage
{
    private String Id;
    private String Title;
    private boolean useIFrame;
    private String Url;
    private boolean initLoad;
    private boolean autoUnload;

    /**
     * @return ���� autoUnload��
     */
    public boolean isAutoUnload()
    {
        return autoUnload;
    }

    /**
     * @param autoUnload Ҫ���õ� autoUnload��
     */
    public void setAutoUnload(boolean autoUnload)
    {
        this.autoUnload = autoUnload;
    }

    /**
     * @return ���� initLoad��
     */
    public boolean isInitLoad()
    {
        return initLoad;
    }

    /**
     * @param initLoad Ҫ���õ� initLoad��
     */
    public void setInitLoad(boolean initLoad)
    {
        this.initLoad = initLoad;
    }

    /**
     * @return ���� tabPageId��
     */
    public String getId()
    {
        return Id;
    }

    /**
     * @param tabPageId Ҫ���õ� tabPageId��
     */
    public void setId(String tabPageId)
    {
        this.Id = tabPageId;
    }

    /**
     * @return ���� url��
     */
    public String getUrl()
    {
        return Url;
    }

    /**
     * @param url Ҫ���õ� url��
     */
    public void setUrl(String url)
    {
        Url = url;
    }

    /**
     * @return ���� useIFrame��
     */
    public boolean isUseIFrame()
    {
        return useIFrame;
    }

    /**
     * @param useIFrame Ҫ���õ� useIFrame��
     */
    public void setUseIFrame(boolean useIFrame)
    {
        this.useIFrame = useIFrame;
    }

    /**
     * @return ���� tabTitle��
     */
    public String getTitle()
    {
        return Title;
    }

    /**
     * @param tabTitle Ҫ���õ� tabTitle��
     */
    public void setTitle(String tabTitle)
    {
        this.Title = tabTitle;
    }

    /**
     * ������һ��TabPage����
     * 
     * @param Id tabPage��ID����JS����п���ʹ�����ID���������TabPage
     * @param Title ����tabҳ����ʾ����
     * @param useIFrame �Ƿ�ʹ��IFrame�л�ҳ�档�����ʹ��IFrame����������������Ч
     * @param url ʹ��IFrame��ʱ�򣬱�Tabҳ��ʾ��ҳ��Url
     * @param initLoad ��ҳ���Ƿ���ҳ��򿪵�ʱ�����
     * @param autoUnload ��ҳ���Ƿ���ҳ���л����Զ�Unload��Unload����Խ�Լ�ͻ����ڴ棬�������ݲ����潫��ʧ��
     */
    public TabPage(String Id, String Title, boolean useIFrame, String url,
            boolean initLoad, boolean autoUnload)
    {
        super();
        this.Id = Id;
        this.Title = Title;
        this.useIFrame = useIFrame;
        Url = url;
        this.initLoad = initLoad;
        this.autoUnload = autoUnload;
    }

    /**
     * ȱʡ���캯��
     */
    public TabPage()
    {
        super();
        this.Id = null;
        this.Title = null;
        this.useIFrame = false;
        this.Url = null;
        this.initLoad = false;
        this.autoUnload = false;
    }

    public static void main(String[] args)
    {
        System.out.println("Hello World!");
    }

}