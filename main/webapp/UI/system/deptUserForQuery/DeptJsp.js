
 function changeheigth(){
	document.all("rootUl").style.height=document.body.offsetHeight -90;
}


var menu;
function onUserDocumentLoad()
{

		var menuText="��ӻ���,,,,/images/insert.gif,add;-,0,,,,;�޸Ļ���,,,,/images/property.gif,edit;";
		//var menuText="��Ӳ���,,,,/images/insert.gif,add;";
		menuText =menuText+"-,0,,,,;ɾ������,,,,/images/delete.gif,delete;";


		var domdoc = createDomDocument();

		var retStr = createselectArr("parentdeptid","text","0","sm0034");

		if (retStr != false)
		{

			domdoc.loadXML(retStr);

			var Root = domdoc.documentElement;

			treeInit();
			Nodes_clear();


			 menu = new Menu(menuText);

			var nRoot = vNodes.add("", "", "0", "��������", "/images/domain.gif");

			nRoot.xData.xNode = new Object();
			nRoot.xData.xNode.appID = "0";
			

			nRoot.xData.xNode.childCount = 0;
			nRoot.xData.waitforLoad = false;

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
		nApp.xData.xNode.appID = "1";
		
		
		
		nApp.span.title = decode(getAttrValue(node.childNodes[2],"value"));

		nApp.xData.codeName = decode(getAttrValue(node.childNodes[1],"value"));
		if (decode(getAttrValue(node.childNodes[3],"value"))!="0"){
			nApp.xData.waitforLoad = true;

			nApp.add("tvwChild", "", "������...");
		}
		node = node.nextSibling;
		count = count +1;


	}



}

//////////////��ʾ�ұ߲˵�

function menuTreeBeforePopup(el)
{
	var tNode = document.lastNode;

	var xNode = tNode.xData.xNode;
	var type = xNode.type;

	switch(el.menuData)
	{

		case "add":	el.disableItem = xNode.appID == "30";
							break;
		case "edit":	el.disableItem = xNode.appID == "0";
							break;
		case "delete":	el.disableItem = xNode.appID == "0";
							break;

	}
}

function menuTreeClick(el)
{
	var tNode = document.lastNode;
	var xNode = tNode.xData.xNode;
	var type = xNode.type;

	//try
	//{
		if (tNode)
		{
			
			if (node.level/1 ==5)
				return;
				
			var xNode = tNode.xData.xNode;

			switch(el.menuData)
			{
				case "add":	addSubNode(tNode);
									break;
				case "edit":	editSubNode(tNode);
									break;
				case "delete":	deleteSubNode(tNode);
									break;

			}
		}
	//}
	//catch(e)
	//{
	//	showError(e);
	//}
}



function tvNodeRightClick(node)
{

		var tNode = node;

		if (tNode)
		{
			document.lastNode = tNode;

			show(event.x, event.y);
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
		var retStr = createselectArr("parentdeptid","text",n.key,"sm0034");
		if (retStr != "false")
		{

			var domdoc = createDomDocument();
			domdoc.loadXML(retStr);

			var Root = domdoc.documentElement;
			var node = Root.firstChild;

			n.xData.waitforLoad = false;
			n.removeChildren();
			while(node)
			{
				var nApp = n.add("tvwChild", decode(getAttrValue(node.childNodes[0],"value")), decode(getAttrValue(node.childNodes[1],"value")), "/images/application.gif");

				nApp.xData.xNode = new Object();
				nApp.xData.xNode.appID = "1";
				
				
				nApp.span.title = decode(getAttrValue(node.childNodes[2],"value"));

				nApp.xData.codeName = decode(getAttrValue(node.childNodes[1],"value"));
				if (decode(getAttrValue(node.childNodes[3],"value"))!="0"){
					nApp.xData.waitforLoad = true;

					nApp.add("tvwChild", "", "������...");
				}
				node = node.nextSibling;


			}
			domdoc.free;
		}
	}
	catch(e){
	}

}
function tvNodeSelected(){

	   topparent ="";
	   ouname ="";
		if (document.node.level/1 ==1){
			ouname    = document.node.caption;
			topparent = document.node.caption;
		}
		if (document.node.level/1 ==2){
			topparent =document.node.parent.caption;
			ouname =document.node.caption;
		}	
		if (document.node.level/1 ==3){
			topparent =document.node.parent.parent.caption;
			ouname =document.node.caption;
		}
		if (document.node.level/1 ==4){
			topparent =document.node.parent.parent.parent.caption;
			ouname =document.node.caption;
		}

		document.all("paramValue").value = "deptid&text&"+document.node.key+"*deptname&text&"+ouname+"*topparent&text&"+topparent;
	
		if (document.node.key !="0")
		{
	
			innerDocTD.innerHTML = "<iframe id='frmContainer' src='UserJsp.jsp' style='WIDTH:100%;HEIGHT:100%' frameborder='0' scrolling='auto'></iframe>";
	
		}
	     imgClick();
}

function addSubNode(node){
	//try{


		var sfeature = "dialogwidth:480px; Dialogheight:280px;center:yes;help:no;resizable:yes;scroll:no;status:no";

		var spath = "EditDept.jsp";
		var arg = new Object();
			arg.type = "insert";
			arg.parentid = node.key;
			
			arg.topparent ="";
			arg.parent ="";
			if (node.level/1 ==1){
				arg.topparent =node.caption;
			}
			if (node.level/1 ==2){
				arg.topparent =node.parent.caption;
				arg.parent =node.caption;
			}	
			if (node.level/1 ==3){
				arg.topparent =node.parent.parent.caption;
				arg.parent =node.caption;
			}
			if (node.level/1 ==4){
				arg.topparent =node.parent.parent.parent.caption;
				arg.parent =node.caption;
			}
			
			

		var  goupstr = window.showModalDialog(spath,arg,sfeature);

		onUserDocumentLoad();



	//}catch(e){
	//}

}

function editSubNode(node){
	//try{
		var sfeature = "dialogwidth:480px; Dialogheight:280px;center:yes;help:no;resizable:yes;scroll:no;status:no";

		var spath = "EditDept.jsp";
		var arg = new Object();
			arg.type = "update";
			arg.parentid = node.key;
			
			
			arg.topparent ="";
			arg.parent ="";
			

		var  goupstr = window.showModalDialog(spath,arg,sfeature);

		onUserDocumentLoad();



	//}catch(e){
	//}

}

function deleteSubNode(node){

		if (confirm("��ȷ��Ҫɾ����ǰ��¼��")){
			var whereArr =  new Array("deptid&text&"+node.key);
			if (createDelArray(whereArr,"sm0033")+"" =="true"){				  
				 onUserDocumentLoad();

			 }

		}


}
