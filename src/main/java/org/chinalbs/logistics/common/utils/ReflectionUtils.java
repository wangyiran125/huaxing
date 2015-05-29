package org.chinalbs.logistics.common.utils;

import java.lang.reflect.Method;

public class ReflectionUtils {
	/**
	 * 获得method对象的一个简单字符串描述，不能保证唯一性
	 * @param method
	 * @return
	 */
	public static String toSimpleSignature(Method method) {
		StringBuilder sb = new StringBuilder();
		sb.append(method.getDeclaringClass().getSimpleName()).append('.').append(method.getName()).append('(');
		Class<?>[] params = method.getParameterTypes();
		for (int i = 0; i <  params.length; i++) {
			sb.append(params[i].getSimpleName());
			if (i < params.length -1) {
				sb.append(",");
			}
		}
		sb.append(')');
		
		return sb.toString();
	}
}
