package com.bootdo_jpa.common.service;

import org.quartz.SchedulerException;

import com.bootdo_jpa.common.domain.TaskDO;
import com.bootdo_jpa.common.service.base.ICommonService;

/**
 * 
 * 
 * @author bootdo-jpa huyidao---123@163.com
 * @email huyidao---123@163.com
 * @date 2019-04-01 10:19:23
 */
public interface JobService extends ICommonService<TaskDO> {

	void initSchedule() throws SchedulerException;

	void changeStatus(Long jobId, String cmd) throws SchedulerException;

	void updateCron(Long jobId) throws SchedulerException;
}
