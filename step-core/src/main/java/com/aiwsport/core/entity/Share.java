package com.aiwsport.core.entity;

public class Share {
    private Integer id;

    private Integer inviteduserid;

    private Integer beinviteduserid;

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
}