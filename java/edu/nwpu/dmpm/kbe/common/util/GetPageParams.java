package edu.nwpu.dmpm.kbe.common.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/*
 *@author Fanl
 *@Date 2014-6-18
 */
public class GetPageParams {

	public static Map<String, Object> GetParams(HttpServletRequest request) {
		Map<String, Object> filter = new HashMap<String, Object>();
		try {
			Enumeration enu = request.getParameterNames();
			while (enu.hasMoreElements()) {
				Object tmp = enu.nextElement();
				Object obj = request.getParameter(StringUtil.getString(tmp));
				// if (request.getAttribute("ISGET") != null)
				//obj = new String(obj.toString().getBytes("ISO8859-1"), "utf-8");
				filter.put(StringUtil.getString(tmp), obj);
			}
			Enumeration attenums = request.getAttributeNames();
			while (attenums.hasMoreElements()) {
				String paramName = (String) attenums.nextElement();
				Object paramValue = request.getAttribute(paramName);
				// 形成键值对应的map
				filter.put(paramName, paramValue);
			}
			//UnsupportedEncodingException e
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filter;
	}
}
