package com.ccb.util;

import java.math.BigDecimal;
import java.text.*;

public class StringUtil {

	public static String parseStr(String orString) {
		String aString = "";
		String reg = new String("\n");
		aString = orString.replaceAll(reg, "<br>");
		// aString=aString.replaceAll("0D","<br>");
		// aString=aString.replaceAll("0A","<br>");
		return aString;
	}

	public static String reversStr(String orString) {
		String aString = "";

		aString = orString.replaceAll("<br>", "\n");

		return aString;
	}

	public static double turnFloat(double orgValue) {
		NumberFormat f = new DecimalFormat();

		return orgValue;
	}

	/**
	 * 
	 * �ṩ��ȷ��С��λ�������봦��
	 * 
	 * @param v
	 *            ��Ҫ�������������
	 * 
	 * @param scale
	 *            С���������λ
	 * 
	 * @return ���������Ľ�� ��ҵ������string������bigdecimal Returns a BigDecimal whose
	 *         value is (this / val), and whose scale is as specified.
	 */

	public static double round(double val, int scale) {
		
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		
	//	if (val.isNaN()) {
			BigDecimal b = new BigDecimal(Double.toString(val));
			BigDecimal one = new BigDecimal("1");
			return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();

	//	}
	}

	public static void main(String[] para) {
		System.out.println(round(78.63633, 2));
	}

}
