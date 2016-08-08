package com.wangzhe.autojoin.common.autojoin.core.autojoin.wangfw.common;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * User: Administrator
 * Date: 13-4-10
 * Time: ����2:29
 */
public class BeanFunction {
    private static Logger logger = LoggerFactory.getLogger(BeanFunction.class);
    private BeanFunction() {
    }

    public static <T> T createBean(Class<T> c, ServletRequest request)
            throws Exception {
        return createBean(c, request.getParameterMap());
    }

    @SuppressWarnings("unchecked")
    public static <T> T createBean(Class<T> c, Map properties) throws InvocationTargetException, IllegalAccessException, InstantiationException {
        //    try {
        // �����������
        T t = c.newInstance();
        BeanFunction.getUtils().populate(t, properties);

        return t;
//        } catch (Exception e) {
//            logger.debug("����", e);
//            throw new Exception("���ݴ���ʧ�ܣ�");
//        }
    }

    public static void copyProperties(Object dest, Object orig)
            throws Exception {
        try {
            BeanFunction.getUtils().copyProperties(dest, orig);

        } catch (Exception e) {
            logger.debug("����", e);
            throw new Exception("���ݴ���ʧ�ܣ�");
        }
    }

    public static BeanUtilsBean getUtils() {
        DateConverter dc = new DateConverter();
        ConvertUtilsBean cub = new ConvertUtilsBean();
        cub.register(dc, java.util.Date.class);
        cub.register(dc, java.sql.Date.class);
        cub.register(dc, java.sql.Time.class);
        cub.register(dc, java.sql.Timestamp.class);
        PropertyUtilsBean pub = new PropertyUtilsBean();
        BeanUtilsBean utils = new BeanUtilsBean(cub, pub);

        return utils;
    }
}
