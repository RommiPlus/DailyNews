package com.dailynews.dailynews.data.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;

/**
 * Created by 123 on 2017/5/11.
 */
@Entity
public class DailyNews {
    @Id
    private Long id;

    private String topic;

    private String title;

    private String imageUrl;

    private String cotentUrl;

    private Date updateDate;

    @Generated(hash = 888416427)
    public DailyNews(Long id, String topic, String title, String imageUrl,
            String cotentUrl, Date updateDate) {
        this.id = id;
        this.topic = topic;
        this.title = title;
        this.imageUrl = imageUrl;
        this.cotentUrl = cotentUrl;
        this.updateDate = updateDate;
    }

    @Generated(hash = 2073608363)
    public DailyNews() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCotentUrl() {
        return this.cotentUrl;
    }

    public void setCotentUrl(String cotentUrl) {
        this.cotentUrl = cotentUrl;
    }

    public String getTopic() {
        return this.topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Date getUpdateDate() {
        return this.updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

}

