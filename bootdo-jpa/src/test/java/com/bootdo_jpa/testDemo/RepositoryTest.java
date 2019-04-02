package com.bootdo_jpa.testDemo;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bootdo_jpa.common.dao.DictRepository;
import com.bootdo_jpa.common.dao.TablesRepository;
import com.bootdo_jpa.common.domain.TablesDO;

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(locations = {"classpath:application.yml"})
@SpringBootTest
public class RepositoryTest {

	@Autowired
	DictRepository dictRepository;
	@Autowired
	TablesRepository TablesRepository;
	
	@Test
    public void should_be_able_to_find_by_using_and_or() {

		/*
		 * List<DictDO> dicts = dictRepository.findDistinctTypeAndDescription();
		 * System.err.println("persons.size===========" + dicts.size()); // then
		 * Assert.assertEquals(dicts.size(), 3);
		 */
		
		List<TablesDO> list = TablesRepository.list();
		System.out.println("list.size==========="+list.size());
	}
}
