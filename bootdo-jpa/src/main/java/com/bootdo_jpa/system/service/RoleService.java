package com.bootdo_jpa.system.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bootdo_jpa.common.service.base.ICommonService;
import com.bootdo_jpa.system.domain.RoleDO;

@Service
public interface RoleService extends ICommonService<RoleDO> {

	List<RoleDO> list(Long userId);

}
