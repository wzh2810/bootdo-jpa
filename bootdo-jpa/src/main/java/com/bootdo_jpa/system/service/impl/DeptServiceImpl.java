package com.bootdo_jpa.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import com.bootdo_jpa.common.domain.Tree;
import com.bootdo_jpa.common.service.base.AbsCommonService;
import com.bootdo_jpa.common.utils.BuildTree;
import com.bootdo_jpa.system.dao.DeptRepository;
import com.bootdo_jpa.system.domain.DeptDO;
import com.bootdo_jpa.system.service.DeptService;


@Service
public class DeptServiceImpl extends AbsCommonService<DeptDO> implements DeptService {
	
    @Autowired
    private DeptRepository deptRepository;
    
    @Override
    @Transactional
    public void deleteByIds(Long... ids) {
    	deptRepository.deleteByDeptIdIn(ids);
    }

    @Override
    public Tree<DeptDO> getTree() {
        List<Tree<DeptDO>> trees = new ArrayList<Tree<DeptDO>>();
        List<DeptDO> sysDepts = deptRepository.findAll();
        for (DeptDO sysDept : sysDepts) {
            Tree<DeptDO> tree = new Tree<DeptDO>();
            tree.setId(sysDept.getDeptId().toString());
            tree.setParentId(sysDept.getParentId().toString());
            tree.setText(sysDept.getName());
            Map<String, Object> state = new HashMap<>(16);
            state.put("opened", true);
            tree.setState(state);
            trees.add(tree);
        }
        // 默认顶级菜单为０，根据数据库实际情况调整
        Tree<DeptDO> t = BuildTree.build(trees);
        return t;
    }

    @Override
    public boolean checkDeptHasUser(Long deptId) {
        // TODO Auto-generated method stub
        //查询部门以及此部门的下级部门
        int result = deptRepository.countByDeptId(deptId);
        return result == 0 ? true : false;
    }

    @Override
    public List<Long> listChildrenIds(Long parentId) {
        List<DeptDO> deptDOS = deptRepository.findAll();
        return treeMenuList(deptDOS, parentId);
    }

    List<Long> treeMenuList(List<DeptDO> menuList, long pid) {
        List<Long> childIds = new ArrayList<>();
        for (DeptDO mu : menuList) {
            //遍历出父id等于参数的id，add进子节点集合
            if (mu.getParentId() == pid) {
                //递归遍历下一级
                treeMenuList(menuList, mu.getDeptId());
                childIds.add(mu.getDeptId());
            }
        }
        return childIds;
    }

    @Override
	public JpaRepository<DeptDO, Long> getDao() {
		return deptRepository;
	}

	@Override
	public JpaSpecificationExecutor<DeptDO> getDao2() {
		return deptRepository;
	}

}
