package com.wangzhe.autojoin.wangfw.bean;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * User: Administrator
 * Date: 13-2-26
 * Time: ����4:59
 */
public interface  AccountMapper {
    public void insert(Account account);

    public Account getAccountById(String aId);

    public Account getAccountByName(String aName);
    public Account checkAccount(Account account);
    
    public List<Account> getAccountByIp(String ip);
    
    public boolean isOnline(Integer aId);  
    
    /**��ȡ���е��û�*/
    public List<Account> getAllAccounts();
    /**�����û�idɾ���û�*/
    public Integer deleteAccountById(String aId);
    /**��ȡ����ͬһ��ipע���ʺŵĸ���*/
    public List<Date> getCountByIP(String aIp);
    /**���µ����û���Ϣ*/
    public void update(Account account);

    public void desPoint(String aId);

    public void InscutPoint(Map<String,Object> map);
    public String getPoint(String aId);

    /**�û��߼�����*/
    public List<Account>  advanSearch(Account account);
    public List<Account>  advanSearchHaveDate(Account account);
    public List<Account>  advanSearchHaveEndDate(Account account);
}
