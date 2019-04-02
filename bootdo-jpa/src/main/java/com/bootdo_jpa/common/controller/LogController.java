package com.bootdo_jpa.common.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bootdo_jpa.common.domain.LogDO;
import com.bootdo_jpa.common.service.LogService;
import com.bootdo_jpa.common.utils.PageUtils;
import com.bootdo_jpa.common.utils.Query;
import com.bootdo_jpa.common.utils.R;

@RequestMapping("/common/log")
@Controller
public class LogController {
	
	@Autowired
	LogService logService;
	String prefix = "common/log";

	@GetMapping()
	String log() {
		return prefix + "/log";
	}

	@ResponseBody
	@GetMapping("/list")
	public PageUtils list(@RequestParam Map<String, Object> params) {
		PageUtils pageUtils = null;
    	try {
    		Query query = new Query(params);
    		pageUtils = logService.findAllByPage(query, LogDO.class, "id");
		} catch (Exception e) {
			e.printStackTrace();
		}
        return pageUtils;
	}
	
	@ResponseBody
	@PostMapping("/remove")
	public R remove(Long id) {
		try {
			logService.deleteById(id);
			return R.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return R.error();
		}
		
	}

	@ResponseBody
	@PostMapping("/batchRemove")
	public R batchRemove(@RequestParam("ids[]") Long[] ids) {
		try {
			logService.deleteByIds(ids);
			return R.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return R.error();
		}
		
	}
}
