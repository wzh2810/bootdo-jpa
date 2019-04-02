package com.bootdo_jpa.system.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.bootdo_jpa.system.domain.UserRoleDO;

/**
 * 用户与角色对应关系
 * 
 * @author bootdo-jpa huyidao---123@163.com
 * @email huyidao---123@163.com
 * @date 2019-04-01 10:19:23
 */
public interface UserRoleRepository extends JpaRepository<UserRoleDO, Long>, JpaSpecificationExecutor<UserRoleDO> {

	@Query(value = "select role_id from sys_user_role where user_id = ?1", nativeQuery = true)
	List<Long> listRoleIdByUserId(Long userId);

	int deleteByUserId(Long userId);

	int deleteByRoleId(Long roleId);

	int deleteByUserIdIn(Long[] ids);
	
	int deleteByIdIn(Long[] ids);
}
