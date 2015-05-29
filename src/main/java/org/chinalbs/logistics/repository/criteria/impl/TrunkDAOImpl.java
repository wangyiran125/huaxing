package org.chinalbs.logistics.repository.criteria.impl;


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.chinalbs.logistics.common.utils.CommonUtils;
import org.chinalbs.logistics.common.utils.ListSlice;
import org.chinalbs.logistics.domain.Truck;
import org.chinalbs.logistics.domain.TruckPlan;
import org.chinalbs.logistics.repository.criteria.AbstractJpaDao;
import org.chinalbs.logistics.repository.criteria.TruckDAO;
import org.chinalbs.logistics.utils.Consts;
import org.chinalbs.logistics.vo.TruckPlanInfo;
import org.chinalbs.logistics.vo.TruckViewInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;


@Repository
public class TrunkDAOImpl extends AbstractJpaDao<TruckPlanInfo> implements TruckDAO{
	private static final Logger logger = LoggerFactory.getLogger(TrunkDAOImpl.class);
    
	public TrunkDAOImpl() {
		super(TruckPlanInfo.class);
	}
	
    public ListSlice<TruckViewInfo> findRange4Search(TruckPlanInfo obj, int[] range) {
    	return findRange4(obj, range);
    }
    
    /*
     * 根据输入条件进行 车源 的检索
     */
    public ListSlice<TruckViewInfo> findRange4(TruckPlanInfo obj, int[] range)  {	
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<?> cq = cb.createQuery();
        
        //Truck 和 TruckPlan联合查询
        Root<TruckPlan> truckPlan = cq.from(TruckPlan.class);
        Root<Truck> truck = cq.from(Truck.class); 
        cq.multiselect(truckPlan, truck);
        Predicate p = cb.equal(truckPlan.get("truckId"), truck.get("id"));
        
        //增加 truck满载/删除不出现在载货列表中
        Path<Integer> path = truck.get("truckStatus"); 
        p = cb.and(p,cb.lessThan(path, Consts.Truck.FULL));
        Path<Integer> path2 = truck.get("isDeleted"); 
        p = cb.and(p,cb.notEqual(path2, Consts.DELETED));
        
        //删除的车源不出现在列表中
        Path<Integer> pathTruckPlan = truckPlan.get("isDeleted"); 
        p = cb.and(p,cb.notEqual(pathTruckPlan, Consts.DELETED));
        
        
        p = getQuery(TruckPlanInfo.class, obj, truckPlan,cb , p);
        p = getQuery(Truck.class, obj, truck,cb , p);
        if (p != null) {
        	cq.where(p);
        }
        //车源按发布时间倒序
        cq.orderBy(cb.desc(truckPlan.get("publishTime")));
        //获得检索的总数量
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        long total = q.getResultList().size(); 
        
        //获得指定区间的检索数据
        q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1]);
        q.setFirstResult(range[0]);
        List<?> listObj= q.getResultList();
        
        //拆分数据，组合返回类型
        List<TruckViewInfo> listTruckViewInfo = new ArrayList<TruckViewInfo> ();
        for (int i = 0; i<listObj.size(); i++) {
            Object[]  objTmp = (Object[])listObj.get(i);
        	TruckViewInfo truckViewInfo = new TruckViewInfo();
        	truckViewInfo.setTruckPlan((TruckPlan)objTmp[0]);
        	truckViewInfo.setTruck((Truck)objTmp[1]);
        	listTruckViewInfo.add(truckViewInfo);
        }

        return new ListSlice<TruckViewInfo>(total,listTruckViewInfo);
    }
    
    private Predicate getQuery(Class<?> classa, TruckPlanInfo obj, Root<?> root, CriteriaBuilder cb, Predicate p ) {
        
    	//TODO 将来可以考虑使用getMethod方法
    	Method[] methods = classa.getDeclaredMethods(); 
        for(int i=0;i<methods.length;i++){
        	// set方法忽略
            if (!methods[i].getName().startsWith("get")) {
            	continue;
            }
            
            //取出属性值 
            Object value = null;
			try {
				value = methods[i].invoke(obj);
			} catch (Exception e) {
				logger.error("Exception = " + e.getMessage());
				e.printStackTrace();
			} 
            Class<?> returnType = methods[i].getReturnType();
            String propertyName = "";
            
            //忽略 int 和 long的默认值
            if (((returnType.getName().equals("int") && (Integer)value == 0) || 
            		(returnType.getName().equals("long") && (Long)value == 0l) ||
            		(returnType.getName().equalsIgnoreCase("double") && (Double)value == 0.0f))) {
            }
            else if (value != null) {
            	propertyName = lowerHeadChar(methods[i].getName().substring(3));
            	if (returnType.getName().equals("int")) {
            		p = CommonUtils.convertValue(classa, propertyName, Integer.parseInt(value.toString()), root, cb, p);
            	}
            	if (returnType.getName().equalsIgnoreCase("double")) {
            		p = CommonUtils.convertValue(classa, propertyName, Double.parseDouble(value.toString()), root, cb, p);
            	}
            }
        }  
        return p;
    }
    
    private String lowerHeadChar(String in){  
        String head=in.substring(0,1);  
        String out=head.toLowerCase()+in.substring(1,in.length());  
        return out;  
    }  
	
}
