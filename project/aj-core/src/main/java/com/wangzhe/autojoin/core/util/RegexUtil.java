package com.wangzhe.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author wzw
 * @author ocq
 */
public class RegexUtil {

    public static String escapeRegexSymbol(String srcStr) {

        if (srcStr.indexOf("[") > -1) {
            srcStr = srcStr.replace("[", "\\[");
        }
        if (srcStr.indexOf("]") > -1) {
            srcStr = srcStr.replace("]", "\\]");
        }
        if (srcStr.indexOf("*") > -1) {
            srcStr = srcStr.replace("*", "\\*");
        }
        if (srcStr.indexOf("$") > -1) {
            srcStr = srcStr.replace("$", "\\$");
        }
        if (srcStr.indexOf("+") > -1) {
            srcStr = srcStr.replace("+", "\\+");
        }
        if (srcStr.indexOf("?") > -1) {
            srcStr = srcStr.replace("?", "\\?");
        }
        if (srcStr.indexOf("(") > -1) {
            srcStr = srcStr.replace("(", "\\(");
        }
        if (srcStr.indexOf(")") > -1) {
            srcStr = srcStr.replace(")", "\\)");
        }
        return srcStr;
    }

    /**
     * 获取匹配内容(单行模式)
     *
     * @return 描述
     * @param regex String 表达�?
     * @param source String 匹配�?
     */
    public static String getMatcher(String regex, String source) {
        return getMatcher(regex, source, 1);
    }

    public static String getMatcher(String regex, String source, int group) {
        return getMatcher(regex, source, group, false);
    }

    public static String getFirstMatcherOfDotall(String regex, String source) {

        return getMatcher(regex, source, 1, true);
    }

    public static String getMatcher(String regex, String source, int group, boolean isFirst) {

        if (StringUtils.isAllEmpty(source)) {
            return null;
        }
        String result = null;
        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(source);
        while (matcher.find()) {
            result = matcher.group(group);
            if (StringUtils.notEmpty(result)) {
                if (isFirst) {
                    break;
                }
            }

        }
        return result;
    }

    /**
     * 获取匹配内容(单行模式)
     *
     * @param regex 描述
     * @param source 描述
     * @return 描述
     */
    public static List getMatcherList(String regex, String source) {
        return getMatcherList(regex, source, 1);
    }

    public static List getMatcherList(String regex, String source, int group) {
        List result = new ArrayList();
        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(source);
        while (matcher.find()) {
            result.add(matcher.group(group));
        }
        return result;
    }

    /**
     * 获取匹配内容
     *
     * @param regex String 表达�?
     * @param source String 匹配�?
     * @return String
     */
    public static String getString(String regex, String source) {
        if (source == null) {
            return null;
        }
        return getString(regex, source, 1);
    }

    public static String getString(String regex, String source, int group) {
        if (source == null || regex == null) {
            return null;
        }
        String result = null;
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(source);
        while (matcher.find()) {
            result = matcher.group(group);
        }
        return result;
    }

    public static String getFirstString(String regex, String source) {
        if (StringUtils.isOneEmpty(source)) {
            return null;
        }
        return getFirstString(regex, source, 1);
    }

    public static String getFirstString(String regex, String source, int group) {
        if (StringUtils.isOneEmpty(source)) {
            return null;
        }
        String result = null;
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(source);
        while (matcher.find()) {
            result = matcher.group(group);
            break;
        }
        return result;
    }

    /**
     * 获取匹配内容
     *
     * @param regex String 表达�?
     * @param source String 匹配�?
     * @return List
     */
    public static List getList(String regex, String source) {
        return getList(regex, source, 1);
    }

    public static List getList(String regex, String source, int group) {
        if (StringUtils.isOneEmpty(source)) {
            return null;
        }
        List result = new ArrayList();
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(source);
        while (matcher.find()) {
            result.add(matcher.group(group));
        }
        return result;
    }

