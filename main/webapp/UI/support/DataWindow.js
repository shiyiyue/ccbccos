/**
 * ���ݴ��ڿؼ�
 * @param dwObj
 * @param dwType ���ݴ��ڶ�������
 *   form : Form��
 *   grid : Grid��
 */
function DataWindow( dwObj, dwType )
{
	this.dw = dwObj;
	this.type = dwType;
	this.ownerDoc = this.dw.ownerDocument;
	this.ownerWin = this.ownerDoc.parentWindow;
	//if (this.type=="grid")
	//{
	//	this.fieldArr=dwObj.fieldname.split(",");
	//	this.columnCount=this.fieldArr.length;
	//}
	//Debug Mode
}

DataWindow.isDebug = true;
DataWindow.alertMsg= "";

DataWindow.prototype.getColumnObject = DataWindow_getColumnObject;
DataWindow.prototype.RowCount = DataWindow_RowCount;
DataWindow.prototype.getColumnIndex = DataWindow_getColumnIndex;
DataWindow.prototype.getSelectRow = DataWindow_getSelectRow;
DataWindow.prototype.getSelectRows = DataWindow_getSelectRows;
DataWindow.prototype.fireEvent = DataWindow_fireEvent;
DataWindow.prototype.selectRow = DataWindow_selectRow;
DataWindow.prototype.insertRow = DataWindow_insertRow;
DataWindow.prototype.editRow = DataWindow_editRow;
DataWindow.prototype.deleteRow = DataWindow_deleteRow;
DataWindow.prototype.cancelRow = DataWindow_cancelRow;
DataWindow.prototype.getItemDisplay = DataWindow_getItemDisplay;
DataWindow.prototype.getItemString = DataWindow_getItemString;
DataWindow.prototype.getItemExpand = DataWindow_getItemExpand;
DataWindow.prototype.setItemString = DataWindow_setItemString;
DataWindow.prototype.getItemInt = DataWindow_getItemInt;
DataWindow.prototype.setItemInt = DataWindow_setItemInt;
DataWindow.prototype.getItemFloat = DataWindow_getItemFloat;
DataWindow.prototype.setItemFloat = DataWindow_setItemFloat;
DataWindow.prototype.validate = DataWindow_validate;
DataWindow.prototype.validatePrimaryKey = DataWindow_validatePrimaryKey;
DataWindow.prototype.disableColumn = DataWindow_disableColumn;
DataWindow.prototype.enableColumn = DataWindow_enableColumn;
DataWindow.prototype.disableColumn2 = DataWindow_disableColumn2;
DataWindow.prototype.enableColumn2 = DataWindow_enableColumn2;
DataWindow.prototype.disableColumnAll = DataWindow_disableColumnAll;
DataWindow.prototype.enableColumnAll = DataWindow_enableColumnAll;
DataWindow.prototype.enableColumnNull = DataWindow_enableColumnNull;
DataWindow.prototype.disableColumnNull = DataWindow_disableColumnNull;
DataWindow.prototype.enableInputOption = DataWindow_enableInputOption;
DataWindow.prototype.disableInputOption = DataWindow_disableInputOption;
DataWindow.prototype.getColumnLabel = DataWindow_getColumnLabel;
DataWindow.prototype.getColumnCName = DataWindow_getColumnCName;
DataWindow.prototype.setOnChangeHandler = DataWindow_setOnChangeHandler;
DataWindow.prototype.setEventHandler = DataWindow_setEventHandler;
DataWindow.prototype.removeOnChangeHandler = DataWindow_removeOnChangeHandler;
DataWindow.prototype.removeEventHandler = DataWindow_removeEventHandler;
DataWindow.prototype.setColumn = DataWindow_setColumn;
DataWindow.prototype.reInitOldValue = DataWindow_reInitOldValue;
DataWindow.prototype.isModified = DataWindow_isModified;
DataWindow.prototype.getOperate = DataWindow_getOperate;
DataWindow.prototype.isValidRow = DataWindow_isValidRow;
DataWindow.prototype.disposeComboBox = DataWindow_disposeComboBox;

DataWindow.prototype.dispose = DataWindow_dispose;

DataWindow.getColumnValue = DataWindow_getColumnValue;
DataWindow.setColumnValue = DataWindow_setColumnValue;
DataWindow.isDate = DataWindow_isDate;
DataWindow.validateColumn = DataWindow_validateColumn;
DataWindow.validateAlert = DataWindow_validateAlert;
DataWindow.RTrim = DataWindow_RTrim;
DataWindow.getRealLength = DataWindow_getRealLength;
DataWindow.DateAdd = DataWindow_DateAdd;
DataWindow.setFocus = DataWindow_setFocus;


/**
 * �������ݴ�����ĳ���ֶζ����onchange�¼���������ֻ��form������Ч
 *
 * @param colName �ֶ���
 * @param handlerName �¼�����������
 */
function DataWindow_setOnChangeHandler( colName, handlerName )
{
	this.setEventHandler(colName, "onchange", handlerName);
}


/**
 * �������ݴ�����ĳ���ֶζ����eventName�¼���������ֻ��form������Ч
 *
 * @param colName �ֶ���
 * @param eventName �¼���
 * @param handlerName �¼�����������
 */
function DataWindow_setEventHandler( colName, eventName, handlerName )
{
	if (this.type=="grid") return;
	var colObj=this.getColumnObject( 1, colName );
	if (colObj==null)
	{
		if (DataWindow.isDebug) DataWindow_log("����Ϊ�����ڵĶ�������"+eventName+"��������\n�ֶ���Ϊ��"+colName);
		return;
	}
	var bSuccess=false;
	if (typeof(handlerName)=="function")
		bSuccess=colObj.attachEvent(eventName, handlerName);
	else if (typeof(handlerName)=="string")
		bSuccess=colObj.attachEvent(eventName, eval(handlerName));
	else
	{
		if (DataWindow.isDebug) DataWindow_log( "��Ч�Ĳ���handlerName�����������\n�ֶ����֣�"+colName+"�¼�����"+eventName+" ��������"+handlerName );
		return false;
	}
	if (!bSuccess)
	{
		if (DataWindow.isDebug) DataWindow_log( "���¼����������ɹ������������\n�ֶ����֣�"+colName+"�¼�����"+eventName+" ��������"+handlerName );
		return false;
	}
}


/**
 * ȡ�����ݴ�����ĳ���ֶζ����onchange�¼���������ֻ��form������Ч
 *
 * @param colName �ֶ���
 * @param handlerName �¼�����������
 */
function DataWindow_removeOnChangeHandler( colName, handlerName )
{
	this.removeEventHandler(colName, "onchange", handlerName);
}

/**
 * ȡ�����ݴ�����ĳ���ֶζ����eventName�¼���������ֻ��form������Ч
 *
 * @param colName �ֶ���
 * @param eventName �¼���
 * @param handlerName �¼�����������
 */
