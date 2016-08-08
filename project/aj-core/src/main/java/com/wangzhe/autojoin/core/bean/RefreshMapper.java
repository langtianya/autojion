package com.wangzhe.autojoin.core.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Refresh: Administrator
 * Date: 13-2-26
 * Time: ����4:59
 */
public interface RefreshMapper {

    public void insert(Refresh refresh);

    public Refresh getRefreshById(String rId);

    public Refresh getRefreshByName(String rKeyword);

    public Integer checkRefresh(Refresh refresh);
    
    public List<Refresh> getAllRefreshs();
    
    public Integer deleteRefresh(Refresh refresh);
    public Integer delNDateAgoRecord(Date endDate);
    
    public List<Refresh> queryByRefresh(Map<String, Object> map);
    public Integer getRefreshSize(String tid);
    
}
