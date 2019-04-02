package com.bootdo_jpa.system.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.imageio.ImageIO;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.bootdo_jpa.common.config.BootdoJpaConfig;
import com.bootdo_jpa.common.domain.FileDO;
import com.bootdo_jpa.common.domain.Tree;
import com.bootdo_jpa.common.service.FileService;
import com.bootdo_jpa.common.service.base.AbsCommonService;
import com.bootdo_jpa.common.utils.BuildTree;
import com.bootdo_jpa.common.utils.FileType;
import com.bootdo_jpa.common.utils.FileUtil;
import com.bootdo_jpa.common.utils.ImageUtils;
import com.bootdo_jpa.common.utils.MD5Utils;
import com.bootdo_jpa.common.utils.StringUtils;
import com.bootdo_jpa.system.dao.DeptRepository;
import com.bootdo_jpa.system.dao.UserRepository;
import com.bootdo_jpa.system.dao.UserRoleRepository;
import com.bootdo_jpa.system.domain.DeptDO;
import com.bootdo_jpa.system.domain.UserDO;
import com.bootdo_jpa.system.domain.UserRoleDO;
import com.bootdo_jpa.system.service.DeptService;
import com.bootdo_jpa.system.service.UserService;
import com.bootdo_jpa.system.vo.UserVO;
import com.github.wenhao.jpa.Specifications;

@Transactional
@Service
public class UserServiceImpl extends AbsCommonService<UserDO> implements UserService {
	
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserRoleRepository userRoleRepository;
    @Autowired
    DeptRepository deptRepository;
    @Autowired
    private FileService sysFileService;
    @Autowired
    private BootdoJpaConfig bootdoConfig;
    @Autowired
    DeptService deptService;

    @Override
//    @Cacheable(value = "user",key = "#id")
    public UserDO findById(Long id) {
        List<Long> roleIds = userRoleRepository.listRoleIdByUserId(id);
        UserDO user = userRepository.getOne(id);
        user.setDeptName(deptRepository.getOne(user.getDeptId()).getName());
        user.setRoleIds(roleIds);
        return user;
    }

    @Override
    public List<UserDO> listByDeptId(String deptId) {
    	Specification<UserDO> spec = Specifications.<UserDO>and().eq("deptId", deptId).build();
        if (StringUtils.isNotBlank(deptId)) {
            Long deptIdl = Long.valueOf(deptId);
            List<Long> childIds = deptService.listChildrenIds(deptIdl);
            childIds.add(deptIdl);
            spec = Specifications.<UserDO>and().eq("deptIds", childIds).build();
            //map.put("deptId", null);
            //map.put("deptIds",childIds);
        }
        return userRepository.findAll(spec);
    }

    @Transactional
    @Override
    public UserDO save(UserDO user) {
    	UserDO userDO = userRepository.save(user);
        Long userId = user.getUserId();
        List<Long> roles = user.getRoleIds();
        userRoleRepository.deleteByUserId(userId);
        List<UserRoleDO> list = new ArrayList<>();
        for (Long roleId : roles) {
            UserRoleDO ur = new UserRoleDO();
            ur.setUserId(userId);
            ur.setRoleId(roleId);
            list.add(ur);
        }
        if (list.size() > 0) {
        	userRoleRepository.saveAll(list);
        }
        return userDO;
    }

    //    @CacheEvict(value = "user")
    @Override
    @Transactional
    public void deleteById(Long userId) {
    	userRoleRepository.deleteByUserId(userId);
    	userRepository.deleteById(userId);
    }

    @Override
    @Transactional
    public UserDO resetPwd(UserVO userVO, UserDO userDO) throws Exception {
        if (Objects.equals(userVO.getUserDO().getUserId(), userDO.getUserId())) {
            if (Objects.equals(MD5Utils.encrypt(userDO.getUsername(), userVO.getPwdOld()), userDO.getPassword())) {
                userDO.setPassword(MD5Utils.encrypt(userDO.getUsername(), userVO.getPwdNew()));
                return userRepository.save(userDO);
            } else {
                throw new Exception("输入的旧密码有误！");
            }
        } else {
            throw new Exception("你修改的不是你登录的账号！");
        }
    }

