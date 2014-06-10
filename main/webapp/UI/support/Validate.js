/**
 * ͨ�ú���������Id���ж�ָ��Tabҳ�����Ƿ���Ч��
 * ������ݴ�������
 * @param Id ����Id
 */
function validateTabPage( Id )
{
	var objError;
	objError=eval( "dw_"+Id+".validate( true )");
	if (objError!=null)
	{
		tp.selectTabPageById(Id);
		alert( DataWindow.alertMsg );
		DataWindow.setFocus(objError);
		return false;
	}
	else
		return true;
}


/**
 * У�龭Ӫ�����Ƿ���Ч��������Ϊ��yyqx1 < yyqx2 & yyqx2 > current Date
 *
 * @return boolean
 *  true ��Ч
 *  false ��Ч
 */
function validateYYQX()
{
	var ls_YYQX1=dw_jb.getItemString(0,"YYQX1");
	var ls_YYQX2=dw_jb.getItemString(0,"YYQX2");
	if (ls_YYQX2!="")
	{
		if (dw_jb.getItemString(0,"YYQX2")<dw_jb.getItemString(0,"YYQX1"))
		{
			alert( "��Ӫ���޷�Χ���ԣ���Ӫ���޽���ʱ��С�ھ�Ӫ������ʼʱ�䣡" );
			tp.selectTabPageById("jb");
			dw_jb.setColumn( "YYQX2" );
			return false;
		}
		if (ls_YYQX2<is_CURDATE)
		{
			alert( "Ӫҵ������С�ڵ�ǰ���ڣ����飡" );
			tp.selectTabPageById("jb");
			dw_jb.setColumn( "YYQX2" );
			return false;
		}
	}
	return true;
}

/**
 * У�龭Ӫ�����Ƿ���Ч��������Ϊ��zzqxq < zzqxz & zzqxz > current Date
 *
 * @return boolean
 *  true ��Ч
 *  false ��Ч
 */
function validateZZQX()
{
	if (is_QYLB=="2")
	{
		var ls_ZZQXQ=dw_jb.getItemString(0,"ZZQXQ");
		var ls_ZZQXZ=dw_jb.getItemString(0,"ZZQXZ");
		if (ls_ZZQXZ!="")
		{
			if (ls_ZZQXZ<ls_ZZQXQ)
			{
				tp.selectTabPageById("jb");
				alert( "ִ����Ч���޷�Χ���ԣ����޽���ʱ��С����ʼʱ�䣡" );
				dw_jb.setColumn("ZZQXZ");
				return false;
			}
			if (ls_ZZQXZ<is_CURDATE)
			{
				alert( "ִ�����޽�ֹ����С�ڵ�ǰ���ڣ����飡" );
				tp.selectTabPageById("jb");
				dw_jb.setColumn("ZZQXZ");
				return false;
			}
		}
	}
	return true;
}

/**
 * �����ʱ������һ�����ʼ�¼�������ǣ� qxz > current Date
 *
 * @return boolean
 *  true ��Ч
 *  false ��Ч
 */
function validateCZQX()
{
	var nCount=dw_cz.RowCount();
	if (nCount>0)
	{
		var ls_MaxQS=findMaxValue( dw_cz, "QS" );
		var nRow    =findRow(dw_cz,"QS",ls_MaxQS);
		var ls_QXZ=dw_cz.getItemString(nRow,"QXZ");
		if (ls_QXZ<is_CURDATE)
		{
			tp.selectTabPageById("cz");
			alert( "����������С�ڵ�ǰ���ڣ����飡" );
			dw_cz2.setColumn("QXZ");
			return false;
		}
	}
	return true;
}

/**
 * ���GS��SYQY�ĳ��ʶ�����Ƿ���Ч�������ǣ����˹ɶ�����Ȼ�˹ɶ�����֮��=ZCZB
 *
 * @return boolean
 *  true ��Ч
 *  false ��Ч
 */
