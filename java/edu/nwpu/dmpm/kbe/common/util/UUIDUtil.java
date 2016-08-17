package edu.nwpu.dmpm.kbe.common.util;

import java.util.UUID;


/**
 * @author wangc
 * UUID工具类
 *
 */
public class UUIDUtil {

	public static String getUUID(){
		
		return UUID.randomUUID().toString();
	}
}
