package com.bootdo_jpa.common.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bootdo_jpa.common.domain.ColumnsDO;

public interface ColumnsRepository extends JpaRepository<ColumnsDO, Long> {

	@Query(value = "select column_name, data_type, column_comment, column_key, extra from information_schema.columns "
			+ " where table_name = ?1 and table_schema = (select database()) order by ordinal_position", nativeQuery = true)
	List<ColumnsDO> listColumns(String tableName);
}
