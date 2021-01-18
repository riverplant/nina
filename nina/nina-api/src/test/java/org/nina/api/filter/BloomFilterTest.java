package org.nina.api.filter;

import java.nio.charset.Charset;

import org.junit.Test;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

public class BloomFilterTest {

	@Test
	public void myTest() {
		/**
		 * expectedInsertions:期待插入的长度,长度越大，误判率越小
		 * fpp:误判率
		 */
		BloomFilter bf = BloomFilter.create(
				Funnels.stringFunnel(Charset.forName("utf-8")), 
				1000,
				0.0001);
		for(int i = 0; i < 10000; i ++) {
			bf.put(String.valueOf(i));	
		}
		int counts = 0;
		for(int i = 0; i < 1000; i ++) {
			boolean isExist = bf.mightContain("river"+i);
			if(isExist) {
				counts ++;
			}
		}
		
		System.out.println("误判率:"+counts);
		//bf.put("1001");
		//bf.mightContain(object)
	}
}
