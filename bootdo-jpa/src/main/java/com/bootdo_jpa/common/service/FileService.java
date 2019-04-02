package com.bootdo_jpa.common.service;

import com.bootdo_jpa.common.domain.FileDO;
import com.bootdo_jpa.common.service.base.ICommonService;

/**
 * 文件上传
 * 
 * @author bootdo-jpa huyidao---123@163.com
 * @email huyidao---123@163.com
 * @date 2019-04-01 10:19:23
 */
public interface FileService extends ICommonService<FileDO> {
	
	/**
	 * 判断一个文件是否存在
	 * @param url FileDO中存的路径
	 * @return
	 */
    Boolean isExist(String url);
}
