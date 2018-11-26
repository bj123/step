package com.aiwsport.core.entity;

public class Template {
    private Integer id;

    private Integer introductionid;

    private String isfree;

    private String showmoduletype;

    private String type;

    private Double saleprice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIntroductionid() {
        return introductionid;
    }

    public void setIntroductionid(Integer introductionid) {
        this.introductionid = introductionid;
    }

    public String getIsfree() {
        return isfree;
    }

    public void setIsfree(String isfree) {
        this.isfree = isfree;
    }

    public String getShowmoduletype() {
        return showmoduletype;
    }

    public void setShowmoduletype(String showmoduletype) {
        this.showmoduletype = showmoduletype;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getSaleprice() {
        return saleprice;
    }

    public void setSaleprice(Double saleprice) {
        this.saleprice = saleprice;
    }
}