    @Override
    @Transactional
    public UserDO adminResetPwd(UserVO userVO) throws Exception {
        UserDO userDO = findById(userVO.getUserDO().getUserId());
        if ("admin".equals(userDO.getUsername())) {
            throw new Exception("超级管理员的账号不允许直接重置！");
        }
        userDO.setPassword(MD5Utils.encrypt(userDO.getUsername(), userVO.getPwdNew()));
        return userRepository.save(userDO);


    }

    @Transactional
    @Override
    public void deleteByIds(Long... userIds) {
    	userRoleRepository.deleteByUserIdIn(userIds);
    	userRepository.deleteByUserIdIn(userIds);
    }

    @Override
    public Tree<DeptDO> getTree() {
        List<Tree<DeptDO>> trees = new ArrayList<Tree<DeptDO>>();
        List<DeptDO> depts = deptRepository.findAll();
        Long[] pDepts = deptRepository.findDistinctParentId();
        Long[] uDepts = userRepository.listAllDept();
        Long[] allDepts = (Long[]) ArrayUtils.addAll(pDepts, uDepts);
        for (DeptDO dept : depts) {
            if (!ArrayUtils.contains(allDepts, dept.getDeptId())) {
                continue;
            }
            Tree<DeptDO> tree = new Tree<DeptDO>();
            tree.setId(dept.getDeptId().toString());
            tree.setParentId(dept.getParentId().toString());
            tree.setText(dept.getName());
            Map<String, Object> state = new HashMap<>(16);
            state.put("opened", true);
            state.put("mType", "dept");
            tree.setState(state);
            trees.add(tree);
        }
        List<UserDO> users = userRepository.findAll();
        for (UserDO user : users) {
            Tree<DeptDO> tree = new Tree<DeptDO>();
            tree.setId(user.getUserId().toString());
            tree.setParentId(user.getDeptId().toString());
            tree.setText(user.getName());
            Map<String, Object> state = new HashMap<>(16);
            state.put("opened", true);
            state.put("mType", "user");
            tree.setState(state);
            trees.add(tree);
        }
        // 默认顶级菜单为０，根据数据库实际情况调整
        Tree<DeptDO> t = BuildTree.build(trees);
        return t;
    }

    @Override
    @Transactional
    public UserDO updatePersonal(UserDO userDO) {
        return userRepository.save(userDO);
    }

    @Override
    @Transactional
    public Map<String, Object> updatePersonalImg(MultipartFile file, String avatar_data, Long userId) throws Exception {
        String fileName = file.getOriginalFilename();
        fileName = FileUtil.renameToUUID(fileName);
        FileDO sysFile = new FileDO(FileType.fileType(fileName), "/files/" + fileName, new Date());
        //获取图片后缀
        String prefix = fileName.substring((fileName.lastIndexOf(".") + 1));
        String[] str = avatar_data.split(",");
        //获取截取的x坐标
        int x = (int) Math.floor(Double.parseDouble(str[0].split(":")[1]));
        //获取截取的y坐标
        int y = (int) Math.floor(Double.parseDouble(str[1].split(":")[1]));
        //获取截取的高度
        int h = (int) Math.floor(Double.parseDouble(str[2].split(":")[1]));
        //获取截取的宽度
        int w = (int) Math.floor(Double.parseDouble(str[3].split(":")[1]));
        //获取旋转的角度
        int r = Integer.parseInt(str[4].split(":")[1].replaceAll("}", ""));
        try {
            BufferedImage cutImage = ImageUtils.cutImage(file, x, y, w, h, prefix);
            BufferedImage rotateImage = ImageUtils.rotateImage(cutImage, r);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(rotateImage, prefix, out);
            //转换后存入数据库
            byte[] b = out.toByteArray();
            FileUtil.uploadFile(b, bootdoConfig.getUploadPath(), fileName);
        } catch (Exception e) {
            throw new Exception("图片裁剪错误！！");
        }
        Map<String, Object> result = new HashMap<>();
        if (sysFileService.save(sysFile).getId() != null) {
            UserDO userDO = new UserDO();
            userDO.setUserId(userId);
            userDO.setPicId(sysFile.getId());
            if (userRepository.save(userDO).getUserId() != null) {
                result.put("url", sysFile.getUrl());
            }
        }
        return result;
    }

    @Override
	public JpaRepository<UserDO, Long> getDao() {
		return userRepository;
	}

	@Override
	public JpaSpecificationExecutor<UserDO> getDao2() {
		return userRepository;
	}

}
