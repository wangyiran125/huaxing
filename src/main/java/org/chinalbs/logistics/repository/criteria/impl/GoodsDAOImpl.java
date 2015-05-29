package org.chinalbs.logistics.repository.criteria.impl;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.chinalbs.logistics.common.utils.CommonUtils;
import org.chinalbs.logistics.common.utils.ListSlice;
import org.chinalbs.logistics.domain.Goods;
import org.chinalbs.logistics.domain.LogisticsOrder;
import org.chinalbs.logistics.repository.criteria.AbstractJpaDao;
import org.chinalbs.logistics.repository.criteria.GoodsDAO;
import org.chinalbs.logistics.utils.Consts;
import org.springframework.stereotype.Repository;


@Repository
public class GoodsDAOImpl extends AbstractJpaDao<Goods> implements GoodsDAO{

	public GoodsDAOImpl() {
		super(Goods.class);
	}
	
    public ListSlice<Goods> findRange4Search(Goods obj, int[] range) {
    	
    	  CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
          CriteriaQuery<?> cq = cb.createQuery();
          
          //goods和order联合查询
          Root<Goods> goods = cq.from(Goods.class);
          Root<LogisticsOrder> order = cq.from(LogisticsOrder.class); 
          cq.multiselect(goods, order);
          Predicate p = cb.equal(goods.get("id"), order.get("goodsId"));
          
          //只有发布和已抢单的货物出现在检索列表， 达成运输意向的不显示。
          Path<Integer> path = order.get("status"); 
          p = cb.and(p,cb.lessThanOrEqualTo(path, Consts.Order.STATUS_ORDER_INTENT));
          
          //超过有效期的货物过滤掉
          Path<String> path_validity = goods.get("validity");
          p = cb.and(p, cb.or(cb.greaterThanOrEqualTo(path_validity, formatDate(new Date())), cb.lessThan(path_validity, "0")));
          
//          p = cb.and(p,cb.)
          //删除的货物不出现在列表中
          Path<Integer> path2= goods.get("isDeleted"); 
          p = cb.and(p,cb.notEqual(path2, Consts.DELETED));
          
          p = getQuery(Goods.class, obj, goods, cb , p);
          if (p != null) {
          	cq.where(p);
          }
          //货源按发布时间倒序
          cq.orderBy(cb.desc(goods.get("publishTime")));
          javax.persistence.Query q = getEntityManager().createQuery(cq);
          long total = q.getResultList().size(); 
          
          q = getEntityManager().createQuery(cq);
          q.setMaxResults(range[1]);
          q.setFirstResult(range[0]);
          
          List<Goods> listGoodsInfo = new ArrayList<Goods> ();
          List<?> listObj= q.getResultList();
          for (int i = 0; i<listObj.size(); i++) {
              Object[]  objTmp = (Object[])listObj.get(i);
              listGoodsInfo.add((Goods)objTmp[0]);
          }
          return new ListSlice<Goods>(total,listGoodsInfo);
    }
    
    private Predicate getQuery(Class<?> classa, Goods obj, Root<?> root, CriteriaBuilder cb, Predicate p ) {
        Method[] methods = classa.getDeclaredMethods(); 
        for(int i=0;i<methods.length;i++){  
            if (!methods[i].getName().startsWith("get")) {
            	continue;
            }
            Object value = null;
			try {
				value = methods[i].invoke(obj);
			} catch (Exception e) {

				e.printStackTrace();
			} 
            Class<?> returnType = methods[i].getReturnType();
            String propertyName = "";
            if (((returnType.getName().equals("int") && (Integer)value == 0) || 
            		(returnType.getName().equals("long") && (Long)value == 0l) ||
            		(returnType.getName().equalsIgnoreCase("double") && (Double)value == 0.0f))) {
            }
            else if (value != null) {
            	propertyName = lowerHeadChar(methods[i].getName().substring(3));
				if (returnType.getName().equalsIgnoreCase("double")
						|| returnType.getName().equals("int")
						|| propertyName.equals("publishTime")) {
					p = CommonUtils.convertValue(classa, propertyName, value,
							root, cb, p);
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
    
    private String formatDate(Date date){
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    	return df.format(date);
    }
	
}
