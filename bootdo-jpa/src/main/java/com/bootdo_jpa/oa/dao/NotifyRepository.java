package com.bootdo_jpa.oa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.bootdo_jpa.oa.domain.NotifyDO;

/**
 * 通知通告
 * 
 * @author bootdo-jpa huyidao---123@163.com
 * @email huyidao---123@163.com
 * @date 2019-04-01 10:19:23
 */
public interface NotifyRepository extends JpaRepository<NotifyDO, Long>, JpaSpecificationExecutor<NotifyDO> {

	//@Query(value = "select * from oa_notify where id in (:ids)", nativeQuery = true)
	//List<NotifyDO> findByIdIn(Long[] ids);
	
	/*
	 * @Query(value = "select count(*) " + "		from oa_notify_record r  " +
	 * "		right JOIN oa_notify n on r.notify_id = n.id  " +
	 * "		where r.user_id = ?1 and r.is_read = ?2", nativeQuery = true) int
	 * countDTO(Long userId, Integer isRead);
	 */

	int deleteByIdIn(Long[] ids);
}
