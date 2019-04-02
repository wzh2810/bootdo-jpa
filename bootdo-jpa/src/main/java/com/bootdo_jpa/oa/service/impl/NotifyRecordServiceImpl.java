package com.bootdo_jpa.oa.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import com.bootdo_jpa.common.service.base.AbsCommonService;
import com.bootdo_jpa.oa.dao.NotifyRecordRepository;
import com.bootdo_jpa.oa.domain.NotifyRecordDO;
import com.bootdo_jpa.oa.service.NotifyRecordService;



@Service
public class NotifyRecordServiceImpl extends AbsCommonService<NotifyRecordDO> implements NotifyRecordService {
	
	@Autowired
	private NotifyRecordRepository notifyRecordRepository;
	
	@Override
	@Transactional
	public void deleteByIds(Long... ids) {
		notifyRecordRepository.deleteByIdIn(ids);
	}
	
	@Override
	@Transactional
	public int changeRead(NotifyRecordDO notifyRecord) {
		return notifyRecordRepository.changeRead(notifyRecord);
	}

	@Override
	public JpaRepository<NotifyRecordDO, Long> getDao() {
		return notifyRecordRepository;
	}

	@Override
	public JpaSpecificationExecutor<NotifyRecordDO> getDao2() {
		return notifyRecordRepository;
	}

}
