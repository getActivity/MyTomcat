package com.hjq.mytomcat.bean;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.ContentValues;
import android.database.Cursor;
/**
 * @author HJQ
 */
public class JsonUtils {
	
	//客户端的转换工具
	//The client's conversion tool
	
	/**
	 * 添加JSONObject对象
	 * Add a JSONObject object
	 */
	public static void addJSONObject(JSONObject json, String name, String string) throws JSONException{
		if (string != null && !string.equals("")) {
			json.put(name, string);
		}
	}
	
	/**
	 * 添加JSONArray对象
	 * Add a JSONArray object
	 */
	public static void addJSONArray(JSONObject json, String name, Object[] objects) throws JSONException{
		if (objects != null && objects.length != 0) {
			json.put(name, ArrayToJSONArray(objects));
		}
	}
	
	/**
	 * 添加ContentValues对象
	 * Add a ContentValues object
	 */
	public static void addContentValues(JSONObject json, String name, ContentValues values) throws JSONException{
		if (values != null && values.size() != 0) {
			json.put(name, ContentValuesToJSONArray(values));
		}
	}
	
	
	/**
	 * 数组转JsonArray
	 * Array conversion to JsonArray
	 */
	public static JSONArray ArrayToJSONArray(Object[] array) {
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < array.length; i++) {
			jsonArray.put(array[i]);
		}
		return jsonArray;
	}
	
	/**
	 * 将ContentValues转换成JSONArray
	 * ContentValues conversion to JSONArray
	 */
	public static JSONArray ContentValuesToJSONArray(ContentValues values) throws JSONException {
		
		JSONArray jsonArray = new JSONArray();
		Set<Entry<String, Object>> valueSet = values.valueSet();
		
		for(Iterator<Entry<String, Object>> iterator = valueSet.iterator(); iterator.hasNext(); ){
			Entry<String, Object> entry = iterator.next();
			jsonArray.put(new JSONObject().put(entry.getKey(), entry.getValue()));
		}
		
		return jsonArray;		
	}
	
	
	
	//服务器的转换工具
	//Server conversion tools
	
	/**
	 * 获取查询的结果，将Cursor封装在Json中
	 * Get the results of the query, the Cursor encapsulated in Json
	 */
	public static String getJsonString(Cursor cursor) throws JSONException {
		
		int count = cursor.getCount();
		
		if (count > 0) {
			
			JSONObject  resultJsonObject = new JSONObject();
			
			JSONArray columnNamesJsonArray = new JSONArray();
			String[] columnNames = cursor.getColumnNames();
			for (int i = 0; i < columnNames.length; i++) {
				columnNamesJsonArray.put(columnNames[i]);
			}
			resultJsonObject.put("columnNames", columnNamesJsonArray);
			
			JSONArray cursorJsonArray = new JSONArray();
			JSONObject  valuesJsonObject;
			
			while(cursor.moveToNext()){
				valuesJsonObject = new JSONObject();
				for (int i = 0; i < columnNames.length; i++) {
					String columnName = columnNames[i];
					valuesJsonObject.put(columnName, cursor.getString(i));
				}
				
				cursorJsonArray.put(valuesJsonObject);
			}
			//关闭cursor
			cursor.close();
			
			resultJsonObject.put("cursor", cursorJsonArray);
			
			return resultJsonObject.toString();
		}
		
		return null;
	}
	
	/**
	 * 将JsonArray转成字符串数组
	 * JsonArray conversion to array of strings
	 */
	public static String[] JsonArrayToStringArray(JSONArray jsonArray) throws JSONException {
		
		if (jsonArray != null) {
			
			int length = jsonArray.length();
			
			if (length > 0) {
				String[] strings = new String[length];
				for (int i = 0; i < length; i++) {
					strings[i] = jsonArray.getString(i);
				}
				return strings;
			}
		}
		
		return null;
	}
	
	/**
	 * 将JsonArray转成ContentValues
	 * JsonArray conversion to ContentValues
	 */
	public static ContentValues JsonArrayToContentValues(JSONArray jsonArray) throws JSONException {
		
		if (jsonArray != null) {
			
			int length = jsonArray.length();
			
			if (length > 0) {
				
				ContentValues contentValues = new ContentValues();
				
				JSONObject jsonObject;
				for (int i = 0; i < length; i++) {
					
					jsonObject = jsonArray.getJSONObject(i);
					
					JSONArray namesJSONArray = jsonObject.names();
					
					int length2 = namesJSONArray.length();
					for (int j = 0; j < length2; j++) {
						String name = namesJSONArray.getString(j);
						String value = jsonObject.getString(name);
						contentValues.put(name, value);
					}
				}
				return contentValues;
			}
		}
		
		return null;
	}
}
