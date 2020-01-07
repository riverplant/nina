package org.nina.commons.async;

import java.util.function.Function;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * 
 * @author riverplant
 * iterate:非常不适用parallel
 */
public class ParallelStreamDemo {
	// 使用并行流进行计算
	public static long parallelSum(long n) {
		return Stream.iterate(1L, i -> i + 1)
				.limit(n)
				.parallel()
				.reduce(0L, Long::sum);
	}
	
	public static long longParallelSum(long n) {
		return LongStream.range(1L, n)
				.parallel()
				.reduce(0L, Long::sum);
	}
	
	public static long wrongSum(long n) {
		Counter counter = new Counter();
		 LongStream.range(1L, n)
				.parallel()
				.forEach(counter::add);
		 return counter.total;
	}
	
	public static void main(String[] args) {
		long n = 20_000_000;
		System.out.println(test(ParallelStreamDemo::wrongSum,n));
	}
	/**
	 * 测试方法:获得最短时间
	 * @param computer
	 * @param n
	 * @return
	 */
	public static long test(Function<Long,Long>computer, long n) {
		long fastest = Long.MAX_VALUE;
		for(int i = 0; i<10; i++) {
			long start = System.currentTimeMillis();
			System.out.println("当前值为"+computer.apply(n));
			long cost = System.currentTimeMillis() - start;
			if(cost < fastest) {
				fastest = cost;
			}
		}
		return fastest;
	}
}
