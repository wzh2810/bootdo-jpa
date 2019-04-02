package com.bootdo_jpa.activiti.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.bootdo_jpa.activiti.domain.SalaryDO;

/**
 * 审批流程测试表
 * @author bootdo-jpa huyidao---123@163.com
 * @email huyidao---123@163.com
 * @date 2019-04-01 10:19:23
 */
public interface SalaryRepository extends JpaRepository<SalaryDO, Long>, JpaSpecificationExecutor<SalaryDO> {
	
	int deleteByIdIn(Long[] ids);
}