function validateCZE()
{
	if (CV_isChecked("CZE",hashCode(""))!=-1) return true;	//�Ѿ�������
	if (is_QYLXDL!="GS" && is_QYLXDL!="SYQY" ) return true;
	var ld_total_cze=0;
	var nFRCount=dw_fr.RowCount();
	for (var i=0; i<nFRCount; i++)
	{
		if (!dw_fr.isValidRow(i)) continue;
		ld_total_cze+=dw_fr.getItemFloat(i,"CZE");
	}
	var nZRCount=dw_zr.RowCount();
	for( var i=0; i<nZRCount; i++)
	{
		if (!dw_zr.isValidRow(i)) continue;
		ld_total_cze+=dw_zr.getItemFloat(i,"CZE");
	}
	var ld_zczb=dw_jb.getItemFloat(1,"ZCZB");

	ld_zczb=new Number(ld_zczb).toFixed(2);
	ld_total_cze=new Number(ld_total_cze).toFixed(2);
	
	if (ld_zczb>0)
	{
		if (ld_total_cze!=ld_zczb)
		{
			var ls_Msg="ע���ʱ������ڷ��˹ɶ�����Ȼ�˹ɶ����ʶ��ܺͣ��Ƿ�����ͨ����\nע���ʱ�������Ԫ����"+ld_zczb+"  �����ܺͣ�����Ԫ����"+ld_total_cze;
			if (confirm(ls_Msg))
			{
				CV_fillOne( is_BH, "CZE", hashCode(""), "0", "ע���ʱ������ڷ��˹ɶ�����Ȼ�˹ɶ����ʶ��ܺͣ�������ͨ����顣\nע���ʱ�������Ԫ����"+ld_zczb+"  �����ܺͣ�����Ԫ����"+ld_total_cze );
				return true;
			}
			else
			{
				if (nFRCount>0) tp.selectTabPageById("fr");
				else tp.selectTabPageById("zr");
				return false;
			}
		}
	}
	return true;
}

/**
 * У��ע���ʱ��Ƿ���Ч��������Ϊ��zczb > 0
 *
 * @return boolean
 *  true ��Ч
 *  false ��Ч
 */
function validateZCZB()
{
	//FGS, WZBS, WGDBû��ע���ʱ�¼����Ŀ����������
	if (is_QYLXDL=="FGS" || is_QYLXDL=="WZBS" || is_QYLXDL=="WGDB" ) return true;
	var ld_zczb=dw_jb.getItemFloat(0,"ZCZB");
	var ls_zczbname=dw_jb.getColumnCName("ZCZB");
	if (ld_zczb<0)
	{
		tp.selectTabPageById("jb");
		alert( ls_zczbname+'�����Ǹ�����');
		dw_jb.setColumn("ZCZB");
		return false;
	}
	if (is_QYLXDL=="WZQY" && isWZDZ(is_QYLX))
	{
		if (dw_jb.getItemFloat(1,"WFRJCZMY")!=dw_jb.getItemFloat(1,"ZCZBMY"))
		{
			tp.selectTabPageById("jb");
			alert("������ҵע���ʱ�Ӧ�õ����ⷽ�Ͻɶ���飡");
			dw_jb.setColumn("ZCZB");
			return false;
		}
	}
	return true;
}

/**
 * GS,SYQY,SYFZ����Ͻ��ʱ��Ƿ���Ч��������Ϊ��sjzb<=zczb
 * WZQYУ���ⷽ�Ͻɳ��ʶ��Ƿ���Ч��������Ϊ��wfrjczmy<=zczbmy
 *
 * @return boolean
 *  true ��Ч
 *  false ��Ч
 */
function validateSJZB()
{
	switch( is_QYLXDL )
	{
		case "GS":
		case "SYQY":
		case "SYFZ":
			if (dw_jb.getItemFloat(1,"SJZB")>dw_jb.getItemFloat(1,"ZCZB"))
			{
				tp.selectTabPageById("jb");
				alert( "�Ͻ��ʱ������ע���ʱ�����޸�!" );
				dw_jb.setColumn("SJZB");
				return false;
			}
			break;
		case "WZQY":
			if (dw_jb.getItemFloat(1,"WFRJCZMY")>dw_jb.getItemFloat(1,"ZCZBMY"))
			{
				tp.selectTabPageById("jb");
				alert( "�ⷽ�Ͻ��ʱ������ע���ʱ�����޸�!" );
				dw_jb.setColumn("WFRJCZE");
				return false;
			}
			break;
	}
	return true;
}

/**
 * У����ʱ���Ϣ�Ƿ���Ч��������Ϊ��
 * ÿ����¼����⣬wfmy<=wfyj �ⷽʵ����Ԫ<=�ⷽӦ����Ԫ
 *
 * @return boolean
 *  true ��Ч
 *  false ��Ч
 */
