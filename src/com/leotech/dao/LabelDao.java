package com.leotech.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONArray;
import com.leotech.util.SpringContextBase;

public class LabelDao {
	private static LabelDao _dao = null;
	private static Map<Integer, JSONArray > _texts = null;
	public JdbcTemplate jdbcTemplate;
	private LabelDao()
	{
		if (null == jdbcTemplate) {
			jdbcTemplate = (JdbcTemplate) SpringContextBase.getSpringContext().getBean("jdbcTemplate");
		}
	}
	public static synchronized LabelDao instance(){
		if(null == _dao){
			_dao = new LabelDao();
		}
		return _dao;
	}
	public void fetchAllText()
	{
		if( _texts == null ) {
			try {
				String sqlFilter = " where isShow=1";
				String strSql = "select *  from text";
				strSql += sqlFilter;
				jdbcTemplate.query(strSql, new RowCallbackHandler(){
					public void processRow(ResultSet result) throws SQLException {
						String labelIds = result.getString("labelIds");
						if(labelIds == null){
							return;
						}
						String[] listIds = labelIds.split(",");
						if( _texts == null) {
							_texts = new ConcurrentHashMap<Integer, JSONArray >();
						}
						JSONObject text = new JSONObject();
						text.put("uuid", result.getInt("uuid"));
						text.put("name", result.getString("name"));
						text.put("font", result.getString("font"));
						text.put("color", result.getString("color"));
						text.put("text", result.getString("text"));
						text.put("offX", result.getDouble("offX"));
						text.put("offY", result.getDouble("offY"));
						for(int i=0; i<listIds.length; ++i){
						    String idstr = listIds[i];
						    int pos = 0;
						    if ((pos = idstr.indexOf('-')) > 0){
						       String start = idstr.substring(0, pos);
						       String end = idstr.substring(pos+1);
                                int idStart = Integer.parseInt(start);
                                int idEnd = Integer.parseInt(end);
                                while(idStart <= idEnd) {
                                    if(!_texts.containsKey(idStart)){
                                        JSONArray list = new JSONArray();
                                        _texts.put(idStart, list);
                                    }
                                    _texts.get(idStart).add(text);
                                    idStart ++;
                                }
                            } else {
                                int labelId = Integer.parseInt(idstr);
                                if(!_texts.containsKey(labelId)){
                                    JSONArray list = new JSONArray();
                                    _texts.put(labelId, list);
                                }
                                _texts.get(labelId).add(text);
                            }
						}
					}
				});
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public List<JSONObject> getAllLabel()
	{
		_texts = null;
	    fetchAllText();
		List<JSONObject> list = new ArrayList<JSONObject>();
		try {
			//String sqlFilter = " where isDirty=1 and isShow=1";
			String sqlFilter = " where isShow=1";
			String strSql = "select *  from label";
			strSql += sqlFilter;
			list = jdbcTemplate.query(strSql, new RowMapper<JSONObject>() {
				public JSONObject mapRow(ResultSet result, int index)
						throws SQLException {
					JSONObject label = new JSONObject();
					try{
						int labelId = result.getInt("uuid");
						label.put("uuid", labelId);
						label.put("name", result.getString("name"));
						label.put("isShow", result.getInt("isShow"));
						label.put("length", result.getString("length"));
						label.put("width", result.getString("width"));
						label.put("height", result.getString("height"));
						label.put("x", result.getDouble("pos_x"));
						label.put("y", result.getDouble("pos_y"));
						label.put("z", result.getDouble("pos_z"));
						label.put("rot_x", result.getDouble("rot_x"));
						label.put("rot_y", result.getDouble("rot_y"));
						label.put("rot_z", result.getDouble("rot_z"));
						label.put("scl_x", result.getDouble("scl_x"));
						label.put("scl_y", result.getDouble("scl_y"));
						label.put("scl_z", result.getDouble("scl_z"));
						label.put("parent", result.getString("parent"));
						label.put("fillColor", result.getString("fillColor"));
						label.put("pic", result.getString("pic"));
					
						if(_texts.containsKey(labelId)) {
							label.put("texts", _texts.get(labelId));
						}
					}
					catch(Exception e){
						e.printStackTrace();
					}
				
					return label;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	public Boolean updateIsDirty(int uuid, Boolean isDirty) {
		int rows = this.jdbcTemplate.update("update label set isDirty=? where uuid=?", isDirty, uuid);
		return rows>0 ? true : false;
	}
	public Boolean updateIsDirty_All(Boolean isDirty) {
		int rows = this.jdbcTemplate.update("update label set isDirty=? where 1=1", isDirty);
		return rows>0 ? true : false;
	}
}

