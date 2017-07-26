package com.hwqgooo.databinding.model.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by weiqiang on 2017/2/10.
 */
@Entity
public class GalleryORM {
    @Id
    public String galleryUrl;

    @Generated(hash = 1519968286)
    public GalleryORM(String galleryUrl) {
        this.galleryUrl = galleryUrl;
    }

    @Generated(hash = 251868390)
    public GalleryORM() {
    }

    public String getGalleryUrl() {
        return this.galleryUrl;
    }

    public void setGalleryUrl(String galleryUrl) {
        this.galleryUrl = galleryUrl;
    }
}
