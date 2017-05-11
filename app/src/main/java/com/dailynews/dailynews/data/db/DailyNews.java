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

    private String title;

    private String imageUrl;

    private String cotentUrl;

    private Date date;

    @Generated(hash = 182913529)
    public DailyNews(Long id, String title, String imageUrl, String cotentUrl,
                     Date date) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.cotentUrl = cotentUrl;
        this.date = date;
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

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}

