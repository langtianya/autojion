package com.wangzhe.autojoin.common.autojoin.core.autojoin.wangfw.common;

import java.sql.*;
import java.net.*;

import org.apache.ibatis.session.SqlSessionFactory;

import com.wangzhe.autojoin.common.autojoin.core.autojoin.wangfw.db.DB;
import com.wangzhe.autojoin.common.autojoin.core.autojoin.wangfw.model.AccountMapper;


/**
 * <p>Title: ���Ӫ������</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007-2015</p>
 *
 * <p>Company: �����лԴ�����������޹�˾</p>
 *
 * @author ��Ҷ��
 * @version 1.0
 */
public class IpUtil
{
    public IpUtil()
    {
    }

//    private static Connection getAccessConnection()
//    {
//        try
//        {
//            String url = "jdbc:odbc:driver={Microsoft Access Driver (*.mdb)};DBQ=d:\\ip.mdb";
//            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
//            return DriverManager.getConnection(url);
//        }
//        catch (Exception ex)
//        {
//            ex.printStackTrace();
//            return null;
//        }
//    }
//
//    public static void importAccessDataToMysql()
//    {
//        try
//        {
//            Connection accessCon = getAccessConnection();
//            System.out.println("���ӵ�access:" + accessCon);
//            ResultSet rs = null;
//
//            Connection mysqlCon = MySQL.getConnection();
//            mysqlCon.setAutoCommit(false);
//            System.out.println("���ӵ�mysql:" + mysqlCon);
//
//            PreparedStatement ps = mysqlCon.prepareStatement("insert into IpTable(ID,StartIPNum,EndIPNum,Country,Local,InChina) values(?,?,?,?,?,?)");
//            rs = accessCon.createStatement().executeQuery("select ID,StartIPNum,EndIPNum,Country,Local from IPTABLE");
//            System.out.println("��ȡ����:" + rs);
//
//            int count = 0;
//            while (rs != null && rs.next())
//            {
//                ps.setInt(1, rs.getInt(1));
//                ps.setString(2, rs.getString(2));
//                ps.setString(3, rs.getString(3));
//
//                String country = rs.getString(4);
//                ps.setString(4, country);
//
//                String local = rs.getString(5);
//                if (local.equalsIgnoreCase("CZ88.NET"))
//                    local = "";
//                ps.setString(5, local);
//
//                boolean inChina = false;
//                if (country.contains("ʡ") || country.contains("��") || country.contains("������") || country.contains("ʡ") ||
//                    country.contains("����") || country.contains("���ɹ�") || country.contains("����") || country.contains("����") ||
//                    country.contains("�½�") || country.contains("���ɹ�") || country.contains("����") || country.contains("����"))
//                {
//                    if (!country.contains("̨��ʡ"))
//                        inChina = true;
//                }
//
//                if (local.contains("����") || country.contains("��ͨ") || country.contains("��ͨ"))
//                {
//                    inChina = true;
//                }
//
//                ps.setBoolean(6, inChina);
//
//                ps.addBatch();
//                count++;
//
//                if (count % 100 == 0)
//                {
//                    ps.executeBatch();
//                    System.out.println("�ɹ������˵�" + count + "����¼...");
//                }
//            }
//
//            ps.executeBatch();
//            mysqlCon.commit();
//
//            rs.close();
//            ps.close();
//            accessCon.close();
//            mysqlCon.close();
//            System.out.println("�ɹ��ĵ�����" + count + "����¼��mysql��");
//        }
//        catch (Exception ex)
//        {
//            ex.printStackTrace();
//        }
//
//    }

    public static String findAddressByIp(String ip)
    {
    	SqlSessionFactory sqlSessionFactory = DB.getSqlSessionFactory();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try
        {
            byte[] addr = java.net.InetAddress.getByName(ip).getAddress();

            long ipNum = 0xffffffffL & ((addr[0] << 24) + ((addr[1] << 16) & 0x00ff0000) + ((addr[2] << 8) & 0xff00) + (addr[3] & 0xff));
            
            con=sqlSessionFactory.openSession().getConnection();

            int id1 = 0, id2 = 0;
            String sql = "select ID from IpTable where StartIpNum<=? order by StartIpNum desc limit 0,1";
            ps = con.prepareStatement(sql);
            ps.setLong(1, ipNum);
            rs = ps.executeQuery();
            if (rs != null && rs.next())
            {
                id1 = rs.getInt(1);
            }

            rs.close();
            ps.close();

            sql = "select ID,country,local from IpTable where EndIpNum>=? order by EndIpNum limit 0,1";
            ps = con.prepareStatement(sql);
            ps.setLong(1, ipNum);
            rs = ps.executeQuery();
            if (rs != null && rs.next())
            {
                id2 = rs.getInt(1);
            }

            if (id1 > 0 && id1 == id2)
            {
                String country = rs.getString(2);
                String local = rs.getString(3);
                if (local == null)
                    local = "";

                if (local.equalsIgnoreCase("CZ88.NET"))
                    return country;

                return country + " " + local;

            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            if (rs != null)
                try
                {
                    rs.close();
                }
                catch (Exception e)
                {
                	e.printStackTrace();
                }
            if (ps != null)
                try
                {
                    ps.close();
                }
                catch (Exception e)
                {
                	e.printStackTrace();
                }

                if(con!=null)
                {
                	try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}              	
                }
        }

        return "���Ҵ���";

    }

//    public static void main(String[] args)
//    {
//        //��ACCESSIP�⵼�����ݵ�MYSQL
//        ////////////////////////////////////
//        //importAccessDataToMysql();
//        //////////////////////////////////
//
//        java.util.Random r = new java.util.Random();
//
//        long t0 = System.currentTimeMillis(), t1 = System.currentTimeMillis(), t2;
//        int loop = 100;
//        for (int i = 0; i < loop; i++)
//        {
//            String ip = (Math.abs(r.nextInt()) % 255) + "." + (Math.abs(r.nextInt()) % 255) + "." + (Math.abs(r.nextInt()) % 255) + "." + (Math.abs(r.nextInt()) % 255);
//            t2 = System.currentTimeMillis();
//
//            System.out.println(ip + "��ѯ�����" + findAddressByIp(ip) + ",��ѯ��ʱ" + (t2 - t1) + "ms");
//            t1 = t2;
//        }
//
//        System.out.println("���Խ�����ƽ�����β�ѯ��ʱ" + (System.currentTimeMillis() - t0) / loop + "ms");
//    }
}
