package com.bootdo_jpa.common.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.bootdo_jpa.common.dao.LogRepository;
import com.bootdo_jpa.common.domain.LogDO;
import com.bootdo_jpa.common.service.LogService;
import com.bootdo_jpa.common.service.base.AbsCommonService;

@Service
public class LogServiceImpl extends AbsCommonService<LogDO> implements LogService {
	
	@Autowired
	LogRepository logRepository;

	@Async
	@Override
	@Transactional
	public LogDO save(LogDO logDO) {
		return logRepository.save(logDO);
	}
	
	@Override
	@Transactional
	public void deleteByIds(Long... ids) {
		logRepository.deleteByIdIn(ids);
	}

	@Override
	public JpaRepository<LogDO, Long> getDao() {
		return logRepository;
	}

	@Override
	public JpaSpecificationExecutor<LogDO> getDao2() {
		return logRepository;
	}
	
}
