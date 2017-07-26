package com.hwqgooo.databinding.model.bean;

/**
 * Created by weiqiang on 2016/8/18.
 */
public class GirlGallery {
    public String href;
    public String dataOriginal;
    public String desc;
    public boolean isRead;

    public GirlGallery(String desc, String dataOriginal, String href) {
        this.desc = desc;
        this.dataOriginal = dataOriginal;
        this.href = href;
        isRead = false;
    }
}
