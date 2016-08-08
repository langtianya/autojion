package com.wangzhe.autojoin.core.model;

import java.util.List;
import java.util.Map;

/**
 * User: Administrator
 * Date: 13-3-12
 * Time: ����4:46
 */
public interface RecordMapper {


    public void insert(Record record);

    public void editByRId(Record record);

    public Record getRecordByRId(String rId);     //����ID

    public List<Record> getRecordByAId(Map<String, Object> map);  //�û�ID

    public Integer getRecordByAIdSize(String aId);

    public List<Record> getRecordByUId(Map<String, Object> map);    //������       ����������

    public Integer getRecordByUIdSize(String uId);

    public List<Record> getRecordByAName(Map<String, Object> map);    //�����û���

    public Integer getRecordByANameSize(String aName);

    public List<Record> getRecordByAll(Map<String, Object> map);

    public Integer getRecordByAllSize();

    public Integer delRecord(String rId);


    public List<Record> getRecordByRDate(Map<String,Object> map);

    public Integer getRecordByRDateSize(Map<String,Object> map);



    public List<Record> getRecordByUAgents(Map<String,Object> map);

    public  Integer getRecordByUAgentsSize(Map<String,Object> map);


}
