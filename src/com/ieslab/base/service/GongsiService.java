package com.ieslab.base.service;

import java.util.List;

import com.ieslab.base.dao.GongsiDao;
import com.ieslab.base.model.Gongsi;

public class GongsiService {

	/**
	 * 根据公司id获取装机信息
	 * @param id
	 * @return
	 */
	public static List<Gongsi> findAllZJXX(int id) {
		return GongsiDao.instance().findAllZJXX(id);
	}
}
