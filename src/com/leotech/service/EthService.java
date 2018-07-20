package com.leotech.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.leotech.dao.EthDao;

public class EthService {
    public static JSONArray getAllEth() {
        return EthDao.instance().getAllEth();
    }
    public static JSONObject getCableInfo(String fmEthCode, String toEthCode) {
        return EthDao.instance().getCableInfo(fmEthCode, toEthCode);
    }
}
