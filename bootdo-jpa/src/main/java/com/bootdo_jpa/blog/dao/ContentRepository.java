package com.bootdo_jpa.blog.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.bootdo_jpa.blog.domain.ContentDO;

/**
 * 文章内容
 * @author bootdo-jpa huyidao---123@163.com
 * @email huyidao---123@163.com
 * @date 2019-04-01 10:19:23
 */
public interface ContentRepository extends JpaRepository<ContentDO, Long>, JpaSpecificationExecutor<ContentDO> {
	
	int deleteByCidIn(Long[] ids);
}
