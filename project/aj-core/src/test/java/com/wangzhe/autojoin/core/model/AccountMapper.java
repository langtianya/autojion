package com.wangzhe.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * User: Administrator
 * Date: 13-2-26
 * Time: 下午4:59
 */
public interface  AccountMapper {
    public void insert(Account account);

    public Account getAccountById(String aId);

    public Account getAccountByName(String aName);
    public Account checkAccount(Account account);
    
    public List<Account> getAccountByIp(String ip);
    
    public boolean isOnline(Integer aId);  
    
    /**获取所有的用户*/
    public List<Account> getAllAccounts();
    /**根据用户id删除用户*/
    public Integer deleteAccountById(String aId);
    /**获取当天同一个ip注册帐号的个数*/
    public List<Date> getCountByIP(String aIp);
    /**更新单个用户信息*/
    public void update(Account account);

    public void desPoint(String aId);

    public void InscutPoint(Map<String,Object> map);
    public String getPoint(String aId);

    /**用户高级搜索*/
    public List<Account>  advanSearch(Account account);
    public List<Account>  advanSearchHaveDate(Account account);
    public List<Account>  advanSearchHaveEndDate(Account account);
}
