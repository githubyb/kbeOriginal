package edu.nwpu.dmpm.kbe.db;

import java.util.List;

public class QueryResult<T> {
	 /** 获得结果集 **/ 
    private List<T> resultlist; 
    /** 获得总的记录数 **/ 
    private long totalrecord; 
     
    public List<T> getResultlist() { 
        return resultlist; 
    } 
    public void setResultlist(List<T> resultlist) { 
        this.resultlist = resultlist; 
    } 
    public long getTotalrecord() { 
        return totalrecord; 
    } 
    public void setTotalrecord(long totalrecord) { 
        this.totalrecord = totalrecord; 
    } 
}
