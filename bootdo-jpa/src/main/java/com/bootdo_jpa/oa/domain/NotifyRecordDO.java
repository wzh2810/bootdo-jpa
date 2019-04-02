package com.bootdo_jpa.oa.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * 通知通告发送记录
 *
 * @author bootdo-jpa huyidao---123@163.com
 * @email huyidao---123@163.com
 * @date 2019-04-01 10:19:23
 */
@Entity
@Table(name = "oa_notify_record")
public class NotifyRecordDO implements Serializable {
	
    private static final long serialVersionUID = 1L;

    /**
     *  编号
     */
    @Id
    @GeneratedValue
    private Long id;
    //通知通告ID
    @Column(name = "notify_id")
    private Long notifyId;
    //接受人
    @Column(name = "user_id")
    private Long userId;
    //阅读标记
    @Column(name = "is_read")
    private Integer isRead;
    //阅读时间
    @Column(name = "read_date")
    private Date readDate;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notify_id", insertable = false, updatable = false)
    @Fetch(FetchMode.JOIN)
    @JsonIgnoreProperties("notifyRecordDOs")
    private NotifyDO notifyDO;

    /**
     * 设置：编号
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取：编号
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置：通知通告ID
     */
    public void setNotifyId(Long notifyId) {
        this.notifyId = notifyId;
    }

    /**
     * 获取：通知通告ID
     */
    public Long getNotifyId() {
        return notifyId;
    }

    /**
     * 设置：接受人
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取：接受人
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置：阅读标记
     */
    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    /**
     * 获取：阅读标记
     */
    public Integer getIsRead() {
        return isRead;
    }

    /**
     * 设置：阅读时间
     */
    public void setReadDate(Date readDate) {
        this.readDate = readDate;
    }

    /**
     * 获取：阅读时间
     */
    public Date getReadDate() {
        return readDate;
    }

    public NotifyDO getNotifyDO() {
		return notifyDO;
	}

	public void setNotifyDO(NotifyDO notifyDO) {
		this.notifyDO = notifyDO;
	}

	@Override
    public String toString() {
        return "NotifyRecordDO{" +
                "id=" + id +
                ", notifyId=" + notifyId +
                ", userId=" + userId +
                ", isRead=" + isRead +
                ", readDate=" + readDate +
                '}';
    }
}
