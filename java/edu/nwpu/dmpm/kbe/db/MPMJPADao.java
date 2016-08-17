package edu.nwpu.dmpm.kbe.db;

import java.util.LinkedHashMap;

import org.hibernate.Session;
public interface MPMJPADao {
	/**
	 * 获取记录总数
	 * @param entityClass 实体类
	 * @return
	 */
	public <T> long getCount(Class<T> entityClass);
	/**
	 * 清除一级缓存的数据
	 */
	public void clear();
	/**
	 * 保存实体
	 * @param entity 实体id
	 */
	public void save(Object entity);
	/**
	 * 更新实体
	 * @param entity 实体id
	 */
	public void update(Object entity);
	
	public void delete(Object entity);
	/**
	 * 删除实体
	 * @param entityClass 实体类
	 * @param entityid 实体id
	 */
	public <T> void delete(Class<T> entityClass, Object entityid);
	/**
	 * 删除实体
	 * @param entityClass 实体类
	 * @param entityids 实体id数组
	 */
	public <T> void delete(Class<T> entityClass, Object[] entityids);
	/**
	 * 获取实体
	 * @param <T>
	 * @param entityClass 实体类
	 * @param entityId 实体id
	 * @return
	 */
	public <T> T find(Class<T> entityClass, Object entityId);
	/**
	 * 获取分页数据
	 * @param <T>
	 * @param entityClass 实体类
	 * @param firstindex 开始索引
	 * @param maxresult 需要获取的记录数
	 * @return
	 */
	public <T> QueryResult<T> getScrollData(Class<T> entityClass, int firstindex, int maxresult
			, String wherejpql, Object[] queryParams,LinkedHashMap<String, String> orderby);
	
	public <T> QueryResult<T> getScrollData(Class<T> entityClass, int firstindex, int maxresult
			, String wherejpql, Object[] queryParams);
	
	public <T> QueryResult<T> getScrollData(Class<T> entityClass, int firstindex, int maxresult
			, LinkedHashMap<String, String> orderby);
	
	public <T> QueryResult<T> getScrollData(Class<T> entityClass, int firstindex, int maxresult);
	
	public <T> QueryResult<T> getScrollData(Class<T> entityClass);
	
	public Session getHibernateSession();
}

