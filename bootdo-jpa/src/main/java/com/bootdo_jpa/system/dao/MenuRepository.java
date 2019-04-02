package com.bootdo_jpa.system.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.bootdo_jpa.system.domain.MenuDO;

/**
 * 菜单管理
 * @author bootdo-jpa huyidao---123@163.com
 * @email huyidao---123@163.com
 * @date 2019-04-01 10:19:23
 */
public interface MenuRepository extends JpaRepository<MenuDO, Long>, JpaSpecificationExecutor<MenuDO> {
	
	@Query(value = "select distinct m.menu_id , m.parent_id, m.name, m.url, m.perms, m.`type`, m.icon, m.order_num, m.gmt_create, m.gmt_modified " + 
			"		from sys_menu m " + 
			"		left join sys_role_menu rm on m.menu_id = rm.menu_id  " + 
			"		left join sys_user_role ur on rm.role_id =ur.role_id  " + 
			"		where ur.user_id = ?1 and m.type in (0,1)  " + 
			"		order by m.order_num", nativeQuery = true)
	List<MenuDO> listMenuByUserId(Long id);
	
	@Query(value = "select distinct m.perms " + 
			"		from sys_menu m  " + 
			"		left join sys_role_menu rm on m.menu_id = rm.menu_id " + 
			"		left join sys_user_role ur on rm.role_id = ur.role_id  " + 
			"		where ur.user_id = ?1", nativeQuery = true)
	List<String> listUserPerms(Long id);
	
	int deleteByMenuIdIn(Long[] ids);
}
