package com.bootdo_jpa.activiti.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bootdo_jpa.activiti.domain.SalaryDO;
import com.bootdo_jpa.activiti.service.SalaryService;
import com.bootdo_jpa.activiti.utils.ActivitiUtils;
import com.bootdo_jpa.common.config.Constant;
import com.bootdo_jpa.common.controller.BaseController;
import com.bootdo_jpa.common.utils.PageUtils;
import com.bootdo_jpa.common.utils.Query;
import com.bootdo_jpa.common.utils.R;
import com.bootdo_jpa.common.utils.ShiroUtils;

/**
 * 审批流程测试表
 *
 * @author bootdo-jpa huyidao---123@163.com
 * @email huyidao---123@163.com
 * @date 2019-04-01 10:19:23
 */

@Controller
@RequestMapping("/act/salary")
public class SalaryController extends BaseController{
	
    @Autowired
    private SalaryService salaryService;
    @Autowired
    ActivitiUtils activitiUtils;

    @GetMapping()
    String Salary() {
        return "activiti/salary/salary";
    }

    @ResponseBody
    @GetMapping("/list")
    public PageUtils list(@RequestParam Map<String, Object> params) {
    	PageUtils pageUtils = null;
    	try {
    		Query query = new Query(params);
    		pageUtils = salaryService.findAllByPage(query, SalaryDO.class, "id");
		} catch (Exception e) {
			e.printStackTrace();
		}
        return pageUtils;
    }

    @GetMapping("/form")
    String add() {
        return "act/salary/add";
    }

    @GetMapping("/form/{taskId}")
    String edit(@PathVariable("taskId") String taskId, Model model) {
        SalaryDO salary = salaryService.findById(Long.valueOf(activitiUtils.getBusinessKeyByTaskId(taskId)));
        salary.setTaskId(taskId);
        model.addAttribute("salary", salary);
        return "act/salary/edit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    public R saveOrUpdate(SalaryDO salary) {
        if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
            return R.error(1, "演示系统不允许修改,完整体验请部署程序");
        }
        salary.setCreateDate(new Date());
        salary.setUpdateDate(new Date());
        salary.setCreateBy(ShiroUtils.getUserId().toString());
        salary.setUpdateBy(ShiroUtils.getUserId().toString());
        salary.setDelFlag("1");
        try {
        	salaryService.save(salary);
        	return R.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return R.error();
		}
        
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    public R update(SalaryDO salary) {
        if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
            return R.error(1, "演示系统不允许修改,完整体验请部署程序");
        }
        String taskKey = activitiUtils.getTaskByTaskId(salary.getTaskId()).getTaskDefinitionKey();
        if ("audit2".equals(taskKey)) {
            salary.setHrText(salary.getTaskComment());
        } else if ("audit3".equals(taskKey)) {
            salary.setLeadText(salary.getTaskComment());
        } else if ("audit4".equals(taskKey)) {
            salary.setMainLeadText(salary.getTaskComment());
        } else if("apply_end".equals(salary.getTaskComment())){
            //流程完成，兑现
        }
        try {
        	salaryService.save(salary);
        	return R.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return R.error();
		}
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    public R remove(Long id) {
        if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
            return R.error(1, "演示系统不允许修改,完整体验请部署程序");
        }
        try {
        	salaryService.deleteById(id);
        	return R.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return R.error();
		}
        
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    @ResponseBody
    public R remove(@RequestParam("ids[]") Long[] ids) {
        if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
            return R.error(1, "演示系统不允许修改,完整体验请部署程序");
        }
        try {
        	salaryService.deleteByIds(ids);
            return R.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return R.error();
		}
        
    }

}
