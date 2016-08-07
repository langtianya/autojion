package com.wangzhe.db;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

/**
 * User: Administrator
 * Date: 13-2-26
 * Time: œ¬ŒÁ4:54
 */
public class DB {
    private final static SqlSessionFactory sqlSessionFactory;
    //∆’Õ®’À∫≈ dev test
    //root’À∫≈ root huichuang
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
