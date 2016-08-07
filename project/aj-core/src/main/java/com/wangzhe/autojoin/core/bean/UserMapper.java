package com.wangzhe.model;

import java.util.ArrayList;
import java.util.List;

/**Mybatis与Ibatis比较:http://www.itnose.net/detail/6203944.html
 * User: Administrator
 * Date: 13-2-26
 * Time: 下午4:59
 */
public interface UserMapper {

    public void insert(User user);

    public User getUserById(String uId);

    public User getUserByName(String uName);

    public User checkUser(User user);

    /**
     * 获取所有的普通管理员
     */
    public List<User> getAllUsers();

    /**
     * 根据普通管理员id删除普通管理员户
     */
    public Integer deleteUserById(String uId);

    public List<String> getUAgent();


    public void updateUserPass(User user);
    public void updateUserInfo(User user);

}
