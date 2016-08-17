package edu.nwpu.dmpm.kbe.common.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 集合工具类
 * @author wangc
 *
 */
public class CollectionUtil {

	public static Set convertListToSet(List list) {
		Set set = new HashSet();

		for(Object o : list) {
			set.add(o);
		}
		
		return set;
	}
}
