package org.chinalbs.logistics.cache;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.chinalbs.logistics.service.DictService;
import org.springframework.stereotype.Component;

@Component
public class DictMapper {

    private static DictService dictService;

    @Resource
    public void setDictService(DictService dictService) {
    	DictMapper.dictService = dictService;
    }

    public static DictService getDictService() {
        return dictService;
    }
    
    
	private static DictMapper instance;
	private Map<String, List<?>> dictContent;
	private long lastUpdateTime; 
	private DictMapper() {		
	}

	public static synchronized DictMapper getInstance() {
		if (null == instance) {
			instance = new DictMapper();
			instance.init();	
		}
		return instance;
	}
	
	public  void init() {
		if (dictContent == null) {
			//DictService dictService = SpringFactory.getBean("dictService");
			dictContent = dictService.findAllDict();
			setLastUpdateTime(new Date().getTime());
		}
	}

	public Map<String, List<?>> getDictContent() {
		return dictContent;
	}

	public void setDictContent(Map<String, List<?>> dictContent) {
		this.dictContent = dictContent;
	}

	public long getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(long lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	
}