function DataWindow_removeEventHandler( colName, eventName, handlerName )
{
	if (this.type=="grid") return;
	var colObj=this.getColumnObject( 1, colName );
	if (colObj==null)
	{
		if (DataWindow.isDebug) DataWindow_log("����Ϊ�����ڵĶ�������"+eventName+"��������\n�ֶ���Ϊ��"+colName);
		return;
	}
	var bSuccess=false;
	if (typeof(handlerName)=="function")
		bSuccess=colObj.detachEvent(eventName, handlerName);
	else if (typeof(handlerName)=="string")
		bSuccess=colObj.detachEvent(eventName, eval(handlerName));
	else
	{
		if (DataWindow.isDebug) DataWindow_log( "��Ч�Ĳ���handlerName�����������\n�ֶ����֣�"+colName+"�¼�����"+eventName+" ��������"+handlerName );
		return false;
	}
	if (!bSuccess)
	{
		if (DataWindow.isDebug) DataWindow_log( "ȡ���¼����������ɹ������������\n�ֶ����֣�"+colName+"�¼�����"+eventName+" ��������"+handlerName );
		return false;
	}
}



/**
 * �������ݴ���һ���ֶεĽ���
 * @param colName �ֶζ�����ֶ�����
 */
function DataWindow_setFocus( colName )
{
	if (colName==undefined || colName==null || colName=="" )
	{
		if (DataWindow.isDebug) DataWindow_log( "Ҫ���ý���Ķ���Ϊ�գ�" );
		return;
	}
	if (typeof(colName)=="object")
	{
		try
		{
			colName.focus();
			if (colName.tagName.toLowerCase()=="input" ||
				colName.tagName.toLowerCase()=="textarea" )
			colName.select();
		}
		catch (e)
		{ if (DataWindow.isDebug) DataWindow_error( "�ؼ��������ô���\n�ֶ���Ϊ��"+colName.id+"\n������Ϣ��"+e.message ); }
	}
}


/**
 * �Ƿ���������ֵ
 */
function DataWindow_isDate( strDate )
{
	try
	{
		var y = strDate.substr(0,4)*1;
		var m = strDate.substr(5,2)*1;
		var d = strDate.substr(8,2)*1;

		date = new Date(y, m-1, d);

		var y2 = date.getFullYear();
		var m2=date.getMonth() +1;
		var d2=date.getDate();

		if (m2<10) m2="0"+m2;
		if (d2<10) d2="0"+d2;

		if((y2+ "-" + m2+"-"+d2) == strDate)
		{
			return true;
		}
		return false;
	}
	catch(e)
	{
		return false;
	}
}


/**
 * ���ڼӼ�����
 */
function DataWindow_DateAdd( strDate, nAfter, sPart)
{
	if (!DataWindow.isDate(strDate))
	{
		if (DataWindow.isDebug)	DataWindow_log( "���ǰ�����ڸ�ʽ����\n�ַ���Ϊ"+strDate );
		return "";
	}
	if (isNaN(nAfter))
	{
		if (DataWindow.isDebug)	DataWindow_log( "�����������Ͳ�����\n����ֵΪ"+nAfter );
		return strDate;
	}
	sPart=sPart.toLowerCase();
	if (sPart!="year" && sPart!="month" && sPart!="day")
	{
		if (DataWindow.isDebug)	DataWindow_log( "��������ȷ�Ĳ���ֵ��\n����ֵΪ"+sPart );
		return strDate;
	}
	var y = strDate.substr(0,4)*1;
	var m = strDate.substr(5,2)*1;
	var d = strDate.substr(8,2)*1;

	var date = new Date(y, m-1, d);
	if (sPart=="year")
		date.setFullYear( date.getFullYear()+nAfter);
	else if (sPart=="month")
		date.setMonth( date.getMonth()+nAfter);
	else if (sPart=="day")
		date.setDate( date.getDate()+nAfter);
	var dateReturn=new Date(date.valueOf());
	var y2=dateReturn.getFullYear();
	var m2=dateReturn.getMonth() +1;
	var d2=dateReturn.getDate();

	if (m2<10) m2="0"+m2;
	if (d2<10) d2="0"+d2;

	return y2+ "-" + m2+"-"+d2;
}

/**
 * ȥ���ұ߿ո�
 */
function DataWindow_RTrim(str)
{
	if ((typeof(str) != "string") || !str)
		return "";
	for(var i=str.length-1; i>=0; i--)
		{
			if (str.charCodeAt(i, 10)!=32)
				break;
		}
	return str.substr(0, i+1);
}

/**
 * �����ַ�������ʵ���ȣ������ֽڼ��㣬�����ǰ���ȱʡ���ַ����㣩
 */
function DataWindow_getRealLength( theValue )
{
	var  bytelen=0;
	for (var i=0; i<theValue.length; i++)
	{
		if (theValue.charCodeAt(i) > 255) bytelen = bytelen + 2;
		else bytelen++;
	}
	return bytelen;
}

/**
 * ��֤����
 */
function DataWindow_validateAlert(element, message, isQuiet)
{
  
	var alertMsg;
	if (element.alertTitle != undefined)
		alertMsg="" + element.alertTitle + "" + message;
	else{
		//alertMsg="�������" + message + "\n�ֶ���Ϊ��" + element.id;
		//edit by wuyeyuan
		//���û��alertTtile���ԣ���Ĭ��ץȡ���ֶα�ǩ
		var label=element.parentElement.previousSibling.innerText;
		label=trimStr(label);
		if(label.substring(label.length-1,label.length)=="*")
			label=label.substring(0,label.length-1);
		//else	
		alertMsg="" + label + "" + message;
		}
	try
	{
		if (isQuiet) DataWindow.alertMsg=alertMsg;
		else
		{
			alert( alertMsg );
			DataWindow.alertMsg="";
		}
		element.focus();
		element.select();
	}
	catch(e)
	{
	}
}

/**
 * �ж�ֵ�Ƿ����������д���
 */
function DataWindow_isValueExist(element, value, isQuiet, valueMustInOptions)
{
	//�����ؼ���Ϊû�����⣬����ͨ�����
	if (element.tagName.toLowerCase()!="input" || element.fieldtype!="select")
		return true;
	//ʹ��enableInputOption���õĿؼ����Զ�ͨ�����
	if (element.canInputOption!=undefined && element.canInputOption=="true")
		return true;
	//���ֵ�Ƿ��������б��д��ڵļ��
	var optionsArr=element.options;
	var isValueExist=false;
	for (var i=0; i<optionsArr.length; i++)
	{
		if (optionsArr[i].value==value)
		{
			isValueExist=true;
			break;
		}
	}
	if (!isValueExist)
	{
		DataWindow_validateAlert( element, "��ѡ��ѡ�����ѡ����б��У�", isQuiet );
		return false;
	}
	return true;
}


/**
 * �����ֶ�ֵ�Ƿ�Ϸ�
 * @param isQuiet
 * @param valueMustInOptions 
 */
