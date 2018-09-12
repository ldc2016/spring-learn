package com.ldc.spring.util;

public class DataMaskUtil {

    private final static String MASK_CHAR = "*";

    public static String maskNameForRetainFirstChineseCharacter(String userName) {
        int length = userName.length();
        return replace(userName, MASK_CHAR, 1, length);
    }

    public static String maskIdNoForFrontFiveAndEndThree(String idNo) {
        int length = idNo.length();
        return replace(idNo, MASK_CHAR,5, length - 3);
    }

    public static String maskBankCardNo(String bankCardNo) {
        int length = bankCardNo.length();
        return replace(bankCardNo, MASK_CHAR,6, length - 4);
    }

    /**
     * 身份证号后六位之前的数字，前5位露出，其余用掩码
     * @param idNo
     * @return
     */
    public static String maskIdNoForCutLastSixBit(String idNo) {
        String cutIdNo = idNo.substring(0,idNo.length()-6);
        return replace(cutIdNo, MASK_CHAR,5, cutIdNo.length());
    }

    private static String replace(String source, String replace, int beginIndex, int endIndex) {
        if (beginIndex < 0 || endIndex > source.length() || endIndex < beginIndex) {
            return source;
        }
        int span = endIndex - beginIndex;
        StringBuilder builder = new StringBuilder();
        builder.append(getHead(source, beginIndex));
        builder.append(getBody(replace, span));
        builder.append(getTail(source, endIndex));
        return builder.toString();
    }

    private static String getHead(String source, int beginIndex) {
        return source.substring(0, beginIndex);
    }

    private static String getBody(String replace, int span) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < span; i++) {
            builder.append(replace);
        }
        return builder.toString();
    }

    private static String getTail(String source, int endIndex) {
        return source.substring(endIndex, source.length());
    }

    public static String getSubstringFour(String str) {
        if (null == str || str.length() < 4) {
            return str;
        }
        return str.substring(str.length() - 4, str.length());
    }
}