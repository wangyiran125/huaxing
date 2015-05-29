package org.chinalbs.logistics.repository.criteria;

import java.lang.reflect.Method;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.chinalbs.logistics.common.domain.BaseEntity;
import org.chinalbs.logistics.common.utils.CommonUtils;
import org.chinalbs.logistics.common.utils.ListSlice;
import org.chinalbs.logistics.utils.Consts;

public abstract class AbstractJpaDao<T extends BaseEntity> implements EntityDao<T> {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @PersistenceContext
    protected EntityManager entityManager;

    private final Class<T> entityClass;

    protected AbstractJpaDao(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    // override this method to provide a different entityManager in sub-class
    protected EntityManager getEntityManager() {
//    	logger.debug("EM : " + this.entityManager.getFlushMode());
        return this.entityManager;
    }


    public void flush() {
    	getEntityManager().flush();
    }
    /*
     * (non-Javadoc)
     * 
     */
    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    /*
     * (non-Javadoc)
     * 
     */
    public void update(T entity) {
        getEntityManager().merge(entity);
    }

    /*
     * (non-Javadoc)
     * 
     */
    public void delete(T entity) {
        getEntityManager().remove(find(entity.getId()));
    }

    /*
     * (non-Javadoc)
     * 
     */
    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<T> findRange(int[] range) {
        @SuppressWarnings("rawtypes")
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
  
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }
    
    @SuppressWarnings("unchecked")
    public ListSlice<T> findRange(T obj, int[] range)  {
        @SuppressWarnings("rawtypes")
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<T> root = cq.from(entityClass); 
        cq.select(root);
        Method[] methods = entityClass.getDeclaredMethods(); 
        Predicate p = null;
        //删除的货物不出现在列表中
        Path<Integer> path2= root.get("isDeleted"); 
        if (path2 != null) {
        	p = cb.notEqual(path2, Consts.DELETED);
        }
        
        for(int i=0;i<methods.length;i++){  
            if (!methods[i].getName().startsWith("get")) {
            	continue;
            }
            Object value = null;
			try {
				value = methods[i].invoke(obj);
			} catch (Exception e) {

				e.printStackTrace();
			}//取出属性值  
            Class returnType = methods[i].getReturnType();
            String propertyName = "";
            String ss = returnType.getName();
            if (((returnType.getName().equals("int") && (Integer)value == 0) || 
            		(returnType.getName().equals("long") && (Long)value == 0l)) ||
            		(returnType.getName().equals("double") && (Double)value == 0.0)) {
            }
            else if (value != null) {
            	propertyName = lowerHeadChar(methods[i].getName().substring(3));
            	Path<String> path = root.get(propertyName); 
            	if (returnType.getName().equals("int") || propertyName.equals("createTime")) {
            		p = CommonUtils.convertValue(obj.getClass(), propertyName, Integer.parseInt(value.toString()), root, cb, p);
            	}
            }
        }  
        if (p != null) {
        	cq.where(p);
        }
        //按照发布时间倒序排序
        cq.orderBy(cb.desc(root.get("createTime")));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        long total = q.getResultList().size(); 
        q = getEntityManager().createQuery(cq);
        
        q.setMaxResults(range[1]);
        q.setFirstResult(range[0]);
        return new ListSlice<T>(total,q.getResultList());
    }
    
    private String lowerHeadChar(String in){  
        String head=in.substring(0,1);  
        String out=head.toLowerCase()+in.substring(1,in.length());  
        return out;  
    }  

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    protected T findByUniqueProperty(String namedQuery, String propertyName, Object propertyValue) {
        return findByUniqueProperties(namedQuery, new Property(propertyName, propertyValue));
    }

    protected T findByUniqueProperty(String namedQuery, Object propertyValue) {
        return findByUniqueProperties(namedQuery, propertyValue);
    }

    @SuppressWarnings("unchecked")
    protected T findByUniqueProperties(String namedQuery, Object... propertyValues) {
        try {
            return (T) createNamedQuery(namedQuery, propertyValues).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    protected T findByUniqueProperties(String namedQuery, Property... properties) {
        return findByUniqueProperties(namedQuery, (Object[]) properties);
    }

    protected List<T> findByProperty(String namedQuery, String propertyName, Object propertyValue) {
        return findByProperties(namedQuery, new Property(propertyName, propertyValue));
    }

//    protected List<T> findByProperty(String namedQuery, String propertyName, int startPosition, int maxResult, Object propertyValue) {
//        return findByProperties(namedQuery, startPosition, maxResult, new Property(propertyName, propertyValue));
//    }
//    
    protected List<T> findByProperty(String namedQuery, Object propertyValue) {
        return findByProperties(namedQuery, propertyValue);
    }

//    protected List<T> findByProperty(String namedQuery, int startPosition, int maxResult, Object propertyValue) {
//        return findByProperties(namedQuery, startPosition, maxResult, propertyValue);
//    }
    
    @SuppressWarnings("unchecked")
    protected List<T> findByProperties(String namedQuery, Object... propertyValues) {
        return createNamedQuery(namedQuery, propertyValues).getResultList();
    }
    
    @SuppressWarnings("unchecked")
    protected List<T> findByProperties(String namedQuery, int startPosition, int maxResult, Object... propertyValues) {
        return createNamedQuery(namedQuery, startPosition, maxResult, propertyValues).getResultList();
    }
    
    @SuppressWarnings({ "hiding", "unchecked" })
	protected <T> List<T> findObjectsByProperties(String namedQuery, int startPosition, int maxResult, Object... propertyValues) {
        return createNamedQuery(namedQuery, startPosition, maxResult, propertyValues).getResultList();
    }
    
    @SuppressWarnings({ "hiding", "unchecked" })
	protected <T> List<T> findObjectsByProperties(String namedQuery, Object... propertyValues) {
        return createNamedQuery(namedQuery, propertyValues).getResultList();
    }
    
	protected Object findObjectByProperties(String namedQuery, Object... propertyValues) {
        return createNamedQuery(namedQuery, propertyValues).getSingleResult();
    }
    
    protected List<T> findByProperties(String namedQuery, Property... properties) {
        return findByProperties(namedQuery, (Object[]) properties);
    }

    protected int countByProperty(String namedQuery, String propertyName, Object propertyValue) {
        return countByProperties(namedQuery, new Property(propertyName, propertyValue));
    }

    protected int countByProperty(String namedQuery, Object propertyValue) {
        return countByProperties(namedQuery, propertyValue);
    }

    protected int countByProperties(String namedQuery, Property... properties) {
        return countByProperties(namedQuery, (Object[]) properties);
    }

    protected int countByProperties(String namedQuery, Object... propertyValues) {
        Long count = (Long) createNamedQuery(namedQuery, propertyValues).getSingleResult();
        return count.intValue();
    }

    private Query createNamedQuery(String namedQuery, Object... propertyValues) {

        return createNamedQuery(namedQuery, -1, -1, propertyValues);
    }

    private Query createNamedQuery(String namedQuery, int startPosition, int maxResult, Object... propertyValues) {
        Query query = getEntityManager().createNamedQuery(namedQuery);
        int i = 1;
        for (Object propertyValue : propertyValues) {
            if (propertyValue instanceof Property) {
                Property property = (Property) propertyValue;
                query.setParameter(property.name, property.value);
            } else {
                query.setParameter(i, propertyValue);
            }
            i++;
        }
        if (startPosition >=0 && maxResult > 0) {
            query.setFirstResult(startPosition);
            query.setMaxResults(maxResult);            
        }
        return query;
    }
    
    public int executeUpdate(String namedQuery, Object... propertyValues) {
        Query query = createNamedQuery(namedQuery, -1, -1, propertyValues);
        return query.executeUpdate();
    }
    
    protected static Property p(String name, Object value) {
        return new Property(name, value);
    }
    
    public static class Property {

        public String name;

        public Object value;

        public Property(String name, Object value) {
            this.name = name;
            this.value = value;
        }
    }
}