function DataWindow_validateColumn(element, isQuiet, valueMustInOptions)
{
	//����ȱʡֵ
	if (valueMustInOptions==undefined)
		valueMustInOptions=true;
	// ��ȡ����ֵ
	var value = DataWindow.RTrim(element.value);
	//����������ֵ��ȡ��ʹ��attr����
	if (element.tagName.toLowerCase()=="input" && element.fieldtype=="select")
	{
		value = DataWindow.RTrim(element.attr);
		//alert( element.id + "---" + element.attr + ":"+ element.value );
	}
	// ��֤��
	if((element.isNull != undefined) && (element.isNull.toLowerCase() == "false"))
	{
		if(value.length == 0)
		{
			DataWindow.validateAlert(element, "����Ϊ��", isQuiet);
			return false;
		}
	}
	if (valueMustInOptions)	//���������б���
	{
		if (!DataWindow_isValueExist(element, value, isQuiet, valueMustInOptions))
		return false;
	}
	// ��֤��󳤶�
	if(element.maxSize != undefined)
	{
		if(DataWindow.getRealLength(value) > (element.maxSize*1))
		{
			DataWindow.validateAlert(element, "ʵ�����볤��Ϊ:" + DataWindow.getRealLength(value) + "����������������볤��" + element.maxSize, isQuiet);
			return false;
		 }
	}
	// ��֤��С����
	if(element.minSize != undefined)
	{
		if(DataWindow.getRealLength(value) < (element.minSize*1))
		{
			DataWindow.validateAlert(element, "ʵ�����볤��Ϊ:" + DataWindow.getRealLength(value) + "��С����С�������볤��" + element.minSize, isQuiet);
				return false;
		}
	}
	// ��֤�ַ��ͳ���
	if(element.textLength != undefined)
	{
		if(DataWindow.getRealLength(value) > (element.textLength*1))
		{
			DataWindow.validateAlert(element, "ʵ�����볤��Ϊ:" + DataWindow.getRealLength(value) + "����������������볤��" + element.textLength, isQuiet);
			return false;
		 }
	}
	// ��֤����
	if(element.fieldType != undefined && element.fieldType == "date")
	{
		if(value.length > 0 && DataWindow.isDate(value) == false)
		{
			DataWindow.validateAlert(element, "����ֵ���ǺϷ��������ͣ���ȷ�����ʽ��YYYY-MM-DD", isQuiet);
				return false;
		}
	}
	// ������֤
	if( (element.intLength != undefined)&&(element.floatLength == undefined) )
	{
		var IntLength=element.intLength;
		for (var i=0; i<value.length; i++)
		{
			var ch = value.substr(i, 1);
			if (ch < "0" || ch > "9" )
			{
				if(ch == "-" && i == 0)
				{
					continue;
				}
				else
				{
					DataWindow.validateAlert(element, "����ֵ���ǺϷ���������", isQuiet);
					return false;
				}
			}
		}
		//���ȼ��
		if (value.length > IntLength*1)
		{
			DataWindow.validateAlert(element,":���������λ�����ܳ���" + IntLength + "λ", isQuiet);
			return false;
		}
	}
	// ��֤������
	if((element.intLength != undefined)&&(element.floatLength != undefined))
	{
		var pointIndex = -1;
		for (var i=0; i<value.length; i++)
		{
			var ch = value.substr(i, 1);
			if (ch < "0" || ch > "9")
			{
				if(ch == ".")
				{
					if(pointIndex != -1)
					{
						DataWindow.validateAlert(element, "���������ֻ����һ��С����", isQuiet);
						return false;
					}
					else
					{
						pointIndex = i;
					}
				}
				else if(ch == "-" && i ==0)
				{
					continue;
				}
				else
				{
					DataWindow.validateAlert(element, "����ֵ���ǺϷ�����������", isQuiet);
					return false;
				}
			}
		}
		if (pointIndex == -1)
		pointIndex = value.length;
		var IntLength=element.intLength;
		var FloatLength=element.floatLength;
		//����λ�����
		if ((value.substring(0, pointIndex) * 1).toString().length > IntLength*1)
		{
			DataWindow.validateAlert(element, "���������λ�����ܳ���" + IntLength + "λ", isQuiet);
			return false;
		}
		//С��λ�����
		var strFrac = value.substring(pointIndex + 1, value.length);
		if (strFrac.length > 0)
		{
			if ( strFrac.length >FloatLength*1 )
			{
				DataWindow.validateAlert(element, "�����С��λ�����ܳ���" + FloatLength + "λ", isQuiet);
				return false;
			}
		}
	}
	// ��֤���ֵ
	if(element.maxValue != undefined && value.length > 0)
	{
		if((value*1) > (element.maxValue*1))
		{
			DataWindow.validateAlert(element, "ʵ������ֵΪ:" + value + "�����������������ֵ" + element.maxValue, isQuiet);
				return false;
		}
	}
	// ��֤��Сֵ
	if(element.minValue != undefined && value.length > 0)
	{
		if((value*1) < (element.minValue*1))
		{
			DataWindow.validateAlert(element, "ʵ������ֵΪ:" + value + "��С����С��������ֵ" + element.minValue, isQuiet);
				return false;
		}
	}
	return true;
}




/**
 * �෽������һ���ֶζ����л������ֵ
 * @param colObj �ֶζ���
 */
function DataWindow_getColumnValue( colObj )
{
	if (colObj==undefined || colObj==null)
	{
		if (DataWindow.isDebug) DataWindow_log("getColumnValue�����������󣡱����ʵ��ֶζ���Ϊ�գ�");
		return "";
	}
	var colType=colObj.type.toLowerCase();
	if(colType=="checkbox"||colType=="radio")
	{
		if (colObj.checked)
			return "1";
		else
			return "0";
	}
	else
	{
		if (colObj.tagName.toLowerCase()=="input" && colObj.fieldtype!=undefined && colObj.fieldtype=="select")	//2005.06.23����������ؼ�
			return colObj.attr;
		else
			return colObj.value;
	}
}

/**
 * ����һ���ֶζ����ֵ
 * @param colObj �ֶζ���
 * @param colValue �ֶ�ֵ
 */
function DataWindow_setColumnValue( colObj, colValue )
{
	if( colObj.tagName.toUpperCase() == "INPUT")
	{
		if(colObj.type=="checkbox" || colObj.type=="radio")
		{
			if (colValue=="1")
				colObj.checked = true;
			else
				colObj.checked = false;
			return;
		}
		if (colObj.fieldtype=="select")	//����������ؼ�
		{
			//colObj.attr=colValue;
			//colObj.comboBox = colObj.ownerDocument.parentWindow.ComboBox_Create(colObj.id);
		  	colObj.comboBox.focusDefaultValue(colValue);
			return;
		}
	}
	colObj.value = colValue;
}


/**
 * ���һ���ֶ��ڱ��е�˳�򡣣���Ҫ����grid�ͣ��ֶ���fieldName�е�˳��
 * @return int �ֶ�˳��
 */
function DataWindow_getColumnIndex( colName )
{
	if (this.type=="form")
		return 0;
	else
	{
		var fieldArr=this.dw.fieldname.split(",");
		var columnCount=fieldArr.length;
		var nIndex = -1;
		for ( i=0; i<columnCount; i++ )
		{
			if (fieldArr[i]==colName)
			{
				nIndex=i;
				break;
			}
		}
		return nIndex;
	}
}

/**
 * �����ֶ���������ֶε���������Ŀǰֻ��grid��Ч��
 * @param colName �ֶ���
 * @return String �����������û�У�����""
 */
