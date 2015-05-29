package org.chinalbs.logistics.common.utils;

import java.io.Serializable;
import java.util.Collection;

import org.chinalbs.logistics.common.domain.BaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.BeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;

/**
 * 忽略jpa返回的未装载的lazy属性
 * 
 * copied from jackson source code
 * 
 */
public class ProxyExclusionPropertyFilter implements Serializable,
		BeanPropertyFilter {

	private static final long serialVersionUID = -510594860443468958L;

	private static Logger logger = LoggerFactory.getLogger(ProxyExclusionPropertyFilter.class);
	
	/**
	 * Method called to determine whether property will be included (if 'true'
	 * returned) or filtered out (if 'false' returned)
	 */
	protected boolean include(BeanPropertyWriter writer) {
		return true;
	}

	@Override
	public void serializeAsField(Object bean, JsonGenerator jgen,
			SerializerProvider provider, BeanPropertyWriter writer)
			throws Exception {
		Object value = writer.get(bean);
		if (value != null) {
			Class<?> valueType = value.getClass();
			if (valueType.getPackage().getName().contains("hibernate") || valueType.getSimpleName().indexOf("_$$_") >= 0) {
				try {
					if (value instanceof Collection) {
						((Collection<?>)value).size();
					} else if (value instanceof BaseEntity<?>) {
						((BaseEntity<?>)value).getId();
					} else {
						logger.error("found a type not been handled, type is " + valueType.getCanonicalName());
					}
				} catch (Exception e) {
					return;
				}
			}
		}
		if (include(writer)) {
			writer.serializeAsField(bean, jgen, provider);
		}
	}

	@Override
	public void depositSchemaProperty(BeanPropertyWriter writer,
			ObjectNode propertiesNode, SerializerProvider provider)
			throws JsonMappingException {
		if (include(writer)) {
			writer.depositSchemaProperty(propertiesNode, provider);
		}
	}

	@Override
	public void depositSchemaProperty(BeanPropertyWriter writer,
			JsonObjectFormatVisitor objectVisitor, SerializerProvider provider)
			throws JsonMappingException {
		if (include(writer)) {
			writer.depositSchemaProperty(objectVisitor);
		}
	}

}