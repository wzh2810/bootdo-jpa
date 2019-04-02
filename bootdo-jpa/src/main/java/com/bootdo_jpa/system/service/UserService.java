package com.bootdo_jpa.system.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bootdo_jpa.common.domain.Tree;
import com.bootdo_jpa.common.service.base.ICommonService;
import com.bootdo_jpa.system.domain.DeptDO;
import com.bootdo_jpa.system.domain.UserDO;
import com.bootdo_jpa.system.vo.UserVO;

@Service
public interface UserService extends ICommonService<UserDO> {
	
	List<UserDO> listByDeptId(String deptId);

	UserDO resetPwd(UserVO userVO,UserDO userDO) throws Exception;
	
	UserDO adminResetPwd(UserVO userVO) throws Exception;
	
	Tree<DeptDO> getTree();

	/**
	 * 更新个人信息
	 * @param userDO
	 * @return
	 */
	UserDO updatePersonal(UserDO userDO);

	/**
	 * 更新个人图片
	 * @param file 图片
	 * @param avatar_data 裁剪信息
	 * @param userId 用户ID
	 * @throws Exception
	 */
    Map<String, Object> updatePersonalImg(MultipartFile file, String avatar_data, Long userId) throws Exception;
}