    /**
     * 获取匹配内容
     *
     * @param regex String 表达�?
     * @param source String 匹配�?
     * @return List
     */
    public static Map<String, String> getMap(String regex, String source) {
        Map<String, String> map = new HashMap<>();
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(source);
        while (matcher.find()) {
            map.put(matcher.group(1), matcher.group(2));
        }
        return map;
    }

    /**
     * 获取匹配内容
     *
     * @param regex String 表达�?
     * @param source String 匹配�?
     * @return List
     */
    public static String[][] getArray2(String regex, String source) {
        if (StringUtils.isEmpty(source) || StringUtils.isEmpty(regex)) {
            return null;
        }
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(source);
        ArrayList list = new ArrayList(20);
        while (matcher.find()) {
            String[] arr1 = new String[]{matcher.group(1), matcher.group(2)};
            list.add(arr1);
        }
        if (list.size() < 1) {
            return null;
        }
        String[][] arr2 = new String[list.size()][2];
        list.toArray(arr2);
        return arr2;
    }

    /**
     * 是否匹配到内�?
     *
     * @return 描述
     * @param regex String 表达�?
     * @param source String 匹配�?
     */
    public static boolean ifMatcher(String regex, String source) {
        if (source != null) {
            Pattern pattern = Pattern.compile(regex, Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(source);
            return matcher.find();
        }
        return false;
    }

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

    public static String[] match(String str, String pat) {
        if (str == null) {
            return null;
        }
        Pattern pattern = Pattern.compile(pat, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            String[] ret = new String[matcher.groupCount()];
            for (int i = 0; i < ret.length; i++) {
                ret[i] = matcher.group(i + 1);
            }
            return ret;
        }
        return null;
    }

    /**
     * 如果desString包含subStr数组中其中一个元素，则返回true
     *
     * @param desString
     * @param subStr
     * @return
     */
    public static boolean isMatcherOne(String desString, String... subStr) {
        if (StringUtils.isOneEmpty(desString)) {
            return false;
        }

        for (String regex : subStr) {
            if (!StringUtils.isOneEmpty(regex) && RegexUtil.ifMatcher(regex, desString)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 如果desString不包含subStr数组中任意一个元素，则返回true
     *
     * @param desString
     * @param subStr
     * @return
     */
    public static boolean notMatcherAll(String desString, String... subStr) {
        return !isMatcherOne(desString, subStr);
    }

    /**
     * 获取匹配内容
     *
     * @param regex String 表达�?
     * @param source String 匹配�?
     * @return List
     */
    public static Map<String, String> getMapOfDotall(String regex, String source) {
        Map<String, String> map = new HashMap<>();
        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL | Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(source);
        while (matcher.find()) {
            map.put(matcher.group(1), matcher.group(2));
        }
        return map;
    }

    /**
     * 获取匹配内容ocq
     *
     * @authorocq
     * @param regex String 表达�?
     * @param source String 匹配�?
     * @param groupNum 要获取的分组数量
     * @return List
     */
    public static List<List<String>> getListByGroupNum(String regex, String source, int groupNum) {

        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(source);
        List<List<String>> retList = new ArrayList(20);
        while (matcher.find()) {
            List<String> retListTemp = new ArrayList();
            for (int i = 1, len = groupNum + 1; i < len; i++) {
                retListTemp.add(matcher.group(i));
            }
            retList.add(retListTemp);
        }
        if (retList.size() < 1) {
            return null;
        }
        return retList;
    }

    /**
     * 获取匹配内容ocq
     *
     * @authorocq
     * @param regex String 表达�?
     * @param source String 匹配�?
     * @param groupNum 要获取的分组数量
     * @return List
     */
    public static List<List<String>> getListByGroupNumOfDotall(String regex, String source, int groupNum) {

        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL | Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(source);
        List<List<String>> retList = new ArrayList(20);
        while (matcher.find()) {
            List<String> retListTemp = new ArrayList();
            for (int i = 1, len = groupNum + 1; i < len; i++) {
                retListTemp.add(matcher.group(i));
            }
            retList.add(retListTemp);
        }
        if (retList.size() < 1) {
            return null;
        }
        return retList;
    }
}
