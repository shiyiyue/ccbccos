package pams.common.enums;

/**
 * ���ϵͳ ����ö��.
 * User: zhanrui
 * Date: 2010-11-17
 * Time: 9:58:32
 * To change this template use File | Settings | File Templates.
 */
public enum ACEnum {

    SYSIDT_AC("8"),
    /*�˺�˳������*/
    ACTSEQ_AC("ACTSEQ"),
    RECSTS_VALID(" "),
    RECSTS_INVALID("I"),
    RECSTS_DEL("D"),
    RECSTS_TMP("T"),
    RECSTS_TMP1("1"),
    RECSTS_TMP2("2"),
    RECSTS_TMP3("3"),
    RECSTS_CLOSE("C"),
    RECSTS_TABLE("3"),
    RECSTS_ONLINE("2"),
    RECSTS_BATCH("0"),
    RECSTS_DOWNLOAD("1"),

    RECSTS_OAC_VALID(" "),
    RECSTS_OAC_INVALID("I"),
    RECSTS_OAC_DEL("D"),
    RECSTS_OAC_ACCESSED("A"),
    RECSTS_OAC_UPDATE("U"),
    RECSTS_OAC_OPEN("O"),
    RECSTS_OAC_CLOSE("C"),

    RECSTS_TVC_VALID(" "),         //����
    RECSTS_TVC_DEL("D"),           //��ɾ��
    RECSTS_TVC_INVALID("I"),       //��Ч
    RECSTS_TVC_REQUEST("R"),       //����
    RECSTS_TVC_SUCCESSBALANCE("S"),  //��ƽ�ɹ�

    //1-����8401DONE�� 2-����8402����ƽ�� 3-��ƽ��ɾ����
    VCHSTS_NEWRECORD("1"),
    VCHSTS_BALANCED("2"),
    VCHSTS_DELETED("3"),

    VCHSTS_TVC_INSTVC("1"),
    VCHSTS_TVC_DELTVC("2"),
    VCHSTS_TVC_DELTVC_SET("3"),
    VCHSTS_TVC_DELVCH("4"),
    VCHSTS_TVC_DELVCH_SET("5"),
    VCHSTS_TVC_INSVCH_SET("6"),

    ERYTYP_VCH_AUTO("A"),
    ERYTYP_VCH_TXN("T"),
    ERYTYP_VCH_IRSS("I"),
    ERYTYP_VCH_NORMAL("N"),
    ERYTYP_VCH_PAYSAV("D"),

    CURCDE_001("001"),
    CURCDE_011("011"),
    CURCDE_014("014"),
    CURFLG_TRUE("1"),
    CURFLG_FALSE("0"),

    TRMUNT_YEAR("Y"),
    TRMUNT_MONTH("M"),

    DCTYPE_1("1"),
    DCTYPE_2("2"),
    DCTYPE_3("3"),
    DCTYPE_4("4"),
    DCTYPE_5("5"),

    ALCTYP_1("1"),
    ALCTYP_2("2"),
    ALCTYP_3("3"),
    ALCTYP_4("4"),
    ALCTYP_5("5"),
    ALCTYP_6("6"),
    ALCTYP_7("7"),

    SUMFLG_TRUE("1"),
    SUMFLG_FALSE("0"),

    RECTYP_D("D"),
    RECTYP_M("M"),
    RECTYP_S("S"),
    RECTYP_Y("Y"),
    RECTYP_F("F"),

    XRTCDE_BUY("1"),
    XRTCDE_SELL("2"),
    XRTCDE_CASH("3"),
    XRTCDE_4("4"),
    XRTCDE_5("5"),
    XRTCDE_6("6"),
    XRTCDE_7("7"),
    XRTCDE_8("8"),
    XRTCDE_9("9"),

    ORGLVL_1("1"),
    ORGLVL_2("2"),
    ORGLVL_3("3"),
    ORGLVL_4("4"),
    ORGLVL_5("5"),

    PLCTYP_IN("1"),
    PLCTYP_OUT("2"),
    PLCTYP_PRO("3"),

    GLCCLS_1("1"),
    GLCCLS_2("2"),

    CLRFLG_TRUE("1"),
    CLRFLG_FALSE("0"),

    GLCODE_9300("9300"),
    GLCCAT_LOANS("1"),

