

function onUserDocumentLoad()
{

		var domdoc = createDomDocument();

		var retStr = createselectArr("parentdeptid","text","0","sm0084");

		if (retStr != false)
		{

			domdoc.loadXML(retStr);

			var Root = domdoc.documentElement;

			treeInit();
			Nodes_clear();

			var nRoot = vNodes.add("", "", "qq", "�������ά��", "/images/domain.gif");

			nRoot.xData.xNode = new Object();
			nRoot.xData.xNode.appID = "qq";

			nRoot.xData.xNode.childCount = 0;
			nRoot.xData.waitforLoad = false;

              var nApp = nRoot.add("tvwChild", "GGMK", "����", "/images/application.gif");

               nApp.xData.xNode = new Object();
               nApp.xData.xNode.appID = "qq";

               nApp.xData.codeName = "����";

               loadChildren(nApp);


			if (Root.childNodes.length >0){
				showApplications(Root.firstChild, nRoot);
			}
			nRoot.setExpanded(true);

		}
		domdoc.free;

}

function showApplications(eleRoot, nFather, bManagedApp)
{
	var node = eleRoot;
	var count = 0;

	while(node)
	{
		var nApp = nFather.add("tvwChild", decode(getAttrValue(node.childNodes[0],"value")), decode(getAttrValue(node.childNodes[1],"value")), "/images/application.gif");

		nApp.xData.xNode = new Object();
		nApp.xData.xNode.appID = "qq";
		nApp.span.title = decode(getAttrValue(node.childNodes[2],"value"));

		nApp.xData.codeName = decode(getAttrValue(node.childNodes[1],"value"));

          nApp.xData.waitforLoad = true;

          nApp.add("tvwChild", "", "������...");

		node = node.nextSibling;
		count = count +1;


	}



}


function tvNodeExpand(){
	try
	{
		var n = document.node;

		if (n.xData.waitforLoad)
			loadChildren(n);
	}
	catch(e)
	{

	}
}
///װ�������ӽڵ�
function loadChildren(n)
{
	try{


                  n.xData.waitforLoad = false;
			    n.removeChildren();

          		var nApp = n.add("tvwChild", "0", "��ʾ", "/images/application.gif");

				nApp.xData.xNode = new Object();
				nApp.xData.xNode.appID = "1";
                    nApp.xData.xNode.parentkey=n.key;

				nApp.xData.codeName = "��ʾ";


                    var nApp = n.add("tvwChild", "1", "��ͨ", "/images/application.gif");

				nApp.xData.xNode = new Object();
				nApp.xData.xNode.appID = "1";
                    nApp.xData.xNode.parentkey=n.key;
				nApp.xData.codeName = "��ͨ";


                    var nApp = n.add("tvwChild", "2", "����", "/images/application.gif");

				nApp.xData.xNode = new Object();
				nApp.xData.xNode.appID = "1";
                    nApp.xData.xNode.parentkey=n.key;
				nApp.xData.codeName = "����";



	}
	catch(e){
	}

}
function tvNodeSelected(){

	document.all("paramValue").value = "errortype&text&"+document.node.key+"*moudleid&text&"+document.node.xData.xNode.parentkey;

	if (document.node.xData.xNode.appID !="qq")
	{

		innerDocTD.innerHTML = "<iframe id='frmContainer' src='lowerdept.jsp' style='WIDTH:100%;HEIGHT:100%' frameborder='0' scrolling='auto'></iframe>";

	}
     imgClick();
}

