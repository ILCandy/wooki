package com.wooki.system.common;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 简单httpclient实例
 *
 * @author arron
 * @date 2015年11月11日 下午6:36:49
 * @version 1.0
 */
public class SimpleHttpClientDemo {

	/**
	 * 模拟请求
	 *
	 * @param url
	 *            资源地址
	 * @param map
	 *            参数列表
	 * @param encoding
	 *            编码
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static String send(String url, Map<String, String> map, String encoding) throws ParseException, IOException {
		String body = "";

		// 创建httpclient对象
		CloseableHttpClient client = HttpClients.createDefault();
		// 创建post方式请求对象
		HttpPost httpPost = new HttpPost(url);

		// 装填参数
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		if (map != null) {
			for (Entry<String, String> entry : map.entrySet()) {
				nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}
		// 设置参数到请求对象中
		httpPost.setEntity(new UrlEncodedFormEntity(nvps, encoding));

		System.out.println("请求地址：" + url);
		System.out.println("请求参数：" + nvps.toString());

		// 设置header信息
		// 指定报文头【Content-type】、【User-Agent】
		httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
		httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

		// 执行请求操作，并拿到结果（同步阻塞）
		CloseableHttpResponse response = client.execute(httpPost);
		// 获取结果实体
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			// 按指定编码转换结果实体为String类型
			body = EntityUtils.toString(entity, encoding);
		}
		EntityUtils.consume(entity);
		// 释放链接
		response.close();
		return body;
	}



	public static String sendGet(String url, Map<String, String> map, String encoding) throws ParseException, IOException {
		String body = "";

		// 创建httpclient对象
		CloseableHttpClient client = HttpClients.createDefault();
		// 创建get方式请求对象
		HttpGet httpGet = new HttpGet(url);

		// 装填参数
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		if (map != null) {
			for (Entry<String, String> entry : map.entrySet()) {
				nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}
		// 设置参数到请求对象中
//		httpGet.setEntity(new UrlEncodedFormEntity(nvps, encoding));

		System.out.println("请求地址：" + url);
		System.out.println("请求参数：" + nvps.toString());

		// 设置header信息
		// 指定报文头【Content-type】、【User-Agent】
		httpGet.setHeader("Content-type", "application/x-www-form-urlencoded");
		httpGet.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

		// 执行请求操作，并拿到结果（同步阻塞）
		CloseableHttpResponse response = client.execute(httpGet);
		// 获取结果实体
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			// 按指定编码转换结果实体为String类型
			body = EntityUtils.toString(entity, encoding);
		}
		EntityUtils.consume(entity);
		// 释放链接
		response.close();
		return body;
	}

	private static String tt(InputStream in)throws IOException{
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] data = new byte[1024];
		int count = -1;
		while((count = in.read(data,0,1024)) != -1)
			outStream.write(data, 0, count);

		data = null;
		return new String(outStream.toByteArray(),"utf-8");
	}

	public static String lockPost(String url,String paramStr,String encode) throws IOException{

		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		InputStream is = null;
		StringEntity entityJson = new StringEntity(paramStr,encode);//解决中文乱码问题

		try {
			// 创建post请求
			HttpPost httpPost = new HttpPost(url);
			httpPost.setHeader("Content-type", "application/json");
			httpPost.setEntity(entityJson);

			//执行请求，
			response = httpClient.execute(httpPost);
			//得到响应体
			HttpEntity entity = response.getEntity();

			String body = EntityUtils.toString(entity, encode);
			System.out.println("body = "+body);
			return body;

		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			//关闭输入流，释放资源
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//消耗实体内容
			if(response != null){
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//关闭相应 丢弃http连接
			if(httpClient != null){
				try {
					httpClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return "";
	}

//	public static String sendJsonGet(String url, Map map, String encoding) throws ParseException, IOException {
//		String body = "";
//
//		// 创建httpclient对象
//		CloseableHttpClient client = HttpClients.createDefault();
//		// 创建post方式请求对象
//		HttpGet httpGet = new HttpGet(url);
//
//		// 装填参数
//		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
//		if (map != null) {
//			for (Entry entry : map.entrySet()) {
//				nvps.add(new BasicNameValuePair(entry.getKey().toString(), entry.getValue().toString()));
//			}
//		}
//		// 设置参数到请求对象中
////		httpGet.setEntity(new UrlEncodedFormEntity(nvps, encoding));
//
//		System.out.println("请求地址：" + url);
//		System.out.println("请求参数：" + nvps.toString());
//
//		// 设置header信息
//		// 指定报文头【Content-type】、【User-Agent】
//		httpGet.setHeader("Content-type", "application/x-www-form-urlencoded");
//		httpGet.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
//
//		// 执行请求操作，并拿到结果（同步阻塞）
//		CloseableHttpResponse response = client.execute(httpGet);
//		// 获取结果实体
//		HttpEntity entity = response.getEntity();
//		if (entity != null) {
//			// 按指定编码转换结果实体为String类型
//			body = EntityUtils.toString(entity, encoding);
//		}
//		EntityUtils.consume(entity);
//		// 释放链接
//		response.close();
//		return body;
//	}

	public static void main(String[] args) throws ParseException, IOException {
//		test1();
//		test2();
//		testLock();

		String str = "{\"access_token\":\"b372c792621d576179f2163ab194489edf5b8966cb2977aL\", \"data\":{}, \"operation\":\"GET\"}";
		String url = "https://www.doormaster.me:9099/doormaster/server/devices";
		String encode = "utf-8";
		lockPost(url,str,encode);
	}


	public static void testLock() throws ParseException, IOException {
		String url = "https://www.doormaster.me:9099/doormaster/server/devices";
//		Map paramMap = new HashMap(10);
//		paramMap.put("access_token", LockConfigurationProperties.getLockAccesToken());
//		paramMap.put("operation", "GET");
//		paramMap.put("data", "{}");

//		String body = sendGet(url, paramMap , "utf-8");

//		System.out.println("交易响应结果：");
//		System.out.println(body);
	}

	public static void test1() throws ParseException, IOException {
		String url = "http://smartammeter.zg118.com:8001/user/login/faqian/qqqqqq";
//		String body = send(url, null, "utf-8");
		String body = sendGet(url, null, "utf-8");
		System.out.println("交易响应结果：");
		System.out.println(body);
	}

	public static void test2() throws ParseException, IOException {
		String url = "http://smartammeter.zg118.com:8001/user/login/faqian/123456";
		Map<String, String> map = new HashMap<String, String>();
		map.put("userid", "faqian");
		map.put("pass", "123456");
//		map.put("city", "上海");
//		map.put("dfc", "1");
		map.put("charset", "utf-8");
		String body = send(url, map, "utf-8");
//		System.out.println("交易响应结果：");
		System.out.println(body);

		System.out.println("-----------------------------------");

//		map.put("city", "北京");
		body = send(url, map, "utf-8");
		System.out.println("交易响应结果：");
		System.out.println(body);
	}
}