    APCODE_9300("9301"),
    APCODE_9330("9331"),
    APCODE_9340("9341"),
    APCODE_7010("7011"),
    APCODE_9990("9991"),
    APCODE_790("3901"),
    APCODE_9048("9048"),

    ACTTYP_FXE("5"),
    ACTTYP_PLE("6"),
    ACTTYP_LON("2"),

    RVSLBL_TRUE("*"),
    RVSLBL_FALSE(" "),
    RVSLBL_CASH("C"),
    RVSLBL_TRAN("T"),
    RVSLBL_BUZH("B"),

    INTFLG_NO("0"),
    INTFLG_AIF("1"),
    INTFLG_TIF("2"),
    INTFLG_AIFCON("3"),
    INTFLG_AIF_4("4"),

    ACTSTS_VALID(" "),
    ACTSTS_INVALID("I"),

    BVASTS_TRUE("Y"),
    BVASTS_FALSE("N"),

    CMTNUM_NORMAL("008"),

    FNTMAK_TRUE("Y"),
    FNTMAK_FALSE("N"),

    WDYMAK_TRUE("Y"),
    WDYMAK_FALSE("N"),

    TDYMAK_TRUE("Y"),
    TDYMAK_FALSE("N"),

    YERMAK_TRUE("Y"),
    YERMAK_FALSE("N"),

    HYRMAK_TRUE("Y"),
    HYRMAK_FALSE("N"),

    QTRMAK_TRUE("Y"),
    QTRMAK_FALSE("N"),

    MONMAK_TRUE("Y"),
    MONMAK_FALSE("N"),

    IPYMAK_TRUE("Y"),
    IPYMAK_FALSE("N"),

    TAXMAK_TRUE("Y"),
    TAXMAK_FALSE("N"),

    PREMAK_TRUE("1"),
    PREMAK_FALSE("0"),

    MODFLG_TRUE("Y"),
    MODFLG_FALSE("N"),

    BVAFLG_REVERSE("1"),
    BVAFLG_BACK("2"),
    BVAFLG_LATE("3"),

    OACFLG_UPDATE("U"),
    OACFLG_OPEN("O"),
    OACFLG_CLOSE("C"),
    OACFLG_BOTH("B"),

    /*//�ʻ�״̬ SP-����,'I'-��Ч
    ACTSTS_NORMAL("SP"),
    ACTSTS_INVALID("|"),
*/
    REGSTS_NORMAL("0"),
    REGSTS_NOSAVE("1"),
    REGSTS_NODRAW("2"),
    REGSTS_NOOPER("3"),

    //�����ȡ�涨:1-ȫ����׼��2-ȫ����׼ȡ3-ȫ����׼��4-���ֲ�׼��5-���ֲ�׼ȡ
    FRZSTS_NORMAL("0"), 
    FRZSTS_NOSAVE("1"),
    FRZSTS_NODRAW("2"),
    FRZSTS_NOOPER("3"),
    FRZSTS_NOSAVE_1("4"),
    FRZSTS_NODRAW_1("5"),
    FRZSTS_RECORD("R"),

    GLCBAL_C("C"),
    GLCBAL_D("D"),
    GLCBAL_B("B"),
    GLCOCC_C("C"),
    GLCOCC_D("D"),
    GLCOCC_B("B"),
    GLCCCY_RMB("1"),
    GLCCCY_FX("2"),
    GLCCCY_ALL("3"),

    STMCYC_D("D"),
    STMCYC_T("T"),
    STMCYC_M("M"),
    STMCYC_S("S"),
    STMCYC_Y("Y"),
    STMCYC_P("P"),
    STMCYC_E("E"),
    STMCYC_F("F"),

    LEGCYC_D("D"),
    LEGCYC_T("T"),
    LEGCYC_M("M"),
    LEGCYC_S("S"),
    LEGCYC_Y("Y"),
    LEGCYC_P("P"),
    LEGCYC_E("E"),
    LEGCYC_F("F"),

    SLFLAG_LOANS("L"),
    SLFLAG_FIXDEP("S"),
    SLFLAG_TRFINT("T"),

