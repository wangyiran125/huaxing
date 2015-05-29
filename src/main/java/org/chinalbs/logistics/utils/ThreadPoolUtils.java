package org.chinalbs.logistics.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolUtils {
	private static ExecutorService cachedPool = Executors.newCachedThreadPool(); //可变数目的线程池
	
	private ThreadPoolUtils(){}
	
	public static void execute(Runnable runnable){
		cachedPool.execute(runnable);
	}
}
