package com.bootdo_jpa.testDemo;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bootdo_jpa.oa.service.NotifyService;

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(locations = {"classpath:application.yml"})
@SpringBootTest
public class ServiceTest {

	@Autowired
	NotifyService notifyService;
	
	@Test
    public void should_be_able_to_find_by_using_and_or() {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", 1);
		notifyService.listDTO(map);
        
	}
}