    ATRCDE_A01("A01"),
    ATRCDE_A02("A02"),
    ATRCDE_A03("A03"),
    ATRCDE_A04("A04"),
    ATRCDE_A05("A05"),
    ATRCDE_A06("A06"),
    ATRCDE_A07("A07"),
    ATRCDE_A08("A08"),
    ATRCDE_A09("A09"),
    ATRCDE_A10("A10"),
    ATRCDE_A11("A11"),
    ATRCDE_A12("A12"),
    ATRCDE_A13("A13"),
    ATRCDE_A14("A14"),
    ATRCDE_A15("A15"),
    ATRCDE_A16("A16"),
    ATRCDE_A17("A17"),
    ATRCDE_A18("A18"),
    ATRCDE_A19("A19"),
    ATRCDE_A20("A20"),
    ATRCDE_A21("A21"),
    ATRCDE_A22("A22"),
    ATRCDE_A23("A23"),
    ATRCDE_A24("A24"),
    ATRCDE_A25("A25"),
    ATRCDE_A26("A26"),
    ATRCDE_A27("A27"),
    ATRCDE_A28("A28"),
    ATRCDE_A29("A29"),
    ATRCDE_A30("A30"),
    ATRCDE_A31("A31"),
    ATRCDE_A32("A32"),
    ATRCDE_A33("A33"),
    ATRCDE_A34("A34"),
    ATRCDE_A35("A35"),
    ATRCDE_A40("A40"),
    ATRCDE_A96("A96"),
    ATRCDE_A97("A97"),
    ATRCDE_A98("A98"),
    ATRCDE_A99("A99"),

    A01_NAME("����Ӧ����Ϣ"),
    A02_NAME("�������ƽ��"),
    A03_NAME("����������"),
    A04_NAME("��������嵥"),
    A05_NAME("���������������۲�����"),
    A06_NAME("�ϻ���������л��"),
    A07_NAME("��������ʽ��Ƿ"),
    A08_NAME("�ɴ�һ���Դ��׼����(1)"),
    A09_NAME("�ɴ�����Դ��׼����"),
    A10_NAME("���תϢ"),
    A11_NAME("����תϢ"),
    A12_NAME("��˰"),
    A13_NAME("���ỵ��׼����"),
    A14_NAME("������������"),
    A15_NAME("���ս�ת����"),
    A16_NAME("�������׼����"),
    A17_NAME("����Ӧ����Ϣ"),
    A18_NAME("���л�ת����"),
    A19_NAME("����Ӧ����Ϣ"),
    A20_NAME("��������ڲ�������ת"),
    A21_NAME("Ͻ������ת���껧"),
    A22_NAME("���ȴ���תϢ(AIF��Ϣ)"),
    A23_NAME("1167��Ŀ��ת921��Ŀ"),
    A24_NAME("��ת����ʵ�ѣ�������"),
    A25_NAME("����933,935�����������"),
    A26_NAME("��ת����Ӧ����Ϣ"),
    A27_NAME("934��Ŀ��ת930��Ŀ������"),
    A28_NAME("���᣹����������������"),
    A29_NAME("������"),
    A30_NAME("������"),
    A31_NAME("������"),
    A32_NAME("������"),
    A33_NAME("������"),
    A34_NAME("������"),
    A35_NAME("������"),
    A40_NAME("����һ��׼����"),
    A96_NAME("ͨ��ͨ��9048�ʽ�����"),
    A97_NAME("ͨ��ͨ��9042�ʽ�����"),
    A98_NAME("���´��ϻ��������Ϣ˰"),
    A99_NAME("������Ϣ˰��ת�����"),

    TRFKID_1("1"),
    TRFKID_2("2"),
    TRFKID_3("3"),
    TRFKID_4("4"),
    TRFKID_5("5"),
    TRFKID_6("6"),
    TRFKID_7("7"),
    TRFKID_8("8"),
    AMTSDE_1("1"),
    AMTSDE_2("2"),
    AMTSDE_3("3"),
    AMTSDE_4("4"),
    AMTSDE_5("5"),
    AMTSDE_6("6"),
    AMTSDE_7("7"),
    AMTSDE_A("A"),
    AMTSDE_B("B"),
    AMTSDE_C("C"),
    AMTSDE_8("8"),
    AMTSDE_D("D"),
    AMTSDE_E("E"),
    AMTSDE_F("F"),

    AUTO_TELLER("AUTO"),
    AT10_TELLER("AT10"),

    RSVFG1_1("1"),
    RSVFG1_2("2"),
    RSVFG1_3("3"),
    RSVFG1_4("4");

    private final String status;

    private ACEnum(String status){
        this.status = status;
    }
    public String getStatus(){
        return this.status;
    }
}
