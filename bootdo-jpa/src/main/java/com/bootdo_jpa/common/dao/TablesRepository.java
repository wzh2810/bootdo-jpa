package com.bootdo_jpa.common.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bootdo_jpa.common.domain.TablesDO;

public interface TablesRepository extends JpaRepository<TablesDO, Long> {

	@Query(value = "select table_name, engine, table_comment, create_time from information_schema.tables " + 
			"			where table_schema = (select database())", nativeQuery = true)
	List<TablesDO> list();
	
	@Query(value = "select count(*) from information_schema.tables where table_schema = (select database())", nativeQuery = true)
	long count();

	@Query(value = "select table_name, engine, table_comment, create_time from information_schema.tables  "
			+ "	where table_schema = (select database()) and table_name = ?1", nativeQuery = true)
	TablesDO get(String tableName);
}