function validateCZQK()
{
	var ld_wfycz=0;
	var ld_zfycz=0;
	//����������ҵ��������ȷ
	if (is_QYLXDL!="WZQY")	return true;
	var nCZCount=dw_cz.RowCount();
	for( var i=0; i<nCZCount; i++)
	{
		if (dw_cz.getItemFloat(i,"WFYJ")<dw_cz.getItemFloat(i,"WFMY"))
		{
			tp.selectTabPageById("cz");
			dw_cz.selectRow(i);
			alert( "�ⷽʵ�����ۺ���Ԫ��ӦС���ⷽӦ���ʣ����޸�" );
			dw_cz2.setColumn("WFMY");
			return false;
		}
		if (dw_cz.getItemFloat(i,"ZFYJ")<dw_cz.getItemFloat(i,"ZFMY"))
		{
			tp.selectTabPageById("cz");
			dw_cz.selectRow(i);
			alert( "�з�ʵ�����ۺ���Ԫ��ӦС���з�Ӧ���ʣ����޸�" );
			dw_cz2.setColumn("ZFMY");
			return false;
		}
		ld_wfycz+=dw_cz.getItemFloat(i,"WFYJ");
		ld_zfycz+=dw_cz.getItemFloat(i,"ZFYJ");
	}
	if (CV_isChecked("CZQK",hashCode(""))!=-1) return true; //�Ѿ�������
	var ld_wfrjczmy=dw_jb.getItemFloat(1,"WFRJCZMY");
	if (Math.abs(ld_wfrjczmy-ld_wfycz/1)>0)
	{
		var ls_Msg="�ⷽӦ���ʲ����ڻ���������ⷽ�Ͻɶ��ۺ���Ԫ��������ͨ����\nӦ�����ܺ�Ϊ��"+ld_wfycz+"  �ⷽ�Ͻɶ�����ԪΪ��"+ld_wfrjczmy;
		if (confirm(ls_Msg))
		{
			CV_fillOne(is_BH, "CZQK", hashCode(""), "0", "�ⷽӦ���ʲ����ڻ���������ⷽ�Ͻɶ��ۺ���Ԫ����������ͨ����顣\nӦ�����ܺ�Ϊ��"+ld_wfycz+"  �ⷽ�Ͻɶ�����ԪΪ��"+ld_wfrjczmy );
			return true;
		}
		else
		{
			tp.selectTabPageById("cz");
			dw_cz2.setColumn("WFYJ");
			return false;
		}
	}
	return true;
}


/**
 * У��Ͷ���ܶ��Ƿ���Ч��������Ϊ������Ƕ�����ҵ��tzzemy=zczbmy
 *
 * @return boolean
 *  true ��Ч
 *  false ��Ч
 */
function validateTZZE()
{
	var ls_qylx=dw_jb.getItemString(0,"QYLX");
	if (is_QYLXDL=="WZQY" && !isWZGF(is_QYLX))	//�ǹɷݹ�˾
	{
		if (dw_jb.getItemFloat(1,"TZZEMY")<dw_jb.getItemFloat(1,"ZCZBMY"))
		{
			tp.selectTabPageById("jb");
			alert("Ͷ���ܶ�С��ע���ʱ������飡");
			dw_jb.setColumn("TZZE");
			return false;
		}
	}
	return true;
}


/**
 * ��ⷨ�˹ɶ��ķ����������Ƿ��������Ȼ�˹ɶ��С�
 *
 * @return boolean
 *  true ��Ч
 *  false ��Ч
 */
function validateFRFDDBR()
{
	if (tp.existTabPageById("fr") && tp.existTabPageById("zr"))	//���߶����ڣ��ż��
	{
		var nFRCount=dw_fr.RowCount();
		for( var i=0; i<nFRCount; i++ )
		{
			var ls_fddbr=dw_fr.getItemString(i,"FDDBR");
			var nZRCount=dw_zr.RowCount();
			for( var j=0; j<nZRCount; j++ )
			{
				if (!dw_zr.isValidRow(j)) continue;
				var ls_xm=dw_zr.getItemString(j,"XM");
				if (dw_fr.isValidRow(i) &&ls_xm==ls_fddbr)
				{
					tp.selectTabPageById("fr");
					dw_fr.selectRow(i);
					alert("���˹ɶ��ķ��������˳�������Ȼ�˹ɶ�����飡\n�÷��������˵�����Ϊ��"+ls_fddbr);
					dw_fr2.setColumn("FDDBR");
					return false;
				}
			}
		}
		return true;
	}
	return true;
}

