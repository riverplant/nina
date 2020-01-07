package org.nina.commons.batch;

import org.springframework.stereotype.Component;

/**
 * 
 * @author riverplant
 * 需要执行的定时任务
 */
@Component
public class NinaTaskImpl implements NinaTask {

	@Override
	public void doTask() {
		/**
		 * 需要执行的业务代码
		 */
		System.out.println("开始执行需要执行的业务代码....");

	}

}
