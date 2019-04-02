package com.bootdo_jpa.oa.service;

import java.util.List;
import java.util.Map;

import com.bootdo_jpa.common.service.base.ICommonService;
import com.bootdo_jpa.common.utils.PageUtils;
import com.bootdo_jpa.oa.domain.NotifyDO;
import com.bootdo_jpa.oa.domain.NotifyDTO;

/**
 * 通知通告
 * 
 * @author bootdo-jpa huyidao---123@163.com
 * @email huyidao---123@163.com
 * @date 2019-04-01 10:19:23
 */
public interface NotifyService extends ICommonService<NotifyDO> {
	
	long countDTO(Map<String, Object> map);
	
	List<NotifyDTO> listDTO(Map<String, Object> map);
	
	public List<NotifyDO> list(Map<String, Object> map) throws Exception;

	PageUtils selfList(Map<String, Object> map) ;
	
}