/**
 * ��⹫˾���»��Ա�����Ƿ���ȫ
 *
 * @return boolean
 *  true ��Ч
 *  false ��Ч
 */
function validateGSDSH()
{
	if (is_QYLXDL=="GS" || is_QYLXDL=="NZFR" || is_QYLXDL=="SYQY" || is_QYLXDL=="WZQY" )
	{
		var QYLXTEXT=dw_jb.getItemExpand(1,"QYLX","text");
		if (QYLXTEXT.indexOf("����")>=0 && QYLXTEXT.indexOf("��˾")>=0 )
		{
			var ZWSArr=new Array(0,0,0,0,0);	//0���¡�1����2���¡�3���³���4ִ�ж���
			nDSCount=dw_ds.RowCount();
			for( var i=0; i<nDSCount; i++ )
			{
				if (!dw_ds.isValidRow(i)) continue;
				var sZW=dw_ds.getItemString(i,"ZW");
				switch( sZW )
				{
					case "01":	//01���³�
						ZWSArr[0]=ZWSArr[0]+1;
						ZWSArr[3]=ZWSArr[3]+1;
						break;
					case "02":  //02�����³�
					case "11":	//11����
						ZWSArr[0]=ZWSArr[0]+1;
					   	break;
					case "03":  //03�ܾ���
					case "04":  //04���ܾ���
					case "12":	//12����
						ZWSArr[1]=ZWSArr[1]+1;
					   	break;
					case "05":  //05ִ�ж���
						ZWSArr[0]=ZWSArr[0]+1;
						ZWSArr[4]=ZWSArr[4]+1;
						break;
				 	case "06":	//06���� 	
				 	case "13":	//13�����ټ���
					   ZWSArr[2]=ZWSArr[2]+1;
					   var ls_XM=dw_ds.getItemString(i, "XM");
					   for( var j=0; j<nDSCount; j++)
					   {	//���±����Ƕ�����,���³�/�ܾ���/ִ�ж��²��ܼ��μ���,����û�м��
					   		var ls_XM2=dw_ds.getItemString(j,"XM");
					   		var ls_ZW2=dw_ds.getItemString(j,"ZW");
					   		if (ls_XM==ls_XM2 && (ls_ZW2=="01"||ls_ZW2=="03"||ls_ZW2=="05"||ls_ZW2=="14"||ls_ZW2=="15"))
					   		{
					   			alert( "���³����ܾ���ִ�ж��²��ܼ��μ��£�" );
								tp.selectTabPageById("ds");
								dw_ds.selectRow(j);
					   			return false;
					   		}
					   }
					   break;
					case "14":	//14���³����ܾ���
						ZWSArr[0]=ZWSArr[0]+1;
						ZWSArr[3]=ZWSArr[3]+1;
						ZWSArr[1]=ZWSArr[1]+1;
						break;
					case "15":	//15ִ�ж��¼��ܾ���
						ZWSArr[0]=ZWSArr[0]+1;
						ZWSArr[4]=ZWSArr[4]+1;
						ZWSArr[1]=ZWSArr[1]+1;
						break;
				}
			}
			if (is_QYLXDL=="WZQY" && isWZDZ(is_QYLX))	//���̶�����ҵ
			{
				//ֻ��һ��ִ�ж��£�����ͨ��
				if (dw_ds.RowCount()==1 && ZWSArr[4]==1) return true;
			}
			if (ZWSArr[3]>1)
			{
				tp.selectTabPageById("ds");
				alert( "���³������ж�����" );
				return false;
			}
			if (ZWSArr[3]>0 && ZWSArr[0]<3)	//�ж��³������Ҷ���<3��
			{
				tp.selectTabPageById("ds");
				alert( "�ж��³�ʱ�����»��Ա������Ҫ���ˣ�" );
				return false;
			}
			if (ZWSArr[0]==0)
			{
				tp.selectTabPageById("ds");
				alert("���޹�˾��Ա����Ҫ�ж��£�")
				return false;
			}
			if (ZWSArr[1]==0)
			{
				tp.selectTabPageById("ds");
				alert("���޹�˾��Ա����Ҫ�о���");
				return false;
			}
		   	if (ZWSArr[2]==0)
		   	{
				tp.selectTabPageById("ds");
				alert("���޹�˾��Ա����Ҫ�м��£�");
				return false;
			}
		}
	}
	return true;
}


