package com.leotech.util;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * oracle建表 用户角色权限表
 * @author Administrator
 *
 */
public class CreateUserRolePermissionTable {
	
	public JdbcTemplate jdbcTemplate;
	public CreateUserRolePermissionTable() {
		if (jdbcTemplate == null) {
			jdbcTemplate = (JdbcTemplate) SpringContextBase.getSpringContext()
					.getBean("jdbcTemplate");
		}
	}
	
	public void createTable(){
		String sql=null;
		int num=0;
		//1.判断web_user是否存在  不存在在创建表
		sql="SELECT COUNT(*) FROM User_Tables WHERE table_name = 'WEB_USER'";
		num=jdbcTemplate.queryForInt(sql);
		if(num==0){
			  sql="create table WEB_USER ( "+
				"	  id NUMBER not null constraint WEB_USER_id primary key, "+
			    "     name  VARCHAR2(20),  "+
				"	  password    VARCHAR2(20),  "+
				"	  info        VARCHAR2(200), "+
				"	  powercorpid NUMBER, "+
				"	  changzhanid NUMBER "+
				"	)"	;
			 jdbcTemplate.update(sql);
		}
		//2.判断web_role是否存在   不存在则创建表
		sql="SELECT COUNT(*) FROM User_Tables WHERE table_name = 'WEB_ROLE'";
		num=jdbcTemplate.queryForInt(sql);
		if(num==0){
			sql="create table WEB_ROLE ("+				
				" id   NUMBER not null constraint WEB_ROLE_id primary key,"+
				" name VARCHAR2(20) not null,"+
				" info VARCHAR2(20)"+
				")";
			jdbcTemplate.update(sql);
		}
		//3.判断WEB_PERMISSION是否存在  不存在则创建表
		sql="SELECT COUNT(*) FROM User_Tables WHERE table_name = 'WEB_PERMISSION'";
		num=jdbcTemplate.queryForInt(sql);
		if(num==0){
			sql="create table WEB_PERMISSION ("+
				"  id   NUMBER not null constraint WEB_PERMISSION_id primary key,"+
				"  name VARCHAR2(40) not null,"+
				"  info VARCHAR2(40)"+
				")";
			jdbcTemplate.update(sql);
		}
		//4.判断WEB_USER_ROLE是否存在  不存在则创建表
		sql="SELECT COUNT(*) FROM User_Tables WHERE table_name = 'WEB_USER_ROLE'";
		num=jdbcTemplate.queryForInt(sql);
		if(num==0){
			sql="create table WEB_USER_ROLE ("+
				"  userid NUMBER,"+
				"  roleid NUMBER "+
				")";
			jdbcTemplate.update(sql);
		}
		//5.判断WEB_ROLE_PERMISSION是否存在  不存在则创建表
		sql="SELECT COUNT(*) FROM User_Tables WHERE table_name = 'WEB_ROLE_PERMISSION'";
		num=jdbcTemplate.queryForInt(sql);
		if(num==0){
			sql="create table WEB_ROLE_PERMISSION ("+
				"  roleid NUMBER,"+
				"  permissionid NUMBER "+
				")";
			jdbcTemplate.update(sql);
		}		
	}
}
