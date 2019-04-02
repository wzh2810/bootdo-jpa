package com.bootdo_jpa.blog.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bootdo_jpa.blog.domain.ContentDO;
import com.bootdo_jpa.blog.service.ContentService;
import com.bootdo_jpa.common.utils.DateUtils;
import com.bootdo_jpa.common.utils.PageUtils;
import com.bootdo_jpa.common.utils.Query;
import com.github.wenhao.jpa.Specifications;

/**
 * @author bootdo-jpa huyidao---123@163.com
 */
@RequestMapping("/blog")
@Controller
public class BlogController {
	
	@Autowired
    ContentService bContentService;

	@GetMapping()
	String blog() {
		return "blog/index/main";
	}

	@ResponseBody
	@GetMapping("/open/list")
	public PageUtils openList(@RequestParam Map<String, Object> params) {
		PageUtils pageUtils = null;
    	try {
    		Query query = new Query(params);
    		pageUtils = bContentService.findAllByPage(query, ContentDO.class, "cid");
		} catch (Exception e) {
			e.printStackTrace();
		}
        return pageUtils;
	}

	@GetMapping("/open/post/{cid}")
	String post(@PathVariable("cid") Long cid, Model model) {
		ContentDO bContentDO = bContentService.findById(cid);
		model.addAttribute("bContent", bContentDO);
		model.addAttribute("gtmModified", DateUtils.format(bContentDO.getGtmModified()));
		return "blog/index/post";
	}
	
	@GetMapping("/open/page/{categories}")
	String about(@PathVariable("categories") String categories, Model model) {
		//Map<String, Object> map = new HashMap<>(16);
		//map.put("categories", categories);
		Specification<ContentDO> spec = Specifications.<ContentDO>and().eq("categories", categories).build();
		List<ContentDO> list = bContentService.findAll(spec);
		ContentDO bContentDO =null;
		if(list.size()>0){
			 bContentDO = list.get(0);
		}
		model.addAttribute("bContent", bContentDO);
		return "blog/index/post";
	}
	
}
