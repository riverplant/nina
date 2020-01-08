package org.nina.commons.async;

/**
 * 
 * @author riverplant 全局计数器
 */
public class Counter {

	public long total = 0;
     /**
      * 加入锁让该方法可以通过多线程操作
      * @param value
      */
	 public synchronized void add (long value) {
		total += value;
	}
}