function DataWindow_getColumnCName( colName )
{
	if (this.type=="form")
	{
		colObj=this.getColumnObject(0, colName);
		if (colObj==null)
		{
			if (DataWindow.isDebug) DataWindow_log( "���ܴӲ����ڵĶ����л���ֶ���������\n�ֶ���Ϊ��"+colName );
			return "";
		}
		else if (colObj.alertTitle==undefined || colObj.alertTitle==null)
		{
			objLabel=this.getColumnLabel(0, colName );
			if (objLabel!=null) return objLabel.innerText;
			else return colName;
		}
		else return colObj.alertTitle;
	}
	else
	{
		var colNum=this.getColumnIndex(colName);
		if (colNum==-1)
		{
			if (DataWindow.isDebug) DataWindow_log("�����ʵ��ֶβ����ڣ�\n�ֶ���Ϊ��"+colName);
			return "";
		}
		var fieldArr=this.dw.fieldCN.split(",");
		var columnCount=fieldArr.length;
		if (colNum<0 && colNum>=columnCount)
			return "";
		else
			return fieldArr[colNum];
	}
}

/**
 * �����ֶ������һ�����ݴ����е��ֶζ���
 * @param rowNum �к�
 * @param colName �ֶ���
 * @return Object �ֶζ���
 */
function DataWindow_getColumnObject( rowNum, colName )
{
	if (this.type=="form")
	{
		var colObj=this.dw.getAttribute(colName);
		if (colObj==undefined || colObj==null)	//�ֶβ�����
		{
			//if (DataWindow.isDebug)
			//	DataWindow_log("���ʵ��ֶβ����ڣ�\n�ֶ���Ϊ��"+colName);
			return null;
		}
		if (colObj.type==undefined)	//���ʵĲ����ֶ�
		{
			//if (DataWindow.isDebug)
			//	DataWindow_log("���ʵĲ����ֶζ���\n�ֶ���Ϊ��"+colName);
			return null;
		}
		return colObj;
	}
	else
		return null;
}

/**
 * ������ݴ�����һ���ֶεı�ǩ������ʱֻ��form������Ч
 * @param rowNum �к�
 * @param colName �ֶ���
 * @return Object ��ǩ����
 */
function DataWindow_getColumnLabel( rowNum, colName )
{
	if (this.type=="form")
	{
		var objLabel=this.dw.all(colName+"_t");
		if (objLabel==null)
		{
			//if (DataWindow.isDebug) DataWindow_log("�����ֶεı�ǩDIV�����ڣ�\n�ֶ���Ϊ��"+colName);
			return null;
		}
		return objLabel;
	}
	else
	{
		return null;
	}
}

function DataWindow_setColumn( colName )
{
	if (this.type=="form")
	{
		var colObj=this.getColumnObject(1, colName );
		if (colObj==null)
		{
			if (DataWindow.isDebug) DataWindow_log("���ܽ��������õ������ڵĶ����У��ֶβ����ڡ�\n�ֶ���Ϊ��"+colName);
			return;
		}
		DataWindow.setFocus(colObj);
	}
}


/**
 * �������ݴ��ڵļ�¼����
 * @return int ��¼����
 */
function DataWindow_RowCount( bValid )
{
	if (this.type=="form")
	{
		if (this.dw.DataWindowRowCount!=undefined)
			return this.dw.DataWindowRowCount;
		else
		{
			if (DataWindow.isDebug)	DataWindow_log("û��ΪForm���ʹ���������ݣ�Ĭ�ϼ�¼��Ϊ1��" );
			return 1;
		}
	}
	else
	{
		if (bValid)	//�����Ч����
		{
			var nNum=0;
			var nCount=this.dw.rows.length;
			for( var i=0; i<nCount; i++)
				if (this.isValidRow(i)) nNum++;
			return nNum;
		}
		else
			return this.dw.rows.length;
	}
}


/**
 * ������ݴ��ڵ�ǰ��ѡ����к�
 * @return int �к�
 */
function DataWindow_getSelectRow()
{
	if (this.type=="form")
		return 1;
	else
	{
		if (this.dw.rows.length==0)
			return -1;
		return parseInt(this.dw.activeIndex);
	}
}


/**
 *
 */
function DataWindow_getSelectRows()
{
	if (this.type=="form")
		return "1";
	else
	{
		var returnStr="";
		var formObj=this.ownerDoc.getElementById( "form_"+this.dw.id );
		for (var i=0; i< formObj.length; i++)
		{
			var obj= formObj.item(i);
		   	if (obj.type=="checkbox")
		   	{
				if (obj.id !="chkpar_lcGrid" && obj.checked)
				{
					var trobj=getOwnerTR(obj);
					if (returnStr=="")
						returnStr=trobj.rowIndex+"";
					else
						returnStr=returnStr+","+trobj.rowIndex+"";
				}
			}
		}
		return returnStr;
	}
}

/**
 * ѡ��DataWindow�е�һ�У�Ŀǰֻ��grid��Ч
 * @param rowNum ��Ҫѡ�����
 */
function DataWindow_selectRow( rowNum )
{
	if (this.type=="form")
		return true;
	else
	{
		if (rowNum>=0 && rowNum<this.dw.rows.length)
		{
			var rowObj=this.dw.rows(rowNum);
			rowObj.fireEvent("onclick");
			return true;
		}
		else
		{
			if (DataWindow.isDebug)	DataWindow_log( "ѡ���˲����ڵ���!" );
			return false;
		}
	}
}


/**
 * �����ݴ����л����ʾ�����ݡ�������ֵ��ֻ��grid��Ч
 * @param rowNum �к�
 * @param colName �ֶ���
 * @return �ַ�������������ʵ��ֶβ����ڣ�����""
 */
function DataWindow_getItemDisplay(rowNum, colName)
{
	if (this.type=="form")
	{
		return "";
	}
	else
	{
		if (rowNum<0 || rowNum>=this.dw.rows.length) return "";
		var cells=this.dw.rows(rowNum).cells;
		var nLength=cells.length;
		for (var i=0; i<nLength; i++)
		{
			if (cells(i).fieldname==colName)
				return cells(i).innerText;
		}
		return "";
	}
}


/**
 * ��DataWindow�л��һ���ֶε�ֵ
 * @param rowNum �к�
 * @param colName �ֶ���
 * @return �ַ�������������ʵ��ֶβ����ڣ�����""
 */
function DataWindow_getItemString(rowNum, colName)
{
	if (arguments.length!=2)
	{
		if (DataWindow.isDebug) DataWindow_error("������ֻ֧������������\n������Ϊ��getItemString");
		return 0;
	}
	if (this.type=="form")
	{
		var colObj=this.getColumnObject( rowNum, colName );
		if (colObj==null) 	//����Ϊ��
		{
			if (DataWindow.isDebug) DataWindow_log("���ܴӲ����ڵĶ����л���ֶ�ֵ��\n�ֶ���Ϊ��"+colName);
			return "";
		}
		else
			return DataWindow.getColumnValue(colObj);
	}
	else
	{
		var rowCount=this.RowCount();
		if (rowNum<0 || rowNum>=rowCount)
		{
			if (DataWindow.isDebug) DataWindow_log( "���ݷ���Խ�磡\n�ö����¼��Ϊ:"+rowCount+"����Ҫ���ʵ������к�Ϊ��"+rowNum );
			return "";
		}
		var colIndex=this.getColumnIndex( colName );
		if ( colIndex==-1 )
		{
		if (DataWindow.isDebug) DataWindow_log( "û���ҵ�ָ�����ֵ��ֶΣ�\n�ֶ���Ϊ��"+colName );
			return "";
		}
		if (this.dw.rows[rowNum].ValueStr==undefined)
		{
			if (DataWindow.isDebug) DataWindow_log( "�����ʵ���û��valueStr���ԣ�����������һ����¼��\n�к��ǣ�"+rowNum+" �ֶ�����"+colName );
			return "";
		}
		var valueStr=this.dw.rows[rowNum].ValueStr;
		var valueArr=valueStr.split(";");
		return valueArr[colIndex];
	}
}


