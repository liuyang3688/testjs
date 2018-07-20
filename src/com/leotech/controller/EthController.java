package com.leotech.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.leotech.service.EthService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("eth")
public class EthController {

    @RequestMapping("get_all_eth")
    public void getAllEth(HttpServletRequest request, HttpServletResponse response)
    {
        JSONArray eths = new JSONArray();
        eths = EthService.getAllEth();
        try {
            response.getWriter().print(eths);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @RequestMapping(value="get_cable_info", method=RequestMethod.POST)
    public void getCableInfo(
            @RequestParam("fmEthCode") String fmEthCode,
            @RequestParam("toEthCode") String toEthCode,
            HttpServletResponse response){
        JSONObject info = EthService.getCableInfo(fmEthCode, toEthCode);
        try {
            response.getWriter().print(info);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
