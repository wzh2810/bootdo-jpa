package com.bootdo_jpa.system.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.bootdo_jpa.system.domain.RoleMenuDO;

/**
 * 角色与菜单对应关系
 * @author bootdo-jpa huyidao---123@163.com
 * @email huyidao---123@163.com
 * @date 2019-04-01 10:19:23
 */
public interface RoleMenuRepository extends JpaRepository<RoleMenuDO, Long>, JpaSpecificationExecutor<RoleMenuDO> {
	
	@Query(value = "select menu_id from sys_role_menu where role_id = ?1", nativeQuery = true)
	List<Long> listMenuIdByRoleId(Long roleId);
	
	int deleteByRoleId(Long roleId);

	int deleteByMenuId(Long menuId);
	
	int deleteByIdIn(Long[] ids);
}
