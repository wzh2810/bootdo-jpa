package com.bootdo_jpa.common.service.impl;

import java.util.List;
import java.util.Objects;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import com.bootdo_jpa.common.dao.DictRepository;
import com.bootdo_jpa.common.domain.DictDO;
import com.bootdo_jpa.common.service.DictService;
import com.bootdo_jpa.common.service.base.AbsCommonService;
import com.bootdo_jpa.common.utils.StringUtils;
import com.bootdo_jpa.system.domain.UserDO;


@Service
public class DictServiceImpl extends AbsCommonService<DictDO> implements DictService {
	
    @Autowired
    private DictRepository dictRepository;
    
    @Override
    @Transactional
	public void deleteByIds(Long... ids) {
    	dictRepository.deleteByIdIn(ids);
    }

    @Override
    public List<DictDO> listType() {
        return dictRepository.findDistinctTypeAndDescription();
    }
    
    @Override
    public String getName(String type, String value) {
    	return dictRepository.findByTypeAndValue(type, value).get(0).getName();
    }

    @Override
    public List<DictDO> getHobbyList(UserDO userDO) {
        List<DictDO> hobbyList = dictRepository.findByType("hobby");

        if (StringUtils.isNotEmpty(userDO.getHobby())) {
            String userHobbys[] = userDO.getHobby().split(";");
            for (String userHobby : userHobbys) {
                for (DictDO hobby : hobbyList) {
                    if (!Objects.equals(userHobby, hobby.getId().toString())) {
                        continue;
                    }
                    hobby.setRemarks("true");
                    break;
                }
            }
        }

        return hobbyList;
    }

    @Override
    public List<DictDO> getSexList() {
        return dictRepository.findByType("sex");
    }

    @Override
    public List<DictDO> listByType(String type) {
        return dictRepository.findByType(type);
    }

    @Override
	public JpaRepository<DictDO, Long> getDao() {
		return dictRepository;
	}

	@Override
	public JpaSpecificationExecutor<DictDO> getDao2() {
		return dictRepository;
	}

}
