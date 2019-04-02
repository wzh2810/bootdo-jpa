package com.bootdo_jpa.common.service.impl;

import java.io.File;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bootdo_jpa.common.config.BootdoJpaConfig;
import com.bootdo_jpa.common.dao.FileRepository;
import com.bootdo_jpa.common.domain.FileDO;
import com.bootdo_jpa.common.service.FileService;
import com.bootdo_jpa.common.service.base.AbsCommonService;


@Service
public class FileServiceImpl extends AbsCommonService<FileDO> implements FileService {
	
	@Autowired
	private FileRepository fileRepository;

	@Autowired
	private BootdoJpaConfig bootdoJpaConfig;
	
	@Override
	@Transactional
	public void deleteByIds(Long... ids) {
		fileRepository.deleteByIdIn(ids);
    }

    @Override
    public Boolean isExist(String url) {
		Boolean isExist = false;
		if (!StringUtils.isEmpty(url)) {
			String filePath = url.replace("/files/", "");
			filePath = bootdoJpaConfig.getUploadPath() + filePath;
			File file = new File(filePath);
			if (file.exists()) {
				isExist = true;
			}
		}
		return isExist;
	}

    @Override
	public JpaRepository<FileDO, Long> getDao() {
		return fileRepository;
	}

	@Override
	public JpaSpecificationExecutor<FileDO> getDao2() {
		return fileRepository;
	}
	
}
