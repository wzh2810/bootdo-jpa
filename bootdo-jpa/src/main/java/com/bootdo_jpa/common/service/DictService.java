package com.bootdo_jpa.common.service;

import java.util.List;

import com.bootdo_jpa.common.domain.DictDO;
import com.bootdo_jpa.common.service.base.ICommonService;
import com.bootdo_jpa.system.domain.UserDO;

/**
 * 字典表
 * 
 * @author bootdo-jpa huyidao---123@163.com
 * @email huyidao---123@163.com
 * @date 2019-04-01 10:19:23
 */
public interface DictService extends ICommonService<DictDO> {

	List<DictDO> listType();
	
	String getName(String type, String value);

	/**
	 * 获取爱好列表
	 * @return
     * @param userDO
	 */
	List<DictDO> getHobbyList(UserDO userDO);

	/**
	 * 获取性别列表
 	 * @return
	 */
	List<DictDO> getSexList();

	/**
	 * 根据type获取数据
	 * @param map
	 * @return
	 */
	List<DictDO> listByType(String type);

}
