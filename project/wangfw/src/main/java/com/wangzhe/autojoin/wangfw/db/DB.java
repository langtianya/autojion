package com.wangzhe.autojoin.common.autojoin.core.autojoin.wangfw.db;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

/**
 * User: Administrator
 * Date: 13-2-26
 * Time: ����4:54
 */
public class DB {
    private final static SqlSessionFactory sqlSessionFactory;
    //��ͨ�˺� dev test
    //root�˺� root huichuang
    static {
        String resource = "configuration.xml";
        Reader reader = null;
        try {
            reader = Resources.getResourceAsReader(resource);
        } catch (IOException e) {
            System.out.println(e.getMessage());

        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
    }

    public static SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }
}
