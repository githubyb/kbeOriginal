package edu.nwpu.dmpm.kbe.common.util;

import java.util.List;


/**
 * @author wangc
 * 断言工具类，提供一些空值判断方法
 *
 */
public class AssertUtil {
	
	/**
	 * str值为null或空返回true
	 * @param str
	 * @return boolean 
	 */
	public static boolean assertNull(String str){
		if(null==str || str.trim().length()==0){
			return true;
		}else {
			return false;
		}
	}
	
	public static boolean assertNull(List<?> list){
		if(null==list || list.size()==0){
			return true;
		}else {
			return false;
		}
	}
	
	public static boolean assertNull(Object object){
		if(object ==null){
			return true;
		} else {
			return false;
		}
	}
}
