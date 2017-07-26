package com.hjq.mytomcat.bean;

import java.util.ArrayList;
import org.json.JSONObject;
/**
 * @author HJQ
 */
public class SQLiteCursorBean {

	/**
	 * 数据库操作语句返回值或者Cursor的总数
	 * Database operation statements return values or Cursor count
	 */
	public long result;
	
	/**
	 * 返回的cursor列名
	 * return cursor column name
	 */
	public ArrayList<String> columnNames;
	
	/**
	 * 返回cursor中的结果，这里可以自定义类改变JSONObject作为泛型
	 * return cursor the results in, Here you can customize class change the JSONObject such as generics
	 */
	public ArrayList<JSONObject> cursor;
}
