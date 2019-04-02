package com.bootdo_jpa.oa.service;

import com.bootdo_jpa.common.service.base.ICommonService;
import com.bootdo_jpa.oa.domain.NotifyRecordDO;

/**
 * 通知通告发送记录
 * 
 * @author bootdo-jpa huyidao---123@163.com
 * @email huyidao---123@163.com
 * @date 2019-04-01 10:19:23
 */
public interface NotifyRecordService extends ICommonService<NotifyRecordDO> {
	
	int changeRead(NotifyRecordDO notifyRecord);
}
