package com.bootdo_jpa.oa.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.bootdo_jpa.common.service.DictService;
import com.bootdo_jpa.common.service.base.AbsCommonService;
import com.bootdo_jpa.common.utils.DateUtils;
import com.bootdo_jpa.common.utils.PageUtils;
import com.bootdo_jpa.oa.dao.NotifyRecordRepository;
import com.bootdo_jpa.oa.dao.NotifyRepository;
import com.bootdo_jpa.oa.domain.NotifyDO;
import com.bootdo_jpa.oa.domain.NotifyDTO;
import com.bootdo_jpa.oa.domain.NotifyRecordDO;
import com.bootdo_jpa.oa.service.NotifyService;
import com.bootdo_jpa.system.dao.UserRepository;
import com.bootdo_jpa.system.domain.UserDO;
import com.bootdo_jpa.system.service.SessionService;
import com.github.wenhao.jpa.Specifications;

@Service
public class NotifyServiceImpl extends AbsCommonService<NotifyDO> implements NotifyService {
	
    @Autowired
    private NotifyRepository notifyRepository;
    @Autowired
    private NotifyRecordRepository notifyRecordRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DictService dictService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private SimpMessagingTemplate template;

    @Override
    @Transactional
    public NotifyDO findById(Long id) {
        NotifyDO rDO = notifyRepository.getOne(id);
        rDO.setType(dictService.getName("oa_notify_type", rDO.getType()));
        return rDO;
    }
    
    @Transactional(rollbackFor = Exception.class)
    @Override
    public NotifyDO save(NotifyDO notify) {
        notify.setUpdateDate(new Date());
        NotifyDO notifyDo = notifyRepository.save(notify);
        // 保存到接受者列表中
        Long[] userIds = notify.getUserIds();
        Long notifyId = notify.getId();
        List<NotifyRecordDO> records = new ArrayList<>();
        for (Long userId : userIds) {
            NotifyRecordDO record = new NotifyRecordDO();
            record.setNotifyId(notifyId);
            record.setUserId(userId);
            record.setIsRead(0);
            records.add(record);
        }
        notifyRecordRepository.saveAll(records);
        //给在线用户发送通知
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1,1,0, TimeUnit.MILLISECONDS,new LinkedBlockingDeque<>());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                for (UserDO userDO : sessionService.listOnlineUser()) {
                    for (Long userId : userIds) {
                        if (userId.equals(userDO.getUserId())) {
                            template.convertAndSendToUser(userDO.toString(), "/queue/notifications", "新消息：" + notify.getTitle());
                        }
                    }
                }
            }
        });
        executor.shutdown();
        return notifyDo;
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        notifyRecordRepository.deleteByNotifyId(id);
        notifyRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteByIds(Long... ids) {
        notifyRecordRepository.deleteByNotifyIdIn(ids);
        notifyRepository.deleteByIdIn(ids);
    }
    
	@Override
	public long countDTO(Map<String, Object> map) {
		Specification<NotifyRecordDO> spec = Specifications.<NotifyRecordDO>and().eq(!ObjectUtils.isEmpty(map.get("userId")), "userId", map.get("userId"))
				.eq(!ObjectUtils.isEmpty(map.get("isRead")), "isRead", map.get("isRead")).build();
		return notifyRecordRepository.count(spec);
	}
	
	@Override
	public List<NotifyDTO> listDTO(Map<String, Object> map) {
		Specification<NotifyRecordDO> spec = Specifications.<NotifyRecordDO>and()
				.eq(!ObjectUtils.isEmpty(map.get("id")), "id", map.get("id"))
				.eq(!ObjectUtils.isEmpty(map.get("notifyId")), "notifyId", map.get("notifyId"))
				.eq(!ObjectUtils.isEmpty(map.get("isRead")), "isRead", map.get("isRead"))
				.eq(!ObjectUtils.isEmpty(map.get("userId")), "userId", map.get("userId"))
				.eq(!ObjectUtils.isEmpty(map.get("readDate")), "readDate", map.get("readDate")).build();
		List<NotifyRecordDO> notifyRecordDOs = notifyRecordRepository.findAll(spec);
		Set<NotifyDTO> notifyDTOs = new HashSet<NotifyDTO>();
		try {
			for (NotifyRecordDO notifyRecordDO : notifyRecordDOs) {
				NotifyDO notifyDO = notifyRecordDO.getNotifyDO();
				NotifyDTO dto = new NotifyDTO();
				org.springframework.beans.BeanUtils.copyProperties(notifyDO, dto, "createDate");
				notifyDTOs.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<NotifyDTO>(notifyDTOs);
	}

    @Override
    public List<NotifyDO> list(Map<String, Object> map) throws Exception {
    	Example<NotifyDO> ex = this.listExample(map, NotifyDO.class);
        List<NotifyDO> notifys = notifyRepository.findAll(ex);
        for (NotifyDO notify : notifys) {
        	notify.setType(dictService.getName("oa_notify_type", notify.getType()));
        }
        return notifys;
    }

    @Override
    public PageUtils selfList(Map<String, Object> map) {
        List<NotifyDTO> rows = this.listDTO(map);
        for (NotifyDTO notiDTO : rows) {
        	notiDTO.setBefore(DateUtils.getTimeBefore(notiDTO.getUpdateDate()));
        	notiDTO.setSender(userRepository.getOne(notiDTO.getCreateBy()).getName());
        }
        PageUtils page = new PageUtils(rows, this.countDTO(map));
        return page;
    }

    @Override
	public JpaRepository<NotifyDO, Long> getDao() {
		return notifyRepository;
	}

	@Override
	public JpaSpecificationExecutor<NotifyDO> getDao2() {
		return notifyRepository;
	}

}
