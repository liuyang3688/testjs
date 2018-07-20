package com.leotech.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONArray;
import com.leotech.util.SpringContextBase;

public class EthDao {
    private static EthDao _dao = null;
    public JdbcTemplate jdbcTemplate;
    private static Map<String, EthInfo > devEthCount = null;
    private EthDao()
    {
        if (null == jdbcTemplate) {
            jdbcTemplate = (JdbcTemplate) SpringContextBase.getSpringContext().getBean("jdbcTemplate");
        }

    }
    public static synchronized EthDao instance(){
        if(null == _dao){
            _dao = new EthDao();
        }
        return _dao;
    }
    public void fetchDevEthCount(){
        try {
            if (devEthCount == null) {
                devEthCount = new HashMap<String, EthInfo>();
            } else {
                devEthCount.clear();
            }
            devEthCount.clear();
            String sqlFilter = " where eth.device = device.id and device.typeid = device_type.uuid";
            String strSql = "select eth.code, eth.peerId, device_type.ethRowCount, device_type.ethColCount from eth, device, device_type";
            strSql += sqlFilter;
            jdbcTemplate.query(strSql, new RowCallbackHandler(){
                public void processRow(ResultSet result) throws SQLException {
                    EthInfo info = new EthInfo();
                    info.code = result.getString("code");
                    info.peerId = result.getString("peerId");
                    info.ethRowCount = result.getInt("ethRowCount");
                    info.ethColCount = result.getInt("ethColCount");
                    devEthCount.put(info.code, info);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public JSONArray getAllEth()
    {
        fetchDevEthCount();
        final JSONArray eths = new JSONArray();
        try {
//            String sqlFilter = " where eth.device = device.id";
//            String strSql = "select eth.code, eth.peerId, device.ethRowCount, device.ethColCount from eth, device";
//            strSql += sqlFilter;
//            jdbcTemplate.query(strSql, new RowCallbackHandler(){
//                public void processRow(ResultSet result) throws SQLException {
//                    JSONObject eth = new JSONObject();
//                    eth.put("code", result.getString("code"));
//                    eth.put("peerCode", result.getString("peerId"));
//                    eth.put("fmEthRowCount", result.getString("ethRowCount"));
//                    eth.put("fmEthColCount", result.getString("ethColCount"));
//                    eths.add(eth);
//                }
//            });
            for (EthInfo info : devEthCount.values()){
                JSONObject eth = new JSONObject();
                eth.put("code", info.code);
                eth.put("peerCode", info.peerId);
                eth.put("fmEthRowCount", info.ethRowCount);
                eth.put("fmEthColCount", info.ethColCount);
                if (info.peerId == null || !devEthCount.containsKey(info.peerId)){
                    continue;
                }
                eth.put("toEthRowCount", devEthCount.get(info.peerId).ethRowCount);
                eth.put("toEthColCount", devEthCount.get(info.peerId).ethColCount);
                eths.add(eth);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return eths;
    }
    public JSONObject getCableInfo(String fmEthCode, String toEthCode) {
        final JSONObject obj = new JSONObject();
        try {
            String sqlFilter = " where eth.device = device.id and eth.code='" + fmEthCode+"'";
            String strSql = "select eth.name, device.name  from eth,device";
            strSql += sqlFilter;
            jdbcTemplate.query(strSql, new RowCallbackHandler(){
                public void processRow(ResultSet result) throws SQLException {
                obj.put("fmDeviceName", result.getString("device.name"));
                obj.put("fmEthName", result.getString("eth.name"));
                }
            });

            sqlFilter = " where eth.device = device.id and eth.code='" + toEthCode +"'";
            strSql = "select eth.name, device.name  from eth,device";
            strSql += sqlFilter;
            jdbcTemplate.query(strSql, new RowCallbackHandler(){
                public void processRow(ResultSet result) throws SQLException {
                    obj.put("toDeviceName", result.getString("device.name"));
                    obj.put("toEthName", result.getString("eth.name"));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }
    class EthInfo {
        public String   code;
        public String   peerId;
        public int      ethRowCount;
        public int      ethColCount;
        public EthInfo(){
            this.ethRowCount = 1;
            this.ethColCount = 10;
        }
    }
}

