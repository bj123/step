package com.aiwsport.web.controller;

import com.aiwsport.core.constant.ResultMsg;
import com.aiwsport.core.entity.*;
import com.aiwsport.core.service.StorysService;
import com.aiwsport.core.service.SysInfoService;
import com.aiwsport.core.showmodel.*;
import com.aiwsport.core.utils.CommonUtil;
import com.aiwsport.web.utlis.ParseUrl;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 服务操作
 *
 * @author yangjian
 */
@RestController
public class ServerController {

    @Autowired
    private StorysService storysService;

    @Autowired
    private SysInfoService sysInfoService;

    private static final String URL1 = "https://api.weixin.qq.com/sns/jscode2session?appid=wx5f42cfc6108addc3&secret=748d20fdca6ab2a5e49368ccc5e2c2c0&js_code=";
    private static final String URL2 = "&grant_type=authorization_code";

    private static Logger logger = LogManager.getLogger();

    @RequestMapping("/story/decrypt.json")
    public ResultMsg decrypt(String code, String encryptdata, String iv) throws Exception{
        JSONObject userInfoObj = null;
        try {
            long start = System.currentTimeMillis();
            String resUser = ParseUrl.getDataFromUrl((URL1+code+URL2));
            logger.info("------resUser------" + resUser + " ------cost------" + (System.currentTimeMillis()-start));
            JSONObject userInfo = JSONObject.parseObject(resUser);
            String sessionKey = String.valueOf(userInfo.get("session_key"));
            userInfoObj = storysService.decrypt(encryptdata, iv, sessionKey);
        } catch (Exception e) {
            logger.error("decrypt is error " + e.getMessage(), e);
            return new ResultMsg(false, 403, "解密数据用户信息失败");
        }

        if (userInfoObj == null) {
            return new ResultMsg(false, 403, "解密数据用户信息失败");
        }

        return new ResultMsg("decryptOK", userInfoObj);
    }

    @RequestMapping(value = "/story/onlogin.json")
    public ResultMsg onlogin(String openid, String province,
                              String avatarUrl, String nickName, String country, String city, String gender) {
        User user = null;
        InitObj initObj = new InitObj();

        try {
            user = storysService.login(openid, province, avatarUrl, nickName, country, city, gender);
            initObj.setUser(user);
            List<Template> banners = storysService.getTemplateByShowModuleType("4");
            initObj.setBanners(banners);
            List<Template> todayList = storysService.getTemplateByShowModuleType("1");
            initObj.setToday_list(todayList);
            List<Template> parentingList = storysService.getTemplateByShowModuleType("2");
            initObj.setParenting_list(parentingList);
            List<Template> recommendList = storysService.getTemplateByShowModuleTypeAndType("3", "1", CommonUtil.storyConfig.get("CHOICE_LIST_LIMIT"));
            initObj.setRecommend_list(recommendList);

            initObj.setShowConfig(sysInfoService.getStoryConfig());
        } catch (Exception e) {
            logger.error("onlogin is error " + e.getMessage(), e);
            return new ResultMsg(false, 403, "登录失败");
        }

        if (user == null) {
            return new ResultMsg(false, 403, "登录失败");
        }
        return new ResultMsg("onloginOK", initObj);
    }

    @RequestMapping(value = "/story/updateChildInfo.json")
    public ResultMsg updateChildInfo(Integer userId, String name, String sex, String brithday) {
        try {
            ResultMsg resultMsg = storysService.updateChildInfo(userId, name, sex, brithday);
            if (resultMsg.getCode() != 200) {
                logger.error("updateChildInfo is error");
                return new ResultMsg(false, 403, "更新失败");
            }
        } catch (Exception e) {
            logger.error("updateChildInfo is error " + e.getMessage(), e);
            return new ResultMsg(false, 403, "更新失败");
        }

        return new ResultMsg("updateChildInfoOK", true);
    }

