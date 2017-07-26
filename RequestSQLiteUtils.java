package com.hjq.mytomcat.bean;

import org.json.JSONException;
import org.json.JSONObject;
import android.content.ContentValues;
/**
 * @author HJQ
 */
public class RequestSQLiteUtils {
	
	private final static String TYPE_EXECSQL = "execSQL";
	private final static String TYPE_RAWQUERY = "rawQuery";
	private final static String TYPE_INSERT = "insert";
	private final static String TYPE_DELETE = "delete";
	private final static String TYPE_UPDATE = "update";
	private final static String TYPE_QUERY = "query";
	
	/**
	 * @param path	The relative path to the database on the server
	 * @param path	数据库在服务器上的相对路径
	 */
	public static String execSQL(String path, String sql, Object[] bindArg) throws JSONException{
		JSONObject json = checkAndCreate(path, TYPE_EXECSQL);
		JsonUtils.addJSONObject(json, "sql", sql);
		JsonUtils.addJSONArray(json, "bindArg", bindArg);
		return json.toString();
	}
	
	/**
	 * @param path	The relative path to the database on the server
	 * @param path	数据库在服务器上的相对路径
	 */
	public static String rawQuery(String path, String sql, String[] selectionArgs) throws JSONException{
		JSONObject json = checkAndCreate(path, TYPE_RAWQUERY);
		JsonUtils.addJSONObject(json, "sql", sql);
		JsonUtils.addJSONArray(json, "selectionArgs", selectionArgs);
		return json.toString();
	}
	
	/**
	 * @param path	The relative path to the database on the server
	 * @param path	数据库在服务器上的相对路径
	 */
	public static String insert(String path, String table, String nullColumnHack, ContentValues values) throws JSONException{
		JSONObject json = checkAndCreate(path, TYPE_INSERT);
		JsonUtils.addJSONObject(json, "table", table);
		JsonUtils.addJSONObject(json, "nullColumnHack", nullColumnHack);
		JsonUtils.addContentValues(json, "values", values);
		return json.toString();
	}
	
	/**
	 * @param path	The relative path to the database on the server
	 * @param path	数据库在服务器上的相对路径
	 */
	public static String delete(String path, String table, String whereClause, String[] whereArgs) throws JSONException{
		JSONObject json = checkAndCreate(path, TYPE_DELETE);
		JsonUtils.addJSONObject(json, "table", table);
		JsonUtils.addJSONObject(json, "whereClause", whereClause);
		JsonUtils.addJSONArray(json, "whereArgs", whereArgs);
		return json.toString();
	}
	
	/**
	 * @param path	The relative path to the database on the server
	 * @param path	数据库在服务器上的相对路径
	 */
	public static String update(String path, String table, ContentValues values, String whereClause, String[] whereArgs) throws JSONException{
		JSONObject json = checkAndCreate(path, TYPE_UPDATE);
		JsonUtils.addJSONObject(json, "table", table);
		JsonUtils.addContentValues(json, "values", values);
		JsonUtils.addJSONObject(json, "whereClause", whereClause);
		JsonUtils.addJSONArray(json, "whereArgs", whereArgs);
		return json.toString();
	}
	
	/**
	 * @param path	The relative path to the database on the server
	 * @param path	数据库在服务器上的相对路径
	 */
	public static String query(String path, String table, String[] columns, String selection,
			String[] selectionArgs, String groupBy, String having,
			String orderBy, String limit) throws JSONException{
		
		JSONObject json = checkAndCreate(path, TYPE_QUERY);
		JsonUtils.addJSONObject(json, "table", table);
		JsonUtils.addJSONArray(json, "columns", columns);
		JsonUtils.addJSONObject(json, "selection", selection);
		JsonUtils.addJSONArray(json, "selectionArgs", selectionArgs);
		JsonUtils.addJSONObject(json, "groupBy", groupBy);
		JsonUtils.addJSONObject(json, "having", having);
		JsonUtils.addJSONObject(json, "orderBy", orderBy);
		JsonUtils.addJSONObject(json, "limit", limit);
		return json.toString();
	}
	
	/**
	 * Check the data path and generate common parameters JSONObject
	 * 检查数据路径和生成共性参数的JSONObject
	 */
	private static JSONObject checkAndCreate(String path, String type) throws JSONException{
		if (path == null || path.equals("")) {
			throw new NullPointerException("数据库所在的路径不能为空");
		}
		
		JSONObject json = new JSONObject();
		JsonUtils.addJSONObject(json, "path", path);
		JsonUtils.addJSONObject(json, "type", type);
		return json;
	}
}