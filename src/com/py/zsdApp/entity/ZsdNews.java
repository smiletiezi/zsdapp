package com.py.zsdApp.entity;

public class ZsdNews {
    private Integer id;

    private String dbTitile;

    private String dbText;

    private String dbImg;

    private String dbVideo;

    private String createDate;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDbTitile() {
        return dbTitile;
    }

    public void setDbTitile(String dbTitile) {
        this.dbTitile = dbTitile;
    }

    public String getDbText() {
        return dbText;
    }

    public void setDbText(String dbText) {
        this.dbText = dbText;
    }

    public String getDbImg() {
        return dbImg;
    }

    public void setDbImg(String dbImg) {
        this.dbImg = dbImg;
    }

    public String getDbVideo() {
        return dbVideo;
    }

    public void setDbVideo(String dbVideo) {
        this.dbVideo = dbVideo;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}