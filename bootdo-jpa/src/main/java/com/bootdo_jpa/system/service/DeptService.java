package com.bootdo_jpa.system.service;

import java.util.List;

import com.bootdo_jpa.common.domain.Tree;
import com.bootdo_jpa.common.service.base.ICommonService;
import com.bootdo_jpa.system.domain.DeptDO;

/**
 * 部门管理
 * 
 * @author bootdo-jpa huyidao---123@163.com
 * @email huyidao---123@163.com
 * @date 2019-04-01 10:19:23
 */
public interface DeptService extends ICommonService<DeptDO> {
	
	Tree<DeptDO> getTree();
	
	boolean checkDeptHasUser(Long deptId);

	List<Long> listChildrenIds(Long parentId);
}
