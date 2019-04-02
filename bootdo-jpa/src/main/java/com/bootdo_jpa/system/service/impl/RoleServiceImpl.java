package com.bootdo_jpa.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bootdo_jpa.common.service.base.AbsCommonService;
import com.bootdo_jpa.system.dao.RoleMenuRepository;
import com.bootdo_jpa.system.dao.RoleRepository;
import com.bootdo_jpa.system.dao.UserRepository;
import com.bootdo_jpa.system.dao.UserRoleRepository;
import com.bootdo_jpa.system.domain.RoleDO;
import com.bootdo_jpa.system.domain.RoleMenuDO;
import com.bootdo_jpa.system.service.RoleService;


@Service
public class RoleServiceImpl extends AbsCommonService<RoleDO> implements RoleService {

    public static final String ROLE_ALL_KEY = "\"role_all\"";

    public static final String DEMO_CACHE_NAME = "role";

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    RoleMenuRepository roleMenuRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserRoleRepository userRoleRepository;
    
    @Override
    @Transactional
    public void deleteByIds(Long... ids) {
    	roleRepository.deleteByRoleIdIn(ids);
    }

    @Override
    public List<RoleDO> list(Long userId) {
        List<Long> rolesIds = userRoleRepository.listRoleIdByUserId(userId);
        List<RoleDO> roles = roleRepository.findAll();
        for (RoleDO roleDO : roles) {
            roleDO.setRoleSign("false");
            for (Long roleId : rolesIds) {
                if (Objects.equals(roleDO.getRoleId(), roleId)) {
                    roleDO.setRoleSign("true");
                    break;
                }
            }
        }
        return roles;
    }
    
    @Transactional
    @Override
    public RoleDO save(RoleDO role) {
        List<Long> menuIds = role.getMenuIds();
        Long roleId = role.getRoleId();
        List<RoleMenuDO> rms = new ArrayList<>();
        for (Long menuId : menuIds) {
            RoleMenuDO rmDo = new RoleMenuDO();
            rmDo.setRoleId(roleId);
            rmDo.setMenuId(menuId);
            rms.add(rmDo);
        }
        roleMenuRepository.deleteByRoleId(roleId);
        if (rms.size() > 0) {
        	roleMenuRepository.saveAll(rms);
        }
        RoleDO roleDO = roleRepository.save(role);
        return roleDO;
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
    	userRoleRepository.deleteByRoleId(id);
    	roleMenuRepository.deleteByRoleId(id);
    	roleRepository.deleteById(id);
    }

    @Override
	public JpaRepository<RoleDO, Long> getDao() {
		return roleRepository;
	}

	@Override
	public JpaSpecificationExecutor<RoleDO> getDao2() {
		return roleRepository;
	}

}
