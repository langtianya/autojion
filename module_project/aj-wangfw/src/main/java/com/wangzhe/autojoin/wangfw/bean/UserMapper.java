package com.wangzhe.autojoin.wangfw.bean;

import java.util.ArrayList;
import java.util.List;

/**Mybatis��Ibatis�Ƚ�:http://www.itnose.net/detail/6203944.html
 * User: Administrator
 * Date: 13-2-26
 * Time: ����4:59
 */
public interface UserMapper {

    public void insert(User user);

    public User getUserById(String uId);

    public User getUserByName(String uName);

    public User checkUser(User user);

    /**
     * ��ȡ���е���ͨ����Ա
     */
    public List<User> getAllUsers();

    /**
     * ������ͨ����Աidɾ����ͨ����Ա��
     */
    public Integer deleteUserById(String uId);

    public List<String> getUAgent();


    public void updateUserPass(User user);
    public void updateUserInfo(User user);

}
