package com.leotech.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.leotech.util.SpringContextBase;

public class MeshDao {
	private static MeshDao _dao = null;
	public JdbcTemplate jdbcTemplate;
	private MeshDao()
	{
		if (null == jdbcTemplate) {
			jdbcTemplate = (JdbcTemplate) SpringContextBase.getSpringContext().getBean("jdbcTemplate");
		}
	}
	public static synchronized MeshDao instance(){
		if(null == _dao){
			_dao = new MeshDao();
		}
		return _dao;
	}
	public List<JSONObject> getAllMesh()
	{
		List<JSONObject> list = new ArrayList<JSONObject>();
		try {
			//String sqlFilter = " where isDirty=1 and isShow=1 and mesh.material = material.id";
			String sqlFilter = " where isShow=1 and mesh.material = material.id";
			String strSql = "select *  from mesh, material";
			strSql += sqlFilter;
			list = jdbcTemplate.query(strSql, new RowMapper<JSONObject>() {
				public JSONObject mapRow(ResultSet result, int index)
						throws SQLException {
					JSONObject mesh = new JSONObject();
					mesh.put("uuid", result.getInt("uuid"));
					mesh.put("name", result.getString("name"));
					mesh.put("type", result.getString("type"));
					mesh.put("isShow", result.getInt("isShow"));
					mesh.put("length", result.getDouble("length"));
					mesh.put("width", result.getDouble("width"));
					mesh.put("height", result.getDouble("height"));
					mesh.put("x", result.getDouble("pos_x"));
					mesh.put("y", result.getDouble("pos_y"));
					mesh.put("z", result.getDouble("pos_z"));
					mesh.put("rot_x", result.getDouble("rot_x"));
					mesh.put("rot_y", result.getDouble("rot_y"));
					mesh.put("rot_z", result.getDouble("rot_z"));
					mesh.put("scl_x", result.getDouble("scl_x"));
					mesh.put("scl_y", result.getDouble("scl_y"));
					mesh.put("scl_z", result.getDouble("scl_z"));
					mesh.put("transparent", result.getBoolean("transparent"));
					mesh.put("opacity", result.getDouble("opacity"));
					mesh.put("skinColor", result.getString("skinColor"));
					mesh.put("isRepeat", result.getInt("isRepeat"));
					mesh.put("imgurl", result.getString("imgurl"));
					//UP闈�
					JSONObject up = new JSONObject();
					up.put("skinColor", result.getString("skin_up.skinColor"));
					up.put("imgurl", result.getString("skin_up.imgurl"));
					mesh.put("skin_up", up);
					//DOWN闈�
					JSONObject down = new JSONObject();
					down.put("skinColor", result.getString("skin_down.skinColor"));
					down.put("imgurl", result.getString("skin_down.imgurl"));
					mesh.put("skin_down", down);
					//LEFT闈�
					JSONObject left = new JSONObject();
					left.put("skinColor", result.getString("skin_left.skinColor"));
					left.put("imgurl", result.getString("skin_left.imgurl"));
					mesh.put("skin_left", left);
					//RIGHT闈�
					JSONObject right = new JSONObject();
					right.put("skinColor", result.getString("skin_right.skinColor"));
					right.put("imgurl", result.getString("skin_right.imgurl"));
					mesh.put("skin_right", right);
					//FORE闈�
					JSONObject fore = new JSONObject();
					fore.put("skinColor", result.getString("skin_fore.skinColor"));
					fore.put("imgurl", result.getString("skin_fore.imgurl"));
					mesh.put("skin_fore", fore);
					//BACK闈�
					JSONObject back = new JSONObject();
					back.put("skinColor", result.getString("skin_back.skinColor"));
					back.put("imgurl", result.getString("skin_back.imgurl"));
					mesh.put("skin_back", back);
					//parent opcode
					mesh.put("parent", result.getString("parent"));
					mesh.put("opcode", result.getString("opcode"));
					return mesh;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	public Boolean updateIsDirty(int uuid, Boolean isDirty) {
		int rows = this.jdbcTemplate.update("update mesh set isDirty=? where uuid=?", isDirty, uuid);
		return rows>0 ? true : false;
	}
	public Boolean updateIsDirty_All(Boolean isDirty) {
		int rows = this.jdbcTemplate.update("update mesh set isDirty=? where 1=1", isDirty);
		return rows>0 ? true : false;
	}
	public JSONArray getAllTpl()
	{
		final JSONArray tpls = new JSONArray();
		try {
			String sqlFilter = " where isShow=1  and template.material = material.id";
			String strSql = "select *  from template, material";
			strSql += sqlFilter;
			jdbcTemplate.query(strSql, new RowCallbackHandler(){
				public void processRow(ResultSet result) throws SQLException {
					JSONObject tpl = new JSONObject();
					tpl.put("uuid", result.getInt("uuid"));
					tpl.put("name", result.getString("name"));
					tpl.put("type", result.getString("type"));
					tpl.put("length", result.getDouble("length"));
					tpl.put("width", result.getDouble("width"));
					tpl.put("height", result.getDouble("height"));
					tpl.put("x", result.getDouble("pos_x"));
					tpl.put("y", result.getDouble("pos_y"));
					tpl.put("z", result.getDouble("pos_z"));
					tpl.put("rot_x", result.getDouble("rot_x"));
					tpl.put("rot_y", result.getDouble("rot_y"));
					tpl.put("rot_z", result.getDouble("rot_z"));
					tpl.put("scl_x", result.getDouble("scl_x"));
					tpl.put("scl_y", result.getDouble("scl_y"));
					tpl.put("scl_z", result.getDouble("scl_z"));
					tpl.put("transparent", result.getDouble("transparent"));
					tpl.put("opacity", result.getDouble("opacity"));
					tpl.put("length", result.getDouble("length"));
					tpl.put("skinColor", result.getString("skinColor"));
					tpl.put("isRepeat", result.getInt("isRepeat"));
					tpl.put("imgurl", result.getString("imgurl"));
					JSONObject up = new JSONObject();
					up.put("skinColor", result.getString("skin_up.skinColor"));
					up.put("imgurl", result.getString("skin_up.imgurl"));
					tpl.put("skin_up", up);
					JSONObject down = new JSONObject();
					down.put("skinColor", result.getString("skin_down.skinColor"));
					down.put("imgurl", result.getString("skin_down.imgurl"));
					tpl.put("skin_down", down);
					JSONObject left = new JSONObject();
					left.put("skinColor", result.getString("skin_left.skinColor"));
					left.put("imgurl", result.getString("skin_left.imgurl"));
					tpl.put("skin_left", left);
					JSONObject right = new JSONObject();
					right.put("skinColor", result.getString("skin_right.skinColor"));
					right.put("imgurl", result.getString("skin_right.imgurl"));
					tpl.put("skin_right", right);
					JSONObject fore = new JSONObject();
					fore.put("skinColor", result.getString("skin_fore.skinColor"));
					fore.put("imgurl", result.getString("skin_fore.imgurl"));
					tpl.put("skin_fore", fore);
					JSONObject back = new JSONObject();
					back.put("skinColor", result.getString("skin_back.skinColor"));
					back.put("imgurl", result.getString("skin_back.imgurl"));
					tpl.put("skin_back", back);
					//parent opcode
					tpl.put("parent", result.getString("parent"));
					tpl.put("opcode", result.getString("opcode"));
					tpls.add(tpl);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tpls;
	}
}