function DataWindow_fireEvent( rowNum, colName, eventName )
{
	if (this.type=="grid") return false;
	var colObj=this.getColumnObject( rowNum, colName );
	if (colObj==null) 	//����Ϊ��
	{
		if (DataWindow.isDebug) DataWindow_log("���ܴ��������ڵĶ�����¼���\n�ֶ���Ϊ��"+colName);
		return false;
	}
	else
	{
		if (colObj.disabled)
		{
			colObj.disabled=false;
			var retValue=colObj.fireEvent(eventName);
			colObj.disabled=true;
			return retValue;
		}
		else
			return colObj.fireEvent(eventName);
	}

}

/**
 * ��Selectѡ���ȡ�ñ�ѡ�����ݵ�expand����ֵ��ֻ��form��Ч
 * @param rowNum �к�
 * @param colName �ֶ���
 * @param attrName ��������
 * @return �ַ�������������ʵ��ֶβ����ڣ�����""���������select������""
 */
function DataWindow_getItemExpand(rowNum, colName, attrName)
{
	if (arguments.length!=2 && arguments.length!=3)
	{
		if (DataWindow.isDebug) DataWindow_error("������ֻ֧������������������\n������Ϊ��getItemExpand");
		return "";
	}
	if (this.type=="form")
	{
		var colObj=this.getColumnObject( rowNum, colName );
		if (colObj==null) 	//����Ϊ��
		{
			if (DataWindow.isDebug) DataWindow_log("���ܴӲ����ڵĶ����л���ֶ�ֵ��\n�ֶ���Ϊ��"+colName);
			return "";
		}
		else
		{
			var expandAttr;
			if (arguments.length==2)
				expandAttr="expand";
			else
				expandAttr=attrName;
			if (colObj.tagName.toUpperCase()=="INPUT" && colObj.fieldtype=="select")	//����������ؼ�
			{
				//colObj.comboBox = this.ownerWin.ComboBox_Create( colObj.id );
				if (colObj.comboBox.activeIndex==-1) return "";
				else return colObj.options[colObj.comboBox.activeIndex].getAttribute(expandAttr);
			}
			else if (colObj.tagName.toUpperCase()!="SELECT")
			{
				return colObj.getAttribute(expandAttr);
			}
			else
			{
				if (colObj.selectedIndex==-1) return "";
				else return colObj.options(colObj.selectedIndex).getAttribute(expandAttr);
			}
		}
	}
	else
		return "";
}



/**
 * �����ݴ����������ݣ���ʱֻ��form������Ч
 * @param rowNum �к�
 * @param colName �ֶ���
 * @param colValue �ֶ�ֵ
 * @return boolean �ɹ�������true��ʧ�ܣ�����false
 */
function DataWindow_setItemString(rowNum, colName, colValue)
{
	if (arguments.length!=3)
	{
		if (DataWindow.isDebug) DataWindow_error("������ֻ֧������������\n������Ϊ��setItemString");
		return false;
	}
	if (this.type=="form")
	{
		var colObj=this.getColumnObject( rowNum, colName );
		if (colObj==null)	//���󲻴���
		{
			if (DataWindow.isDebug) DataWindow_log("��������ֶ����������ݣ�\n�ֶ���Ϊ��"+colName);
			return false;
		}
		else
		{
			DataWindow.setColumnValue(colObj, colValue );
			//2005.03.09 Orchid �޸ģ�ֱ�Ӵ���ˢ�¹��ܣ���������ͼ�����¼�����Ϊ���ֶ�disabled����£��¼��޷�����
			if (colObj.dbgridid == undefined) return true;	//û�й���DBGrid
			this.ownerWin.ElementFillGrid( colObj );
			//colObj.fireEvent("onchange");
			return true;
		}
	}
	else
	{
		//if (DataWindow.isDebug) DataWindow_error("��grid���������ֶ�ֵ�Ĺ�����δʵ�֣�");
		//return false;
		var rowCount=this.RowCount();
		if (rowNum<0 || rowNum>=rowCount)
		{
			if (DataWindow.isDebug) DataWindow_log( "���ݷ���Խ�磡\n�ö����¼��Ϊ:"+rowCount+"����Ҫ���ʵ������к�Ϊ��"+rowNum );
			return false;
		}
		var colIndex=this.getColumnIndex( colName );
		if ( colIndex==-1 )
		{
			if (DataWindow.isDebug) DataWindow_log( "û���ҵ�ָ�����ֵ��ֶΣ�\n�ֶ���Ϊ��"+colName );
			return false;
		}
		//����fieldvaluearr����
		var fieldnamearr=this.dw.fieldname.split(",");
		var fieldvaluearr=new Array();
		if ( this.dw.rows[rowNum].ValueStr != undefined )
			fieldvaluearr=this.dw.rows[rowNum].ValueStr.split(";");
		else
		{
			if (DataWindow.isDebug) DataWindow_log( "�����ʵ���û��valueStr���ԣ�����������һ����¼��\n�к��ǣ�"+rowNum+" �ֶ�����"+colName );
			fieldvaluearr=new Array(fieldnamearr.length);
			for (var j=0; j<fieldnamearr.length; j++)
				fieldvaluearr[j]="";
		}
		if (colValue==undefined || colValue==null) colValue="";
		colValue=""+colValue;
		var re=/;/g;
		colValue=colValue.replace(re,"��");
		fieldvaluearr[colIndex]=colValue;
		//����ValueStr����
		var valuStr="undefined";
		for (var j=0; j< fieldnamearr.length; j++)
		{
			if (valuStr =="undefined")
			{
				if (fieldvaluearr[j]=="")	valuStr ="";
				else valuStr =fieldvaluearr[j];
			}
			else
			{
				if (fieldvaluearr[j]=="") valuStr=valuStr+";";
				else valuStr =valuStr+";"+fieldvaluearr[j];
			}
		}
		this.dw.rows[rowNum].ValueStr = valuStr;
		//����DBGrid�е�����
		for (var j=0; j< this.dw.rows[rowNum].cells.length; j++)
		{
		   	if (this.dw.rows[rowNum].cells[j].fieldname != undefined)
				if (this.dw.rows[rowNum].cells[j].fieldname.toUpperCase()== colName.toUpperCase())
				{
					this.dw.rows[rowNum].cells[j].innerText=colValue;
				}
		}
		this.editRow(rowNum);
	}
}

