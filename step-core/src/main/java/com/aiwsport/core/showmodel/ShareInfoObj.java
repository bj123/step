package com.aiwsport.core.showmodel;

import com.aiwsport.core.entity.User;

import java.util.List;

/**
 * Created by yangjian9 on 2018/12/3.
 */
public class ShareInfoObj {

    private List<User> shareList;

    private User myUser;

    private String isShowfriendImg;

    public List<User> getShareList() {
        return shareList;
    }

    public void setShareList(List<User> shareList) {
        this.shareList = shareList;
    }

    public User getMyUser() {
        return myUser;
    }

    public void setMyUser(User myUser) {
        this.myUser = myUser;
    }

    public String getIsShowfriendImg() {
        return isShowfriendImg;
    }

    public void setIsShowfriendImg(String isShowfriendImg) {
        this.isShowfriendImg = isShowfriendImg;
    }
}
