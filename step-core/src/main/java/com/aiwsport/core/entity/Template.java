package com.aiwsport.core.entity;

public class Template {
    private Integer id;

    private Integer introductionid;

    private String templatename;

    private String templateurl;

    private String isfree;

    private String showmoduletype;

    private String type;

    private Double saleprice;

    private String modifytime;

    private String createtime;

    private String status;

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

    public String getTemplatename() {
        return templatename;
    }

    public void setTemplatename(String templatename) {
        this.templatename = templatename;
    }

    public String getTemplateurl() {
        return templateurl;
    }

    public void setTemplateurl(String templateurl) {
        this.templateurl = templateurl;
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

    public String getModifytime() {
        return modifytime;
    }

    public void setModifytime(String modifytime) {
        this.modifytime = modifytime;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}