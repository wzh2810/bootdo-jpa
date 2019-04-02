package com.bootdo_jpa.system.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.bootdo_jpa.common.domain.Tree;
import com.bootdo_jpa.common.service.base.ICommonService;
import com.bootdo_jpa.system.domain.MenuDO;

@Service
public interface MenuService extends ICommonService<MenuDO> {
	
	Tree<MenuDO> getSysMenuTree(Long id);

	List<Tree<MenuDO>> listMenuTree(Long id);

	Tree<MenuDO> getTree();

	Tree<MenuDO> getTree(Long id);

	Set<String> listPerms(Long userId);
}
