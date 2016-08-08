package com.wangzhe.autojoin.common.autojoin.core.autojoin.wangfw.common;

import org.apache.commons.beanutils.Converter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: Administrator
 * Date: 13-4-10
 * Time: ����2:30
 */
public class DateConverter implements Converter {

    /**
     * ʱ��ת����ʽ
     */
    public static final String[] FORMATS = {"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
            "yyyy-M-d HH:mm:ss", "yyyy-MM-dd H:m:s", "yyyy-M-d H:m:s",
            "yyyy/MM/dd HH:mm:ss", "yyyy-M-d HH:mm:ss", "yyyy/MM/dd H:m:s",
            "yyyy/M/d H:m:s", "yyyy-MM-dd", "yyyy-M-d",
            "yyyy/MM/dd", "yyyyMMdd", "yyyy/M/d", "HH:mm:ss", "H:m:s"};
    private SimpleDateFormat sdf = new SimpleDateFormat();

    @SuppressWarnings("unchecked")

    public Object convert(Class c, Object value) {
        if (c == java.util.Date.class) {
            Date v = getDate(value);
            if (v != null) {
                return v;
            }
        } else if (c == java.sql.Date.class) {
            Date v = getDate(value);
            if (v != null) {
                return new java.sql.Date(v.getTime());
            }
        } else if (c == java.sql.Time.class) {
            Date v = getDate(value);
            if (v != null) {
                return new java.sql.Time(v.getTime());
            }
        } else if (c == java.sql.Timestamp.class) {
            Date v = getDate(value);
            if (v != null) {
                return new java.sql.Timestamp(v.getTime());
            }
        }
        return value;
    }

    public Date getDate(Object value) {
        String[] vs = String.valueOf(value).split(" ", 2);
        if (vs != null) {
            String date = null;
            String time = null;
            if (vs.length == 2) {
                date = vs[0];
                time = vs[1];
            }
            if (vs.length == 1) {
                date = vs[0];
            }
            if (time == null) {
                time = "00:00:00";
            }
            String[] ts = time.split(":", 3);
            if (ts != null && ts.length == 2) {
                time += ":00";
            }
            value = date + " " + time;
            //20121117
            //2012-07-27
            //2012727
        }


        for (int i = 0; i < FORMATS.length; i++) {
            Date v = tryConvert(value, FORMATS[i]);
            if (v != null) {
                return v;
            }
        }
        return null;
    }

    public Date tryConvert(Object value, String format) {
        try {

            sdf.applyPattern(format);
            Date v = sdf.parse(String.valueOf(value));
            return v;
        } catch (Exception e) {
            return null;
        }
    }
}