    @RequestMapping(value = "/story/getCourse.json")
    public ResultMsg getCourse() {
        CourseObj courseObj = new CourseObj();
        try {
            List<Template> banners = storysService.getTemplateByShowModuleType("5");
            List<Template> all = storysService.getTemplateByShowModuleType("6");
            List<Template> news = new ArrayList<Template>();

            int count = 2;
            if (all.size() < 2) {
                count = all.size();
            }
            for (int i=0; i<count; i++) {
                news.add(all.get(i));
            }

            courseObj.setBanners(banners);
            courseObj.setAll(all);
            courseObj.setNews(news);
        } catch (Exception e) {
            logger.error("getCourse is error " + e.getMessage(), e);
            return new ResultMsg(false, 403, "获取课程失败");
        }

        return new ResultMsg("getCourseOK", courseObj);
    }

    @RequestMapping(value = "/story/getUserInfo.json")
    public ResultMsg getUserInfo(Integer userId) {
        return new ResultMsg("getCourseOK", storysService.getUserInfo(userId));
    }

    @RequestMapping(value = "/story/getTemplatelistByTypeAndPage.json")
    public ResultMsg getTemplatelistByTypeAndPage(String templateType, String pid, String psize) {
        List<Template> templates = null;
        try {
            templates = storysService.getTemplateByTypeAndPage(templateType, pid, psize);
        } catch (Exception e) {
            logger.error("getTemplatelistBypage is error " + e.getMessage(), e);
            return new ResultMsg(false, 403, "获取模板列表失败");
        }

        return new ResultMsg("getTemplatelistBypageOK", templates);
    }

    @RequestMapping(value = "/story/getTemplatelistByShowModuleTypeAndPage.json")
    public ResultMsg getTemplatelistByShowModuleTypeAndPage(String templateType, String pid, String psize) {
        List<Template> templates = null;
        try {
            templates = storysService.getTemplateByShowModuleTypeAndType(templateType, pid, psize);
        } catch (Exception e) {
            logger.error("getTemplatelistBypage is error " + e.getMessage(), e);
            return new ResultMsg(false, 403, "获取模板列表失败");
        }

        return new ResultMsg("getTemplatelistByShowModuleTypeAndPageOK", templates);
    }

    @RequestMapping(value = "/story/createShare.json")
    public ResultMsg createShare(Integer invitedUserId, Integer beInvitedUserId) {
        if (invitedUserId == beInvitedUserId) {
            logger.warn("createShare is error invitedUserId:"+invitedUserId+", beInvitedUserId:"+beInvitedUserId);
            return new ResultMsg(false, 403, "不能邀请自己");
        }
        try {
            return storysService.createShare(invitedUserId, beInvitedUserId);
        } catch (Exception e) {
            logger.error("createShare is error invitedUserId:"+invitedUserId+", beInvitedUserId:"+beInvitedUserId+", errorInfo:" + e.getMessage(), e);
            return new ResultMsg(false, 403, "创建邀请关系失败");
        }
    }

    @RequestMapping(value = "/story/getShareInfo.json")
    public ResultMsg getShareInfo(Integer pid) {
        ShareInfoObj shareInfoObj = new ShareInfoObj();
        try {
            // 获取邀请的人集合
            List<Share> shares = storysService.getShareByInvitedUserId(pid);
            List<User> shareUserList = new ArrayList<User>();
            for (Share share : shares) {
                User user = storysService.getUserInfo(share.getBeinviteduserid());
                shareUserList.add(user);
            }
            shareInfoObj.setShareList(shareUserList);

            // 获取邀请获得的奖励金额
            User user = storysService.getUserInfo(pid);
            shareInfoObj.setMyUser(user);

            String isShowfriendImg = sysInfoService.getStoryConfig().get("IS_SHOW_FRIEND_IMG");
            shareInfoObj.setIsShowfriendImg(isShowfriendImg);
        } catch (Exception e) {
            logger.error("getShareInfo is error " + e.getMessage(), e);
            return new ResultMsg(false, 403, "获取邀请关系信息");
        }
        return new ResultMsg("getShareInfoOK", shareInfoObj);
    }