/**
 * ��������ҵ���м�飺��Ͻ��λ�͸�������������Ĺ�Ͻ��λ�����жϡ�
 *
 */
function vlidateGXDW()
{
	if (CV_isChecked("GXDW",hashCode(""))!=-1) return true;	//�Ѿ�������
	if (is_QYLB!="2") return true;
	var ls_QXM=dw_jb.getItemString(0,"QXM");
	var ls_QXMEXPAND=dw_jb.getItemExpand(1,"QXM");
	var dqdmArr=ls_QXMEXPAND.split("-");
	if (dqdmArr.length==0) return true;	//ͨ�����
	var ls_GXDW=dw_jb.getItemString(0,"GXDW");
	if (ls_GXDW!=dqdmArr[0])
	{
		var ls_msg="����������ȷ���Ĺ�Ͻ��λ�����ڵ�ѡ��һ�£��Ƿ�����ͨ����飿\n"+
				"���ݸ���ҵ����������" + ls_QXM + "������ҵ�Ĺ�Ͻ��λӦ���ǣ�" + dqdmArr[0] +"\n"+
				"���е�ֵ��Ͻ��λ�ǣ�"+ls_GXDW;
		if (confirm(ls_msg))
		{
			CV_fillOne(is_BH, "GXDW", hashCode(""), "0",
				"����������ȷ���Ĺ�Ͻ��λ�����ڵ�ѡ��һ�£�������ͨ����顣\n"+
				"���ݸ���ҵ����������" + ls_QXM + "������ҵ�Ĺ�Ͻ��λӦ���ǣ�" + dqdmArr[0] +"\n"+
				"���е�ֵ��Ͻ��λ�ǣ�"+ls_GXDW);
			return true;
		}
		else
		{
			tp.selectTabPageById("jb");
			return false;
		}
	}
	return true;
}

/**
 * ��������������Ƿ��ͻ��
 *
 * @return boolean
 *  true ��Ч
 *  false ��Ч
 */
function validatePrimaryKey( tpName )
{
	switch( tpName )
	{
		case "sp":	//�����ļ�wjmc
			if (!dw_sp.validatePrimaryKey("WJMC")) { tp.selectTabPageById("sp"); return false; }
			break;
		case "tz":	//Ͷ��˫��qymc
			if (!dw_tz.validatePrimaryKey("QYMC")) { tp.selectTabPageById("tz"); return false; }
			break;
		case "cz":	//�������qs
			if (!dw_cz.validatePrimaryKey("QS")) { tp.selectTabPageById("cz"); return false; }
			break;
		case "fr":	//���˹ɶ�qymc
			if (!dw_fr.validatePrimaryKey("QYMC")) { tp.selectTabPageById("fr");return false; }
			break;
		case "zr":	//��Ȼ�˹ɶ�xm,zjhm
			if (!dw_zr.validatePrimaryKey("XM,ZJHM")) { tp.selectTabPageById("zr");return false; }
			break;
		case "ds":	//���»��Ա��������ҵ��Աxm,zw,zjhm
			if (!dw_ds.validatePrimaryKey("XM,ZW,ZJHM")) { tp.selectTabPageById("ds");return false; }
			break;
		case "fz":	//��֧����qymc
			if (!dw_fz.validatePrimaryKey("QYMC")) { tp.selectTabPageById("fz");return false; }
			break;
		case "wt":	//��Ȩί����wtrlx
			if (!dw_wt.validatePrimaryKey("WTRLX")) { tp.selectTabPageById("wt");return false; }
			break;
		case "yj":	//�����xh
			if (!dw_yj.validatePrimaryKey("XH")) { tp.selectTabPageById("yj");return false; }
			break;
		case "jc":	//���������lx,xh
			if (!dw_jc.validatePrimaryKey("LX,XH")) { tp.selectTabPageById("jc");return false; }
			break;
		default:
			return true;
			break;
	}
	return true;
}

/**
 * У���¼���Ƿ������������������Щ��ҵ������дĳЩ��¼��
 *
 * @return boolean
 *  true ��Ч
 *  false ��Ч
 */
