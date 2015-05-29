package org.chinalbs.logistics.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// @Aspect : 标记为切面类
// @Pointcut : 指定匹配切点集合
// @Before : 指定前置通知，value中指定切入点匹配
// @AfterReturning ：后置通知，具有可以指定返回值
// @AfterThrowing ：异常通知
// 注意：前置/后置/异常通知的函数都没有返回值，只有环绕通知有返回值

@Component
// 首先初始化切面类
@Aspect
// 声明为切面类，底层使用动态代理实现AOP
public class AopLogger {
	private static Logger logger = LoggerFactory.getLogger(AopLogger.class); 

	// 指定切入点匹配表达式，注意它是以方法的形式进行声明的。
	// 即切点集合是：aop.annotation包下所有类所有方法
	// 第一个* 代表返回值类型
	// 如果要设置多个切点可以使用 || 拼接
	@Pointcut("execution(* org.chinalbs.logistics.controller.*.*(..))")
	public void anyMethod() {
	}

	// 前置通知
	// 在切点方法集合执行前，执行前置通知
	@Before(value = "anyMethod()")
	public void doBefore(JoinPoint jp) {
		Class<? extends Object> targetClass = jp.getTarget().getClass();
		String targetMethod = ",method=" + jp.getSignature().getName();
		Object[] param = jp.getArgs();
		String paramString = ",paramString=";
		if (param != null && param.length != 0) {
			for (int i = 0; i < param.length; i++) {
				paramString += param[i] + ",";
			}
		}
		String log = targetClass + targetMethod + paramString;
		logger.debug("+++++进入 " + log + "+++++");

	}

	// 后置通知
	@AfterReturning(value = "anyMethod()",returning="rtv")
	public void doAfter(JoinPoint jp,Object rtv) {
		Class<? extends Object> targetClass = jp.getTarget().getClass();
		String targetMethod = ",method=" + jp.getSignature().getName();
		
		String result = ",result=";
		if (rtv != null) {
			result += rtv.toString();
		}
		String log = targetClass + targetMethod + result;
		logger.debug("+++++退出 " + log + "+++++");
	}

	@AfterThrowing(value = "anyMethod()", throwing = "e")
	public void doThrow(JoinPoint jp, Throwable e) {
		Class<? extends Object> targetClass = jp.getTarget().getClass();
		String targetMethod = ",method=" + jp.getSignature().getName();
		String log = targetClass + targetMethod;
		logger.debug("+++++异常 " + log + "=" + e + "+++++");
	}

}