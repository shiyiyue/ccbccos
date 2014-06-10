package com.ccb.util;

import java.util.Date;

import pub.platform.db.SequenceManager;
import pub.platform.utils.StringUtils;

public class SeqUtil {

    // �����ڲ����
    // 
    public static String getNbxh() {
        String temp = "" + SequenceManager.nextID(CcbLoanConst.NBXH);
        String rtn = StringUtils.toDateFormat(new Date(), "yyyyMMdd") + StringUtils.addPrefix(temp, "0", 7);
        return rtn;
    }

    // ���ɵ�Ѻ���
    public static String getMortID() {
        String temp = "" + SequenceManager.nextID(CcbLoanConst.MORTTYPE);
        String rtn = StringUtils.toDateFormat(new Date(), "yyyy") + StringUtils.addPrefix(temp, "0", 5);
        return rtn;
    }

    // ������Ŀ���
    public static String getCoop() {
        String temp = "" + SequenceManager.nextID(CcbLoanConst.COOPTYPE);
        String rtn = StringUtils.toDateFormat(new Date(), "yyyyMMdd") + StringUtils.addPrefix(temp, "0", 5);
        return rtn;
    }

    // ������ˮ���
    public static String getTaskSeq() {
        String temp = "" + SequenceManager.nextID(CcbLoanConst.TASKSEQ);
        String rtn = StringUtils.toDateFormat(new Date(), "yyyyMMdd") + StringUtils.addPrefix(temp, "0", 7);
        return rtn;
    }

    /*
     * ����ҵ����Ҫ��Ψһ���
     */

    // public static String getSeq(String seqName) {
    //
    // DatabaseConnection dc = null;
    // String seq = "";
    // dc = ConnectionManager.getInstance().getConnection();
    // Connection conn = dc.getConnection();
    // Statement st = null;
    // ResultSet rs = null;
    // StringBuffer sb = null;
    //
    // // �����������޿������
    // RecordSet recSet =
    // dc.executeQuery("select discardno from sys_seq_discard where bhlx='" +
    // CcbLoanConst.MORTTYPE
    // + "' and useflg='0' order by discardno asc ");
    // if (recSet.next()) {
    // seq = recSet.getString("discardno");
    // }
    // // ��seqΪnull����������������µ����
    // if(seq.equals("")||seq.equals("null")){
    // // ���������
    // // ��ǰ��š����λ��
    // int dqbh;
    // int bhws = 0;
    // String rq = "";
    // String[] rq2 = null;
    // try {
    // st = conn.createStatement();
    // sb = new StringBuffer();
    // rs = st.executeQuery("select DQBH,BHWS,RQ from sys_seq where bhlx='" +
    // seqName + "'");
    //
    // rs.next();
    // // ��ǰ���
    // dqbh = rs.getInt("DQBH");
    // // ���λ��
    // bhws = rs.getInt("BHWS");
    // // ����������
    // rq2 = rs.getString("RQ").toString().trim().split(" ");
    // rq = rq2[0].substring(0, 4);
    // // yyyy-mm-dd format
    // // �����ݱ仯�����0���¿�ʼ���
    // if (BusinessDate.getYear().equals(rq)) {
    // int len = String.valueOf(dqbh).length();
    // if (len < bhws) {
    // for (int i = 0; i < (bhws - len); i++) {
    // sb.append("0");
    // }
    // }
    // seq = StringUtils.toDateFormat(new Date(), "yyyy") + sb.toString() +
    // String.valueOf(dqbh);
    // dqbh += 1;
    // } else {
    // dqbh = 1;
    // seq = StringUtils.toDateFormat(new Date(), "yyyy") + "00001";
    // dqbh += 1;
    // }
    // // yyyy ��ʽ������ݣ�����º������Ϊ��ݺ��-01-01 ��Ӱ���ж�
    // dc.executeUpdate("update sys_seq set dqbh=" + dqbh + ",rq=to_date('" +
    // BusinessDate.getYear()
    // + "','yyyy') where bhlx='" + seqName + "'");
    //
    // } catch (SQLException ex) {
    // System.out.println(ex.getSQLState());
    // } finally {
    // ConnectionManager.getInstance().releaseConnection(dc);
    // }
    // }else{
    // return seq;
    // }
    //    
    // return seq;
    // }

    public static void main(String[] args) throws Exception {
        System.out.println(getCoop());
    }
}