function validateRowCount()
{
	var ls_QYLB=dw_jb.getItemString(0,"QYLB");
	if (ls_QYLB=="1")
	{
		if (tp.existTabPageById("sp"))
		{
			if (dw_sp.RowCount(true)==0)
			{
				alert("����ҵ�������ļ�û��¼�룬�������룡");
				tp.selectTabPageById("sp");
				return false;
			}
		}
		if (tp.existTabPageById("fr") && tp.existTabPageById("zr"))
		{
			if (dw_fr.RowCount(true)==0 && dw_zr.RowCount(true)==0)
			{
				alert("����ҵ�Ĺɶ���Ϣû��¼�룬����¼�룡");
				tp.selectTabPageById("fr");
				return false;
			}
		}
		if (tp.existTabPageById("ds"))
		{
			if (dw_ds.RowCount(true)==0)
			{
				alert("����ҵ�Ĺ�˾��Աû��¼�룬�������룡");
				tp.selectTabPageById("ds");
				return false;
			}
		}
		if (tp.existTabPageById("wt"))
		{
			if (dw_wt.RowCount(true)==0)
			{
				alert("����ҵ��ί����û��¼�룬�������룡" );
				tp.selectTabPageById("wt");
				return false;
			}
		}
	}
	else if (ls_QYLB=="2")
	{
		if (is_QYLXDL!="WGDB")	//������WGDB����������ļ�
		{
			if (tp.existTabPageById("sp"))
			{
				if (dw_sp.RowCount(true)==0)
				{
					alert("����ҵ�������ļ�û��¼�룬�������룡");
					tp.selectTabPageById("sp");
					return false;
				}
			}
		}
		if (tp.existTabPageById("tz"))
		{
			if (dw_tz.RowCount(true)==0)
			{
				alert("����ҵ��Ͷ�ʷ�û��¼�룬�������룡");
				tp.selectTabPageById("tz");
				return false;
			}
		}
		/*
		if (tp.existTabPageById("cz") && is_YWLX!="1")
		{
			if (dw_cz.RowCount(true)==0)
			{
				alert("����ҵ�ĳ������û��¼�룬�������룡");
				tp.selectTabPageById("cz");
				return false;
			}
		}
		*/
		if (tp.existTabPageById("ds"))
		{
			if (dw_ds.RowCount(true)==0)
			{
				alert("����ҵ����ҵ��Աû��¼�룬�������룡");
				tp.selectTabPageById("ds");
				return false;
			}
		}
	}
	return true;
}


/**
 * �����Ա��֤������
 */
function validateZJHM()
{
	var ls_ZJMC, ls_ZJHM;
	if (is_QYLXDL=="WGJY" || is_QYLXDL=="WGFZ" )
	{
		ls_ZJMC=dw_jb.getItemString(0, "FZRSFZJ");
		ls_ZJHM=dw_jb.getItemString(0, "FZRZJHM");
		if (ls_ZJMC=="01")	//���֤
			if (!validatezjhm(ls_ZJHM))
			{
				tp.selectTabPageById("jb");
				dw_jb.setColumn("FZRZJHM");
				return false;
			}
	}
	if (tp.existTabPageById("fzr"))
	{
		ls_ZJMC=dw_fzr.getItemString(0, "ZJMC");
		ls_ZJHM=dw_fzr.getItemString(0, "ZJHM");
		if (ls_ZJMC=="01")	//���֤
			if (!validatezjhm(ls_ZJHM))
			{
				tp.selectTabPageById("fzr");
				dw_fzr.setColumn("ZJHM");
				return false;
			}
	}
	if (tp.existTabPageById("ds"))
	{
		var nDSCount=dw_ds.RowCount();
		for( var i=0; i<nDSCount; i++)
		{
			ls_ZJMC=dw_ds.getItemString(i, "ZJMC");
			ls_ZJHM=dw_ds.getItemString(i, "ZJHM");
			if (ls_ZJMC=="01")	//���֤
				if (!validatezjhm(ls_ZJHM))
				{
					tp.selectTabPageById("ds");
					dw_ds.selectRow(i);
					dw_ds2.setColumn("ZJHM");
					return false;
				}
		}
	}
	if (tp.existTabPageById("wt"))
	{
		var nWTCount=dw_wt.RowCount();
		for( var i=0; i<nWTCount; i++)
		{
			ls_ZJMC=dw_wt.getItemString(i, "SFZJ");
			ls_ZJHM=dw_wt.getItemString(i, "ZJHM");
			if (ls_ZJMC=="01")	//���֤
				if (!validatezjhm(ls_ZJHM))
				{
					tp.selectTabPageById("wt");
					dw_wt.selectRow(i);
					dw_wt2.setColumn("ZJHM");
					return false;
				}
		}
	}
	return true;
}

