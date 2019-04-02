/**
 * 
 */
package com.bootdo_jpa.common.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bootdo_jpa.common.domain.TablesDO;

/**
 * @author bootdo-jpa huyidao---123@163.com
 * @Time 2017年9月6日
 * @description
 * 
 */
@Service
public interface GeneratorService {
	
	List<TablesDO> list();

	byte[] generatorCode(String[] tableNames);
}
