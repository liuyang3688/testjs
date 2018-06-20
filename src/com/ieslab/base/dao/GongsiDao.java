package com.ieslab.base.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.ieslab.base.model.Gongsi;
import com.ieslab.util.SpringContextBase;

public class GongsiDao {
	
	private static GongsiDao dao = null;
	@Autowired
	public JdbcTemplate jdbcTemplate;
	public GongsiDao() {
		if (jdbcTemplate == null) {
			jdbcTemplate = (JdbcTemplate) SpringContextBase.getSpringContext()
					.getBean("jdbcTemplate");
		}
	}
	
	public static synchronized GongsiDao instance(){
		if(dao == null){
			dao = new GongsiDao();
		}
		return dao;
	}
	
	/**
	 * 根据公司ID获取装机信息
	 * @param i
	 * @return
	 */
	public List<Gongsi> findAllZJXX(int i) {
		List<Gongsi> list = new ArrayList<Gongsi>();
		try {
			String sqlFilter = " where ID = " + i;
			String strSql = "select id, mingzi, CLFJNums, ZLFJNums, CLFCNums, ZLFCNums, CLFJCaps, ZLFJCaps, CLGFNums, ZLGFNums, CLGFCaps, ZLGFCaps from powercorp";
			strSql += sqlFilter;
			list = jdbcTemplate.query(strSql, new RowMapper<Gongsi>() {
				public Gongsi mapRow(ResultSet result, int index)
						throws SQLException {
					Gongsi gs = new Gongsi();
					gs.setID(result.getInt("id"));
					gs.setMingzi(result.getString("mingzi"));
					gs.setZLFJNums(result.getInt("ZLFJNums"));
					gs.setCLFJNums(result.getInt("CLFJNums"));
					gs.setZLFJNums(result.getInt("ZLFJNums"));
					gs.setCLFCNums(result.getInt("CLFCNums"));
					gs.setZLFCNums(result.getInt("ZLFCNums"));
					gs.setCLFJCaps(result.getInt("CLFJCaps"));
					gs.setZLFJCaps(result.getInt("ZLFJCaps"));
					gs.setCLGFNums(result.getInt("CLGFNums"));
					gs.setZLGFNums(result.getInt("ZLGFNums"));
					gs.setCLGFCaps(result.getInt("CLGFCaps"));
					gs.setZLGFCaps(result.getInt("ZLGFCaps"));
					return gs;
				}
			});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}
	

	/**
	 * 获取公司信息
	 * @return
	 */
	public List<Gongsi> findAll() {
		List<Gongsi> list = new ArrayList<Gongsi>();
		String strSql = "select * from dianwang";
		list = jdbcTemplate.query(strSql, new RowMapper<Gongsi>() {
			public Gongsi mapRow(ResultSet result, int index)
					throws SQLException {
				Gongsi gs = new Gongsi();
				gs.setID(result.getInt("id"));
				gs.setMingzi(result.getString("mingzi"));
				gs.setZLFJNums(result.getInt("ZLFJNums"));
				gs.setCLFJNums(result.getInt("CLFJNums"));
				gs.setZLFJNums(result.getInt("ZLFJNums"));
				gs.setCLFCNums(result.getInt("CLFCNums"));
				gs.setZLFCNums(result.getInt("ZLFCNums"));
				gs.setCLFJCaps(result.getInt("CLFJCaps"));
				gs.setZLFJCaps(result.getInt("ZLFJCaps"));
				gs.setCLGFNums(result.getInt("CLGFNums"));
				gs.setZLGFNums(result.getInt("ZLGFNums"));
				gs.setCLGFCaps(result.getInt("CLGFCaps"));
				gs.setZLGFCaps(result.getInt("ZLGFCaps"));
				return gs;
			}
		});
		return list;
	}
}
