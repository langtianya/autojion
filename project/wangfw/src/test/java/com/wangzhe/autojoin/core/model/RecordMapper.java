package com.wangzhe.model;

import java.util.List;
import java.util.Map;

/**
 * User: Administrator
 * Date: 13-3-12
 * Time: 下午4:46
 */
public interface RecordMapper {


    public void insert(Record record);

    public void editByRId(Record record);

    public Record getRecordByRId(String rId);     //订单ID

    public List<Record> getRecordByAId(Map<String, Object> map);  //用户ID

    public Integer getRecordByAIdSize(String aId);

    public List<Record> getRecordByUId(Map<String, Object> map);    //操作人       所属代理商

    public Integer getRecordByUIdSize(String uId);

    public List<Record> getRecordByAName(Map<String, Object> map);    //根据用户名

    public Integer getRecordByANameSize(String aName);

    public List<Record> getRecordByAll(Map<String, Object> map);

    public Integer getRecordByAllSize();

    public Integer delRecord(String rId);


    public List<Record> getRecordByRDate(Map<String,Object> map);

    public Integer getRecordByRDateSize(Map<String,Object> map);



    public List<Record> getRecordByUAgents(Map<String,Object> map);

    public  Integer getRecordByUAgentsSize(Map<String,Object> map);


}
