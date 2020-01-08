package org.nina.commons.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.MethodInvokingTaskletAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author riverplant 将NinaTask定时任务配置成Batch
 */
@Configuration
@EnableBatchProcessing // 启动批处理能力
public class NinaTaskConfig {
	@Autowired
	private JobBuilderFactory jobs;

	@Autowired
	private StepBuilderFactory steps;
	/**
	 * 在定时任务中真正需要执行的业务逻辑
	 */
	@Autowired
	private NinaTask ninaTask;

	/**
	 * 1.配置任务,一个任务由多个Step组成
	 */
	@Bean
	public Job job(Step step1) {

		return jobs.get("ninaJob").start(step1).build();
	}
	/**
	 * tasklet:真正要执行的业务逻辑
	 * @return
	 */
	@Bean
	public Step step1() {
     MethodInvokingTaskletAdapter  adapter = new MethodInvokingTaskletAdapter();
     //适配器执行的目标对象时注入的ninaTask
     adapter.setTargetObject(ninaTask);
     //适配器要执行的方法是ninaTask中的doTask方法
     adapter.setTargetMethod("doTask");
		return steps.get("ninaJobStep1").tasklet(adapter).build();
	}
}
