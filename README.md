#欢迎使用MyTomcat

>此文档不再维护，[点击查看最新文档](https://github.com/getActivity/MyTomcat)

>基于HTTP协议制作的服务器，支持GET和POST请求

>能跨设备进行访问，支持访问和上传文件，断点续传，操作SQLite

>对中文地址和中文参数有比较好的支持

##访问服务器

>服务器与客户端处于同一个设备上

	http://127.0.0.1: + port
	http://localhost: + port
	//例如
	http://127.0.0.1:8888
	http://localhost:8888

>跨设备访问，在同一局域网环境下，以当前您的设备的IP地址为准，推荐使用静态IP

	http:// + host address + ":" + port
	//例如
	http://192.168.1.123：8888

>注意：模拟器创建的服务端将无法被其他设备访问

>如果您想改变访问的主页，请进入设置进行更改

##访问服务器上的文件

>若您设置了以/mnt/sdcard/（SD卡根目录）为服务目录

>若您想访问服务器上的/mnt/sdcard/MyTomcat.apk文件

>可以使用以下地址进行访问该资源，访问成功将返回响应码200，若这个文件不存在则返回响应码404

	http://127.0.0.1:8888/MyTomcat.apk

>这种方式比较快捷，但并不推荐此方法，推荐如下两种方式

##带参数的请求方式访问文件

####GET请求参数访问服务器上的文件

>在参数“path”加上需要访问的文件地址，地址根据您设置的服务器目录而定

	http://127.0.0.1:8888/file?path=

>若您设置了以/mnt/sdcard/（SD卡根目录）为服务目录

>若您想访问/mnt/sdcard/MyTomcat.apk的文件

>请您使用以下地址进行访问该资源，访问成功将返回响应码200，若这个文件不存在则返回响应码404

	http://127.0.0.1:8888/file?path=MyTomcat.apk

>以您的设置的服务目录为基准，URL的参数为服务目录下的文件路径字符串，但不包含服务目录路径字符串

>若您想访问包含中文路径的文件，可以通过对参数值（MyTomcat.apk）进行编码，注意请不要对参数名（?path=）进行编码

	URLDecoder.decode(String s, String charsetName);

>s：是需要被编码的字符串

>charsetName：是被编码的字符编码，推荐您使用UTF-8，服务器上的字符编码必须和此编码一致，否则会乱码

	"http://127.0.0.1:8888/file?path=" + URLDecoder.decode("MyTomcat.apk", "UTF-8");

>GET请求也支持中文，步骤比较繁琐，推荐您使用POST请求进行访问

####POST请求参数访问服务上的文件

>GET和Post请求的区别在于，GET请求的参数是通过URL传递给服务器的，而POST将参数作为输出流传递到服务器

>如果您的URL里面包含中文参数，推荐使用此方法，您必须确保该服务器上的字符编码支持中文才不会乱码

>使用基本与GET无太大差别，若想访问上一个例子（GET请求）上的文件

	//URL地址
	http://127.0.0.1:8888/file

---

	//参数部分
	path=MyTomcat.apk

---

	conn.setDoOutput(true);
	OutputStream os = conn.getOutputStream();
	os.write("path=MyTomcat.apk".getBytes());

###关于文件的断点续传

>这个功能需要通过参数进行告知服务器

>您若想获取文件的部分内容，这里以GET请求为例子，可以直接在URL加入参数

>访问成功将返回响应码200，若这个文件不存在则返回响应码404

	http://127.0.0.1:8888/file?path=MyTomcat/MyTomcat.apk&range=1000

>请注意，range参数是指文件内容的起始位置，用于获取文件从这个位置开始后面的内容

##使用POST请求上传文件

>上传文件只能使用POST请求的方式进行，不支持GET请求，不支持断点续传

>建议您不要上传超过30MB的文件，否则您的程序可能会内存溢出，实际请根据您的设备实际为准

>上传成功后则返回响应码200，上传失败则返回响应码412

>您需要严格设置读取时长，否则可能会出现文件已经上传但客户端读取响应码超时导致失败，推荐您设置为

	conn.setReadTimeout((int) file.length() / 1024 + 2048);

>这种算法不一定符合程序的要求，可以根据程序的需求而定

>您需要将文件作为POST请求的输出流传递给服务器，同时需要在请求头指定该文件的信息

---

	//必须使用此属性请求服务器为此文件在服务器上的路径名称
	//请使用URLDecoder.decode(String s, String charsetName)进行编码再请求
	Content-Disposition

---

	//服务器返回内容长度，使用断点续传则返回您请求的文件大小减去忽略的字节数
	Content-Length

---

	//您可以设置此属性让服务比对上传后的文件MD5值，若MD5没有指定，则返回响应码200，若MD5与服务器上的文件不符合，则返回响应码412
	Content-MD5

####关于服务器返回一些信息

>请注意，只有在使用带参数的请求访问文件和上传文件才会返回

---

	//服务器返回内容名称，希望您的应用给这个文件命名的名称，一般为您请求时的文件名
	//请使用URLDecoder.decode(String s, String charsetName)进行解码再使用
	Content-Disposition

---

	//服务器返回内容的类型，若这个文件为html、txt、json文件
	//服务器会给您返回该文件的文本编码，以便您解析文档，请不要使用此参数请求服务器
	Content-Type

---

	//服务器返回内容长度，使用断点续传则返回您请求的文件大小减去忽略的字节数
	Content-Length

---

	//服务器返回文件MD5值，使用断点续传则不会返回此信息
	Content-MD5

---

	//服务器返回文件最后修改时间，返回long类型的参数
	Last-Modified

##使用POST请求操作服务器上的数据库

>仅支持SQLite数据库，操作数据库只能使用POST请求的方式进行，不支持GET请求

>客户端通过JSON文本请求操作数据库，将JSON数据封装到输出流中传递给服务器

>服务器会对JSON进行解析，将结果转换成JSON文本返回客户端

>注意，如果您请求的JSON数据包含中文，请在服务器设置支持中文的字符编码，否则会乱码

>目前服务器仅支持以下几种操作数据库的API

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

>目前服务器只支持解析以上参数的SQLite语句，若返回Cursor的也会封装成同一个JSON返回

>您必须熟悉JSON的生成和解析，才能确保服务器能正确识别您的命令

>String参数以JSONObject表示；数组和ContentValues参数以JSONArray表示

>如果您不需要为这个参数指定任何的JSONObject，可以直接忽略

---

>您必须在请求的JSONObject中必须指定以下内容

* path：数据库文件所在路径名称，请填写数据库在服务目录中所在位置，不能进行编码，类型为JSONObject

* type：操作类型（execSQL、rawQuery、insert、delete、update、query），类型为JSONObject

* 请求参数名称和对应值，若参数类型为String，类型为JSONObject，否则则为JSONArray

---

> 服务器一定会返回以下JSON内容

* result：类型为JSONObject，是数据库操作语句返回值，值为int

> 另外查询语句返回了cursor对象，服务器会返回以下这些数据

* count：类型为JSONObject，为服务器查询后的cursor中的数量

* columnNames：类型为JSONArray，为服务器查询后的cursor中的所有字段名称

* cursor：类型为JSONObject，这个对象中包含着一个或多个JSONArray，每一个JSONArray中的JSONObject键所对应的值就是您需要查询的字段的名称和字段的值

##请求重定向

>请求重定向是浏览器在访问服务器时，服务器将这个请求转到了另外一个URL地址上，所携带的路径、参数、流、请求头都会交给新的URL地址所在的服务器进行处理

>如果当前的服务器上的地址为

	http://192.168.1.126:8888/

>如果设置的请求重定向URL为

	http://192.168.1.119:8888/

>访问的当前服务器会进行跳转

	http://192.168.1.126:8888/    --->    http://192.168.1.119:8888/

##关于字符编码

>在软件设置提供了修改字符编码的设置，您可以点击通知栏上的通知进入

>如果您选择了某一种编码，很大程度上决定服务器解析和返回数据的所用编码

>如果您的文档里面包含中文，强烈推荐使用UTF-8为默认字符编码

>字符编码决定了服务器解析URL参数的编码和服务器返回给您的信息中的内容类型，和您解析文本文档的编码格式所解析的编码格式

>字符编码至关重要，请不要设置与其无关的内容，不然会出现乱码

##关于响应码

>200：响应成功

>400：请求有误

>404：请求的资源不存在

>412：未满足请求条件

>500：服务器内部错误

>503：服务不可用

##参考代码

>GET请求

	URL url = new URL(path);
					
	HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	conn.setRequestMethod("GET");
	conn.setConnectTimeout(timeoutMillis);
	conn.setReadTimeout(timeoutMillis);
					
	conn.connect();
					
	int responseCode = conn.getResponseCode();
	if (responseCode == 200) {
		//System.out.println(conn.getHeaderFields());
						
		InputStream is = conn.getInputStream();
		String text = getTextFromStream(is);
		System.out.println(text);
	}

>POST请求

	URL url = new URL(path);
	HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	conn.setRequestMethod("POST");
	conn.setConnectTimeout(timeoutMillis);
	conn.setReadTimeout(timeoutMillis);
	
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


	URL url = new URL(path);
	HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	conn.setRequestMethod("POST");
	conn.setConnectTimeout(timeoutMillis);
	conn.setReadTimeout((int) (file.length() / 1024) + 2048);
	
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

	URL url = new URL(path);
	HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	conn.setRequestMethod("POST");
	conn.setConnectTimeout(timeoutMillis);
	conn.setReadTimeout(timeoutMillis);
	
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

>将字节读取流转换成文本的工具类，客户端需要在此指定字符编码进行解析

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