    @RequestMapping(value = "/story/returnCash.json")
    public ResultMsg returnCash(Integer userId, String real_name, double amount) {
        if (amount < 30) {
            return new ResultMsg(false, 403, "大于30元才能体现");
        }

        try {
            return storysService.returnCash(userId, real_name, amount);
        } catch (Exception e) {
            return new ResultMsg(false, 403, "提现失败");
        }
    }

    @RequestMapping(value = "/story/getMoneyLogs.json")
    public ResultMsg getMoneyLogs(Integer userId) {
        List<MoneyLog> moneyLogs = null;
        try {
            moneyLogs = storysService.getMoneyLogs(userId);
            for (MoneyLog moneyLog : moneyLogs) {
                if ("1".equals(moneyLog.getType())) {
                    moneyLog.setType("充值");
                } else if ("2".equals(moneyLog.getType())) {
                    moneyLog.setType("返现");
                } else if ("3".equals(moneyLog.getType())) {
                    moneyLog.setType("消费");
                } else if ("4".equals(moneyLog.getType())) {
                    moneyLog.setType("提现");
                } else {
                    moneyLog.setType("不明");
                }
            }
        } catch (Exception e) {
            logger.error("getMoneyLogs is error userId:"+userId, e);
            return new ResultMsg(false, 403, "获取余额明细失败");
        }
        return new ResultMsg("getMoneyLogOK", moneyLogs);
    }

    @RequestMapping(value = "/story/getTemplateDesc.json")
    public ResultMsg getTemplateDesc(Integer templateId, Integer userId) {
        DescObj descObj = new DescObj();
        try {
            Template template = storysService.getTemplateById(templateId);
            descObj.setAlbum(template);
            descObj.setShowConfig(sysInfoService.getStoryConfig());

            List<Introduction> introductions = storysService.getIntroduction(templateId);
            descObj.setIntroductions(introductions);

            BigDecimal price = BigDecimal.valueOf(template.getSaleprice());
            BigDecimal ss = BigDecimal.valueOf(0.1);
            BigDecimal shareReward = price.multiply(ss);
            descObj.setShareReward(shareReward.doubleValue()+"");

            User user = storysService.getUserInfo(userId);
            String buytemplateid = user.getBuytemplateid();
            if (buytemplateid != null && StringUtils.isNotBlank(buytemplateid) && buytemplateid.contains(templateId+"")) {
                descObj.setIsbuy("true");
            } else {
                descObj.setIsbuy("false");
            }

            List<Story> stories = storysService.getStroysByTemplateId(templateId);
            descObj.setStories(stories);

            List<Comment> comments = storysService.getCommentByTemplateId(templateId);
            List<CommentBean> showcomments = new ArrayList<CommentBean>();
            for (Comment comment : comments) {
                CommentBean commentBean = new CommentBean();
                commentBean.setContent(comment.getContent());
                commentBean.setCreatetime(comment.getCreatetime());
                commentBean.setUser(storysService.getUserInfo(comment.getUserid()));
                Story story = storysService.getStroyById(comment.getStroyid());
                if (story != null) {
                    commentBean.setStroyname(story.getStoryname());
                }
                showcomments.add(commentBean);
            }
            descObj.setComments(showcomments);

        } catch (Exception e) {
            logger.error("getMoneyLogs is error userId:"+userId, e);
            return new ResultMsg(false, 403, "获取余额明细失败");
        }
        return new ResultMsg("getTemplateDescOK", descObj);
    }

