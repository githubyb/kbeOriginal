package edu.nwpu.dmpm.kbe.common.util;

import java.util.Date;
import java.util.List;

import org.apache.regexp.RE;


/**
 * 字符串操作工具类
 * @author wangc
 *
 */
public class StringUtil {

	/**
	 * check string if is null or empty
	 * 
	 * @param strs
	 * @return
	 */
	public static boolean isNullOrEmpty(String... strs) {
		boolean flag = false;
		
		for(String s : strs) {
			if(s == null || "".equals(s.trim())) {
				flag = true;
				break;
			}
		}
		
		return flag; 
	}
	
	/*public static boolean isNullOrEmpty(String str) {
		boolean flag = false;
		if(str == null || "".equals(str.trim())) {
			flag = true;
		}
		return flag; 
	}*/
	
	/**
	 * combine a list data to a String connect by comma ","
	 * 
	 * @param datas
	 * @return
	 */
	public static String combineByComma(List datas) {
		
		if(datas == null) {
			return "";
		}
		
		StringBuffer sb = new StringBuffer();
		
		for(Object o : datas) {
			
			if(sb.length() != 0) {
				sb.append(",");
			}
			
			sb.append((String) o);
		}
		
		return sb.toString();
	}
	
	/**
	 * ========================  text filter  ========================
	 */
	
	public static String filterHtmlTag(String text) {
		
		return text.replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>").replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
	}
	
	
	/**
     * 获得对象描述字符串
     * @param obj
     * @return 字符串
     */
    public static String getString(Object obj){
    	if(obj!=null){
    		return obj.toString();
    	}else{
    		return null;
    	}
    }
    
    public static String decodeNull(String ojb1,String obj2){
    	if(ojb1==null||obj2.equals("")){
    		return obj2;
    	}else{
    		return ojb1;
    	}
    }
    
    public static boolean matches(String value, String regexp) {
        String paren = null;
        RE re = null;

        if (regexp == null) {
            return false;
        }
        
        if(isEmpty(value))
        	return true;
        
        re = new RE(regexp);
        if (!re.match(value)) {
            return false;
        }
        paren = re.getParen(0);
        if (!paren.equals(value)) {
            return false;
        }

        return true;
    }
    
    public static boolean isFloat(Object number){
    	String str = StringUtil.getString(number);
    	return StringUtil.matches(str, "^(\\d*\\.)?\\d+$");
    }
    
    public static int getInt(Object obj)
    {
    	if(obj!=null){
    		return Integer.parseInt(obj.toString());
    	}else{
    		return 0;
    	}
    }
    
    public static Date getDate(Object obj)
    {
    	if(obj!=null){
    			return (Date)(obj);
  
    	}else{
    		return null;
    	}

    }
    
    /**
     * 判断字符串中有无内容
     * @param str 字符串
     * @return 结果
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
    
    public static boolean getBoolean(Object booleanString)
    {
    	if(booleanString==null){
    		return false;
    	}
    	String booleanStringTemp=booleanString.toString();
    	return Boolean.parseBoolean(booleanStringTemp);
    }

	public static boolean isNotEmpty(String str) {
		
		if(str==null||str.equals("")){
			return false;
		}
		else{
			return true;
		}
	}
	

	
	
	public static boolean verifySameValueToUpper(String valueA,String valueB){
		boolean verifyValue=valueA.toUpperCase().equals(valueB.toUpperCase());
		return verifyValue;
	}
}
