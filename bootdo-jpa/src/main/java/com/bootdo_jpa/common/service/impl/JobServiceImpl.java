package com.bootdo_jpa.common.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import com.bootdo_jpa.common.config.Constant;
import com.bootdo_jpa.common.dao.TaskRepository;
import com.bootdo_jpa.common.domain.ScheduleJob;
import com.bootdo_jpa.common.domain.TaskDO;
import com.bootdo_jpa.common.quartz.utils.QuartzManager;
import com.bootdo_jpa.common.service.JobService;
import com.bootdo_jpa.common.service.base.AbsCommonService;
import com.bootdo_jpa.common.utils.ScheduleJobUtils;

@Service
public class JobServiceImpl extends AbsCommonService<TaskDO> implements JobService {

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	QuartzManager quartzManager;

	@Override
	@Transactional
	public void deleteById(Long id) {
		try {
			TaskDO scheduleJob = findById(id);
			quartzManager.deleteJob(ScheduleJobUtils.entityToData(scheduleJob));
			taskRepository.deleteById(id);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	@Override
	@Transactional
	public void deleteByIds(Long... ids) {
		for (Long id : ids) {
			try {
				TaskDO scheduleJob = findById(id);
				quartzManager.deleteJob(ScheduleJobUtils.entityToData(scheduleJob));
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
		}
		taskRepository.deleteByIdIn(ids);
	}

	@Override
	@Transactional
	public void initSchedule() throws SchedulerException {
		// 这里获取任务信息数据
		List<TaskDO> jobList = taskRepository.findAll();
		for (TaskDO scheduleJob : jobList) {
			if ("1".equals(scheduleJob.getJobStatus())) {
				ScheduleJob job = ScheduleJobUtils.entityToData(scheduleJob);
				quartzManager.addJob(job);
			}

		}
	}

	@Override
	@Transactional
	public void changeStatus(Long jobId, String cmd) throws SchedulerException {
		TaskDO scheduleJob = findById(jobId);
		if (scheduleJob == null) {
			return;
		}
		if (Constant.STATUS_RUNNING_STOP.equals(cmd)) {
			quartzManager.deleteJob(ScheduleJobUtils.entityToData(scheduleJob));
			scheduleJob.setJobStatus(ScheduleJob.STATUS_NOT_RUNNING);
		} else {
			if (!Constant.STATUS_RUNNING_START.equals(cmd)) {
			} else {
                scheduleJob.setJobStatus(ScheduleJob.STATUS_RUNNING);
                quartzManager.addJob(ScheduleJobUtils.entityToData(scheduleJob));
            }
		}
		save(scheduleJob);
	}

	@Override
	@Transactional
	public void updateCron(Long jobId) throws SchedulerException {
		TaskDO scheduleJob = findById(jobId);
		if (scheduleJob == null) {
			return;
		}
		if (ScheduleJob.STATUS_RUNNING.equals(scheduleJob.getJobStatus())) {
			quartzManager.updateJobCron(ScheduleJobUtils.entityToData(scheduleJob));
		}
		save(scheduleJob);
	}

	@Override
	public JpaRepository<TaskDO, Long> getDao() {
		return taskRepository;
	}

	@Override
	public JpaSpecificationExecutor<TaskDO> getDao2() {
		return taskRepository;
	}

}