/**
 * ��DataWindow�л��һ���ֶε�����ֵ
 * @param rowNum �к�
 * @param colName �ֶ���
 * @return int����������ʵ��ֶβ����ڣ�����0
 */
function DataWindow_getItemInt( rowNum, colName )
{
	if (arguments.length!=2)
	{
		if (DataWindow.isDebug) DataWindow_error("������ֻ֧������������\n������Ϊ��getItemInt");
		return 0;
	}
	var sTemp=this.getItemString(rowNum,colName);
	if (sTemp=="")
		return 0;
	else
	{
		var nTemp=parseInt(sTemp,10);
		if (isNaN(nTemp))
		{
			if (DataWindow.isDebug) DataWindow_log( "��ʽת������!\n�ַ���ֵΪ��"+sTemp );
			return 0;
		}
		else
			return nTemp;
	}
}


/**
 * �����ݴ����������ݣ���ʱֻ��form������Ч
 * @param rowNum �к�
 * @param colName �ֶ���
 * @param colValue �ֶ�ֵ
 * @return boolean �ɹ�������true��ʧ�ܣ�����false
 */
function DataWindow_setItemInt( rowNum, colName,colValue )
{
	if (arguments.length!=3)
	{
		if (DataWindow.isDebug) DataWindow_error("������ֻ֧������������\n������Ϊ��setItemInt");
		return false;
	}
	this.setItemString(rowNum,colName,colValue);
}

/**
 * ��DataWindow�л��һ���ֶε�����ֵ
 * @param rowNum �к�
 * @param colName �ֶ���
 * @return int����������ʵ��ֶβ����ڣ�����0
 */
function DataWindow_getItemFloat( rowNum, colName )
{
	if (arguments.length!=2)
	{
		if (DataWindow.isDebug) DataWindow_error("������ֻ֧������������\n������Ϊ��getItemFloat");
		return 0;
	}
	var sTemp=this.getItemString(rowNum,colName);
	if (sTemp=="")
		return 0;
	else
	{
		var nTemp=parseFloat(sTemp,10);
		if (isNaN(nTemp))
		{
			if (DataWindow.isDebug) DataWindow_log( "��ʽת������!\n�ַ���ֵΪ��"+sTemp );
			return 0;
		}
		else
			return nTemp;
	}
}


/**
 * �����ݴ����������ݣ���ʱֻ��form������Ч
 * @param rowNum �к�
 * @param colName �ֶ���
 * @param colValue �ֶ�ֵ
 * @return boolean �ɹ�������true��ʧ�ܣ�����false
 */
function DataWindow_setItemFloat( rowNum, colName,colValue )
{
	if (arguments.length!=3)
	{
		if (DataWindow.isDebug) DataWindow_error("������ֻ֧������������\n������Ϊ��setItemFloat");
		return false;
	}
	this.setItemString(rowNum,colName,colValue);
}


/**
 * �����ݴ����в���һ����¼��ֻ��grid�������ݴ�������
 * @rowNum ��ѡ��������ָ���к�֮ǰ�����¼
 * @return int �²����¼���кš������form���ͣ�����-1
 */
function DataWindow_insertRow( rowNum )
{
	if (this.type=="form")
	{
		if (this.dw.DataWindowRowCount==undefined)
			this.dw.DataWindowRowCount=1;
		else
			this.dw.DataWindowRowCount=this.dw.DataWindowRowCount+1;
		return -1;
	}
	this.ownerWin.DBGrid_insertRow(this.dw, rowNum);
	return this.dw.activeIndex;
}

/**
 * �����ݴ�����ɾ��һ����¼��ֻ��grid��Ч
 *
 */
function DataWindow_deleteRow( rowNum )
{
	if (this.type=="grid")
	{
		this.selectRow( rowNum );
		this.ownerWin.DBGrid_deleteRow( this.dw, rowNum );
	}
}

/**
 * �޸����ݴ���һ����¼��ֻ��grid��Ч
 *
 */
function DataWindow_editRow( rowNum )
{
	if (this.type=="grid")
	{
		this.selectRow( rowNum );
		this.ownerWin.DBGrid_editRow( this.dw, rowNum );
	}
}

/**
 * ���������ݴ���һ����¼���޸ģ�ֻ��grid��Ч
 *
 */
function DataWindow_cancelRow( rowNum )
{
	if (this.type=="grid")
	{
		this.selectRow( rowNum );
		this.ownerWin.DBGrid_cancelRow( this.dw, rowNum );
	}
}


/**
 * ������ݴ����е������Ƿ���Ͻ��������ʱ��֧��û�а�form��grid�ʹ���
 * @param isQuiet ������ʽ�ļ��
 * @param valueMustInOptions ֵ�Ƿ�������������б��У�ȱʡֵΪtrue
 * @return Object ���鲻ͨ���Ķ������ȫ��ͨ��������null
 */
function DataWindow_validate( isQuiet, valueMustInOptions )
{
	if (valueMustInOptions==undefined)
		valueMustInOptions==true;
	var elementObj;
	if (this.type=="form")
	{
		elementObj=null;
		var elementsArr=this.dw.elements;
		for( i=0; i<elementsArr.length; i++ )
		{
			elementObj=elementsArr.item(i);
			if (DataWindow.validateColumn(elementObj, isQuiet, valueMustInOptions)==false)
				return elementObj;
		}
		return null;
	}
	else
	{
		if (this.dw.dbformname == undefined)
			return null;
		//ѭ��grid��ÿһ�У�����䵽������form�У�Ȼ���form���м�飬����
		for( j=0; j<this.dw.rows.length; j++ )
		{
			if (!this.isValidRow(j)) continue;	//�Ѿ�ɾ���ļ�¼������У��
			this.selectRow(j);
			var formObj=this.ownerDoc.getElementById( this.dw.dbformname );
			var elementsArr=formObj.elements;
			for( i=0; i<elementsArr.length; i++ )
			{
				elementObj=elementsArr.item(i);
				if (DataWindow.validateColumn(elementObj, isQuiet, valueMustInOptions)==false)
					return elementObj;
			}
		}
		return null;
	}
}


/**
 * ������ݴ����е������Ƿ�������ͻ
 * @param pkStr �����ֶ��б�ʹ��","�ָ���磺"BH,XM,ZJMC,ZJHM"��
 */
function DataWindow_validatePrimaryKey( pkStr )
{
	if (this.type=="form")
		return true;
	else
	{
		var pkArr=pkStr.split(",");
		if (pkArr.length==0)
		{
			if (DataWindow.isDebug) DataWindow_log( "��������Ҫ���������ֶ�!" );
			return true;
		}
		var pkTitle="";
		for (var pki=0; pki<pkArr.length; pki++)
		{
			pkTitle+=this.getColumnCName(pkArr[pki])+"��";
		}
		pkTitle=pkTitle.substring(0,pkTitle.length-1);
		var nRowCount=this.RowCount();
		for( var i=0; i<nRowCount; i++ )
		{
			if (!this.isValidRow(i)) continue;
			var pkValue="";
			for( var pki=0; pki<pkArr.length; pki++)
			{
				pkValue+=this.getItemString(i,pkArr[pki]);
			}
			for( var j=i+1; j<nRowCount; j++)
			{
				if (!this.isValidRow(j)) continue;
				var pkValue2="";
				for( var pki=0; pki<pkArr.length; pki++)
				{
					pkValue2+=this.getItemString(j,pkArr[pki]);
				}
				if (pkValue==pkValue2)
				{
					this.selectRow(i);
					alert( "��"+(i+1)+","+(j+1)+"����¼�ؼ����ظ���\n�ؼ����ֶΣ�"+pkTitle );
					return false;
				}
			}
		}
		return true;
	}
}

