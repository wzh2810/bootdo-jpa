package com.bootdo_jpa.activiti.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import com.bootdo_jpa.activiti.config.ActivitiConstant;
import com.bootdo_jpa.activiti.dao.SalaryRepository;
import com.bootdo_jpa.activiti.domain.SalaryDO;
import com.bootdo_jpa.activiti.service.SalaryService;
import com.bootdo_jpa.common.service.base.AbsCommonService;
import com.bootdo_jpa.common.utils.BizUtil;


@Service
public class SalaryServiceImpl extends AbsCommonService<SalaryDO> implements SalaryService {
	
	@Autowired
	private SalaryRepository salaryRepository;
	@Autowired
	private ActTaskServiceImpl actTaskService;

	@Override
	@Transactional
	public SalaryDO save(SalaryDO t) {
		if(t.getId() == null) {
			actTaskService.startProcess(ActivitiConstant.ACTIVITI_SALARY[0],ActivitiConstant.ACTIVITI_SALARY[1],t.getId(),t.getContent(),new HashMap<>());
			return salaryRepository.save(t);
		}else {
			SalaryDO origin= findById(t.getId());
			Map<String,Object> vars = new HashMap<>(16);
			vars.put("pass",  origin.getTaskPass() );
			vars.put("title","");
			actTaskService.complete(origin.getTaskId(),vars);
            BizUtil.copyProperties(origin, t);
            return salaryRepository.save(origin);
		}
	}
	
	@Override
	@Transactional
	public void deleteByIds(Long... ids) {
		salaryRepository.deleteByIdIn(ids);
	}

	@Override
	public JpaRepository<SalaryDO, Long> getDao() {
		return salaryRepository;
	}

	@Override
	public JpaSpecificationExecutor<SalaryDO> getDao2() {
		return salaryRepository;
	}
	
}
