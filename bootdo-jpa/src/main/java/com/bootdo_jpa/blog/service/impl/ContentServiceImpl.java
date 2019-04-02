package com.bootdo_jpa.blog.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import com.bootdo_jpa.blog.dao.ContentRepository;
import com.bootdo_jpa.blog.domain.ContentDO;
import com.bootdo_jpa.blog.service.ContentService;
import com.bootdo_jpa.common.service.base.AbsCommonService;



@Service
public class ContentServiceImpl extends AbsCommonService<ContentDO> implements ContentService {
	
	@Autowired
	private ContentRepository contentRepository;
	
	@Override
	@Transactional
	public void deleteByIds(Long... ids) {
		contentRepository.deleteByCidIn(ids);
	}

	@Override
	public JpaRepository<ContentDO, Long> getDao() {
		return contentRepository;
	}

	@Override
	public JpaSpecificationExecutor<ContentDO> getDao2() {
		return contentRepository;
	}
	
}
