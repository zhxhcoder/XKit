package com.zhxh.xlibkit.common;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhxh on 2018/6/29
 */
public final class StringUtil {

    private static final String floatRegex = "(-?\\d+)|(-?\\d+\\.\\d+)";
    private static final String emptyRegex = "\\s*";
    private static final String colorRegex = "#([0-9a-fA-F]{3}|[0-9a-fA-F]{6}|[0-9a-fA-F]{8})";
    private static final String hanziRegex = "[\\u4e00-\\u9fa5]";
    private static final String emailRegex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
    private static final String zeroRegex = "[\\s0\\.]+";

    public static List<String> getRegexList(String input, String regex) {
        List<String> stringList = new ArrayList<>();
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);
        while (m.find())
            stringList.add(m.group());

        return stringList;
    }


    public static boolean isFloatStr(String input) {
        return isRegexMatch(input, floatRegex);
    }

    public static boolean isColorStr(String input) {
        return isRegexMatch(input, colorRegex);
    }

    public static boolean isEmptyStr(String input) {
        return isRegexMatch(input, emptyRegex);
    }

    public static boolean isHanziStr(String input) {
        return isRegexMatch(input, hanziRegex);
    }

    public static boolean isEmailStr(String input) {
        return isRegexMatch(input, emailRegex);
    }

    public static boolean isZeroStr(String input) {
        return isRegexMatch(input, zeroRegex);
    }

    public static boolean isRegexMatch(String input, String regex) {

        if (TextUtils.isEmpty(input) || TextUtils.isEmpty(regex)) {
            return false;
        }
        if (!regex.startsWith("^")) {
            regex = "^" + regex;
        }
        if (!regex.endsWith("$")) {
            regex = regex + "$";
        }

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);

        return m.find();

    }


    public static boolean isNull(String checkStr) {
        boolean result = false;
        if (null == checkStr) {
            result = true;
        } else {
            if (checkStr.length() == 0) {
                result = true;
            }
        }
        return result;
    }

    public static boolean isNull(List<?> list) {
        boolean result = false;
        if (null == list) {
            result = true;
        } else {
            if (list.size() == 0) {
                result = true;
            }
        }
        return result;
    }

    public static String[] split(String source, String split) {
        if (isNull(source))
            return null;
        if (isNull(split))
            return new String[]{source};

        Vector<String> vector = new Vector<String>();

        int startIndex = 0;
        int endIndex = -1;

        while (true) {

            if ((endIndex = source.indexOf(split, startIndex)) != -1) {
                vector.add(source.substring(startIndex, endIndex));
                startIndex = endIndex + split.length();

            } else {

                if (startIndex <= source.length()) {
                    vector.add(source.substring(startIndex));
                }

                break;
            }
        }
        return vector.toArray(new String[vector.size()]);
    }
}