    @RequestMapping(value = "/story/getPlayInfo.json")
    public ResultMsg getPlayInfo(Integer storyId, Integer templateId) {
        PlayObj playObj = new PlayObj();
        try {
            List<Story> stories = storysService.getStroysByTemplateId(templateId);
            playObj.setStories(stories);
            for (int i=0;i<stories.size();i++) {
                if (stories.get(i).getId() == storyId) {
                    playObj.setCurrentStory(stories.get(i));
                    playObj.setSuffix(i);
                }
            }
        } catch (Exception e) {
            return new ResultMsg(false, 403, "获取播放信息失败");
        }
        return new ResultMsg("getPlayInfoOK", playObj);
    }

    @RequestMapping(value = "/story/isLike.json")
    public Boolean isLike(Integer tempId,Integer storyId, Integer userId) {
        try {
           String s = storysService.getLikedId(userId);
           if (StringUtils.isBlank(s)) {
               return false;
           }
           String regex = tempId+"#"+storyId;
           if (s.contains(regex)){
               return true;
           }
           return false;
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return false;
        }
    }

    @RequestMapping(value = "/story/updateLike.json")
    public boolean updateLike(Integer storyId,Integer tempId,Integer userId) {
        try {
            String regex = tempId+"#"+storyId;
            String s = storysService.getLikedId(userId);
            String likeId = "";
            if (StringUtils.isBlank(s)) {
                likeId = regex;
            } else {
                if (s.contains(","+regex)) {
                    likeId = s.replaceAll(","+regex, "");
                } else if (s.contains(regex+",")) {
                    likeId = s.replaceAll(regex+",", "");
                } else {
                    likeId = s + "," + regex;
                }
            }
            int i = storysService.updateLike(userId,likeId);
            if (i > 0){
                return true;
            }
            return false;
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return false;
        }
    }

//    @RequestMapping(value = "/story/isLike.json")
//    public boolean isLike(String storyId, Integer userId) {
//        boolean flag = false;
//        try {
//            User user = storysService.getUserInfo(userId);
//            if (user.getLikeid().contains(storyId)) {
//                flag = true;
//            }
//        } catch (Exception e) {
//            return flag;
//        }
//        return flag;
//    }

    @RequestMapping(value = "/story/getCommentByTemplateId.json")
    public ResultMsg getCommentByTemplateId(Integer templateId,Integer storyId) {
        List<Comment> Comments = null;
        try {
            Comments = storysService.getCommentByTemplateId(templateId);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return new ResultMsg(false, 403, "获取留言信息失败");
        }
        return new ResultMsg("getCommentByTemplateIdOK", Comments);
    }

    @RequestMapping(value = "/story/getCommentInfo.json")
    public ResultMsg getCommentInfo(Integer templateId,Integer storyId) {
        List<CommentBean> Comments = null;
        try {
            Comments = storysService.getCommentInfo(templateId,storyId);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return new ResultMsg(false, 403, "获取留言信息失败");
        }
        return new ResultMsg("getCommentByTemplateIdOK", Comments);
    }


    @RequestMapping(value = "/story/getCommentByStoryId.json")
    public ResultMsg getCommentByStoryId(Integer storyId) {
        List<Comment> Comments = null;
        try {
            Comments = storysService.getCommentByStoryId(storyId);
        } catch (Exception e) {
            return new ResultMsg(false, 403, "获取留言信息失败");
        }
        return new ResultMsg("getCommentByStoryIdOK", Comments);
    }

    @RequestMapping(value = "/story/getBuyTemplate.json")
    public ResultMsg getBuyTemplate(Integer userId){
        return storysService.getBuyTemplate(userId);
    }

    @RequestMapping(value = "/story/getLikeStory.json")
    public ResultMsg getLikeStory(Integer userId){
        List<Story> stories = storysService.getLikeStory(userId);
        return new ResultMsg("getLikeStoryOK", stories);
    }

    @RequestMapping("/test.json")
    public ResultMsg test() throws Exception{
        return new ResultMsg("服务启动成功", 9276);
    }

}
