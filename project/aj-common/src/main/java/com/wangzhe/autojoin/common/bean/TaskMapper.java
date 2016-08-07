package com.wangzhe.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Task: Administrator
 * Date: 13-2-26
 * Time: 下午4:59
 */
public interface TaskMapper {

    public void insert(Map<String, Object> map);

    public Task getTaskById(String tId);

    public Task getTaskByName(String tKeyword);

    public Integer checkTask(Task task);
    
    public List<Task> getAllTasks();
    
    public List<Task> getTasksByUserId(Integer userId);

    public String getTaskId(Map<String, Object> map);
   
    public Integer deleteTaskById(String tId);
    public Integer deleteByTask(Map<String, Object> map);

    
    /**更新单个用户信息*/
    public void update(Task task);
    
    public Task queryByTask(Map<String, Object> map);
    public Task queryByTask2(Map<String, Object> map);
    public Integer getCountByTask(Task task);
}
