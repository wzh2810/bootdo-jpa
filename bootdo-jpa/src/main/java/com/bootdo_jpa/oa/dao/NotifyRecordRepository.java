package com.bootdo_jpa.oa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bootdo_jpa.oa.domain.NotifyRecordDO;

/**
 * 通知通告发送记录
 * 
 * @author bootdo-jpa huyidao---123@163.com
 * @email huyidao---123@163.com
 * @date 2019-04-01 10:19:23
 */
public interface NotifyRecordRepository extends JpaRepository<NotifyRecordDO, Long>, JpaSpecificationExecutor<NotifyRecordDO> {
	
	//@Query(value = "select * from oa_notify_record where notify_id in (:notifyIds)", nativeQuery = true)
	//List<NotifyRecordDO> findByNotifyIdIn(Long[] notifyIds);

	@Modifying(clearAutomatically = true)
    @Query("update NotifyRecordDO nr set nr.isRead = :#{#notiRe.isRead}, nr.readDate = :#{#notiRe.readDate}  where nr.notifyId = :#{#notiRe.notifyId} and nr.userId = :#{#notiRe.userId}")
	int changeRead(@Param("notiRe") NotifyRecordDO notifyRecord);
	
	//int updateIsReadAndReadDateByNotifyIdAndUserId(Long notifyId, Long userId);
	
	int deleteByIdIn(Long[] ids);
	
	int deleteByNotifyId(Long notifyId);
	
	int deleteByNotifyIdIn(Long[] notifyIds);
}
