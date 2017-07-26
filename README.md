# 简述

# intro

>[点击下载MyTomcat安装程序](https://raw.githubusercontent.com/getActivity/MyTomcat/master/MyTomcat_3.0.apk)

>[Click on the download MyTomcat installation program](https://raw.githubusercontent.com/getActivity/MyTomcat/master/MyTomcat_3.0.apk)

![](/screenshot/MyTomcat1_zh.png)
![](/screenshot/MyTomcat2_zh.png)
![](/screenshot/MyTomcat3_zh.png)
![](/screenshot/MyTomcat4_zh.png)
![](/screenshot/MyTomcat5.png)

![](/screenshot/MyTomcat1_en.png)
![](/screenshot/MyTomcat2_en.png)
![](/screenshot/MyTomcat3_en.png)
![](/screenshot/MyTomcat4_en.png)

# 欢迎使用MyTomcat

# Welcome to use MyTomcat

>基于HTTP协议制作的服务器，支持GET和POST请求

>Based on the HTTP protocol server, support the GET and POST requests

>能跨设备进行访问，支持访问和上传文件，断点续传，操作SQLite数据库

>Can other device access, support access and upload files, breakpoint continuingly, SQLite database operation

>对带中文URL和中文参数有比较好的支持

>With Chinese URL and Chinese parameters have good support

## 访问服务器

## Access to the server

>服务器与客户端处于同一个设备上

>The client and server in the same device

	http://127.0.0.1: + port
	http://localhost: + port

---

	http://127.0.0.1:8888
	http://localhost:8888

>跨设备访问，在同一局域网环境下，使用当前服务器设备的IP地址，推荐使用静态IP

>Different devices for a visit, In the same LAN environment, using the current IP address of the server device, it is recommended to use a static IP

	http:// + host address + ":" + port

---

	http://192.168.1.123：8888

>请注意：模拟器创建的服务端将无法被其他设备访问

>Please note: simulator to create the server will not be able to make other device access

>如果您想改变访问的主页，请到设置中进行更改

>If you want to change the home page access, please make changes to the Settings

## 访问服务器上的文件

## To access the file on the server

>若您设置了以 /mnt/sdcard/（SD卡根目录） 为服务目录

>If you set to /mnt/sdcard/ (SD kagan directory) for the service directory

>若您想访问服务器上的 /mnt/sdcard/MyTomcat.apk 文件

>If you want to access server /mnt/sdcard/MyTomcat.apk file

>可以使用以下地址进行访问该资源，访问成功将返回响应码200，若这个文件不存在则返回响应码404

>You can use the following address to access the resources, access successfully returns the response code 200, if the file does not exist, it returns the response code 404

	http://127.0.0.1:8888/MyTomcat.apk

>这种方式比较快捷，但并不推荐此方法，推荐如下两种方式

>This way is quick, but does not recommend this method, recommend the following two ways

## GET请求带参数访问服务器上的文件

## GET request with parameter access the file on the server

>在参数“path”加上需要访问的文件地址，地址根据您设置的服务器目录而定

>In the parameter "path" add need to access the file address, the address according to you to set the server directory

	http://127.0.0.1:8888/file?path=

>若您设置了以 /mnt/sdcard/（SD卡根目录） 为服务目录

>If you set to /mnt/sdcard/ (SD kagan directory) for the service directory

>若您想访问服务器上的 /mnt/sdcard/MyTomcat.apk 文件

>If you want to access server /mnt/sdcard/MyTomcat.apk file

>可以使用以下地址进行访问该资源，访问成功将返回响应码200，若这个文件不存在则返回响应码404

>You can use the following address to access the resources, access successfully returns the response code 200, if the file does not exist, it returns the response code 404

	http://127.0.0.1:8888/file?path=MyTomcat.apk

>以您的设置的服务目录为基准，以服务目录中的文件路径作为参数，但不包含服务目录所在路径名称

>Based on your setup service directory, to serve in the directory path to the file as a parameter, but does not include the service directory in the path name

>若您想访问包含中文路径的文件，可以通过对参数值进行编码 “MyTomcat.apk”，请不要对参数名进行编码 “?path=”

>If you want to access files, including the Chinese path can be based on the parameter value is encoded "MyTomcat.apk" , careful not to encode parameter name "?path =".

	URLDecoder.decode(String s, String charsetName);

>s：是需要被编码的字符串

>s: it is need to be encoded string

>charsetName：编码的字符编码，建议使用UTF-8，服务器上设置的字符编码必须和此编码一致，否则会导致乱码

>Is coded character encoding, it is recommended to use utf-8, set the character encoding on the server must be in accordance with this code, otherwise it will lead to the messy code

	"http://127.0.0.1:8888/file?path=" + URLDecoder.decode("MyTomcat.apk", "UTF-8");

>GET请求也支持中文，步骤比较繁琐，推荐您使用POST请求进行访问

>GET request also supports Chinese, steps more complicated, it is recommended that you use a POST request for a visit

## POST请求带参数访问服务上的文件

## POST request with parameter access the file on the server

>GET和Post请求的区别在于，GET请求的参数是通过URL传递给服务器的，而POST将参数作为输出流传递到服务器

>The difference between the GET and Post requests, the parameters of the GET request is passed through the URL to the server, and Post transfer parameters as the output stream to the server

>如果您的URL里面包含中文参数，建议使用此方法，您必须确保该服务器上的字符编码支持中文才不会乱码

>If your URL contains Chinese parameters, it is recommended to use this method, you must make sure that the server will not garbled character encoding support Chinese

>使用基本与GET无太大差别，若想访问上一个例子（GET请求）上的文件

>Using basic and GET no much difference, if you want to visit an example of the GET request on file

	http://127.0.0.1:8888/file

---

	path=MyTomcat.apk

---

	conn.setDoOutput(true);
	OutputStream os = conn.getOutputStream();
	os.write("path=MyTomcat.apk".getBytes());

## 文件的断点续传

## Breakpoint continuingly on file

>这个功能需要通过参数进行告知服务器

>This feature requires via parameters to the server

>您若想获取文件的部分内容，支持GET和POST，GET请求为例子，可以直接在URL加入参数

>If you want to GET part of the file content, support the GET and POST, GET requests for example, can be directly in the URL parameter

>访问成功将返回响应码200，若这个文件不存在则返回响应码404

>Access successfully returns the response code 200, if the file does not exist then returns a response code 404

	http://127.0.0.1:8888/file?path=MyTomcat/MyTomcat.apk&range=1000

>注意：“range” 参数是指文件内容的起始位置，用于获取文件从这个位置开始后面的内容

>Note: "range" parameter is refers to the starting position of the file content, used to get files from this location at the back of the content

## 使用POST请求上传文件

## Use a POST request to upload files

>上传文件只能使用POST请求的方式进行，不支持GET请求，不支持断点续传

>Upload files can only use a POST request manner, does not support a GET request, does not support breakpoint continuingly

>建议您不要上传超过 30MB 的文件，否则您的程序可能会内存溢出，实际请根据您的设备实际为准

>It is recommended that you do not upload more than 30 MB of file, otherwise your application may be out of memory, please according to your actual equipment actual shall prevail

>上传成功后则返回响应码200，上传失败则返回响应码412

>Uploaded successfully after it returns the response code 200, upload fails then returns a response code 412

>需要严格设置读取时长，否则可能会出现文件已经上传但客户端读取响应码超时导致失败，建议这样设置

>To strictly set the read time, otherwise it may appear file has been uploaded but the client reads the response code overtime result in failure, suggest such Settings

	conn.setReadTimeout((int) (file.length() / 128 + 2048));

>读取时长是指客户端等待的最大时长，服务器处理完后会立马响应，具体处理时长和网络环境有关

>Read time refers to the biggest client waiting time, after the server processing immediately response, processing time and the network environment

>这种算法不一定符合程序的要求，请结合实际的需求

>This algorithm may not accord with the requirement of application, combining with the actual demand, please

>您需要将文件作为POST请求的输出流传递给服务器，同时需要在请求头指定该文件的信息

>You need to file as the output of the POST request passed to the server, also need the file specified in the request header information

	//必须使用此属性请求服务器为此文件在服务器上的路径名称，请使用 URLDecoder.decode 编码后再请求
	//Must use the property request to the server for this file on the server path name, please use the URLDecoder. Decode code before you request

	Content-Disposition

---

	//服务器返回内容长度，使用断点续传则返回您请求的文件大小减去忽略的字节数
	//The server returns the content length, using breakpoint continuingly returns you requested file size minus ignore the number of bytes

	Content-Length

---

	//您可以设置此属性让服务比对上传后的文件MD5值，若MD5没有指定，则返回响应码200，若MD5与服务器上的文件不符合，则返回响应码412
	//you can set this property to service than after the upload file MD5 value, if the MD5 is not specified, it returns the response code 200, if the MD5 and the file on the server does not conform to, it returns the response code 412

	Content-MD5

## 服务器返回一些响应头信息

## The server returns some response header information

>注意：只有在使用带参数的请求访问文件和上传文件才会返回这些响应头信息

>Note: only the using request access to the file with parameters and upload files will return these response headers

---

	//文件在服务器上的名称，希望您的应用给这个文件命名的名称，一般为请求时的文件名
	//The name of the file on the server, in the hope that your application for the file name, the name of the general for the request of the file name
	//请使用URLDecoder.decode进行解码再使用
	//Please use URLDecoder. Decode decoding to use again

	Content-Disposition

---

	//服务器返回内容的类型，若这个文件是HTML、TXT、JSON文件
	//The server returns the type of content, If this file is, TXT, HTML JSON file
	//服务器返回这个文件的文本编码，方便客户端解析文本文件
	//The server returns the file text encoding, convenient for the client to parse text file

	Content-Type

---

	//服务器返回内容长度，使用断点续传则返回您请求的文件大小减去忽略的字节数
	//The server returns the content length, using breakpoint continuingly returns you requested file size minus ignore the number of bytes

	Content-Length

---

	//服务器返回文件MD5值，使用断点续传则不会返回此信息
	//MD5 value returned from the server files, using breakpoint continuingly will not return to this information

	Content-MD5

---

	//服务器返回文件最后修改时间，返回 long 类型的参数
	//The server returns the file was last modified time, return to the parameters type of the long

	Last-Modified

## 操作服务器上的SQLite数据库

>仅支持操作SQLite数据库，只能使用POST请求，不支持GET请求

>Only supports SQLite database operation, can only use a POST request, does not support a GET request

>客户端通过JSON文本来请求操作数据库，需要将JSON文本封装到输出流中，然后传递给服务器

>Client requests through JSON text database operation, need to encapsulate a JSON text into the output stream, and then passed to the server

>服务器会对JSON进行解析，将结果转换成JSON文本返回客户端

>Server will be to parse JSON, transform the results into a JSON text back to the client

>注意：如果您请求的JSON数据包含中文，请在服务器设置支持中文的字符编码，否则会乱码

>Note: if you request the JSON data contains Chinese, please in the server Settings to support Chinese character encoding, otherwise you will stil messy code

>目前服务器仅支持以下几种操作数据库的API

>The server only supports the following operations database API

	public void execSQL(String sql, Object[] bindArgs)

---

	public Cursor rawQuery(String sql, String[] selectionArgs)

---

	public long insert(String table, String nullColumnHack, ContentValues values)

---

	public int delete(String table, String whereClause, String[] whereArgs)

---

	public int update(String table, ContentValues values, String whereClause, String[] whereArgs)

---

	public Cursor query(String table, String[] columns, String selection,
            			String[] selectionArgs, String groupBy, String having,
            			String orderBy, String limit)

>目前服务器只支持解析以上参数的 SQLite 语句，若返回是Cursor会封装成JSON文本输出到客户端

>The server only supports the above analytical parameters of SQLite statement, if the return is the Cursor will be encapsulated into JSON text output to the client

>您必须熟悉JSON的生成和解析，才能确保服务器能正确识别您的命令

>You must be familiar with to parse and generate JSON, can ensure that the server can correctly identify your order

> API 中的 String 参数类型以 JSONObject 表示，数组参数类型和 ContentValues 参数类型以JSONArray表示

>In the API string parameters type expressed as a JSONObject, array parameter type and ContentValues parameter expressed as a JSONArray

>如果您不需要为这个参数类型指定任何的JSONObject，可以直接忽略

>If you do not need to specify any for this parameter type JSONObject, can be ignored directly

---

>您必须在请求的 JSON 中必须指定以下内容

>You must be in request JSON must specify the following content

* path：键为 JSONObject 类型，值为 String 类型，数据库文件所在路径名称，请填写数据库在服务目录中所在位置，不能进行编码，类型为JSONObject

* path: the key for the JSONObject types, the value of type String, the database file path name, location, please fill out the database in the service directory cannot be encoded, type JSONObject

* type：键为 JSONObject 类型，值为 String 类型，操作类型（execSQL、rawQuery、insert、delete、update、query），类型为JSONObject

* type: Key for JSONObject types, the value of type String, operation type (execSQL, rawQuery, insert, delete, update, query), type JSONObject

* 请求参数名称和对应值，若参数类型为String，键为 JSONObject 类型，否则则为 JSONArray 类型

* The request parameter name and its corresponding value, if the parameters of type String, key for JSONObject type, otherwise it is JSONArray type

---

	{
		"path": "visit.db",
	    "table": "visit",
	    "type": "query",
	    "columns": [
	        "date",
	        "localName"
	    ]
	}

---

>服务器一定会返回以下 JSON 内容

>The server will return the following JSON content

* result：键为 JSONObject 类型，值为 long 类型，是数据库操作语句返回值或者Cursor对象的总数

* result: Key for JSONObject types, the value of long type, the return value is the database operation statements or the total number of Cursor object

>另外查询语句返回了cursor对象，服务器会返回以下这些数据

>Another query returns a cursor object, the server will return the following these data

* columnNames：键为 JSONArray 类型，值为 ArrayList 类型，为服务器查询返回的Cursor中所有字段的名称

* columnNames: Key for JSONArray types, the value of an ArrayList, for the server query returns the names of all fields in the Cursor

* cursor：键为 JSONArray 类型，包含着一个或多个 JSONObject ，每一个 JSONObject 中的键和值对应着每一个 Cusror 键和值

* cursor: Key for JSONArray type, contains one or more JSONObject, each JSONObject corresponds to the key and the value each Cusror keys and values

---

	{
		"result": 16,
	    "columnNames": [
	        "date",
	        "localName"
	    ],
	    "cursor": [
	        {
	            "date": "2017-02-20 11:16:25",
	            "localName": "10.0.3.15"
	        },
	        {
	            "date": "2017-02-20 11:18:51",
	            "localName": "10.0.3.15"
	        },
	        {
	            "date": "2017-02-20 11:18:53",
	            "localName": "10.0.3.15"
	        }
	    ]
	}

---

>使用原生的API解析

>Using the native API

	JSONObject jsonObject = new JSONObject(json);
	System.out.println("Test：" + jsonObject.getLong("result") + jsonObject.getJSONArray("columnNames") + jsonObject.getJSONArray("cursor"));

>使用第三方框架Gson解析

>Using a third party framework Gson parsing

	Gson gson = new Gson();
	SQLiteCursorBean bean = gson.fromJson(json, SQLiteCursorBean.class);

## 操作服务器SQLite数据库给出了工具类

## For operating the server SQLite database tools is presented

>SQLiteCursorBean.java：服务器返回的 JSON 文本，可以使用这个类供第三方框架 Gson 进行解析

>SQLiteCursorBean.java: The server returns JSON text, Can use this class to a third party framework Gson parsing

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

>RequestSQLiteUtils.java：客户端请求服务器的工具类，可以使用这个类快速生成 JSON 文本

>RequestSQLiteUtils.java: Client requests the server tools, you can use this class quickly generate JSON text

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

>JsonUtils.java：这是一个转换的工具类，包含了客户端和服务器的工具类的源码，配合上一个类使用

>JsonUtils.java:This is a conversion tool class, including the source of the client and server tools class, coupled with a class to use

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


## 请求重定向

## Request to redirect

>请求重定向是浏览器在访问服务器时，服务器将这个请求转到了另外一个URL地址上，所携带的路径、参数、流、请求头都会交给新的URL地址所在的服务器进行处理

>Request is redirect the browser when access to the server, the server will turn this request to another URL, carries path, parameters, flow, request header will be handed over to the new URL address of the server for processing

>如果当前的服务器上的地址为

>If the current server address is

	http://192.168.1.126:8888/

>如果设置的请求重定向URL为

>If set the request redirection URL

	http://192.168.1.119:8888/

>访问的当前服务器URL会跳转到重定向URL

>Access to the current server URL will jump to redirect URL

	http://192.168.1.126:8888/    --->    http://192.168.1.119:8888/

## 字符编码

## Character encoding

>提供了修改字符编码的设置，您可以点击通知栏上的进入设置

>Provides a modified character encoding Settings, you can click on the notification bar into the set

>如果您选择了某一种编码，很大程度上决定服务器解析和返回数据的所用编码

>If you select the one encoding, largely determine server parsing and encoding used to return data

>如果您的文本里面包含中文，强烈推荐使用UTF-8为默认字符编码

>If your text contains Chinese, strongly recommended to use utf-8 as the default character encoding

>字符编码决定了服务器解析URL和参数的编码，和您解析文本文档所用的编码格式

>Character encoding decided the server to parse the URL and parameters of coding, coding format used in the analytical text documents with you

>字符编码至关重要，请不要设置与其无关的内容，不然会出现乱码

>Character encoding is critical, please don't set has nothing to do with its content, or it will appear garbled

## 响应码

## Response code

>200：响应成功

>200：Response  success

>400：请求有误

>400：Request is wrong

>404：请求的资源不存在

>404：Requested resource does not exist

>412：未满足请求条件

>412：Did not meet the request

>500：服务器内部错误

>500：Server internal error

>503：服务不可用

>503：Service is not available

## 参考代码

## Reference code

>GET请求

>GET request

	URL url = new URL(path);
					
	HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	conn.setRequestMethod("GET");
	conn.setConnectTimeout(5000);
	conn.setReadTimeout(5000);
					
	conn.connect();
					
	int responseCode = conn.getResponseCode();
	if (responseCode == 200) {
		//System.out.println(conn.getHeaderFields());
						
		InputStream is = conn.getInputStream();
		String text = getTextFromStream(is);
		System.out.println(text);
	}

>POST请求

>POST request

	URL url = new URL(path);
	HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	conn.setRequestMethod("POST");
	conn.setConnectTimeout(5000);
	conn.setReadTimeout(5000);
	
	conn.setDoOutput(true);
	OutputStream os = conn.getOutputStream();
	os.write(data.getBytes());
	
	conn.connect();

	int responseCode = conn.getResponseCode();

	if (responseCode == 200) {
		//System.out.println(conn.getHeaderFields());
		
		InputStream is = conn.getInputStream();
		String text = getTextFromStream(is);
		System.out.println(text);
	}

>上传文件

>Upload file

	URL url = new URL(path);
	HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	conn.setRequestMethod("POST");
	conn.setConnectTimeout(5000);
	conn.setReadTimeout((int) (file.length() / 128 + 2048));
	
	conn.setRequestProperty("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, charsetName));
	conn.setRequestProperty("Content-Length", file.length() + "");
	conn.setRequestProperty("Content-MD5", MD5Utils.getFileMD5(file.toString()));
	
	conn.setDoOutput(true);
	
	OutputStream out = conn.getOutputStream();
	
	FileInputStream in = new FileInputStream(file);
	int count;
	byte[] buffer = new byte[1024];
	while ((count = in.read(buffer)) != -1) {
		out.write(buffer, 0, count);
		out.flush();
	}
	in.close();
	out.close();
	
	conn.connect();
	
	int responseCode = conn.getResponseCode();

	if (responseCode == 200) {
		//System.out.println(conn.getHeaderFields());
		//System.out.println(URLDecoder.decode(conn.getHeaderFields().get("Content-Disposition").get(0), charsetName));

		System.out.println(conn.getHeaderFields().get("Content-MD5").get(0));
	}

>操作SQLite数据库

>SQLite database operation

	URL url = new URL(path);
	HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	conn.setRequestMethod("POST");
	conn.setConnectTimeout(5000);
	conn.setReadTimeout(5000);
	
	conn.setDoOutput(true);
	OutputStream os = conn.getOutputStream();
	os.write(text.getBytes());
	
	conn.connect();
	
	int responseCode = conn.getResponseCode();

	if (responseCode == 200) {
		InputStream in = conn.getInputStream();
		String json = getTextFromStream(in);

		System.out.println(json);
	}

>将字节读取流转换成文本的工具类，客户端需要指定字符编码

>To circulate the bytes read into the text tool, the client need to specify the character encoding

	public String getTextFromStream(InputStream in) throws IOException 
	{
		byte[] b = new byte[1024];
		int len = 0;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		while((len = in.read(b)) != -1){
			bos.write(b, 0, len);
		}
		String text = new String(bos.toByteArray(), charsetName);
		return text;
	}

>若您有任何疑问或反馈，可以发邮件到880634@qq.com

>If you have any queries or feedback, you can send email to jinqun0730@gmail.com
