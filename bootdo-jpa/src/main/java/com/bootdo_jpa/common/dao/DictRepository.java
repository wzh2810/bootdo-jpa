package com.bootdo_jpa.common.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.bootdo_jpa.common.domain.DictDO;

/**
 * 字典表
 * 
 * @author bootdo-jpa huyidao---123@163.com
 * @email huyidao---123@163.com
 * @date 2019-04-01 10:19:23
 */
public interface DictRepository extends JpaRepository<DictDO, Long>, JpaSpecificationExecutor<DictDO> {

	@Query("select distinct new DictDO(d.type, d.description) from DictDO d")
	List<DictDO> findDistinctTypeAndDescription();
	
	List<DictDO> findByTypeAndValue(String type, String value);
	
	List<DictDO> findByType(String type);
	
	int deleteByIdIn(Long[] ids);
}
