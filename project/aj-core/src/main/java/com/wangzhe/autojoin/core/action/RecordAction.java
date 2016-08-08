package com.wangzhe.autojoin.core.action;

import com.wangzhe.autojoin.common.autojoin.core.autojoin.wangfw.common.BeanFunction;
import com.wangzhe.autojoin.common.autojoin.core.autojoin.wangfw.db.DB;
import com.wangzhe.autojoin.common.autojoin.core.autojoin.wangfw.model.*;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.beanutils.converters.DateTimeConverter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Administrator
 * Date: 13-3-14
 * Time: ����5:22
 */
@Namespace("/")
public class RecordAction extends ActionSupport {

    private static final long serialVersionUID = 5845835338666651309L;


    @Action(value = "/allRecord", results = {
            @Result(name = "success", location = "/recordList"),
            @Result(name = "error", location = "/recordList", type = "redirect")})
    public String recordList() {
        SqlSession sqlSession = null;
        try {
            SqlSessionFactory sqlSessionFactory = DB.getSqlSessionFactory();
            sqlSession = sqlSessionFactory.openSession();


            HttpServletRequest request = ServletActionContext.getRequest();
            String start = request.getParameter("jumpPage");
            if (start == null || !StringUtils.isNumeric(start)) {
                start = "1";
            }
            int curPage = 1;   //��ǰ�ǵڼ�ҳ
            int maxRowCount = 0;    //һ���ж�����
            int rowsPerPage = 30; //ÿҳ�ж�����
            int maxPage; //һ���ж���ҳ
            try {
                curPage = Integer.valueOf(start);
            } catch (Exception ex) {
                curPage = 1;
            }


            Map<String, Object> map = new HashMap<String, Object>();

            map.put("start", curPage == 0 ? curPage : ((curPage - 1) * rowsPerPage));
            map.put("limit", rowsPerPage);
            List<Record> records = null;
            // List<Record> records=sqlSession.selectList("com.wangzhe.autojoin.common.autojoin.core.autojoin.wangfw.model.RecordMapper.getRecordByPage",map);
            RecordMapper recordMapper = sqlSession.getMapper(RecordMapper.class);
//
//            request.setAttribute("records", records);
//            System.out.println("aa" + records.size());


            //��ȡ�Ķ�����¼
            String aName = request.getParameter("aName");
            String aId = request.getParameter("aId");
            String rId = request.getParameter("rId");
            String rDate = request.getParameter("rDate");
            String uName = request.getParameter("uName");
            String uAgents = request.getParameter("uAgents");
            String tDate = request.getParameter("tDate");

            if (aName != null && aName.trim().length() > 0) {
                map.put("aName", aName);
                records = recordMapper.getRecordByAName(map); //sqlSession.selectList("com.wangzhe.autojoin.common.autojoin.core.autojoin.wangfw.model.RecordMapper.getRecordByAName", map);
                if (records != null && records.size() > 0) {
                    maxRowCount = recordMapper.getRecordByANameSize(aName);
                }
            } else if (aId != null && aId.trim().length() > 0 && StringUtils.isNumeric(aId)) {
                map.put("aId", aId);
                records = recordMapper.getRecordByAId(map);

                if (records != null && records.size() > 0) {
                    maxRowCount = recordMapper.getRecordByAIdSize(aId);
                }

            } else if (rId != null && rId.trim().length() > 0 && StringUtils.isNumeric(rId)) {
                map.put("rId", rId);
                Record record = recordMapper.getRecordByRId(rId);
                records = new ArrayList<Record>(1);
                records.add(record);
                maxRowCount = 1;
            } else if (rDate != null && rDate.trim().length() > 0 && tDate != null && tDate.trim().length() > 0 && uName != null && uName.trim().length() > 0) {
                Object sql_date = BeanFunction.getUtils().getConvertUtils().convert(rDate, java.sql.Timestamp.class);
                Object tdate = BeanFunction.getUtils().getConvertUtils().convert(tDate, java.sql.Timestamp.class);
                if (sql_date != null && tdate != null) {
                    map.put("rDate", DateFormatUtils.format((java.sql.Timestamp) sql_date, "yyyy-MM-dd HH:mm:ss"));
                    map.put("tDate", DateFormatUtils.format((java.sql.Timestamp) tdate, "yyyy-MM-dd HH:mm:ss"));
                    map.put("uName", uName);
                    records = recordMapper.getRecordByRDate(map);
                    if (records != null && records.size() > 0) {
                        maxRowCount = recordMapper.getRecordByRDateSize(map);
                    }
                }


            } else if (rDate != null && rDate.trim().length() > 0 && tDate != null && tDate.trim().length() > 0 && uName.trim().length() < 1 && uAgents != null && uAgents.trim().length() > 0) {
                Object sql_date = BeanFunction.getUtils().getConvertUtils().convert(rDate, java.sql.Timestamp.class);
                Object tdate = BeanFunction.getUtils().getConvertUtils().convert(tDate, java.sql.Timestamp.class);
                if (sql_date != null && tdate != null) {
                    map.put("rDate", DateFormatUtils.format((java.sql.Timestamp) sql_date, "yyyy-MM-dd HH:mm:ss"));
                    map.put("tDate", DateFormatUtils.format((java.sql.Timestamp) tdate, "yyyy-MM-dd HH:mm:ss"));
                    map.put("uAgents", uAgents);
                    records = recordMapper.getRecordByUAgents(map);
                    if (records != null && records.size() > 0) {
                        maxRowCount = recordMapper.getRecordByUAgentsSize(map);
                    }
                }

            }
            if (records == null) {
                // records = sqlSession.selectList("com.wangzhe.autojoin.common.autojoin.core.autojoin.wangfw.model.RecordMapper.getRecordByAll", map);
                records = recordMapper.getRecordByAll(map);
                if (records != null && records.size() > 0) {
                    maxRowCount = recordMapper.getRecordByAllSize();
                }
            }
            if (records != null && records.size() == 0) {
                request.setAttribute("msg", "�]������������ѯ����ؽ����");
            }

            if (records == null) {
                records = new ArrayList<Record>();
            }
            request.setAttribute("records", records);


            //����������������ҳ��
            if (maxRowCount % rowsPerPage == 0) {
                maxPage = maxRowCount / rowsPerPage;
            } else {
                maxPage = maxRowCount / rowsPerPage + 1;
            }
            request.setAttribute("maxPage", maxPage);
            request.setAttribute("maxRowCount", maxRowCount);
            request.setAttribute("rowsPerPage", rowsPerPage);
            request.setAttribute("curPage", curPage);
            // setRequestUAgent(sqlSession, request);
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
        return "success";
    }

    public void setRequestUAgent(SqlSession sqlSession, HttpServletRequest request) {
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        List<String> us = userMapper.getUAgent();
        if (us != null) {
            request.setAttribute("Agents", us);
        }

    }

    @Action(value = "/recharge", results = {
            @Result(name = "success", location = "/allRecord"),
            @Result(name = "error", location = "/addRecord")})
    public String recharge() {
        SqlSession sqlSession = null;
        try {
            SqlSessionFactory sqlSessionFactory = DB.getSqlSessionFactory();
            sqlSession = sqlSessionFactory.openSession();
            RecordMapper recordMapper = sqlSession.getMapper(RecordMapper.class);
            Record record = new Record();
            HttpServletRequest request = ServletActionContext.getRequest();
            String uName = request.getParameter("uName");
            if (uName != null) {
                record.setUName(uName);
            }
            String aId = request.getParameter("aId");

            if (aId != null) {
                aId = aId.trim();
                if (StringUtils.isNumeric(aId)) {

                    record.setAId(aId);
                }
            } else {

                request.setAttribute("msg", "�ύ��Ϣ���Ϸ�,�ͻ���Ϣ����!");
                return "error";
            }
            AccountMapper accountMapper = sqlSession.getMapper(AccountMapper.class);
            Account account = accountMapper.getAccountById(aId);


            if (account != null && account.getAName() != null) {

                record.setAName(account.getAName());
            } else {
                request.setAttribute("msg", "�ύ��Ϣ���Ϸ�,�ͻ���Ϣ����!");
                return "error";
            }

            String rClick = request.getParameter("rClick");
            if (rClick == null || !StringUtils.isNumeric(rClick)) {
                request.setAttribute("msg", "�ύ��Ϣ���Ϸ�,��������!");
                return "error";
            }
            record.setRClick(rClick);

            record.setRDate(new Timestamp(new java.util.Date().getTime()));


            String rMoney = request.getParameter("rMoney");
            if (rMoney == null || !StringUtils.isNumeric(rMoney)) {
                request.setAttribute("msg", "�ύ��Ϣ���Ϸ�,Ǯ������!");
                return "error";
            }
            Integer money = 0;
            Integer click = 0;
            try {
                money = Integer.valueOf(rMoney);
                click = Integer.valueOf(rClick);
            } catch (Exception e) {
                money = 0;
            }
            if (money == null && click == null) {

                request.setAttribute("msg", "��������!");
                return "error";
            }
            User user = (User) ServletActionContext.getRequest().getSession().getAttribute("login_");
            if (user != null && user.getUAuth() != null && user.getUAuth().intValue() == 1) {

                //��������Ա����У��
            } else {


                if (click == 2000 && money < 100) {
                    request.setAttribute("msg", "Ǯ����!");
                    return "error";
                } else if (click == 5000 && money < 200) {
                    request.setAttribute("msg", "Ǯ����!");
                    return "error";
                } else if (click == 7000 && money < 300) {
                    request.setAttribute("msg", "Ǯ����!");
                    return "error";
                } else if (click == 9000 && money < 400) {
                    request.setAttribute("msg", "Ǯ����!");
                    return "error";
                } else {
                    if ((click / 20) > money) {
                        request.setAttribute("msg", "Ǯ����!");
                        return "error";
                    }
                }
            }

            Integer status = click / 1000;
            if (status != null) {
                if (status.intValue() == 2 && status > account.getAStatus()) {
                    account.setAStatus(2);
                    accountMapper.update(account);
                } else if (status.intValue() == 5 && status > account.getAStatus()) {
                    account.setAStatus(3);
                    accountMapper.update(account);
                } else if (status.intValue() == 7 && status > account.getAStatus()) {
                    account.setAStatus(4);
                    accountMapper.update(account);
                } else if (status.intValue() == 9 && status > account.getAStatus()) {
                    account.setAStatus(5);
                    accountMapper.update(account);
                }

            }
            record.setRMoney(rMoney);
            String uid = request.getParameter("uId");
            if (uid == null || !StringUtils.isNumeric(uid)) {
                request.setAttribute("msg", "�ύ��Ϣ���Ϸ�,�����˴���!");
                return "error";
            }

            String uuid = request.getParameter("uuId");
            if (uuid != null && StringUtils.isNumeric(uuid)) {
                record.setUuId(uuid);
            }
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            User user2 = userMapper.getUserById(uid);
            if (user2 != null && user2.getUAgents() != null) {
                record.setUAgent(user2.getUAgents() + "");
            }
            record.setUId(uid);

            recordMapper.insert(record);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("point", rClick);
            map.put("aId", aId);
            accountMapper.InscutPoint(map);
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
        return "success";
    }

    @Action(value = "/delRecord", results = {
            @Result(name = "success", location = "/allRecord"),
            @Result(name = "error", location = "/allRecord")})
    public String deleteRecord() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String rid = request.getParameter("rid");

        if (rid == null || !StringUtils.isNumeric(rid)) {
            request.setAttribute("msg", "���ݲ��Ϸ�!");
            return "error";
        }

        User user = (User) ServletActionContext.getRequest().getSession().getAttribute("login_");
        if (user == null || user.getUAuth() == null || user.getUAuth() != 1) {

            request.setAttribute("msg", "����Ȩ��ɾ��������¼!");
            return "error";
        }

        SqlSession sqlSession = null;
        try {
            SqlSessionFactory sqlSessionFactory = DB.getSqlSessionFactory();
            sqlSession = sqlSessionFactory.openSession();
            RecordMapper recordMapper = sqlSession.getMapper(RecordMapper.class);
            int ok = recordMapper.delRecord(rid);
            if (ok < 0) {
                return "error";
            }

        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
        return "success";
    }

    @Action(value = "/edit", results = {
            @Result(name = "success", location = "/editRecord"),
            @Result(name = "error", location = "/editRecord")})
    public String edit() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String rid = request.getParameter("rid");

        if (rid == null || !StringUtils.isNumeric(rid)) {
            request.setAttribute("msg", "���ݲ��Ϸ�!");
            return "error";
        }
        User user = (User) ServletActionContext.getRequest().getSession().getAttribute("login_");
        if (user == null || user.getUAuth() == null || user.getUAuth() != 1) {

            request.setAttribute("msg", "����Ȩ���޸Ķ�����¼!");
            return "error";
        }


        String rClick = request.getParameter("rClick");
        if (rClick == null || !StringUtils.isNumeric(rClick)) {
            request.setAttribute("msg", "�ύ��Ϣ���Ϸ�,��������!");
            return "error";
        }
        String rMoney = request.getParameter("rMoney");
        if (rMoney == null || !StringUtils.isNumeric(rMoney)) {
            request.setAttribute("msg", "�ύ��Ϣ���Ϸ�,Ǯ������!");
            return "error";
        }
        String uuid = request.getParameter("uuId");

        Integer money = 0;
        Integer click = 0;
        try {
            money = Integer.valueOf(rMoney);
            click = Integer.valueOf(rClick);
        } catch (Exception e) {
            money = 0;
        }
        if (money != null && click != null) {

            if ((click / 20) > money) {
                request.setAttribute("msg", "Ǯ̫��!");
                return "error";
            }
        }
        SqlSession sqlSession = null;
        try {
            SqlSessionFactory sqlSessionFactory = DB.getSqlSessionFactory();
            sqlSession = sqlSessionFactory.openSession();
            RecordMapper recordMapper = sqlSession.getMapper(RecordMapper.class);
            Record record = recordMapper.getRecordByRId(rid);
            if (record != null && record.getRId() != null) {
                if (!record.getRClick().equals(rClick)) {
                    record.setRClick(rClick);
                    record.setRMoney(rMoney);

                }
                if (uuid != null && StringUtils.isNumeric(uuid)) {
                    record.setUuId(uuid);
                }
                recordMapper.editByRId(record);
            }


        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
        request.setAttribute("msg", "�޸ĳɹ�!");
        return "success";
    }
}
