package edu.nwpu.dmpm.kbe.db;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
@Repository
public class MPMJPADaoImpl implements MPMJPADao{
	
	@PersistenceContext(unitName="mpm")
	protected EntityManager em;
	
	public void clear(){
		em.clear();
	}
	public <T> void delete(Class<T> entityClass,Object entityid) {
		delete(entityClass, new Object[]{entityid});
	}
	public <T> void delete(Class<T> entityClass,Object[] entityids) {
		for(Object id : entityids){
			em.remove(em.getReference(entityClass, id));
		}
	}
	public <T> T find(Class<T> entityClass, Object entityId) {
		return em.find(entityClass, entityId);
	}
	public void save(Object entity) {
		em.persist(entity);
	}
	public void delete(Object entity){
		em.remove(entity);
	}
	public <T> long getCount(Class<T> entityClass) {
		return (Long)em.createQuery("select count("+ getCountField(entityClass) +") from "+ getEntityName(entityClass)+ " o").getSingleResult();
	}
	
	public void update(Object entity) {
		em.merge(entity);
	}
	
	public <T> QueryResult<T> getScrollData(Class<T> entityClass,
			int firstindex, int maxresult, LinkedHashMap<String, String> orderby) {
		return getScrollData(entityClass,firstindex,maxresult,null,null,orderby);
	}
	
	public <T> QueryResult<T> getScrollData(Class<T> entityClass,
			int firstindex, int maxresult, String wherejpql, Object[] queryParams) {
		return getScrollData(entityClass,firstindex,maxresult,wherejpql,queryParams,null);
	}
	
	public <T> QueryResult<T> getScrollData(Class<T> entityClass, int firstindex, int maxresult) {
		return getScrollData(entityClass,firstindex,maxresult,null,null,null);
	}
	
	public <T> QueryResult<T> getScrollData(Class<T> entityClass) {
		return getScrollData(entityClass, -1, -1);
	}
	@SuppressWarnings("unchecked")
	public <T> QueryResult<T> getScrollData(Class<T> entityClass, int firstindex, int maxresult
			, String wherejpql, Object[] queryParams,LinkedHashMap<String, String> orderby) {
		QueryResult qr = new QueryResult<T>();
		String entityname = getEntityName(entityClass);
		Query query = em.createQuery("select o from "+ entityname+ " o "+(wherejpql==null? "": "where "+ wherejpql)+ buildOrderby(orderby));
		setQueryParams(query, queryParams);
		if(firstindex!=-1 && maxresult!=-1) query.setFirstResult(firstindex).setMaxResults(maxresult);
		qr.setResultlist(query.getResultList());
		query = em.createQuery("select count("+ getCountField(entityClass)+ ") from "+ entityname+ " o "+(wherejpql==null? "": "where "+ wherejpql));
		setQueryParams(query, queryParams);
		qr.setTotalrecord((Long)query.getSingleResult());
		return qr;
	}
	
	protected void setQueryParams(Query query, Object[] queryParams){
		if(queryParams!=null && queryParams.length>0){
			for(int i=0; i<queryParams.length; i++){
				query.setParameter(i+1, queryParams[i]);
			}
		}
	}
	/**
	 * 组装order by语句
	 * @param orderby
	 * @return
	 */
	protected String buildOrderby(LinkedHashMap<String, String> orderby){
		StringBuffer orderbyql = new StringBuffer("");
		if(orderby!=null && orderby.size()>0){
			orderbyql.append(" order by ");
			for(String key : orderby.keySet()){
				orderbyql.append("o.").append(key).append(" ").append(orderby.get(key)).append(",");
			}
			orderbyql.deleteCharAt(orderbyql.length()-1);
		}
		return orderbyql.toString();
	}
	/**
	 * 获取实体的名称
	 * @param <T>
	 * @param entityClass 实体类
	 * @return
	 */
	protected <T> String getEntityName(Class<T> entityClass){
		String entityname = entityClass.getSimpleName();
		Entity entity = entityClass.getAnnotation(Entity.class);
		if(entity.name()!=null && !"".equals(entity.name())){
			entityname = entity.name();
		}
		return entityname;
	}
	
	protected <T> String getCountField(Class<T> clazz){
		String out = "o";
		try {
			PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(clazz).getPropertyDescriptors();
			for(PropertyDescriptor propertydesc : propertyDescriptors){
				Method method = propertydesc.getReadMethod();
				if(method!=null && method.isAnnotationPresent(EmbeddedId.class)){					
					PropertyDescriptor[] ps = Introspector.getBeanInfo(propertydesc.getPropertyType()).getPropertyDescriptors();
					out = "o."+ propertydesc.getName()+ "." + (!ps[1].getName().equals("class")? ps[1].getName(): ps[0].getName());
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
        return out;
	}
	
	public Session getHibernateSession(){
		Session session=em.unwrap(org.hibernate.Session.class);
		return session;
	}
}
