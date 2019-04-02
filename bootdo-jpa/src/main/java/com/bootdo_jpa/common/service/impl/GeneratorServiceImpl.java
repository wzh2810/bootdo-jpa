package com.bootdo_jpa.common.service.impl;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bootdo_jpa.common.dao.ColumnsRepository;
import com.bootdo_jpa.common.dao.TablesRepository;
import com.bootdo_jpa.common.domain.ColumnsDO;
import com.bootdo_jpa.common.domain.TablesDO;
import com.bootdo_jpa.common.service.GeneratorService;
import com.bootdo_jpa.common.utils.GenUtils;


@Service
public class GeneratorServiceImpl implements GeneratorService {
	
	@Autowired
	TablesRepository tablesRepository;
	@Autowired
	ColumnsRepository columnsRepository;

	@Override
	public List<TablesDO> list() {
		List<TablesDO> list = tablesRepository.list();
		return list;
	}

	@Override
	public byte[] generatorCode(String[] tableNames) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ZipOutputStream zip = new ZipOutputStream(outputStream);
		for(String tableName : tableNames){
			//查询表信息
			TablesDO table = tablesRepository.get(tableName);
			//查询列信息
			List<ColumnsDO> columns = columnsRepository.listColumns(tableName);
			//生成代码
			GenUtils.generatorCode(table, columns, zip);
		}
		IOUtils.closeQuietly(zip);
		return outputStream.toByteArray();
	}

}
