package com.aiwsport.core.entity;

public class Share {
    private Integer id;

    private Integer inviteduserid;

    private Integer beinviteduserid;

    private String createtime;

    private String modifytime;

    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getInviteduserid() {
        return inviteduserid;
    }

    public void setInviteduserid(Integer inviteduserid) {
        this.inviteduserid = inviteduserid;
    }

    public Integer getBeinviteduserid() {
        return beinviteduserid;
    }

    public void setBeinviteduserid(Integer beinviteduserid) {
        this.beinviteduserid = beinviteduserid;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getModifytime() {
        return modifytime;
    }

    public void setModifytime(String modifytime) {
        this.modifytime = modifytime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}