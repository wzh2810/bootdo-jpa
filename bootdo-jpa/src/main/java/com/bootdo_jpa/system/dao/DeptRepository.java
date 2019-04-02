package com.bootdo_jpa.system.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.bootdo_jpa.system.domain.DeptDO;

/**
 * 部门管理
 * @author bootdo-jpa huyidao---123@163.com
 * @email huyidao---123@163.com
 * @date 2019-04-01 10:19:23
 */
public interface DeptRepository extends JpaRepository<DeptDO, Long>, JpaSpecificationExecutor<DeptDO> {
	
	@Query(value = "select DISTINCT parent_id from sys_dept", nativeQuery = true)
	Long[] findDistinctParentId();
	
	int countByDeptId(Long deptId);
	
	int deleteByDeptIdIn(Long[] ids);
}
