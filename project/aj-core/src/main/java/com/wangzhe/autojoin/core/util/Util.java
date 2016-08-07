package com.wangzhe.common;

/**
 * <p>
 * Title: </p>
 *
 * <p>
 * Description: </p>
 *
 * <p>
 * Copyright: Copyright (c) 2007</p>
 *
 * <p>
 * Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;

import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import javax.swing.table.*;

import org.apache.struts2.ServletActionContext;

import com.wangzhe.action.InterfaceAction;
import com.wangzhe.model.User;
//import com.wangzhe.charset.*;
import sun.audio.*;

import java.util.zip.*;

//import com.wangzhe.active.ActiveDialog;
//import com.wangzhe.main.CM;
public class Util {

	public static Random r = new Random();

	public static final Pattern patCSS = Pattern.compile("<style[^>]*>([\\s\\S](?!<style))*?</style>",
			Pattern.CASE_INSENSITIVE);
	public static final Pattern patJS = Pattern.compile("<script[^>]*>([\\s\\S](?!<script))*?</script>",
			Pattern.CASE_INSENSITIVE);
	public static final Pattern patHTML = Pattern.compile("<([^>]*)>", Pattern.DOTALL);
	public static final Pattern patLINK = Pattern.compile("<link.*?>", Pattern.DOTALL);
	public static final Pattern patSentence = Pattern.compile("[,，。;；]+", Pattern.DOTALL);
	public static PropertyResourceBundle bundle = null;

	// static
	// {
	// Locale type = new Locale("zh_CN");
	// //英文
	// if (!CM.Z)
	// {
	// type = new Locale("en_US");
	// }
	// bundle = ResourceBundle.getBundle("base", type);
	// }
	/**
	 * 整数转换成制定长度的字符串
	 *
	 * @param i
	 *            int
	 * @param w
	 *            int
	 * @return String
	 */
	public static String intToFullString(int i, int w) {
		StringBuffer sb = new StringBuffer(w);
		String tmp = Integer.toString(i);
		int r = tmp.length();
		for (int j = 0; j < w - r; j++) {
			sb.append("0");
		}
		sb.append(tmp);
		return sb.toString();
	}

	public static void copyFile(String file, String newFile) {
		FileInputStream fis = null;
		FileOutputStream fos = null;

		try {
			File f = new File(file);

			fis = new FileInputStream(f);
			fos = new FileOutputStream(newFile);

			byte[] buf = new byte[(int) f.length()];
			int readed = 0;
			while (true) {
				int len = fis.read(buf, readed, buf.length - readed);
				if (len < 0) {
					break;
				}
				readed += len;
				if (readed >= buf.length) {
					break;
				}
			}

			fos.write(buf);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (Exception ex) {
			}
			try {
				fos.close();
			} catch (Exception ex) {
			}
		}
	}

	/**
	 * 获取时间
	 *
	 * @return String
	 */
	public static String getTime() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(new java.util.Date());
	}

	public static String getTime(String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(new java.util.Date());
	}

	public static String getTime(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(date);
	}

	public static String getTime(String format, Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date);
	}

	public static Date stringToTime(String time) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.parse(time);
	}

	public static Date stringToTime(String time, String format) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.parse(time);
	}

	/**
	 * 获取随机数
	 *
	 * @param max
	 *            int
	 * @return int
	 */
	public static int getRandomInt(int max) {
		if (max == 0) {
			return 0;
		}
		return r.nextInt(max);
	}

	public static int getRandomInt(int min, int max) {
		if (max == min) {
			return 0;
		}
		return min + r.nextInt(max - min + 1);
	}

	public static String getRandomStr() {
		return getRandomStr(6);
	}

	/**
	 * 生成随机字符串
	 *
	 * @param size
	 *            int
	 * @return String
	 */
	public static String getRandomStr(int size) {
		StringBuffer ret = new StringBuffer(size);
		int len1 = size / 2 + size % 2;
		int len2 = size / 2;

		char c = 'a';
		c += Math.abs(r.nextInt()) % 22;
		for (int i = 0; i < len1; i++) {
			ret.append((char) (c + i));
		}
		for (int i = 0; i < len2; i++) {
			c = '0';
			c += Math.abs(r.nextInt()) % 10;
			ret.append(c);
		}
		return ret.toString();
	}

	/**
	 * 按指定长度切断字符串
	 *
	 * @param s
	 *            String
	 * @param byteLen
	 *            int
	 * @return String
	 */
	public static String cutString(String s, int byteLen) {
		if (byteLen <= 0) {
			return "";
		}

		if (s.getBytes().length <= byteLen) {
			return s;
		}

		StringBuffer sb = new StringBuffer(byteLen);

		int midLen = byteLen / 2;
		sb.append(s.substring(0, midLen));

		int len = sb.toString().getBytes().length;
		for (int i = midLen; i < s.length(); i++) {
			String w = s.substring(i, i + 1);
			len += w.getBytes().length;
			if (len > byteLen) {
				break;
			}

			sb.append(w);
		}

		return sb.toString();
	}

	/**
	 * 刷新JTable
	 *
	 * @param jTable1
	 *            JTable
	 */
	public synchronized static void updateJTable(final JTable jTable1) {
		if (jTable1 == null) {
			return;
		}

		DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
		model.fireTableDataChanged();
	}

	/**
	 * 从矢量对象里面随机构造字符串
	 *
	 * @param v
	 *            Vector
	 * @param count
	 *            int
	 * @param token
	 *            String
	 * @return String
	 */
	public static String makeRandom(Vector v, int count, String token) {
		String str = "";
		Vector c = (Vector) v.clone();
		int vSize = c.size();
		if (count > vSize) {
			count = vSize;
		}
		for (int i = 0; count == 0 || i < count; i++) {
			if (c.size() < 1) {
				break;
			}
			int index = Math.abs(r.nextInt()) % c.size();
			str = str + c.elementAt(index);
			c.removeElementAt(index);
			if (count == 0 || i < count - 1) {
				str += token;
			}
		}
		return str;
	}

	/**
	 * 从矢量对象里面随机获取数组
	 *
	 * @param v
	 *            Vector
	 * @param count
	 *            int
	 * @return Object[]
	 */
	public static Object[] makeRandom(Vector v, int count) {
		Vector c = (Vector) v.clone();
		int vSize = c.size();
		if (count > vSize) {
			count = vSize;
		}
		Object[] ret = new Object[count];
		for (int i = 0; i < count; i++) {
			if (c.size() < 1) {
				break;
			}
			int index = Math.abs(r.nextInt()) % c.size();
			ret[i] = c.elementAt(index);
			c.removeElementAt(index);
		}
		return ret;
	}

	/**
	 * 建立HttpURLConnection链接
	 *
	 * @param url
	 *            String
	 * @param refer
	 *            String
	 * @param cookiesProp
	 *            Properties
	 * @param timeout
	 *            int
	 * @return HttpURLConnection
	 * @throws Exception
	 */
	public static HttpURLConnection doConnect(String url, String refer, Properties cookiesProp, int timeout)
			throws Exception {
		HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
		con.setReadTimeout(timeout * 1000);
		con.setConnectTimeout(timeout * 1000);
		if (refer != null) {
			con.setRequestProperty("Referer", refer);
		}
		con.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1;)");
		con.setRequestProperty("Accept-Encoding", "gzip");
		// con.setRequestProperty("Accept-Language","zh-cn");
		if (cookiesProp != null && cookiesProp.size() > 0) {
			con.setRequestProperty("Cookie", Util.getCookieFromProperties(cookiesProp));
		}
		return con;
	}

	/**
	 * 从网址读取内容
	 *
	 * @param url
	 *            String
	 * @return String
	 * @throws Exception
	 */
	public static String readStringFromUrl(String url) throws Exception {
		return readStringFromUrl(url, null, null, 30);
	}

	public static int readUrlConResponesCode(String url) throws Exception {
		return readUrlConResponesCode(url, null, null, 30);
	}

	/**
	 * 从网址读取内容
	 *
	 * @param url
	 *            String
	 * @param refer
	 *            String
	 * @return String
	 * @throws Exception
	 */
	public static String readStringFromUrl(String url, String refer) throws Exception {
		return readStringFromUrl(url, refer, null, 30);
	}

	/**
	 * 从网址读取内容
	 *
	 * @param url
	 *            String
	 * @param refer
	 *            String
	 * @param cookiesProp
	 *            Properties
	 * @param autoredirect
	 *            boolean
	 * @return String
	 * @throws Exception
	 */
	public static String readStringFromUrl(String url, String refer, Properties cookiesProp, int timeout,
			boolean autoredirect) throws Exception {
		int times = 0;
		HttpURLConnection con = null;
		do {
			con = doConnect(url, refer, cookiesProp, timeout);
			con.setInstanceFollowRedirects(false);
			if (cookiesProp != null) {
				Map map = con.getHeaderFields();
				Collection c = (Collection) (map.get("Set-Cookie"));

				if (c != null) {
					String[] setCookies = new String[c.size()];
					c.toArray(setCookies);
					for (int i = 0; setCookies != null && i < setCookies.length; i++) {
						if (!setCookies[i].startsWith("LSID=EXPIRED")) {
							Util.addCookieToProperties(setCookies[i], cookiesProp);
						}
					}
				}
			}
			refer = url;
			url = con.getHeaderField("LOCATION");
		} while (url != null && times++ < 20);

		byte[] buf = null;
		String pageContent = null;
		String encode = null;
		try {
			if (con == null) {
				return null;
			}
			buf = readBytesFromHttpConnection(con, false);
			if (buf == null) {
				return null;
			}

			encode = Util.getPatternValue(con.getHeaderField("Content-Type"), patEncode, 1);
		} catch (Exception ex1) {
		} finally {
			try {
				con.disconnect();
			} catch (Exception ex2) {
			}
		}

		if (encode != null) {
			return new String(buf, encode);
		}

		encode = Util.detectCharset(buf, buf.length);
		if (encode == null) {
			encode = "GBK";
		}

		pageContent = new String(buf, encode);

		String tmpEncode = Util.getPatternValue(pageContent, patEncode, 1);
		if (tmpEncode == null) {
			return pageContent;
		}

		tmpEncode = tmpEncode.trim();
		if (tmpEncode.equalsIgnoreCase(encode)) {
			return pageContent;
		}

		if (tmpEncode.indexOf("2312") > -1) {
			tmpEncode = "GB2312";
		}

		try {
			pageContent = new String(buf, tmpEncode);
		} catch (UnsupportedEncodingException ex) {
			pageContent = new String(buf, "ISO8859-1");
		}

		return pageContent;
	}

	public static String patEncode = "charset=[' ]?([\\w-]+)";

	/**
	 * 从网址读取内容
	 *
	 * @param url
	 *            String
	 * @param refer
	 *            String
	 * @param cookiesProp
	 *            Properties
	 * @return String
	 * @throws Exception
	 */
	public static String readStringFromUrl(String url, String refer, Properties cookiesProp, int timeout)
			throws Exception {
		HttpURLConnection con = doConnect(url, refer, cookiesProp, timeout);

		if (cookiesProp != null) {
			Map map = con.getHeaderFields();
			Collection c = (Collection) (map.get("Set-Cookie"));

			if (c != null) {
				String[] setCookies = new String[c.size()];
				c.toArray(setCookies);

				for (int i = 0; setCookies != null && i < setCookies.length; i++) {
					if (!setCookies[i].startsWith("LSID=EXPIRED")) {
						Util.addCookieToProperties(setCookies[i], cookiesProp);
					}
				}
			}
		}

		byte[] buf = null;
		String pageContent = null;
		String encode = null;
		try {
			buf = readBytesFromHttpConnection(con, false);
			if (buf == null) {
				return null;
			}

			encode = Util.getPatternValue(con.getHeaderField("Content-Type"), patEncode, 1);
		} catch (Exception ex1) {
		} finally {
			try {
				con.disconnect();
			} catch (Exception ex2) {
			}
		}

		if (encode != null) {
			return new String(buf, encode);
		}

		encode = Util.detectCharset(buf, buf.length);
		if (encode == null) {
			encode = "GBK";
		}

		pageContent = new String(buf, encode);

		String tmpEncode = Util.getPatternValue(pageContent, patEncode, 1);
		if (tmpEncode == null) {
			return pageContent;
		}

		tmpEncode = tmpEncode.trim();
		if (tmpEncode.equalsIgnoreCase(encode)) {
			return pageContent;
		}

		if (tmpEncode.indexOf("2312") > -1) {
			tmpEncode = "GB2312";
		}

		try {
			pageContent = new String(buf, tmpEncode);
		} catch (UnsupportedEncodingException ex) {
			pageContent = new String(buf, "ISO8859-1");
		}

		return pageContent;
	}

	/**
	 * 从网址读取 返回代码
	 *
	 * @param url
	 *            String
	 * @param refer
	 *            String
	 * @param cookiesProp
	 *            Properties
	 * @return String
	 * @throws Exception
	 */
	public static int readUrlConResponesCode(String url, String refer, Properties cookiesProp, int timeout)
			throws Exception {
		HttpURLConnection con = doConnect(url, refer, cookiesProp, timeout);

		if (cookiesProp != null) {
			Map map = con.getHeaderFields();
			Collection c = (Collection) (map.get("Set-Cookie"));

			if (c != null) {
				String[] setCookies = new String[c.size()];
				c.toArray(setCookies);

				for (int i = 0; setCookies != null && i < setCookies.length; i++) {
					if (!setCookies[i].startsWith("LSID=EXPIRED")) {
						Util.addCookieToProperties(setCookies[i], cookiesProp);
					}
				}
			}
		}

		byte[] buf = null;
		String pageContent = null;
		String encode = null;
		try {
			buf = readBytesFromHttpConnection(con, false);
			encode = Util.getPatternValue(con.getHeaderField("Content-Type"), patEncode, 1);
		} catch (Exception ex1) {
		} finally {
			try {
				con.disconnect();
			} catch (Exception ex2) {
			}
		}

		return con.getResponseCode();
	}

	/**
	 * 从网址读取字节数组
	 *
	 * @param url
	 *            String
	 * @return byte[]
	 * @throws Exception
	 */
	public static byte[] readDataFromUrl(String url) throws Exception {
		return readBytesFromHttpConnection(doConnect(url, null, null, 30));

	}

	/**
	 * 从网址读取字符串
	 *
	 * @param con
	 *            HttpURLConnection
	 * @param encode
	 *            String
	 * @return String
	 * @throws Exception
	 */
	public static String readStringFromHttpConnection(HttpURLConnection con, String encode) throws Exception {
		InputStream is = null;

		try {
			is = con.getInputStream();
			int contentLength = 50 * 1024;
			try {
				contentLength = Integer.parseInt(con.getHeaderField("Content-Length"));
			} catch (Exception ex) {
			}

			// 长度超过1M的就过滤掉，避免照成内存溢出
			if (contentLength > 1024 * 1024) {
				Exception ex = new Exception("网址内容超过1M，避免内存溢出，跳到对该网址的解析...");
				ex.printStackTrace();
				// MainPanel.append("网址内容超过1M，避免内存溢出，跳到对该网址的解析...");
				return null;
			}

			byte[] data = null;
			if (con.getHeaderField("Content-Encoding") != null) {
				if (con.getHeaderField("Content-Encoding").equalsIgnoreCase("gzip")) {
					data = readFromGZipInputStream(new GZIPInputStream(is));
				}
			}

			if (data == null) {
				data = readAllDataFromInputStream(is, contentLength);
			}

			if (data == null) {
				return null;
			}

			return new String(data, encode);
		} catch (Exception ex) {
			System.err.println("下载" + con.getURL() + "失败，错误码：" + con.getResponseCode() + "，错误消息：" + ex.getMessage()
					+ "," + con.getResponseMessage());
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (Exception ex) {
				}
			}

			try {
				con.disconnect();
			} catch (Exception ex) {
			}
		}

		return null;

	}

	/**
	 * 从输入流读取所有内容
	 *
	 * @param is
	 *            InputStream
	 * @return byte[]
	 * @throws Exception
	 */
	public static byte[] readAllDataFromInputStream(InputStream is) throws Exception {
		return readAllDataFromInputStream(is, 0);
	}

	/**
	 * 从输入流读取内容
	 *
	 * @param is
	 *            InputStream
	 * @param size
	 *            int
	 * @return byte[]
	 * @throws Exception
	 */
	public static byte[] readAllDataFromInputStream(InputStream is, int size) throws Exception {
		if (is == null) {
			return null;
		}

		if (size < 1) {
			size = 50 * 1024;
		}

		byte buf[] = new byte[size];
		int readed = 0;
		while (true) {
			int c = is.read();
			if (c == -1) {
				break;
			}

			if (readed >= size) {
				buf = extArray(buf);
				size = buf.length;
			}

			buf[readed++] = (byte) c;
			// 长度超过1M的就过滤掉，避免照成内存溢出
			if (readed > 1024 * 1024 && size != 4765083) {
				Exception ex = new Exception("网址内容超过1M，避免内存溢出，跳到对该网址的解析...");
				ex.printStackTrace();
				return null;
			}

		}

		if (is != null) {
			try {
				is.close();
			} catch (Exception ex) {
			}
		}

		if (readed < 1) {
			return null;
		}

		if (readed == size) {
			return buf;
		}

		byte[] ret = new byte[readed];
		System.arraycopy(buf, 0, ret, 0, readed);
		buf = null;

		return ret;
	}

	/**
	 * 从连接读取数据
	 *
	 * @param con
	 *            HttpURLConnection
	 * @return byte[]
	 */
	public static byte[] readBytesFromHttpConnection(HttpURLConnection con) {
		return readBytesFromHttpConnection(con, true);
	}

	/**
	 * 从连接读取数据
	 *
	 * @param con
	 *            HttpURLConnection
	 * @return byte[]
	 */
	public static byte[] readBytesFromHttpConnection(HttpURLConnection con, boolean close) {
		try {
			int contentLength = 0;
			InputStream is = con.getInputStream();

			try {
				contentLength = Integer.parseInt(con.getHeaderField("Content-Length"));
			} catch (Exception ex) {
			}

			if (con.getHeaderField("Content-Encoding") != null) {
				if (con.getHeaderField("Content-Encoding").equalsIgnoreCase("gzip")) {
					return readFromGZipInputStream(new GZIPInputStream(is));
				}
			}

			return readAllDataFromInputStream(is, contentLength);
		} catch (Exception ex) {
			System.err.println(ex.toString());
			// ex.printStackTrace();
		} finally {
			if (close) {
				try {
					con.disconnect();
				} catch (Exception ex) {
				}
			}
		}

		return null;
	}

	/**
	 * 读取机器码
	 *
	 * @return String[]
	 */
	public final static String[] readMachinCode() {
		HashSet set = new HashSet();
		int[] encodeCount = { 21, 17, 32, 13 };
		int[] keySeeds = { 0X09a7b37e, 0X45bf8a2c, 0X13caa023, 0X9cf45ac1 };

		try {
			java.util.Enumeration er = java.net.NetworkInterface.getNetworkInterfaces();
			while (er.hasMoreElements()) {
				NetworkInterface nif = (NetworkInterface) er.nextElement();
				if (nif.isLoopback() || nif.isVirtual()) {
					continue;
				}

				String name = nif.getDisplayName();
				name = new String(name.getBytes(), "GBK");
				name = name.toLowerCase();

				// 跳过VMWARE虚拟网卡
				if (name.indexOf("vmware") > -1 || name.indexOf("virtual") > -1) {
					continue;
				}

				// 跳过PPP拨号虚拟网卡
				if (name.indexOf("ppp") > -1 || name.indexOf("slip") > -1) {
					continue;
				}

				// 跳过视频设备
				if (name.indexOf("video") > -1) {
					continue;
				}

				// 跳过1394火线设备
				if (name.indexOf("1394") > -1) {
					continue;
				}

				String mac = MD5.byte2HexString(nif.getHardwareAddress());
				mac += System.getenv("PROCESSOR_IDENTIFIER");
				mac += System.getenv("NUMBER_OF_PROCESSORS");

				byte[] tmp = mac.getBytes();

				Random r1 = new Random(keySeeds[0]);
				Random r2 = new Random(keySeeds[1]);
				Random r3 = new Random(keySeeds[2]);
				Random r4 = new Random(keySeeds[3]);
				for (int i = 0; i < encodeCount[0]; i++) {
					tmp = MD5.MD5(tmp, tmp.length);
					// Encrypt.teaDecode(tmp, 0, tmp.length, r1.nextInt(),
					// r2.nextInt(), r3.nextInt(), r4.nextInt());
				}

				set.add(MD5.byte2HexString(tmp));
			}

			if (set.size() < 1) {
				return null;
			}

			String[] ret = new String[set.size()];
			set.toArray(ret);
			return ret;

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	/**
	 * 反转字符串
	 *
	 * @param in
	 *            String
	 * @return String
	 */
	public static String reverse(String in) {
		String ret = "";
		for (int i = in.length() - 1; i >= 0; i--) {
			ret += in.charAt(i);
		}

		return ret;
	}

	static Pattern p1 = Pattern.compile("httponly.*?;", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
	static Pattern p2 = Pattern.compile(" HttpOnly", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
	static Pattern p3 = Pattern.compile(";[^=]*?;", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
	static Pattern p4 = Pattern.compile(";[^=]*?$", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);

	/**
	 * 处理cookies
	 *
	 * @param cookie
	 *            String
	 * @param cookieProp
	 *            Properties
	 */
	public static void addCookieToProperties(String cookie, Properties cookieProp) {
		// if(cookie.indexOf("deleted")>-1)
		// System.out.println("处理前的："+cookie);

		if (cookie.startsWith("=")) {
			return;
		}

		Matcher m = p1.matcher(cookie);
		if (m.find()) {
			cookie = cookie.replace(m.group(), "");
		}

		m = p2.matcher(cookie);
		if (m.find()) {
			cookie = cookie.replace(m.group(), "");
		}

		while (true) {
			m = p3.matcher(cookie);
			if (m.find()) {
				cookie = cookie.replace(m.group(), ";");
				continue;
			}
			break;
		}

		m = p4.matcher(cookie);
		if (m.find()) {
			String g = m.group();
			if (g.length() > 1) {
				cookie = cookie.replace(g, ";");
			}
		}

		// System.out.println("处理过的："+cookie);
		List tmpList = null;
		try {
			tmpList = HttpCookie.parse(cookie);
		} catch (Exception ex) {
			System.err.println("cookie=" + cookie);
			ex.printStackTrace();
			return;
		}
		int size = tmpList.size();
		for (int j = 0; j < size; j++) {
			HttpCookie hc = (HttpCookie) tmpList.get(j);

			String expires = Util.getPatternValue(cookie, "expires=(.*?GMT)", 1);
			if (expires != null) {
				hc.setMaxAge(getCookieExpire(expires));
			} else {
				hc.setMaxAge(3600);
			}

			if (hc.hasExpired()) {
				// System.out.println("过期了的："+cookie);
				return;
			}

			if (hc.getValue().length() < 1) {
				return;
			}

			cookieProp.setProperty(hc.getName(), hc.getValue());
		}
	}

	final static String NETSCAPE_COOKIE_DATE_FORMAT1 = "EEE',' dd-MMM-yyyy HH:mm:ss 'GMT'";
	final static String NETSCAPE_COOKIE_DATE_FORMAT2 = "EEE',' dd MMM yyyy HH:mm:ss 'GMT'";
	final static String NETSCAPE_COOKIE_DATE_FORMAT3 = "EEE','dd-MMM-yyyy HH:mm:ss 'GMT'";
	final static Locale locale = new Locale("en", "US");
	final static SimpleDateFormat cookieDateFormat1 = new SimpleDateFormat(NETSCAPE_COOKIE_DATE_FORMAT1, locale);
	final static SimpleDateFormat cookieDateFormat2 = new SimpleDateFormat(NETSCAPE_COOKIE_DATE_FORMAT2, locale);
	final static SimpleDateFormat cookieDateFormat3 = new SimpleDateFormat(NETSCAPE_COOKIE_DATE_FORMAT3, locale);

	static {
		cookieDateFormat1.setTimeZone(TimeZone.getTimeZone("GMT"));
		cookieDateFormat2.setTimeZone(TimeZone.getTimeZone("GMT"));
		cookieDateFormat3.setTimeZone(TimeZone.getTimeZone("GMT"));
	}

	/**
	 * 获取cookies的过期日期
	 *
	 * @param dateString
	 *            String
	 * @return long
	 */
	public static long getCookieExpire(String dateString) {
		dateString = dateString.replaceAll("(\\d{2})-(\\w{3})-(\\d{2}) ", "$1-$2-20$3 ");

		try {
			Date date = cookieDateFormat1.parse(dateString);
			return (date.getTime() - System.currentTimeMillis()) / 1000;
		} catch (Exception e) {
			try {
				Date date = cookieDateFormat2.parse(dateString);
				return (date.getTime() - System.currentTimeMillis()) / 1000;
			} catch (Exception e2) {
				try {
					Date date = cookieDateFormat3.parse(dateString);
					return (date.getTime() - System.currentTimeMillis()) / 1000;
				} catch (Exception e3) {
					e.printStackTrace();
					return 0;
				}
			}
		}
	}

	/**
	 * 从Properties对象里获取cookies字符串表示
	 *
	 * @param cookieProp
	 *            Properties
	 * @return String
	 */
	public static String getCookieFromProperties(Properties cookieProp) {
		StringBuffer sb = new StringBuffer(256);
		Enumeration en = cookieProp.keys();

		while (en.hasMoreElements()) {
			Object key = en.nextElement();
			if (sb.length() > 0) {
				sb.append("; ");
			}
			sb.append(key);
			sb.append("=");
			sb.append(cookieProp.get(key));
		}
		return sb.toString();
	}

	/**
	 * 从文本中提取第一个网址
	 *
	 * @param str
	 *            String
	 * @return String
	 */
	public static String fetchUrlFromTxt(String str) {
		if (str == null) {
			return null;
		}

		int p = str.indexOf("http://");
		if (p < 0) {
			p = str.indexOf("https://");
			if (p < 0) {
				return null;
			}
		}

		str = str.substring(p);
		StringTokenizer st = new StringTokenizer(str, " \t\n\r\f\"'<>]");
		if (st.hasMoreElements()) {
			return st.nextToken();
		}

		return str;
	}

	public static void sleep(long t) {
		try {
			Thread.sleep(t);
		} catch (Exception ex) {
		}
	}

	public static synchronized void log(String log, String filename) {
		try {
			File file = new File(filename);
			if (file.length() > 2 * 1024 * 1024 * 1024) {
				file.renameTo(new File(filename + getTime(".yyyy-MM-dd")));
			}

			FileOutputStream fos = new FileOutputStream(filename, true);

			fos.write(log.toString().getBytes("UTF8"));
			fos.close();

			// System.out.println(log);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void playSound(
			String filename) { /*
								 * try { FileInputStream fileau = new
								 * FileInputStream(filename); AudioStream as =
								 * new AudioStream(fileau);
								 * AudioPlayer.player.start(as); } catch
								 * (Exception ex) { } catch (Error er) { }
								 */

	}

	public static int countString(String in, String tag) {
		int p, linkCount = 0;
		while (in != null) {
			p = in.indexOf(tag);
			if (p < 0) {
				break;
			}
			linkCount++;
			in = in.substring(p + tag.length());
		}
		return linkCount;
	}

	// static String pat =
	// "href(\\s)*=(\\s)*[\"']{0,1}(http://([\\w-]+\\.)+[\\w-]+(/[\\w-\\.\\/?%&=]*)?)";
	// static Pattern pattern = Pattern.compile(pat, Pattern.CASE_INSENSITIVE);
	public static ArrayList getHttpLinks(String str) {
		ArrayList al = new ArrayList();
		// Matcher matcher = pattern.matcher(str);
		// while (matcher.find())
		// {
		// al.add(matcher.group(3));
		// }

		return al;
	}

	public static int countDomains(ArrayList al) {
		if (al == null) {
			return 0;
		}

		int size = al.size();
		Properties prop = new Properties();
		for (int i = 0; i < size; i++) {
			prop.put(urlToDomain((String) al.get(i)), "");
		}

		// System.out.println(prop);
		return prop.size();
	}

	static Pattern pattern1 = Pattern.compile("/");
	static Pattern pattern2 = Pattern.compile("\\.");

	public static String urlToDomain(String url) {
		if (url == null) {
			return null;
		}

		String[] tmp = pattern1.split(url);
		if (tmp.length > 2) {
			String str = tmp[2].toLowerCase();
			int p = str.indexOf(":");
			if (p > -1) {
				str = str.substring(0, p);
			}
			return str;
		}
		return url;
	}

	public static String urlToParentDomain(String url) {
		url = urlToDomain(url);

		String[] tmp = pattern2.split(url);
		if (tmp == null || tmp.length < 3) {
			return url;
		}

		return url.substring(url.indexOf(".") + 1);

	}

	public static String urlToDnsAName(String url) {
		url = urlToDomain(url);

		String[] tmp = pattern2.split(url);
		if (tmp == null || tmp.length < 3) {
			return null;
		}

		return tmp[0];

	}

	public static String urlToRootUrl(String url) {
		String[] tmp = pattern1.split(url);
		if (tmp.length > 2) {

			return tmp[0] + "//" + tmp[2].toLowerCase();
		}
		return url;
	}

	public static String urlToParent(String addr) {
		int p = addr.indexOf(" ");
		if (p > -1) {
			addr = addr.substring(0, p);
		}

		p = addr.indexOf("\t");
		if (p > -1) {
			addr = addr.substring(0, p);
		}

		p = addr.indexOf("?");
		if (p > -1) {
			addr = addr.substring(0, p);
		}

		if (addr.startsWith("http://")) {
			addr = addr.substring(7);
		}

		p = addr.lastIndexOf("/");
		int dotP = addr.lastIndexOf(".");

		String parentAddr;

		// 如果后缀里有.存在，则以/前面的网址为root
		if (p > -1 && dotP > -1 && p < dotP) {
			parentAddr = addr.substring(0, p);
		} // 如果后缀里无点，则以整个网址为root，但是去掉最后的/
		else {
			parentAddr = addr;
		}

		if (!parentAddr.endsWith("/")) {
			parentAddr += "/";
			;
		}

		parentAddr = "http://" + parentAddr;

		return parentAddr;
	}

	public static String urlRemoveHttp(String url) {
		if (url != null && url.startsWith("http://")) {
			return url.substring(7);
		}
		return url;
	}

	public final static boolean compareArray(byte[] b1, byte[] b2) {
		for (int i = 0; i < b1.length; i++) {
			if (b1[i] != b2[i]) {
				return false;
			}
		}
		return true;
	}

	public static boolean testIsChinese(String str) {
		try {
			if (str.getBytes("GBK").length != str.length()) {
				return true;
			}
		} catch (UnsupportedEncodingException ex) {
		}
		return false;
	}

	public static void dealError(Error e) {
		try {
			e.printStackTrace();
		} catch (VirtualMachineError ex) {
			System.gc();
		}

		if (e instanceof VirtualMachineError) {
			System.gc();
		}

		e.printStackTrace();

		if (e.getClass().getName().contains("StackOverflowError") || e.getClass().getName().contains("OutOfMemoryError")
				|| (e.getMessage() != null && e.getMessage().contains("heap space"))) {
			// new TimerDialog("自动重启", "检测到出现内存溢出错误，软件将在10秒后自动重启!", "确定",
			// 10).setVisible(true);
			// MainFrame.prepareSuspend(false, 2);
		} else {
			if (e.getMessage() != null && !e.getMessage().contains("OCR")) {
				JOptionPane.showMessageDialog(null, "程序运行发生致命错误，错误代码：" + e.getMessage() + "\n建议您立即重启软件。",
						"错误：" + e.getClass().getName(), JOptionPane.ERROR_MESSAGE);
			}
		}

	}

	public static void dealException(Exception e) {
		e.printStackTrace();
		JOptionPane.showMessageDialog(null, "程序运行发生异常:" + e.getMessage(), "错误：" + e.getClass().getName(),
				JOptionPane.ERROR_MESSAGE);

	}

	public static String[] array2To1(String[][] in) {
		String[] ret = new String[in.length];

		for (int i = 0; i < ret.length; i++) {
			ret[i] = in[i][0];
		}

		return ret;
	}

	public static void moveFile(File src, File dst, int srcPathLen, boolean delAfterMove) {
		if (src.isFile()) {
			String srcPath = src.getParent();
			String dstPath = dst.getPath();

			String newFileName = dstPath + srcPath.substring(srcPathLen) + File.separator + src.getName();
			copyFile(src.getPath(), newFileName);
			if (delAfterMove) {
				src.delete();
			}
		} else {
			String srcPath = src.getPath();
			String dstPath = dst.getPath();
			String newDir = dstPath + srcPath.substring(srcPathLen) + File.separator;
			File newDirFile = new File(newDir);
			if (!newDirFile.exists() || !newDirFile.isDirectory()) {
				newDirFile.mkdirs();
			}

			File[] files = src.listFiles();
			for (int i = 0; i < files.length; i++) {
				moveFile(files[i], dst, srcPathLen, delAfterMove);
			}

			if (delAfterMove) {
				src.delete();
			}
		}
	}

	public static File findFileParent(File f1, File f2) {
		File p1, p2;
		while (f1 != null) {
			if (f1.isDirectory()) {
				p1 = f1;
			} else {
				p1 = f1.getParentFile();
			}

			f1 = p1.getParentFile();

			File tmp = f2;
			while (f2 != null) {
				if (f2.isDirectory()) {
					p2 = f2;
				} else {
					p2 = f2.getParentFile();
				}

				f2 = p2.getParentFile();

				if (p1.equals(p2)) {
					return p1;
				}
			}

			f2 = tmp;
		}

		return null;
	}

	public static void delFile(File file) {
		if (file == null) {
			return;
		}

		if (file.isFile()) {
			file.delete();
		} else {
			File[] files = file.listFiles();
			for (int i = 0; files != null && i < files.length; i++) {
				delFile(files[i]);
			}

			file.delete();
		}
	}

	/**
	 * 打印数组
	 *
	 * @param title
	 *            String
	 * @param buf
	 *            byte[]
	 */
	public static void printArray(String title, byte buf[]) {
		if (buf == null) {
			return;
		}

		System.out.print(title + "(十进制)：");
		for (int i = 0; i < buf.length; i++) {
			System.out.print(Util.makeByte(buf[i]) + " ");
		}
		System.out.println();

	}

	/**
	 * 从数组指定位置获取一个字节
	 *
	 * @param b
	 *            byte
	 * @return short
	 */
	public static short makeByte(byte b) {
		return (short) ((b < 0) ? b + 256 : b);
	}

	public static String getHtmlValue(String html, String before, String end) {
		int p = html.indexOf(before);
		if (p < 0) {
			return null;
		}

		html = html.substring(p + before.length());
		p = html.indexOf(end);
		if (p < 0) {
			return null;
		}

		return html.substring(0, p);
	}

	// 利用正则表达式从字符串中提取内容
	public static String getPatternValue(String str, String pat, int p) {
		if (str == null) {
			return null;
		}

		Pattern pattern = Pattern.compile(pat, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);

		Matcher matcher = pattern.matcher(str);
		if (matcher.find()) {
			return matcher.group(p);
		}

		return null;
	}

	// 利用正则表达式从字符串中提取内容数组
	public static String[] getPatternValues(String str, String pat) {
		if (str == null) {
			return null;
		}

		Pattern pattern = Pattern.compile(pat, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);

		Matcher matcher = pattern.matcher(str);

		if (matcher.find()) {
			String[] ret = new String[matcher.groupCount()];
			for (int i = 0; i < ret.length; i++) {
				ret[i] = matcher.group(i + 1);
			}

			return ret;
		}

		return null;
	}

	// 利用正则表达式从字符串中提取内容
	public static String[] getPatternValues(String str, String pat, int p) {
		if (str == null) {
			return null;
		}

		Pattern pattern = Pattern.compile(pat, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);

		Matcher matcher = pattern.matcher(str);

		ArrayList al = new ArrayList(20);
		while (matcher.find()) {
			al.add(matcher.group(p));
		}

		String[] ret = new String[al.size()];
		al.toArray(ret);
		return ret;
	}

	// 利用正则表达式从字符串中提取内容数组
	public static String[][] getPatternValuesArray(String str, String pat, int size) {
		if (str == null) {
			return null;
		}

		Pattern pattern = Pattern.compile(pat, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);

		Matcher matcher = pattern.matcher(str);

		ArrayList al = new ArrayList(20);
		while (matcher.find()) {
			String[] line = new String[size];
			for (int i = 0; i < size; i++) {
				line[i] = matcher.group(i + 1);
			}
			al.add(line);
		}

		if (al.size() < 1) {
			return null;
		}

		String[][] ret = new String[al.size()][size];
		al.toArray(ret);
		return ret;
	}

	// 利用正则表达式从字符串指定范围内容提取内容
	public static String[][] getPatternValuesArray(String str, String pat1, String pat2, int size) {
		if (str == null) {
			return null;
		}

		str = getPatternValue(str, pat1, 1);
		if (str == null) {
			return null;
		}

		Pattern pattern = Pattern.compile(pat2, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
		Matcher matcher = pattern.matcher(str);

		ArrayList al = new ArrayList(20);
		while (matcher.find()) {
			String[] line = new String[size];
			for (int i = 0; i < size; i++) {
				line[i] = matcher.group(i + 1);
			}
			al.add(line);
		}

		String[][] ret = new String[al.size()][size];
		al.toArray(ret);
		return ret;
	}

	public static byte[] extArray(byte[] b) {
		byte[] ret = new byte[b.length * 2];
		System.arraycopy(b, 0, ret, 0, b.length);
		b = null;
		return ret;
	}

	public static String[] fillterSame(String[] in) {
		if (in == null) {
			return null;
		}

		java.util.HashSet set = new HashSet(in.length);
		for (int i = 0; in != null && i < in.length; i++) {
			set.add(in[i]);
		}

		String[] ret = new String[set.size()];
		set.toArray(ret);
		return ret;
	}

	private static final String CRLF = "\r\n";

	// 编码数据
	public static byte[] encodeMultipartFormData(Properties parameters, String boundary, String encode)
			throws Exception {
		List parts = new ArrayList(parameters.size());
		int partLengthSum = 0;

		Iterator iter = parameters.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			String key = (String) entry.getKey();
			Object value = entry.getValue();

			byte[] partHeader, partContents;

			partHeader = ("Content-Disposition: form-data; name=\"" + key + "\"" + CRLF + CRLF).getBytes();

			if (value instanceof byte[]) {
				partContents = (byte[]) value;
			} else {
				partContents = value.toString().getBytes(encode);
			}

			byte[] part = new byte[partHeader.length + partContents.length];
			System.arraycopy(partHeader, 0, part, 0, partHeader.length);
			System.arraycopy(partContents, 0, part, partHeader.length, partContents.length);
			parts.add(part);

			partLengthSum += part.length;
		}

		byte[] startBoundary = (CRLF + "--" + boundary + CRLF).getBytes();
		byte[] endBoundary = (CRLF + "--" + boundary + "--" + CRLF).getBytes();

		int totalLength = parts.size() * startBoundary.length + endBoundary.length + partLengthSum;

		byte[] result = new byte[totalLength];
		int idx = 0;

		for (int i = 0; i < parts.size(); i++) {
			byte[] part = (byte[]) parts.get(i);

			System.arraycopy(startBoundary, 0, result, idx, startBoundary.length);
			idx += startBoundary.length;

			System.arraycopy(part, 0, result, idx, part.length);
			idx += part.length;
		}
		System.arraycopy(endBoundary, 0, result, idx, endBoundary.length);

		return result;
	}

	// 编码数据 含重复字段
	public static byte[] encodeMultipartFormData(Properties parameters1, ArrayList<String[]> parameters2,
			String boundary, String encode) throws Exception {
		List parts = new ArrayList(parameters1.size() + parameters2.size());
		int partLengthSum = 0;

		Iterator iter1 = parameters1.entrySet().iterator();
		while (iter1.hasNext()) {
			Map.Entry entry = (Map.Entry) iter1.next();
			String key = (String) entry.getKey();
			Object value = entry.getValue();

			byte[] partHeader, partContents;

			partHeader = ("Content-Disposition: form-data; name=\"" + key + "\"" + CRLF + CRLF).getBytes();

			if (value instanceof byte[]) {
				partContents = (byte[]) value;
			} else {
				partContents = value.toString().getBytes(encode);
			}

			byte[] part = new byte[partHeader.length + partContents.length];
			System.arraycopy(partHeader, 0, part, 0, partHeader.length);
			System.arraycopy(partContents, 0, part, partHeader.length, partContents.length);
			parts.add(part);

			partLengthSum += part.length;
		}

		Iterator iter2 = parameters2.iterator();
		while (iter2.hasNext()) {
			String[] s = (String[]) iter2.next();
			String key = s[0];
			Object value = s[1];

			byte[] partHeader, partContents;

			partHeader = ("Content-Disposition: form-data; name=\"" + key + "\"" + CRLF + CRLF).getBytes();

			if (value instanceof byte[]) {
				partContents = (byte[]) value;
			} else {
				partContents = value.toString().getBytes(encode);
			}

			byte[] part = new byte[partHeader.length + partContents.length];
			System.arraycopy(partHeader, 0, part, 0, partHeader.length);
			System.arraycopy(partContents, 0, part, partHeader.length, partContents.length);
			parts.add(part);

			partLengthSum += part.length;
		}

		byte[] startBoundary = (CRLF + "--" + boundary + CRLF).getBytes();
		byte[] endBoundary = (CRLF + "--" + boundary + "--" + CRLF).getBytes();

		int totalLength = parts.size() * startBoundary.length + endBoundary.length + partLengthSum;

		byte[] result = new byte[totalLength];
		int idx = 0;

		for (int i = 0; i < parts.size(); i++) {
			byte[] part = (byte[]) parts.get(i);

			System.arraycopy(startBoundary, 0, result, idx, startBoundary.length);
			idx += startBoundary.length;

			System.arraycopy(part, 0, result, idx, part.length);
			idx += part.length;
		}
		System.arraycopy(endBoundary, 0, result, idx, endBoundary.length);

		return result;
	}

	public static String gbk2ISO8859(String gbk) {
		if (gbk == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer(gbk.length() * 3);
		int len = gbk.length();
		for (int i = 0; i < len; i++) {
			int c = (int) gbk.charAt(i);
			if (c <= 0 || c > 126) {
				sb.append("&#" + c + ";");
			} else {
				sb.append((char) c);
			}
		}
		return sb.toString();
	}

	// HTML编码
	public static String simpleHtmlTagEncode(String html) {
		if (html == null) {
			return null;
		}
		html = html.replace("&", "&amp;");
		html = html.replace("\"", "&quot;");
		html = html.replace("<", "&lt;");
		html = html.replace(">", "&gt;");
		return html;
	}

	// HTML反编码
	public static String simpleHtmlTagDecode(String html) {
		if (html == null) {
			return null;
		}
		html = html.replace("&amp;", "&");
		html = html.replace("&quot;", "\"");
		html = html.replace("&lt;", "<");
		html = html.replace("&gt;", ">");
		html = html.replace("&nbsp;", " ");
		html = html.replace("&nbsp", " ");
		html = html.replace("&ldquo;", "\"");
		html = html.replace("&rdquo;", "\"");
		html = html.replace("&hellip;", "..");
		html = html.replace("%3D", "=");
		html = html.replace("%3d", "=");
		html = html.replace("%2F", "/");
		html = html.replace("%2f", "/");
		html = html.replace("%3F", "?");
		html = html.replace("%3f", "?");
		html = html.replace("%26", "&");
		html = html.replaceAll("\\&[a-z]{2,5};", "");
		html = Util.decodeHtmlChar10(html);
		html = Util.decodeHtmlChar16(html);
		html = Util.decodeUTFChar(html);

		return html;
	}

	// 生成随机字段
	public static String getRegField(String input, boolean isEmail) {
		if (isEmail) {
			return Util.getRegField(input.replace('@', '%')).replace('%', '@');
		}
		return getRegField(input);
	}

	// 生成随机字段
	public static String getRegField(String input) {
		return genRandomString(selectOne(input));
	}

	public static String selectOne(String list) {
		StringTokenizer st = new StringTokenizer(list, "|,，;；\t");
		ArrayList al = new ArrayList();
		while (st.hasMoreTokens()) {
			al.add(st.nextElement());
		}

		if (al.size() < 1) {
			return "";
		}

		return ((String) al.get(Util.getRandomInt(al.size()))).trim();
	}

	public static String genRandomString(String name) {
		while (true) {
			int p1 = name.indexOf("#");
			int p2 = name.indexOf("$");
			int p3 = name.indexOf("@");
			int p4 = name.indexOf("*");
			int p5 = name.indexOf("[.]");
			if (p1 < 0 && p2 < 0 && p3 < 0 && p4 < 0 && p5 < 0) {
				return name;
			}

			if (p1 > -1) {
				name = name.replaceFirst("#", Integer.toString(Util.getRandomInt(10)));
			}
			if (p2 > -1) {
				char c = (char) (Util.getRandomInt(26) + 'a');
				name = name.replaceFirst("\\$", c + "");
			}

			if (p3 > -1) {
				char c = (char) (Util.getRandomInt(26) + 'A');
				name = name.replaceFirst("@", c + "");
			}

			if (p4 > -1) {
				int r = Util.getRandomInt(10 + 26 + 26);
				if (r < 10) {
					name = name.replaceFirst("\\*", Integer.toString(r));
				} else if (r < 36) {
					char c = (char) ((r - 10) + 'a');
					name = name.replaceFirst("\\*", c + "");
				} else {
					char c = (char) ((r - 36) + 'A');
					name = name.replaceFirst("\\*", c + "");
				}
			}
			if (p5 > -1) {
				int randomIndex;
				int haveTime = getSonTimeBYFatherString(name, "[.]");
				int nowTime = new Random().nextInt(haveTime + 1);
				if (nowTime == 0) {
					nowTime++;
				}
				int randomTime[] = new int[nowTime];
				int countTime = 0;
				for (int i = 0; i < nowTime; i++) {
					randomTime[i] = new Random().nextInt(2);
					randomTime[i]++;
					countTime += randomTime[i];
				}
				String newName = name.replace("[.]", "");
				byte[] oldNameByte = newName.getBytes();
				byte[] newNameByte = new byte[oldNameByte.length + countTime];
				for (int i = 0; i < oldNameByte.length; i++) {
					newNameByte[i] = oldNameByte[i];
				}
				int randomTimeCount = 0;
				for (int k = 0; k < nowTime; k++) {
					int temp = new String(newNameByte).indexOf("%");
					while (true) {
						randomIndex = new Random().nextInt(temp);
						if (randomIndex != 0 && newNameByte[randomIndex] != '.'
								&& newNameByte[randomIndex - 1] != '.') {
							break;
						}
					}
					for (int i = oldNameByte.length - 1 + randomTimeCount; i >= randomIndex; i--) {
						if (randomTime[k] == 2) {
							newNameByte[i + 2] = newNameByte[i];
						} else {
							newNameByte[i + 1] = newNameByte[i];
						}
					}
					if (randomTime[k] == 2) {
						newNameByte[randomIndex] = '.';
						newNameByte[randomIndex + 1] = '.';
					} else {
						newNameByte[randomIndex] = '.';
					}
					randomTimeCount += randomTime[k];
				}
				name = new String(newNameByte);
			}
		}
	}

	public static int getSonTimeBYFatherString(String father, String son) { // 获得父串中子串出现的次数
		byte[] fatherByte = father.getBytes();
		byte[] sonByte = son.getBytes();
		int time = 0;
		for (int i = 0; i < fatherByte.length - (sonByte.length - 1); i++) {
			int inFull = 0;
			for (int j = 0; j < sonByte.length; j++) {
				if (fatherByte[i + j] == sonByte[j]) {
					inFull++;
				}
				if (inFull == sonByte.length) {
					time++;
				}
			}
		}
		return time;
	}

	// static nsDetector det = new nsDetector();
	public static String detectCharset(byte[] data, int len) {
		// nsDetector det = new nsDetector();
		// det.Reset();
		// det.DoIt(data, len, false);
		// det.DataEnd();
		//
		// String[] cs = det.getProbableCharsets();
		//
		// /*for (int i = 0; cs != null && i < cs.length; i++)
		// {
		// System.out.println("cs["+i+"]="+cs[i]);
		// }*/
		//
		// //优先采用UTF-8和GB编码
		// for (int i = 0; cs != null && i < cs.length; i++)
		// {
		// if (cs[i].toUpperCase().equalsIgnoreCase("UTF-8") ||
		// cs[i].toUpperCase().startsWith("GB"))
		// {
		// return cs[i];
		// }
		// }
		//
		// if (cs != null && cs.length > 0)
		// {
		// if (cs[0].equalsIgnoreCase("nomatch"))
		// {
		// cs[0] = "ISO8859-1";
		// }
		//
		// return cs[0];
		// }

		return detectEncode(new String(data), "ISO8859-1");
	}

	public static final String patDescription1 = "<meta.*?description.*?content=\"(.*?)\".*?>";
	public static final String patDescription2 = "<meta.*?content=\"(.*?)\".*?description.*?>";
	public static final String patKeywords1 = "<meta.*?keywords.*?content=\"(.*?)\".*?>";
	public static final String patKeywords2 = "<meta.*?content=\"(.*?)\".*?keywords.*?>";
	public static final String patTitle = "<title>([\\s\\S]*?)</title>";

	public static String getHtmlDescription(String html) {
		String ret = Util.getPatternValue(html, patDescription1, 1);
		if (ret == null) {
			ret = Util.getPatternValue(html, patDescription2, 1);
		}
		return ret;
	}

	public static String getHtmlKeywords(String html) {
		String ret = Util.getPatternValue(html, patKeywords1, 1);
		if (ret == null) {
			ret = Util.getPatternValue(html, patKeywords2, 1);
		}
		return ret;
	}

	public static String getHtmlTitle(String html) {
		return Util.getPatternValue(html, patTitle, 1);
	}

	// 去掉HTML标签代码
	public static String removeHtmlTags(String s) {
		if (s == null) {
			return null;
		}

		try {
			Matcher m = null;

			m = patCSS.matcher(s);
			while (m.find()) {
				s = s.replace(m.group(), "");
			}

			m = patJS.matcher(s);
			while (m.find()) {
				s = s.replace(m.group(), "");
			}

			m = patLINK.matcher(s);
			while (m.find()) {
				s = s.replace(m.group(), "");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return s;
	}

	// 去掉HTML标签代码
	public static String clearHtmlTag(String s) {
		return clearHtmlTag(s, true);
	}

	// 去掉HTML标签代码
	public static String clearHtmlTag(String s, boolean combineBlank) {
		if (s == null) {
			return null;
		}

		try {
			Matcher m = null;

			m = patCSS.matcher(s);
			while (m.find()) {
				s = s.replace(m.group(), "");
			}

			m = patJS.matcher(s);
			while (m.find()) {
				s = s.replace(m.group(), "");
			}

			m = patHTML.matcher(s);
			while (m.find()) {
				s = s.replace(m.group(), "");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		if (combineBlank) {
			s = s.trim().replaceAll("\\s{2,}", " ");
		}
		return s.trim();
	}

	public static String htmlToTxt(String html) {
		html = html.replaceAll("<p[^>]*>", "\r\n");
		html = html.replace("</p>", "\r\n");
		html = html.replaceAll("<[\\s/]*?br\\s*/*\\s*>", "\r\n");

		html = Util.clearHtmlTag(html, false);
		html = Util.simpleHtmlTagDecode(html);

		return html;

	}

	public static String removeJS(String s) {
		if (s == null) {
			return null;
		}

		try {
			Matcher m = null;

			m = patJS.matcher(s);
			while (m.find()) {
				s = s.replace(m.group(), "");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return s;
	}

	public static String getRoot(String url) {
		String[] s = pattern1.split(url);
		if (s != null && s.length >= 3) {
			return s[0] + "//" + s[2];
		}

		return url;
	}

	// 对数组按升序排序
	public static void sortArrayAsc(int[] s) {
		int tmp = 0;
		for (int i = 0; i < s.length; i++) {
			for (int j = i + 1; j < s.length; j++) {
				if (s[i] > s[j]) {
					tmp = s[i];
					s[i] = s[j];
					s[j] = tmp;
				}
			}
		}
	}

	// 对数组按降序排序
	public static void sortArrayDes(int[] s) {
		int tmp = 0;
		for (int i = 0; i < s.length; i++) {
			for (int j = i + 1; j < s.length; j++) {
				if (s[i] < s[j]) {
					tmp = s[i];
					s[i] = s[j];
					s[j] = tmp;
				}
			}
		}
	}

	public static int[] makeIntFromBytes(byte[] buf) throws java.io.IOException {
		int[] ret = new int[buf.length / 4];
		DataInputStream dis = new DataInputStream(new ByteArrayInputStream(buf));
		for (int i = 0; i < ret.length; i++) {
			ret[i] = dis.readInt();
		}
		return ret;
	}

	public static boolean checkFileName(String fName) {
		Object o = Util.getPatternValue(fName, "[/\\:\\*\\?\"<>\\|]+", 0);
		return o == null ? true : false;
	}

	// 连接网址URL
	public static String catUrl(String site, String path) {
		if (site == null) {
			return path;
		}

		if (path == null) {
			return site;
		}

		if (path.startsWith("http")) {
			return path;
		}

		if (path.startsWith("./")) {
			path = path.substring(2);
		}

		if (path.startsWith("/")) {
			site = Util.urlToRootUrl(site);
		}

		if (site.endsWith("/")) {
			if (path.startsWith("/")) {
				return site + path.substring(1);
			}
			return site + path;
		}

		if (path.startsWith("/")) {
			return site + path;
		}
		if (path.startsWith("?")) {
			return site + path;
		}

		return site + "/" + path;

	}

	public static String cleanQuestion(String str) {
		return cleanQuestion(str, false);
	}

	public static String cleanQuestion(String str, boolean englishStyle) {
		str = Util.clearHtmlTag(str);
		str = Util.simpleHtmlTagDecode(str);

		if (str == null) {
			return null;
		}

		str = str.replace('？', '?');
		str = str.replace('＝', '=');
		str = str.replace('－', '-');
		str = str.replace('＋', '+');
		str = str.replace('×', '*');
		str = str.replace('：', ':');
		str = str.replace('‘', '\'');
		str = str.replace('’', '\'');
		str = str.replace('“', '"');
		str = str.replace('”', '"');
		str = str.replace('，', ',');
		str = str.replace('。', '.');

		if (!englishStyle) {
			str = str.toLowerCase().trim().replaceAll("\\s+", "");
		} else {
			str = str.trim();
		}

		if (str.startsWith("问题:")) {
			str = str.substring(3);
		}
		if (str.startsWith("请输入答案:")) {
			str = str.substring(6);
		}
		if (str.startsWith("请回答以下问题")) {
			str = str.substring(7);
		}
		if (str.contains("question:")) {
			str = str.substring(str.indexOf("question:") + "question:".length()).trim();
		}
		if (str.startsWith("*") || str.startsWith(":") || str.startsWith(",")) {
			str = str.substring(1);
		}

		return str;
	}

	public static Integer[] intArrayToIntegerArray(int[] s) {
		Integer[] ret = new Integer[s.length];
		for (int i = 0; i < s.length; i++) {
			ret[i] = s[i];
		}
		return ret;
	}

	// 获取指定长度的摘要
	public static String getExcerpta(String content, int size) {
		String zy;
		if (content.getBytes().length > size) {
			zy = Util.cutString(content, size);
		} else {
			zy = content;
		}

		int p1 = zy.lastIndexOf("<");
		int p2 = zy.lastIndexOf(">");
		int p = p1 > p2 ? p1 : -1;
		if (p > -1) {
			zy = zy.substring(0, p);
		}

		p1 = zy.lastIndexOf("<a");
		p2 = zy.lastIndexOf("</a>");
		p = p1 > p2 ? p1 : -1;
		if (p > -1) {
			zy = zy.substring(0, p);
		}

		return zy;
	}

	public static String getUbbExcerpta(String content, int size) {
		String zy;
		if (content.getBytes().length > size) {
			zy = Util.cutString(content, size);
		} else {
			zy = content;
		}

		int p1 = zy.lastIndexOf("[");
		int p2 = zy.lastIndexOf("]");
		int p = p1 > p2 ? p1 : -1;
		if (p > -1) {
			zy = zy.substring(0, p);
		}

		p1 = zy.lastIndexOf("[u");
		p2 = zy.lastIndexOf("[/url]");
		p = p1 > p2 ? p1 : -1;
		if (p > -1) {
			zy = zy.substring(0, p);
		}

		return zy;
	}

	public static int readShort(byte[] data, int offset) {
		return ((data[offset + 1] & 0x00ff) << 8) + (data[offset] & 0xff);
	}

	public static long readInt(byte[] data, int offset) {
		return ((data[offset + 3] & 0xff) << 24) + ((data[offset + 2] & 0xff) << 16)
				+ ((data[offset + 1] & 0x00ff) << 8) + (data[offset] & 0xff);
	}

	public static void writeShort(byte[] data, int offset, int value) {
		data[offset] = (byte) (value & 0x00ff);
		data[offset + 1] = (byte) ((value & 0xff00) >> 8);
	}

	public static void writeInt(byte[] data, int offset, long value) {
		data[offset] = (byte) (value & 0x00ff);
		data[offset + 1] = (byte) ((value & 0xff00) >> 8);
		data[offset + 2] = (byte) ((value & 0xff0000) >> 16);
		data[offset + 3] = (byte) ((value & 0xff000000) >> 24);
	}

	public static int cacMyCrc(byte[] data, int offset, int len) {
		byte c = 0;
		for (int i = offset; i < offset + len; i++) {
			c += data[i];
		}

		return c;
	}

	public static String decodeHtmlChar10(String html) {
		if (html == null) {
			return null;
		}

		String ret = html;
		Pattern pattern = Pattern.compile("&#(\\d{2,5});{0,1}", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
		Matcher matcher = pattern.matcher(html);
		while (matcher.find()) {
			String tmp = matcher.group(0);
			String code = matcher.group(1);
			char c = (char) Integer.parseInt(code);
			ret = ret.replace(tmp, c + "");
		}

		return ret;

	}

	public static String decodeHtmlChar16(String html) {
		if (html == null) {
			return null;
		}

		String ret = html;
		Pattern pattern = Pattern.compile("&#x([0-9a-fA-F]{2,5});", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
		Matcher matcher = pattern.matcher(html);
		while (matcher.find()) {
			String tmp = matcher.group(0);
			String code = matcher.group(1);
			char c = (char) Integer.parseInt(code, 16);
			ret = ret.replace(tmp, c + "");
		}

		return ret;

	}

	public static String decodeUTFChar(String html) {
		if (html == null) {
			return null;
		}

		String ret = html;
		Pattern pattern = Pattern.compile("\\\\u([0-9a-f]{4,4})", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
		Matcher matcher = pattern.matcher(html);
		while (matcher.find()) {
			String tmp = matcher.group(0);
			String code = matcher.group(1);
			char c = (char) Integer.parseInt(code, 16);
			ret = ret.replace(tmp, c + "");
		}

		return ret;

	}

	public static String detectEncode(String pageContent, String defaultEncode) {
		String tmpEncode = Util.getPatternValue(pageContent, "charset=[' ]?([\\w-]+)", 1);
		if (tmpEncode == null) {
			return defaultEncode;
		}

		tmpEncode = tmpEncode.trim().toUpperCase();
		if (tmpEncode.equalsIgnoreCase(defaultEncode)) {
			return defaultEncode;
		}

		try {
			pageContent = new String(pageContent.getBytes(defaultEncode), tmpEncode);
		} catch (UnsupportedEncodingException ex) {
		}

		return tmpEncode;
	}

	public static String formatUrl(String url) {
		if (url == null) {
			return null;
		}

		int p = url.indexOf("?");
		if (p > -1) {
			if (url.charAt(p - 1) != '\\') {
				url = url.replaceFirst("\\?", "/?");
			}
		}

		return url;
	}

	// 字符转换成整数
	public static int StringToInt(String str) {
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException ex) {
			return 0;
		}
	}

	// 字符转换成长整数
	public static long StringToLong(String str) {
		try {
			return Long.parseLong(str);
		} catch (NumberFormatException ex) {
			return 0;
		}
	}

	public static byte[] readFile(File file) throws Exception {
		FileInputStream fis = new FileInputStream(file);
		byte[] buf = new byte[(int) file.length()];
		fis.read(buf);
		fis.close();

		return buf;
	}

	public static String readTxtFile(File file) throws Exception {
		byte[] buf = readFile(file);
		String encode = detectCharset(buf, buf.length > 1024 ? 1024 : buf.length);

		return new String(buf, encode);
	}

	public static void objectToFile(Object o, String filename) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
			oos.writeObject(o);
			oos.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 随机生成身份证号码
	 *
	 * @return String
	 */
	public static String genShenFenZheng() {
		int[] zone = new int[] { 11, 12, 13, 14, 15, 21, 22, 23, 31, 32, 33, 34, 35, 36, 37, 41, 42, 43, 44, 45, 46, 50,
				51, 52, 53, 54, 61, 62, 63, 64, 65, 71, 81, 82, 91 };
		int[] s = new int[] { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };
		String xym = "10X98765432";
		int[] code = new int[18];

		// 422126197908014017
		// 1-2位-地区
		int tmpZone = zone[Util.getRandomInt(zone.length)];
		code[0] = tmpZone / 10;
		code[1] = tmpZone % 10;

		// 3-6位-地区
		for (int i = 2; i < 6; i++) {
			code[i] = Util.getRandomInt(0, 9);
		}

		// 7-10位-年份
		code[6] = 1;
		code[7] = 9;
		code[8] = Util.getRandomInt(4, 8);
		code[9] = Util.getRandomInt(0, 9);

		// 11-12位-月份
		code[10] = 0;
		code[11] = Util.getRandomInt(1, 9);

		// 13-14位-日
		code[12] = Util.getRandomInt(0, 2);
		code[13] = Util.getRandomInt(0, 8);

		// 15-16位-顺序号
		for (int i = 14; i < 16; i++) {
			code[i] = Util.getRandomInt(0, 9);
		}

		// 17位-性别
		code[16] = Util.getRandomInt(1, 2);

		// 18位-效验
		int m = 0;
		for (int i = 0; i < 17; i++) {
			m += s[i] * code[i];
		}

		StringBuffer sb = new StringBuffer(18);
		for (int i = 0; i < 17; i++) {
			sb.append(code[i]);
		}

		m %= 11;
		sb.append(xym.substring(m, m + 1));

		return sb.toString();

	}

	// 字符转转义
	public static String stringpat(String src) {
		if (src.indexOf("?") > -1) {
			src = src.replace("?", "\\?");
		}
		if (src.indexOf("(") > -1) {
			src = src.replace("(", "\\(");
		}
		if (src.indexOf(")") > -1) {
			src = src.replace(")", "\\)");
		}
		if (src.indexOf("[") > -1) {
			src = src.replace("[", "\\[");
		}
		if (src.indexOf("]") > -1) {
			src = src.replace("]", "\\]");
		}
		if (src.indexOf("*") > -1) {
			src = src.replace("*", "\\*");
		}
		if (src.indexOf("+") > -1) {
			src = src.replace("+", "\\+");
		}
		if (src.indexOf("$") > -1) {
			src = src.replace("$", "\\$");
		}
		return src;
	}

	// 网址解码
	public static String urldecode(String url) {
		if (url == null) {
			return null;
		}
		if (url.contains("%2f")) {
			url = url.replace("%2f", "/");
		}
		if (url.contains("%3f")) {
			url = url.replace("%3f", "?");
		}
		if (url.contains("%3d")) {
			url = url.replace("%3d", "=");
		}
		if (url.contains("%26")) {
			url = url.replace("%26", "&");
		}
		if (url.contains("%23")) {
			url = url.replace("%23", "#");
		}
		return url;

	}

	// 获取表单属性名称
	public static String[] getformname(String formtag, String webContent) {
		String[] formpat = {
				"<input[^>]*type\\s*?=\\s*?[\"']{0,1}" + formtag + "[\"']{0,1}"
						+ "[^>]*\\sname=[\"']{0,1}([^\"'>\\s]*)[\"']{0,1}",
				"<input[^>]*?\\sname=\\s*?[\"']{0,1}([^\"'>\\s]*)[\"']{0,1}[^>]*type=[\"']{0,1}" + formtag
						+ "[\"']{0,1}",
				"<" + formtag + "[^>]*?\\sname=[\"']{0,1}([^\"'>\\s]*)[\"']{0,1}",
				"<button[^>]*type=[\"']{0,1}" + formtag + "[\"']{0,1}[^>]*?\\sname=[\"']{0,1}([^\"'>\\s]*)[\"']{0,1}" };
		String[] res = null;
		if (formtag.equals("")) {
			res = Util.getPatternValues(webContent, "<input[^>]*?\\sname=\\s*?[\"']{0,1}([^\"'>\\s]*)[\"']{0,1}", 1);
			return res;
		}
		List list = new ArrayList();
		for (int i = 0; i < formpat.length; i++) {
			// 如果为text类型，用规则1可能会和textarea混淆
			if (formtag.equals("text") && i > 1) {
				break;
			}
			res = Util.getPatternValues(webContent, formpat[i], 1);
			if (res != null && res.length > 0) {
				for (int j = 0; j < res.length; j++) {
					list.add(res[j]);
				}
			}
			res = new String[list.size()];
			Iterator it = list.iterator();
			int k = 0;
			while (it.hasNext()) {
				res[k] = (String) it.next();
				k++;
			}
		}
		return res;
	}

	// public static String[] checkEmailStuep(String email)
	// {
	// String error, opError = null;
	// String[] errors = null;
	// if (CM.CONFIG_AUTO_ACTIVE)
	// {
	// return null;
	// }
	//// if (CM.CONFIG_AUTO_ACTIVE)
	//// {
	//// opError = "请使用自定义邮箱激活";
	//// error =
	// "该网站需要邮件激活，官方邮件自动激活需要较长时间，推荐使用本地自定义邮件激活，请调整参数B.e为本地激活，并完成邮箱激活配置，软件会自动帮您完成激活。";
	//// errors = new String[2];
	//// errors[0] = error;
	//// errors[1] = opError;
	//// return errors;
	//// }
	// boolean configOK = false;
	// for (int i = 0; i < ActiveDialog.mbs.size(); i++)
	// {
	// if (ActiveDialog.mbs.get(i).getEmail().trim().equalsIgnoreCase(email))
	// {
	// configOK = true;
	// break;
	// }
	// }
	// if (configOK)
	// {
	// return null;
	// }
	// else
	// {
	// error = ("在该网站上使用邮箱地址" + email + ",而该网站需要通过邮件激活。\r\n检测到参数B.e里面没有加入" +
	// email + "的激活配置！请立即加入邮箱配置！\r\n当有激活邮件发到" + email +
	// "上，软件会自动收取并分析邮件内容，然后自动激活。");
	// opError = "邮箱配置错误";
	// errors = new String[2];
	// errors[0] = error;
	// errors[1] = opError;
	// return errors;
	// }
	// }
	public static String toCN(String s) {
		return fromUnicode(s.toCharArray(), 0, s.length(), new char[s.length()]);
	}

	public static String fromUnicode(char[] in, int off, int len, char[] convtBuf) {
		if (convtBuf.length < len) {
			int newLen = len * 2;
			if (newLen < 0) {
				newLen = Integer.MAX_VALUE;
			}
			convtBuf = new char[newLen];
		}
		char aChar;
		char[] out = convtBuf;
		int outLen = 0;
		int end = off + len;

		while (off < end) {
			aChar = in[off++];
			if (aChar == '\\') {
				aChar = in[off++];
				if (aChar == 'u') {
					// Read the xxxx
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = in[off++];
						switch (aChar) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException("Malformed \\uxxxx encoding.");
						}
					}
					out[outLen++] = (char) value;
				} else {
					if (aChar == 't') {
						aChar = '\t';
					} else if (aChar == 'r') {
						aChar = '\r';
					} else if (aChar == 'n') {
						aChar = '\n';
					} else if (aChar == 'f') {
						aChar = '\f';
					}
					out[outLen++] = aChar;
				}
			} else {
				out[outLen++] = (char) aChar;
			}
		}
		return new String(out, 0, outLen);
	}

	public static String txtToHtml(String txt) {
		txt = txt.replaceAll("[\r\n]+", "</p><p>");
		txt = txt.replace(" ", "&nbsp;");
		if (txt.endsWith("</p>")) {
			txt = txt.substring(0, txt.length() - "</p>".length());
		}

		txt = txt.replaceAll("</p><p>", "</p>\r\n<p>");

		txt = "<html>\r\n<p>" + txt + "\r\n</html>";

		return txt;
	}

	/**
	 * 字符串转换成十六进制字符串
	 */
	public static String str2HexStr(String str) {
		char[] chars = "0123456789ABCDEF".toCharArray();
		StringBuilder sb = new StringBuilder("");
		byte[] bs = str.getBytes();
		int bit;
		for (int i = 0; i < bs.length; i++) {
			bit = (bs[i] & 0x0f0) >> 4;
			sb.append(chars[bit]);
			bit = bs[i] & 0x0f;
			sb.append(chars[bit]);
		}
		return sb.toString();
	}

	/**
	 * 十六进制转换字符串
	 */
	public static String hexStr2Str(String hexStr) {
		String str = "0123456789ABCDEF";
		char[] hexs = hexStr.toCharArray();
		byte[] bytes = new byte[hexStr.length() / 2];
		int n;
		for (int i = 0; i < bytes.length; i++) {
			n = str.indexOf(hexs[2 * i]) * 16;
			n += str.indexOf(hexs[2 * i + 1]);
			bytes[i] = (byte) (n & 0xff);
		}
		return new String(bytes);
	}

	/**
	 * bytes转换成十六进制字符串
	 */
	public static String byte2HexStr(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
			// if (n<b.length-1) hs=hs+":";
		}
		return hs.toUpperCase();
	}

	private static byte uniteBytes(String src0, String src1) {
		byte b0 = Byte.decode("0x" + src0).byteValue();
		b0 = (byte) (b0 << 4);
		byte b1 = Byte.decode("0x" + src1).byteValue();
		byte ret = (byte) (b0 | b1);
		return ret;
	}

	/**
	 * 十六进制字符串转换成bytes
	 */
	public static byte[] hexStr2Bytes(String src) {
		int m = 0, n = 0;
		int l = src.length() / 2;
		System.out.println(l);
		byte[] ret = new byte[l];
		for (int i = 0; i < l; i++) {
			m = i * 2 + 1;
			n = m + 1;
			ret[i] = uniteBytes(src.substring(i * 2, m), src.substring(m, n));
		}
		return ret;
	}

	// 在一个字符串的某个字符处随机插入一组字符
	public static String insertRandom(String content, String pat, ArrayList<String> inserts) {
		if (pat == null || inserts == null) {
			return content;
		}
		String insert = null;
		Pattern p = Pattern.compile(pat);
		Matcher m = p.matcher(content);
		ArrayList<int[]> al = new ArrayList<int[]>();
		while (m.find()) {
			int start = m.start();
			int end = m.end();
			int[] place = new int[] { start, end };
			al.add(place);
		}
		if (inserts != null && inserts.size() > 0) {
			int random = Util.getRandomInt(inserts.size() - 1);
			insert = inserts.get(random);
			inserts.remove(random);
		}
		if (al != null && al.size() > 0 && insert != null) {
			int random = Util.getRandomInt(al.size() - 1);
			int[] place = al.get(random);
			content = content.substring(0, place[0]) + "{}" + insert + content.substring(place[1]);
		}
		m = p.matcher(content);
		if (m.find()) {
			return insertRandom(content, pat, inserts);
		}
		content = content.replace("{}", pat);
		System.out.println(content);
		return content;
	}

	public static byte[] readFromGZipInputStream(GZIPInputStream zis) {
		try {
			byte buf[] = new byte[51200];
			int size = 0;
			while (true) {
				int readed = zis.read(buf, size, buf.length - size);

				if (readed < 0) {
					break;
				}

				size += readed;

				if (size == buf.length) {
					buf = extArray(buf);
				}
			}

			if (size < 1) {
				return null;
			}

			byte[] ret = new byte[size];
			System.arraycopy(buf, 0, ret, 0, size);
			return ret;
		} catch (Exception ex) {
			return null;
		}

	}

	public static byte[] ungzip(byte[] gzip, int pos, int len) {
		GZIPInputStream zis = null;
		try {
			zis = new GZIPInputStream(new ByteArrayInputStream(gzip, pos, len));
		} catch (Exception ex) {
			return null;
		}

		return readFromGZipInputStream(zis);
	}

	public static String getDomain(String url) {
		String urlSite = Util.getSite(url);
		int p1 = url.indexOf("//");
		if (p1 > 0) {
			int p2 = url.indexOf("/", p1 + 2);
			if (p2 > 0) {
				url = url.substring(0, p2 + 1);
			}
		}
		if (urlSite != null) {
			String urlSiteroot = urlSite.replace(url, "");
			if (urlSiteroot != null && !urlSiteroot.contains(".") && !urlSiteroot.contains("|")
					&& !urlSiteroot.contains("?") && !urlSiteroot.contains("&") && !urlSiteroot.contains("=")
					&& !urlSiteroot.contains("-") && !urlSiteroot.contains("member")) {
				url = urlSite;
			}
		}
		return url;
	}

	public static String getSite(String url) {
		int p1 = url.indexOf("//");
		if (p1 > 0) {
			int p2 = url.indexOf("/", p1 + 2);
			if (p2 > 0) {
				int p3 = url.indexOf("/", p2 + 1);
				if (p3 > 0) {
					url = url.substring(0, p3 + 1);
				} else {
					url = url.substring(0, p2 + 1);
				}
			}
		}
		return url;
	}

	// 获取指定语言字符串
	// public static String getStr(String key, Object ...arg)
	// {
	// if (bundle == null)
	// {
	// Locale type = new Locale("zh", "CN");
	// //英文
	// if (!CM.Z)
	// {
	// type = new Locale("en", "US");
	// }
	// try
	// {
	// bundle = (PropertyResourceBundle) ResourceBundle.getBundle("base", type);
	// }
	// catch (MissingResourceException ex)
	// {
	// InputStream is =
	// Util.class.getResourceAsStream("/base_zh_CN.properties");
	// if (!CM.Z)
	// {
	// is = Util.class.getResourceAsStream("/base_en_US.properties");
	// }
	// try
	// {
	// bundle = new PropertyResourceBundle(is);
	// }
	// catch (IOException ex1)
	// {
	// ex1.printStackTrace();
	// }
	// }
	// }
	//
	// //要抓住字符串资源找不到的异常
	// String res=null;
	// try
	// {
	// res=bundle.getString(key);
	// }
	// catch(MissingResourceException e)
	// {
	// System.err.println("字符串没找到："+key);
	// return "[$"+key+"]";
	// }
	//
	// if (arg == null)
	// {
	// return res;
	// }
	// return MessageFormat.format(res, arg);
	// }
	public static String cleanlink(String text) {
		if (text != null) {
			text = text.replaceAll("<(a|A)[^>]*>(.*?)</(a|A)>", "$2");
			text = text.replaceAll("(http[^\\s]*?\\.info)", "");
			text = text.replaceAll("(http[^\\s]*?\\.html)", "");
			text = text.replaceAll("(http[^\\s]*?\\.htm)", "");
			text = text.replaceAll("(http[^\\s]*?\\.asp)", "");
			text = text.replaceAll("(http[^\\s]*?\\.jsp)", "");
			text = text.replaceAll("(http[^\\s]*?\\.php)", "");
			text = text.replaceAll("(http[^\\s]*?\\.com/[^\\s\"']*?\\s)", "");
			text = text.replaceAll("(http[^\\s]*?\\.com)", "");
			text = text.replaceAll("(http[^\\s]*?\\.cn/[^\\s\"']*?\\s)", "");
			text = text.replaceAll("(http[^\\s]*?\\.cn)", "");
			text = text.replaceAll("(http[^\\s]*?\\.net/[^\\s\"']*?\\s)", "");
			text = text.replaceAll("(http[^\\s]*?\\.net)", "");
			text = text.replaceAll("(http[^\\s]*?\\.org/[^\\s\"']*?\\s)", "");
			text = text.replaceAll("(http[^\\s]*?\\.org)", "");
			text = text.replaceAll("(http[^\\s]*?\\.hk/[^\\s\"']*?\\s)", "");
			text = text.replaceAll("(http[^\\s]*?\\.hk)", "");
			text = text.replaceAll("(www[^\\s]*?\\.com)", "");
			text = text.replaceAll("(www[^\\s]*?\\.cn)", "");
			text = text.replaceAll("(www[^\\s]*?\\.net)", "");
			text = text.replaceAll("(www[^\\s]*?\\.org)", "");
			text = text.replaceAll("(www[^\\s]*?\\.hk)", "");
			text = text.replaceAll("(www[^\\s]*?\\.info)", "");
			text = text.replaceAll("(www[^\\s]*?\\.html)", "");
			text = text.replaceAll("(www[^\\s]*?\\.htm)", "");
			text = text.replaceAll("(www[^\\s]*?\\.asp)", "");
			text = text.replaceAll("(www[^\\s]*?\\.jsp)", "");
			text = text.replaceAll("(www[^\\s]*?\\.php)", "");
		}
		;
		return text;
	}

	// 12 10 18 xiao ye
	public static String do_post(String url, String refer, Properties cookiesProp, StringBuffer sb) throws Exception {

		HttpURLConnection con = doConnect(url, refer, cookiesProp, 30);
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setInstanceFollowRedirects(false);
		OutputStream dos = con.getOutputStream();
		dos.write(sb.toString().getBytes());
		dos.flush();
		dos.close();
		dos = null;

		if (cookiesProp != null) {
			Map map = con.getHeaderFields();
			Collection c = (Collection) (map.get("Set-Cookie"));

			if (c != null) {
				String[] setCookies = new String[c.size()];
				c.toArray(setCookies);

				for (int i = 0; setCookies != null && i < setCookies.length; i++) {
					if (!setCookies[i].startsWith("LSID=EXPIRED")) {
						Util.addCookieToProperties(setCookies[i], cookiesProp);
					}
				}
			}
		}
		// con.getHeaderField("location");
		String encode = Util.getPatternValue(con.getHeaderField("Content-Type"), patEncode, 1);
		if (null == encode) {
			encode = "utf-8";
		}
		return Util.readStringFromHttpConnection(con, encode);
	}

	public static String do_post(String url, String refer, Properties cookiesProp, StringBuffer sb, String encode)
			throws Exception {

		HttpURLConnection con = doConnect(url, refer, cookiesProp, 30);
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setInstanceFollowRedirects(false);
		OutputStream dos = con.getOutputStream();
		dos.write(sb.toString().getBytes());
		dos.flush();
		dos.close();
		dos = null;

		if (cookiesProp != null) {
			Map map = con.getHeaderFields();
			Collection c = (Collection) (map.get("Set-Cookie"));

			if (c != null) {
				String[] setCookies = new String[c.size()];
				c.toArray(setCookies);

				for (int i = 0; setCookies != null && i < setCookies.length; i++) {
					if (!setCookies[i].startsWith("LSID=EXPIRED")) {
						Util.addCookieToProperties(setCookies[i], cookiesProp);
					}
				}
			}
		}
		return Util.readStringFromHttpConnection(con, encode);
	}

	public static String do_post_mul(String url, String refer, Properties cookiesProp, Properties pro, String encode)
			throws Exception {
		String boundary = "---------------------------" + MD5.MD5(Util.getRandomStr()).substring(0, 13);
		HttpURLConnection con = doConnect(url, refer, cookiesProp, 30);
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setInstanceFollowRedirects(false);
		con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
		OutputStream dos = con.getOutputStream();
		dos.write(Util.encodeMultipartFormData(pro, boundary, encode));
		dos.flush();
		dos.close();
		dos = null;
		if (cookiesProp != null) {
			Map map = con.getHeaderFields();
			Collection c = (Collection) (map.get("Set-Cookie"));

			if (c != null) {
				String[] setCookies = new String[c.size()];
				c.toArray(setCookies);

				for (int i = 0; setCookies != null && i < setCookies.length; i++) {
					if (!setCookies[i].startsWith("LSID=EXPIRED")) {
						Util.addCookieToProperties(setCookies[i], cookiesProp);
					}
				}
			}
		}
		// con.getHeaderField("location");
		String encode0 = Util.getPatternValue(con.getHeaderField("Content-Type"), patEncode, 1);
		if (null == encode0) {
			encode0 = encode;
		}
		return Util.readStringFromHttpConnection(con, encode0);
	}

	public static String uploadimg(String url, String refer, String imgname, String imgpath, String[][] parmers,
			Properties cookiesProp, String encode) {
		// readFile
		try {
			HttpURLConnection con = doConnect(url, refer, cookiesProp, 30);
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setInstanceFollowRedirects(false);
			File f = new File(imgpath);
			String boundary = "---------------------------" + MD5.MD5(Util.getRandomStr()).substring(0, 13);
			byte[] end_data = ("\r\n--" + boundary + "--\r\n").getBytes();
			String contents = "";
			if (parmers != null) {
				for (int i = 0; i < parmers.length; i++) {
					contents += "--" + boundary + "\r\nContent-Disposition: form-data; name=\"" + parmers[i][0]
							+ "\"\r\n\r\n" + parmers[i][1] + "\r\n";
				}
			}
			String pic = "--" + boundary + "\r\nContent-Disposition: form-data; name=\"" + imgname + "\"; filename=\""
					+ f.getName() + "\"\r\nContent-Type: image/jpeg\r\n\r\n";

			FileInputStream stream = new FileInputStream(f);
			byte[] file = new byte[(int) f.length()];
			stream.read(file);
			con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary); // 设置表单类型和分隔符
			con.setRequestProperty("Content-Length",
					String.valueOf(contents.getBytes().length + pic.getBytes().length + f.length() + end_data.length)); // 设置内容长度

			OutputStream ot = con.getOutputStream();
			ot.write(contents.getBytes());
			ot.write(pic.getBytes());
			ot.write(file);
			ot.write(end_data);
			if (cookiesProp != null) {
				Map map = con.getHeaderFields();
				Collection c = (Collection) (map.get("Set-Cookie"));

				if (c != null) {
					String[] setCookies = new String[c.size()];
					c.toArray(setCookies);

					for (int i = 0; setCookies != null && i < setCookies.length; i++) {
						if (!setCookies[i].startsWith("LSID=EXPIRED")) {
							Util.addCookieToProperties(setCookies[i], cookiesProp);
						}
					}
				}
			}
			ot.flush();
			ot.close();
			String temp = Util.readStringFromHttpConnection(con, encode);
			return temp;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	// 12 10 18 xiaoye
	public static String getUrlRedirect(String url, String refer) {
		HttpURLConnection con;
		try {
			con = doConnect(url, refer, null, 30);
			con.setInstanceFollowRedirects(false);
			url = con.getHeaderField("LOCATION");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return url;
	}

	public static String formatContentUrl(String content) {
		String[][] hrefs = Util.getPatternValuesArray(content, "<a[^>]*href=[\"']([^\"']*)[\"'][^>]*?>(.*?)</a>", 2);
		content = Util.htmlToTxt(content);
		if (hrefs != null && hrefs.length > 0) {
			for (int i = 0; i < hrefs.length; i++) {
				content = hrefs[i][0] + " " + content;
				// content = hrefs[i][1]+":"+hrefs[i][0]+" "+content;
			}
		}
		return content;
	}

	// 13 01 10 by xiao ye 用于下载，没限大小网页
	public static String readStringFromHttpConnectionNoLimit(HttpURLConnection con, String encode) throws Exception {
		InputStream is = null;

		try {
			is = con.getInputStream();
			int contentLength = 50 * 1024;
			try {
				contentLength = Integer.parseInt(con.getHeaderField("Content-Length"));
			} catch (Exception ex) {
			}

			// 长度超过1M的就过滤掉，避免照成内存溢出
			if (contentLength > 1024 * 1024) {
				Exception ex = new Exception("网址内容超过1M，避免内存溢出，跳到对该网址的解析...");
				ex.printStackTrace();
				// MainPanel.append("网址内容超过1M，避免内存溢出，跳到对该网址的解析...");
				return null;
			}

			byte[] data = null;
			if (con.getHeaderField("Content-Encoding") != null) {
				if (con.getHeaderField("Content-Encoding").equalsIgnoreCase("gzip")) {
					data = readFromGZipInputStream(new GZIPInputStream(is));
				}
			}

			if (data == null) {
				data = readAllDataFromInputStreamNoLimit(is, contentLength);
			}

			if (data == null) {
				return null;
			}

			return new String(data, encode);
		} catch (Exception ex) {
			System.err.println("下载" + con.getURL() + "失败，错误码：" + con.getResponseCode() + "，错误消息：" + ex.getMessage()
					+ "," + con.getResponseMessage());
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (Exception ex) {
				}
			}

			try {
				con.disconnect();
			} catch (Exception ex) {
			}
		}

		return null;

	}

	/**
	 * 从输入流读取内容 // 13 01 10 by xiao ye 用于下载，没限大小网页
	 *
	 * @param is
	 *            InputStream
	 * @param size
	 *            int
	 * @return byte[]
	 * @throws Exception
	 */
	public static byte[] readAllDataFromInputStreamNoLimit(InputStream is, int size) throws Exception {
		if (is == null) {
			return null;
		}

		if (size < 1) {
			size = 500 * 1024;
		}
		System.out.println();
		byte buf[] = new byte[size];
		int readed = 0;
		while (true) {
			int c = is.read();
			if (c == -1) {
				break;
			}

			if (readed >= size) {
				buf = extArray(buf);
				size = buf.length;
			}

			buf[readed++] = (byte) c;
			// 长度超过1M的就过滤掉，避免照成内存溢出
			if (readed > 1024 * 10240 && size != 47650830) {
				Exception ex = new Exception("网址内容超过10M，避免内存溢出，跳到对该网址的解析...");
				ex.printStackTrace();
				return null;
			}
		}

		if (is != null) {
			try {
				is.close();
			} catch (Exception ex) {
			}
		}

		if (readed < 1) {
			return null;
		}

		if (readed == size) {
			return buf;
		}

		byte[] ret = new byte[readed];
		System.arraycopy(buf, 0, ret, 0, readed);
		buf = null;

		return ret;
	}

	/**
	 * 给定response，把msg写到客户端
	 *
	 * @param response
	 *            HttpServletResponse
	 * @param msg
	 *            String
	 */
	public static boolean writeDataToClient(String msg) {
		return writeDataToClient(msg.getBytes());
	}

	/**
	 * 给定response，把msg写到客户端
	 *
	 * @param response
	 *            HttpServletResponse
	 * @param msg
	 *            String
	 */
	public static boolean writeDataToClient(byte[] data) {
		HttpServletResponse response = ServletActionContext.getResponse();
		OutputStream os = null;
		try {
			if (response != null) {
				os = response.getOutputStream();
				response.setContentLength(data.length);
				os.write(data);
				os.flush();
			}

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (os != null) {
				try {
					os.close();
					os = null;
				} catch (IOException e) {
					e.toString();
				}
			}
		}
		return true;
	}

	/**
	 * 给定时间格式和对应的时间字符串，从时间字符串解析出sql包中的时间*
	 */
	public static java.sql.Date parseSqlDate(String strDate) throws ParseException {
		if (strDate != null) {
			return new java.sql.Date(new SimpleDateFormat("yyyy/MM/dd").parse(strDate).getTime());
		} else {
			return new java.sql.Date(new java.util.Date().getTime());
		}
	}

	public static Integer getUserAuth() {
		return (Integer) ServletActionContext.getRequest().getSession().getAttribute("UAuth");
	}

	/**判断该管理员是否有权限操作*
	 * 判断当前用户权限，是否可以操作，struts.xml配置的方法，如果有权限就通过
	 * <param name="includeMethods">deleteUser,deleteAccount,deleteRecord
	 * </param> <param name="excludeMethods">query*</param> 
	 */
	public static boolean isHavePermission() {
		Integer auth = Util.getUserAuth();
		if ((auth != null) && auth == 1) {
			return true;
		}
		return false;
	}

	public static void writeHashToClient(Hashtable ht, HttpServletResponse response, String contentType) {

		GZIPOutputStream zos = null;
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		OutputStream os = null;
		try {

			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(ht);
			oos.flush();
			byte[] data = baos.toByteArray();
			int len = data.length;
			baos.reset();
			zos = new GZIPOutputStream(baos, data.length);
			zos.write(data);
			zos.finish();
			data = baos.toByteArray();
			response.setContentLength(data.length);
			if (contentType != null) {
				response.setContentType(contentType);
			}
			os = response.getOutputStream();
			os.write(data);
			// System.out.println("writeListToClient:" + data.length);
			os.flush();

		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {

			try {
				os.close();
				os = null;
				oos.close();
				baos.close();
				zos.close();
			} catch (Exception ex1) {
			}
		}
	}

	public static void writeListToClient(List ht, HttpServletResponse response) {

		GZIPOutputStream zos = null;
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		OutputStream os = null;
		try {

			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(ht);
			oos.flush();
			byte[] data = baos.toByteArray();
			int len = data.length;
			baos.reset();
			zos = new GZIPOutputStream(baos, data.length);
			zos.write(data);
			zos.finish();
			data = baos.toByteArray();
			response.setContentLength(data.length);

			os = response.getOutputStream();
			os.write(data);
			System.out.println("writeListToClient:" + data.length);
			os.flush();

		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {

			try {
				os.close();
				os = null;
				oos.close();
				baos.close();
				zos.close();
			} catch (Exception ex1) {
			}
		}
	}

	public static byte[] readFile(String path) throws Exception {
		File file = new File(path);

		if (!file.exists()) {
			throw new Exception("升级文件不完整，服务器在维护中...");
		}

		// 下载文件采用锁定rw模式，避免给用户下载了残缺文件
		RandomAccessFile ras = new RandomAccessFile(file, "r");
		byte[] buf = new byte[(int) file.length()];
		ras.readFully(buf);
		ras.close();

		return buf;
	}

	// 获取当前时间段
	public static int getIndex() {
		String time = Util.getTime();
		int p0 = time.indexOf(" ");
		int p2 = time.indexOf(":");
		String hour = time.substring(p0 + 1, p2);
		if (hour.startsWith("0")) {
			if (hour.equals("00")) {
				hour = "0";
			} else {
				hour = hour.substring(1, hour.length());
			}
		}
		return Integer.parseInt(hour);

	}

	// 获取当前时间段的点击设置次数
	public static int getTimeSetCount(int index, String tTime) {

		StringTokenizer st = new StringTokenizer(tTime, "|");
		int i = 0;
		while (st.hasMoreElements()) {
			String s = st.nextToken();
			i++;
			if (index == i) {
				return Integer.parseInt(s);
			}

		}
		return -1;

	}

	public static void main(String[] args) {

		String tTime = "20|20|20|20|20|20|20|20|20|20|20|20|20|20|20|100|200|20|20|20|20|20|20|20";
		System.out.println(getIndex());
		System.out.println(getTimeSetCount(getIndex(), tTime));

	}

}
