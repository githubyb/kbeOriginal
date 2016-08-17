package edu.nwpu.dmpm.kbe.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * IP工具类
 * 
 * @author wangc
 * 
 */
public class IpUtil {

	/**
	 * 获取登录用户的IP地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip.equals("0:0:0:0:0:0:0:1")) {
			ip = "本地";
		}
		if (ip.split(",").length > 1) {
			ip = ip.split(",")[0];
		}
		return ip;
	}

	/**
	 * 通过IP获取地址，应用于外网IP地址
	 * 
	 * @param ip
	 * @return
	 */
	public static String getIpInfo(String ip) {
		String info = "";
		try {
			// 淘宝IP地址库
			URL url = new URL("http://ip.taobao.com/service/getIpInfo.php?ip="
					+ ip);
			HttpURLConnection htpcon = (HttpURLConnection) url.openConnection();
			htpcon.setRequestMethod("GET");
			htpcon.setDoOutput(true);
			htpcon.setDoInput(true);
			htpcon.setUseCaches(false);

			InputStream in = htpcon.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(in));
			StringBuffer temp = new StringBuffer();
			String line = bufferedReader.readLine();
			while (line != null) {
				temp.append(line).append("\r\n");
				line = bufferedReader.readLine();
			}
			bufferedReader.close();
			JSONObject obj = (JSONObject) JSON.parse(temp.toString());
			if (obj.getIntValue("code") == 0) {
				JSONObject data = obj.getJSONObject("data");
				info += data.getString("country") + " ";
				info += data.getString("region") + " ";
				info += data.getString("city") + " ";
				info += data.getString("isp");
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return info;
	}


	public static String callCmd(String[] cmd) {

		String result = "";

		String line = "";

		try {

			Process proc = Runtime.getRuntime().exec(cmd);

			InputStreamReader is = new InputStreamReader(proc.getInputStream());

			BufferedReader br = new BufferedReader(is);

			while ((line = br.readLine()) != null) {

				result += line;

			}

		}

		catch (Exception e) {

			e.printStackTrace();

		}

		return result;

	}

	/**
	 * 
	 * 
	 * 
	 * @param cmd
	 *            第一个命令
	 * 
	 * @param another
	 *            第二个命令
	 * 
	 * @return 第二个命令的执行结果
	 */

	public static String callCmd(String[] cmd, String[] another) {

		String result = "";

		String line = "";

		try {

			Runtime rt = Runtime.getRuntime();

			Process proc = rt.exec(cmd);

			proc.waitFor(); // 已经执行完第一个命令，准备执行第二个命令

			proc = rt.exec(another);

			InputStreamReader is = new InputStreamReader(proc.getInputStream());

			BufferedReader br = new BufferedReader(is);

			while ((line = br.readLine()) != null) {

				result += line;

			}

		}

		catch (Exception e) {

			e.printStackTrace();

		}

		return result;

	}

	/**
	 * 
	 * 
	 * 
	 * @param ip
	 *            目标ip,一般在局域网内
	 * 
	 * @param sourceString
	 *            命令处理的结果字符串
	 * 
	 * @param macSeparator
	 *            mac分隔符号
	 * 
	 * @return mac地址，用上面的分隔符号表示
	 */

	public static String filterMacAddress(final String ip,
			final String sourceString, final String macSeparator) {

		String result = "";

		String regExp = "((([0-9,A-F,a-f]{1,2}" + macSeparator
				+ "){1,5})[0-9,A-F,a-f]{1,2})";

		Pattern pattern = Pattern.compile(regExp);

		Matcher matcher = pattern.matcher(sourceString);

		while (matcher.find()) {

			result = matcher.group(1);

			if (sourceString.indexOf(ip) <= sourceString.lastIndexOf(matcher
					.group(1))) {

				break; // 如果有多个IP,只匹配本IP对应的Mac.

			}

		}

		return result;

	}

	/**
	 * 
	 * 
	 * 
	 * @param ip
	 *            目标ip
	 * 
	 * @return Mac Address
	 * 
	 * 
	 */

	public static String getMacInWindows(final String ip) {

		String result = "";

		String[] cmd = {

		"cmd",

		"/c",

		"ping " + ip

		};

		String[] another = {

		"cmd",

		"/c",

		"arp -a"

		};

		String cmdResult = callCmd(cmd, another);

		result = filterMacAddress(ip, cmdResult, "-");

		return result;

	}

	/**
	 * 
	 * @param ip
	 *            目标ip
	 * 
	 * @return Mac Address
	 * 
	 * 
	 */

	public static String getMacInLinux(final String ip) {

		String result = "";

		String[] cmd = {

		"/bin/sh",

		"-c",

		"ping " + ip + " -c 2 && arp -a"

		};

		String cmdResult = callCmd(cmd);

		result = filterMacAddress(ip, cmdResult, ":");

		return result;

	}

	/**
	 * 
	 * 获取MAC地址
	 * 
	 * @return 返回MAC地址
	 */

	public static String getMacAddress(String ip) {

		String macAddress = "";

		macAddress = getMacInWindows(ip).trim();

		if (macAddress == null || "".equals(macAddress)) {

			macAddress = getMacInLinux(ip).trim();

		}

		return macAddress;

	}

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		System.out.println("通过ip获取mac地址为:" + getMacAddress("10.128.9.9"));
		long end = System.currentTimeMillis();
		System.out.println("获取电脑MAC地址时间为:" + (end - start));
	}

}