/**
 * ��������������¼���� FLAG=0
 *
 */
function getNewYJCount()
{
	var nYJCount=dw_yj.RowCount();
	var nNewYJCount=0;
	for( var i=0; i<nYJCount; i++ )
	{
		if (dw_yj.isValidRow(i) && dw_yj.getItemString(i,"FLAG")=="0")	//���¼�¼�ˣ�����Ҫ���
		{
			nNewYJCount++;
			break;
		}
	}
	return nNewYJCount;
}


/**
 * ������������������ǿ�Ƽ���ģ����ͨ�������������
 */
function validateSPJCJG()
{
	var nJCCount=dw_jc.RowCount();
	for( var i=0; i<nJCCount;i++)
	{
		var JB=dw_jc.getItemString(i, "JB");
		var TG=dw_jc.getItemString(i, "TG");
		var YJ=dw_jc.getItemString(i, "YJ");
		if (dw_jc.isValidRow(i) && JB=="1" && TG=="1" && YJ=="")	//ǿ����������������ͨ�������Ҳû����д
		{
			tp.selectTabPageById("jc");
			dw_jc.selectRow(i);
			dw_jc2.setColumn("YJ");
			alert( "ǿ�Ƽ���ļ�����������Ҫͨ���������������");
			return false;
		}
	}
	return true;
}

/**
 * ���Ͷ��˫��
 */
function validateTZSF()
{
	if (is_QYLB!="2") return true;
	var ld_ZFCZ=0, ld_WFCZ=0;
	var nTZCount=dw_tz.RowCount();
	for( var i=0; i<nTZCount; i++)
	{
		if (!dw_tz.isValidRow(i)) continue;
		var ls_ZWBZ=dw_tz.getItemString(i,"ZWBZ");
		if ( ls_ZWBZ=="1" )	//�з�
		{
			var ls_QYLX=dw_tz.getItemString(i,"QYLX");
			if (ls_QYLX=="")
			{
				tp.selectTabPageById("tz");
				dw_tz.selectRow(i);
				alert("�з�Ͷ���˱���ѡ���з���ҵ���ͣ�");
				return false;
			}
			ld_ZFCZ=ld_ZFCZ+dw_tz.getItemFloat(i,"CZMY");
		}
		else
		{
			var ls_QYGB=dw_tz.getItemString(i,"QYGB");
			if (ls_QYGB=="")
			{
				tp.selectTabPageById("tz");
				dw_tz.selectRow(i);
				alert("�ⷽͶ���˱���ѡ���ⷽ����");
				return false;
			}
			ld_WFCZ=ld_WFCZ+dw_tz.getItemFloat(i,"CZMY");
		}
	}
	if (isWZDZ(is_QYLX) && ld_ZFCZ>0)	//���̶���
	{
		tp.selectTabPageById("tz");
		dw_tz.selectRow(i);
		alert( "������ҵ�������з�Ͷ���ˣ����޸ģ�" );
		return false;
	}
	//�����ܺ���ע���ʱ���Ƚ�
	if (CV_isChecked("TZSF",hashCode(""))==-1)	//û�м���
	{
		var ld_ZCZBMY=dw_jb.getItemFloat(0,"ZCZBMY");
		if (Math.abs(ld_ZCZBMY-ld_WFCZ-ld_ZFCZ)>0)
		{
			var ls_Msg="�����ܺͲ�����ע���ʱ����Ƿ�����ͨ����\nע���ʱ�������Ԫ����"+ld_ZCZBMY+"  �����ܺͣ�����Ԫ����"+(ld_WFCZ+ld_ZFCZ);
			if (confirm(ls_Msg))
			{
				CV_fillOne( is_BH, "TZSF", hashCode(""), "0", "�����ܺͲ�����ע���ʱ���������ͨ����顣\nע���ʱ�������Ԫ����"+ld_ZCZBMY+"  �����ܺͣ�����Ԫ����"+(ld_WFCZ+ld_ZFCZ) );
				return true;
			}
			else
			{
				tp.selectTabPageById("tz");
				return false;
			}
			return false;
		}
	}
	return true;
}
