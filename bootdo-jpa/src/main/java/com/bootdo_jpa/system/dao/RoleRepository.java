package com.bootdo_jpa.system.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.bootdo_jpa.system.domain.RoleDO;

/**
 * 角色
 * @author bootdo-jpa huyidao---123@163.com
 * @email huyidao---123@163.com
 * @date 2019-04-01 10:19:23
 */
public interface RoleRepository extends JpaRepository<RoleDO, Long>, JpaSpecificationExecutor<RoleDO> {

	int deleteByRoleIdIn(Long[] ids);
}
