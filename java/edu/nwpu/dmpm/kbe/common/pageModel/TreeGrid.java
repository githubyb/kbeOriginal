package edu.nwpu.dmpm.kbe.common.pageModel;

import java.util.ArrayList;
import java.util.List;

/*
 * EasyUI TreeGrid模型
 * 
 *@author: ZhuJZ
 *@date: 2014年6月4日
 */
public class TreeGrid implements java.io.Serializable {

	private Long total = 0L;
	private List rows = new ArrayList();

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}
}
