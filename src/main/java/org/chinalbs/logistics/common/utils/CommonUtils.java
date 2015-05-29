package org.chinalbs.logistics.common.utils;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.chinalbs.logistics.cache.DictMapper;
import org.chinalbs.logistics.common.domain.BaseDict;

public class CommonUtils {

    @SuppressWarnings("rawtypes")
    public static void fatherToChild (Object father,Object child)throws Exception{  
        if(!(child.getClass().getSuperclass()==father.getClass())){  
            throw new Exception("child不是father的子类");  
        }  
        Class childClass= child.getClass();
		Class fatherClass= father.getClass();
		Method[] methods = fatherClass.getMethods();
        for(int i=0;i<methods.length;i++){  
            if (!methods[i].getName().startsWith("get") || methods[i].getName().equals("getClass")) {
            	continue;
            }
            Object obj=methods[i].invoke(father);//取出属性值  
            @SuppressWarnings("unchecked")
            Method setMethod = childClass.getMethod(methods[i].getName().replaceAll("get", "set"),
            		methods[i].getReturnType());
            setMethod.invoke(child, obj);
        }  
    } 
    
    
    public static Predicate convertValue(Class<?> classParam, String propertyName, Object value, Root<?> root, CriteriaBuilder cb, Predicate p) {
    	Map<String, List<?>> dicMapper = DictMapper.getInstance().getDictContent();
    	if (classParam.getName().equals("org.chinalbs.logistics.domain.Goods")) {
    		if(propertyName.equals("volume")) {
    			List<?> listObj = dicMapper.get("DictGoodsVolume");
    			p = predicateJoin(classParam, listObj, propertyName, value, root, cb, p);
    		}
    		else if(propertyName.equals("weight")) {
    			List<?> listObj = dicMapper.get("DictGoodsWeight");
    			p = predicateJoin(classParam, listObj, propertyName, value, root, cb, p);
    		}
       		else if(propertyName.equals("publishTime")) {
        		Path<Date> path2 = root.get(propertyName); 
            	if (p == null) {
                	p = cb.lessThanOrEqualTo(path2, (Date)value);
            	}
            	else {
            		p = cb.and(p,cb.lessThanOrEqualTo(path2, (Date)value));
            	}          		
        	}
        	else {
        		Path<Date> path = root.get(propertyName);
            	if (p == null) {
                	p = cb.equal(path, value);
            	}
            	else {
            		p = cb.and(p,cb.equal(path, value));
            	}	
        	}
    	}
    	else if(classParam.getName().equals("org.chinalbs.logistics.domain.Warehouse")){
    		if(propertyName.equals("area")) {
    			List<?> listObj = dicMapper.get("DictWarehouseArea");
    			p = predicateJoin(classParam, listObj, propertyName, value, root, cb, p);
    		} 
    		else if (propertyName.equals("volume")) {
    			List<?> listObj = dicMapper.get("DictWarehouseVolume");
    			p = predicateJoin(classParam, listObj, propertyName, value, root, cb, p);   			
    		}
    		else if(propertyName.equals("publishTime")) {
        		Path<Date> path2 = root.get(propertyName); 
            	if (p == null) {
                	p = cb.lessThanOrEqualTo(path2, (Date)value);
            	}
            	else {
            		p = cb.and(p,cb.lessThanOrEqualTo(path2, (Date)value));
            	}          		
        	}
        	else {
        		Path<Date> path = root.get(propertyName);
            	if (p == null) {
                	p = cb.equal(path, value);
            	}
            	else {
            		p = cb.and(p,cb.equal(path, value));
            	}	
        	}
    	}
    	else if(classParam.getName().equals("org.chinalbs.logistics.domain.Truck")){
    		if(propertyName.equals("truckLength")) {
    			List<?> listObj = dicMapper.get("DictTruckLength");
    			p = predicateJoin(classParam, listObj, propertyName, value, root, cb, p);
    		} 
    		else if (propertyName.equals("truckLoad")) {
    			List<?> listObj = dicMapper.get("DictTruckLoad");
    			p = predicateJoin(classParam, listObj, propertyName, value, root, cb, p);   			
    		}
    		else if(propertyName.equals("publishTime")) {
        		Path<Date> path2 = root.get(propertyName); 
            	if (p == null) {
                	p = cb.lessThanOrEqualTo(path2, (Date)value);
            	}
            	else {
            		p = cb.and(p,cb.lessThanOrEqualTo(path2, (Date)value));
            	}          		
        	}
        	else {
        		Path<Date> path = root.get(propertyName);
            	if (p == null) {
                	p = cb.equal(path, value);
            	}
            	else {
            		p = cb.and(p,cb.equal(path, value));
            	}	
        	}
    	}    	
    	else if(classParam.getName().equals("org.chinalbs.logistics.vo.TruckPlanInfo")){
    		if(propertyName.equals("publishTime")) {
        		Path<Date> path2 = root.get(propertyName); 
            	if (p == null) {
                	p = cb.lessThanOrEqualTo(path2, (Date)value);
            	}
            	else {
            		p = cb.and(p,cb.lessThanOrEqualTo(path2, (Date)value));
            	}          		
        	}
        	else {
        		Path<Date> path = root.get(propertyName);
            	if (p == null) {
                	p = cb.equal(path, value);
            	}
            	else {
            		p = cb.and(p,cb.equal(path, value));
            	}	
        	}
    	}

    	return p;
    }
    
    private  static Predicate  predicateJoin(Class<?> classParam, List<?> listObj, String propertyName, Object value, Root<?> root, CriteriaBuilder cb, Predicate p) {
        for (int i = 0; i<listObj.size(); i++) {
			BaseDict<Long>  objTmp = (BaseDict<Long>)listObj.get(i);
            if (Double.parseDouble(value.toString()) == objTmp.getCode()) {
            	String[] finalValue =  objTmp.getName().split("-");
            	if (finalValue.length == 1) {
            		Path<Double> path2 = root.get(propertyName); 
	            	if (p == null) {
	                	p = cb.greaterThanOrEqualTo(path2, Double.parseDouble(finalValue[0]));
	            	}
	            	else {
	            		p = cb.and(p,cb.greaterThanOrEqualTo(path2, Double.parseDouble(finalValue[0])));
	            	}          		
            	}
            	else {
            		Path<Double> path2 = root.get(propertyName); 
	            	if (p == null) {
	                	p = cb.greaterThanOrEqualTo(path2, Double.parseDouble(finalValue[0]));
	            	}
	            	else {
	            		p = cb.and(p,cb.greaterThanOrEqualTo(path2, Double.parseDouble(finalValue[0])));
	            	} 
	            	p = cb.and(p,cb.lessThanOrEqualTo(path2, Double.parseDouble(finalValue[1])));
	            	
            	}
            	break;
            }
        }
        return p;
    }
    
    
	
}