/**
 * �������ݴ����е�һ���ֶε�disabled���ԡ���ʱֻ��form���ʹ�����Ч��
 * @param colName �ֶ���
 * @return
 */
function DataWindow_disableColumn2( colName )
{
	if (this.type=="form")
	{
		var colObj=this.getColumnObject( 1, colName );
		if (colObj==null)
		{
			if (DataWindow.isDebug) DataWindow_log("���ܶԲ����ڵĶ�������disabled���ԣ�\n�ֶ���Ϊ��"+colName);
			return false;
		}
		else
		{
			colObj.disabled2="true";
		}
	}
}


/**
 * �������ݴ����е�һ���ֶε�disabled���ԡ���ʱֻ��form���ʹ�����Ч��
 * @param colName �ֶ���
 * @return
 */
function DataWindow_disableColumn( colName )
{
	if (this.type=="form")
	{
		var colObj=this.getColumnObject( 1, colName );
		if (colObj==null)
		{
			if (DataWindow.isDebug) DataWindow_log("���ܶԲ����ڵĶ�������disabled���ԣ�\n�ֶ���Ϊ��"+colName);
			return false;
		}
		else
		{
			//if ((colObj.tagName.toLowerCase()=="input" && (colObj.type=="text" || colObj.type=="password")) || colObj.tagName.toLowerCase()=="textarea")
			//	colObj.readOnly=true;
			//else
				colObj.disabled=true;
		}
	}
}


/**
 * ȡ�����ݴ�����һ���ֶε�disabled���ԡ���ʱֻ��form���ʹ�����Ч��
 * @param colName �ֶ���
 * @return
 */
function DataWindow_enableColumn2( colName )
{
	if (this.type=="form")
	{
		var colObj=this.getColumnObject( 1, colName );
		if (colObj==null)
		{
			if (DataWindow.isDebug) DataWindow_log("���ܶԲ����ڵĶ�������enable���ԣ�\n�ֶ���Ϊ��"+colName);
			return false;
		}
		else colObj.disabled2="false";
	}
}



/**
 * ȡ�����ݴ�����һ���ֶε�disabled���ԡ���ʱֻ��form���ʹ�����Ч��
 * @param colName �ֶ���
 * @return
 */
function DataWindow_enableColumn( colName )
{
	if (this.type=="form")
	{
		var colObj=this.getColumnObject( 1, colName );
		if (colObj==null)
		{
			if (DataWindow.isDebug) DataWindow_log("���ܶԲ����ڵĶ�������enable���ԣ�\n�ֶ���Ϊ��"+colName);
			return false;
		}
		else
		{
			if (colObj.disabled2==undefined || colObj.disabled2=="false")
			{
				//if ((colObj.tagName.toLowerCase()=="input" && (colObj.type=="text" || colObj.type=="password")) || colObj.tagName.toLowerCase()=="textarea")
				//	colObj.readOnly=false;
				//else
					colObj.disabled=false;
			}
		}
	}
}

/**
 * ��һ�����ݴ����е����пؼ�����disabled���ԡ���ʱֻ��form������Ч��
 * @return
 */
function DataWindow_disableColumnAll()
{
	if (this.type=="form")
	{
		var elementsArr=this.dw.elements;
		for( i=0; i<elementsArr.length; i++ )
		{
			elementObj=elementsArr.item(i);
			this.disableColumn( elementObj.id );
		}
	}
}

/**
 * ��һ�����ݴ����е����пؼ�ȡ��disabled���ԡ���ʱֻ��form������Ч��
 * @return
 */
function DataWindow_enableColumnAll()
{
	if (this.type=="form")
	{
		var elementsArr=this.dw.elements;
		for( i=0; i<elementsArr.length; i++ )
		{
			elementObj=elementsArr.item(i);
			this.enableColumn( elementObj.id );
		}
	}
}

/**
 * �������ݴ�����ĳ���ֶ����ݿ������ֹ�¼��ѡ��������������б���
 * @param colName �ֶ��� 
 */
function DataWindow_enableInputOption(colName)
{
	if (this.type=="form")
	{
		var colObj=this.getColumnObject( 1, colName );
		if (colObj==null)
		{
			if (DataWindow.isDebug) DataWindow_log("���ܶԲ����ڵĶ���enableInputOption��\n�ֶ���Ϊ��"+colName);
			return false;
		}
		//ֻ���������Ŀؼ�����
		if (colObj.tagName.toLowerCase()=="input" && colObj.fieldtype=="select" )
			colObj.canInputOption="true";
	}
}

/**
 * �������ݴ�����ĳ���ֶ����ݲ������ֹ�¼��ѡ��������������б���
 * @param colName �ֶ���
 */
function DataWindow_disableInputOption(colName)
{
	if (this.type=="form")
	{
		var colObj=this.getColumnObject( 1, colName );
		if (colObj==null)
		{
			if (DataWindow.isDebug) DataWindow_log("���ܶԲ����ڵĶ���disableInputOption��\n�ֶ���Ϊ��"+colName);
			return false;
		}
		if (colObj.tagName.toLowerCase()=="input" && colObj.fieldtype=="select" )
			colObj.canInputOption="false";
	}
}


/**
 * ����һ���ֶο���Ϊ��ֵ
 * @param colName �ֶ���
 */
function DataWindow_enableColumnNull( colName )
{
	if (this.type=="form")
	{
		var colObj=this.getColumnObject( 1, colName );
		if (colObj==null)
		{
			if (DataWindow.isDebug) DataWindow_log("���ܶԲ����ڵĶ���enableNull��\n�ֶ���Ϊ��"+colName);
			return false;
		}
		else colObj.isNull="true";

		var colLabel=this.getColumnLabel( 1, colName );
		if (colLabel==null)
		{
			if (DataWindow.isDebug) DataWindow_log("�޷����ָ���ֶεı�ǩ����\n�ֶ���Ϊ��"+colName);
			return false;
		}
		else colLabel.style.color="black";
	}
}


/**
 * ������һ���ֶε�ֵΪ��ֵ
 * @param colName �ֶ���
 */
function DataWindow_disableColumnNull( colName )
{
	if (this.type=="form")
	{
		var colObj=this.getColumnObject( 1, colName );
		if (colObj==null)
		{
			if (DataWindow.isDebug) DataWindow_log("���ܶԲ����ڵĶ���disableNull��\n�ֶ���Ϊ��"+colName);
			return false;
		}
		else colObj.isNull="false";
		var colLabel=this.getColumnLabel( 1, colName );
		if (colLabel==null)
		{
			if (DataWindow.isDebug) DataWindow_log("�޷����ָ���ֶεı�ǩ����\n�ֶ���Ϊ��"+colName);
			return false;
		}
		else colLabel.style.color="red";
	}
}

