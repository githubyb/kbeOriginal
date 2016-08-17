package edu.nwpu.dmpm.kbe.common.util;

import edu.nwpu.dmpm.kbe.common.pageModel.Page;
import edu.nwpu.dmpm.kbe.system.pageModel.PageHelper;

public class PageUtil {

	public static int[] init(Page<?> page, PageHelper ph) {
		int pageNumber = ph.getPage();
		page.setPageNo(pageNumber);
		if (page.getPageSize() == -1) {
			int pageSize = ph.getRows();
			page.setPageSize(pageSize);
		}
		int firstResult = page.getFirst() - 1;
		int maxResults = page.getPageSize();
		return new int[] { firstResult, maxResults };
	}
}