/**
 * �����������ݴ��ڵ�OldValue���ԡ�
 * ���������ύ�ɹ��󣬵ڶ����ύ��ʱ��������޸ģ�
 * oldvalue���ݵ���̨��Ȼ���޸�ǰ�����ݣ������Ǹոմ��̵��޸ĺ����ݡ�
 */
function DataWindow_reInitOldValue()
{
	if (this.type=="form")
	{
		elementObj=null;
		var elementsArr=this.dw.elements;
		for( i=0; i<elementsArr.length; i++ )
		{
			elementObj=elementsArr.item(i);
			elementObj.oldvalue=elementObj.value;
		}
	}
	else
	{
		var nRowCount=this.RowCount();
		for (var i=nRowCount-1; i>=0; i-- )
		{
			this.dw.rows(i).className="gridEvenRow";	//�����ѡ����к�
			this.dw.rows(i).OldValueStr=this.dw.rows(i).ValueStr;
			if (this.dw.rows(i).operate=="insert")
				this.dw.rows(i).operate="update";
			else if (this.dw.rows(i).operate=="delete")
				this.dw.rows(i).removeNode(true);
		}
		if (this.dw.rows.length>0)
		{
			this.dw.activeIndex=0;
			this.selectRow(0);
		}
		else
			this.dw.activeIndex=-1;
	}
}

/**
 * �ж�DataWindow�е�ֵ�Ƿ��Ѿ��޸Ĺ���
 * 
 */
function DataWindow_isModified()
{
	var isModified=false;
	if (this.type=="form")
	{
		elementObj=null;
		var elementsArr=this.dw.elements;
		for( i=0; i<elementsArr.length; i++ )
		{
			elementObj=elementsArr.item(i);
			if (elementObj.value!=elementObj.oldvalue)
			{
				if (DataWindow.isDebug)
				{
					DataWindow_log( "�ֶ��������޸ģ�\n�ֶ���Ϊ��"+elementObj.id
						+"\nvalue="+elementObj.value
						+" oldvalue="+elementObj.oldValue );
				}
				isModified=true;
				break;
			}
		}
	}
	else
	{
		var nRowCount=this.RowCount();
		for (var i=0; i<nRowCount; i++ )
		{
			if (this.dw.rows(i).operate=="insert"
				|| this.dw.rows(i).operate=="delete")
			{
				isModified=true;
				break;
			}
			else if (this.dw.rows(i).operate=="update"
					&& this.dw.rows(i).OldValueStr!=this.dw.rows(i).ValueStr)
			{
				if (DataWindow.isDebug)
				{
					DataWindow_log( "�������޸ģ�\n�к�Ϊ��"+i
						+"ValueStr="+this.dw.rows(i).ValueStr
						+"OldValueStr="+this.dw.rows(i).OldValueStr );
				}
				isModified=true;
				break;
			}
		}
	}
	return isModified;
}

/**
 * ȡ��grid�������ݴ���ĳһ�м�¼��operation���ԡ�
 * operation����������ʾ�Ե�ǰ��¼�Ĳ������͡�
 * insert, update, delete��ʾ�Ѿ��޸Ĺ����С�""��ʾû�в������С�
 */
function DataWindow_getOperate( rowNum )
{
	if (this.type=="form")
		return null;
	if (rowNum<0 && rowNum>=this.dw.rows.length) return null;
	return this.dw.rows(rowNum).operate;
}

/**
 * ���һ����¼�Ƿ�Ϊ��Ч��¼
 */
function DataWindow_isValidRow( rowNum )
{
	if (this.getOperate(rowNum)=="delete")
		return false;
	else
		return true;
}

function DataWindow_disposeComboBox()
{
	if (this.type=="form")	//���ComboBox����ռ���ڴ�
	{
		elementObj=null;
		var elementsArr=this.dw.elements;
		for( i=0; i<elementsArr.length; i++ )
		{
			elementObj=elementsArr.item(i);
			if (elementObj.tagName.toLowerCase()=="input" && elementObj.fieldtype=="select")
			{
				if (elementObj.comboBox!=undefined)
				{
					elementObj.comboBox.dispose();
					elementObj.comboBox=null;
				}
			}
		}
	}
}

/**
 * �ͷ�DataWindow������ڴ�
 */
function DataWindow_dispose()
{
	//this.disposeComboBox();
	this.dw = null;
	this.type = null;
	this.ownerDoc = null;
	this.ownerWin = null;
	if (this.type=="grid")
	{
		this.fieldArr=null;
		this.columnCount=null;
	}
	/*
	this.getColumnObject  = null;
	this.RowCount  = null;
	this.getColumnIndex  = null;
	this.getSelectRow  = null;
	this.getSelectRows  = null;
	this.fireEvent  = null;
	this.selectRow  = null;
	this.insertRow  = null;
	this.editRow  = null;
	this.deleteRow  = null;
	this.cancelRow  = null;
	this.getItemDisplay  = null;
	this.getItemString  = null;
	this.getItemExpand  = null;
	this.setItemString  = null;
	this.getItemInt  = null;
	this.setItemInt  = null;
	this.getItemFloat  = null;
	this.setItemFloat  = null;
	this.validate  = null;
	this.validatePrimaryKey  = null;
	this.disableColumn  = null;
	this.enableColumn  = null;
	this.disableColumn2  = null;
	this.enableColumn2  = null;
	this.disableColumnAll  = null;
	this.enableColumnAll  = null;
	this.enableColumnNull  = null;
	this.disableColumnNull  = null;
	this.enableInputOption  = null;
	this.disableInputOption  = null;
	this.getColumnLabel  = null;
	this.getColumnCName  = null;
	this.setOnChangeHandler  = null;
	this.setEventHandler  = null;
	this.removeOnChangeHandler  = null;
	this.removeEventHandler  = null;
	this.setColumn  = null;
	this.reInitOldValue  = null;
	this.isModified  = null;
	this.getOperate  = null;
	this.isValidRow  = null;
	this.disposeComboBox = null;

	this.dispose  = null;

	DataWindow.getColumnValue  = null;
	DataWindow.setColumnValue  = null;
	DataWindow.isDate  = null;
	DataWindow.validateColumn  = null;
	DataWindow.validateAlert  = null;
	DataWindow.RTrim  = null;
	DataWindow.getRealLength  = null;
	DataWindow.DateAdd  = null;
	DataWindow.setFocus  = null;

	DataWindow.isDebug  = null;
	DataWindow.alertMsg = null;
	*/
}


//////////////////////////////////////////////////////////////////////
/////////////////////// Debug Function ///////////////////////////////
// Using Debug.js Function. If release, no debug
function DataWindow_log( v )
{
	if (typeof(Debug_log)=="function")
		Debug_log( v );
	else
		window.status=v;
}

function DataWindow_error( v )
{
	if (typeof(Debug_error)=="function")
	{
		window.status=v;
		Debug_error( v );
	}
	else
		window.status=v;
}
/////////////////////// Debug Function ///////////////////////////////
//////////////////////////////////////////////////////////////////////

