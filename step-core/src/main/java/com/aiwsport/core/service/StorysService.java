package com.aiwsport.core.service;

import com.aiwsport.core.constant.ResultMsg;
import com.aiwsport.core.entity.*;
import com.aiwsport.core.mapper.*;
import com.aiwsport.core.utils.CommonUtil;
import com.aiwsport.core.utils.DataTypeUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangjian9 on 2018/9/10.
 */
@Service
public class StorysService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TemplateMapper templateMapper;

    @Autowired
    private ShareMapper shareMapper;

    @Autowired
    private MoneyLogMapper moneyLogMapper;

    @Autowired
    private IntroductionMapper introductionMapper;

    @Autowired
    private StoryMapper storyMapper;

    @Autowired
    private CommentMapper commentMapper;


    private static Logger logger = LogManager.getLogger();

    public JSONObject decrypt(String encryptedData, String iv, String sessionKey){
        return CommonUtil.decrypt(encryptedData, iv, sessionKey);
    }

    public User login(String openid, String province,
                      String avatarUrl, String nickName, String country, String city, String gender)throws Exception {
        User user = userMapper.getByOpenId(openid);
        if (user == null) {
            // 创建用户
            user = new User();
            user.setOpenid(openid);
            user.setCoinnum(0.00);
            user.setRewardcoin(0.00);
            user.setAvatarurl(avatarUrl);
            user.setCity(city);
            user.setGender(gender);
            user.setCountry(country);
            user.setNickname(nickName);
            user.setProvince(province);
            user.setLikeid("");
            user.setBuytemplateid("");
            user.setCreatetime(DataTypeUtils.formatCurDateTime());
            CommonUtil.buildBaseInfo(user);
            userMapper.insert(user);
        } else {
            if (!avatarUrl.equals(user.getAvatarurl())) {
                user.setAvatarurl(avatarUrl);
            }
            if (!nickName.equals(user.getNickname())) {
                user.setNickname(nickName);
            }
            user.setModifytime(DataTypeUtils.formatCurDateTime());
            userMapper.updateByPrimaryKey(user);
        }
        return user;
    }

    public List<Template> getTemplateByShowModuleType(String showModuleType){
        Map<String, String> param = new HashMap<String, String>();
        param.put("showModuleType", showModuleType);
        return templateMapper.search(param);
    }

    public List<Template> getTemplateByShowModuleTypeAndType(String showModuleType, String page, String pageSize){
        Map<String, String> param = new HashMap<String, String>();
        param.put("showModuleType", showModuleType);
        int limitS = (Integer.parseInt(page)-1) * Integer.parseInt(pageSize);
        param.put("limitS", String.valueOf(limitS));
        int limitE = Integer.parseInt(page) * Integer.parseInt(pageSize);
        param.put("limitE", String.valueOf(limitE));
        return templateMapper.search(param);
    }


    public List<Template> getTemplateByTypeAndPage(String templateType, String page, String pageSize){
        Map<String, String> param = new HashMap<String, String>();

        if ("100".equals(templateType)) { // 100 是全部
            param.put("type", "1,2");
        } else if ("200".equals(templateType)) { // 200 是最新
            param.put("type", "1,2,3"); // 逻辑上排除微课
        } else {
            param.put("type", templateType);
        }

        int limitS = (Integer.parseInt(page)-1) * Integer.parseInt(pageSize);
        param.put("limitS", String.valueOf(limitS));
        int limitE = Integer.parseInt(page) * Integer.parseInt(pageSize);
        param.put("limitE", String.valueOf(limitE));
        return templateMapper.search(param);
    }

    public ResultMsg updateChildInfo(Integer userId, String name, String sex, String brithday)throws Exception{
        User user = userMapper.selectByPrimaryKey(userId);
        if (user == null) {
            logger.error("updateChildInfo is error userId " + userId);
            return new ResultMsg(false, 403, "用户不存在");
        }

        user.setChildname(name);
        user.setChildsex(sex);
        user.setChildbirthday(brithday);
        user.setModifytime(DataTypeUtils.formatCurDateTime());
        int flag = userMapper.updateByPrimaryKey(user);
        if (flag == 0) {
            logger.error("updateChildInfo is error name:" + name + ",sex:" + sex + ",brithday:" + brithday);
            return new ResultMsg(false, 403, "更新失败");
        }

        return new ResultMsg("updateChildInfo", true);
    }

    public ResultMsg createShare(Integer invitedUserId, Integer beInvitedUserId)throws Exception{
        Share share = shareMapper.getShareLink(invitedUserId, beInvitedUserId);
        if (share != null) {
            return new ResultMsg(false, 403, "已经邀请");
        }

        Share share1 = shareMapper.getShareLink(beInvitedUserId, invitedUserId);
        if (share1 != null) {
            return new ResultMsg(false, 403, "已经邀请");
        }

        Share newShare = new Share();
        newShare.setInviteduserid(invitedUserId);
        newShare.setBeinviteduserid(beInvitedUserId);
        newShare.setCreatetime(DataTypeUtils.formatCurDateTime());
        CommonUtil.buildBaseInfo(newShare);
        shareMapper.insert(newShare);
        return new ResultMsg("createShareOk", newShare);
    }

    public List<Share> getShareByInvitedUserId(Integer invitedUserId){
        List<Share> shareList = shareMapper.getShareByInvitedUserId(invitedUserId);
        return shareList;
    }

    public Story getPlayInfo(Map<String,Object> map){
        return storyMapper.getPlayInfo(map);
    }

    public ResultMsg returnCash(Integer userId, String real_name, double amount)throws Exception {
        // 查询用户
        User user = userMapper.selectByPrimaryKey(userId);
        double rewardCoin = user.getRewardcoin();
        if (amount > rewardCoin) {
            return new ResultMsg(false, 403, "余额不足");
        }

        // 扣款
        BigDecimal doubleamount = BigDecimal.valueOf(amount);
        BigDecimal doublerewardCoin = BigDecimal.valueOf(rewardCoin);
        BigDecimal res = doublerewardCoin.subtract(doubleamount);
        user.setRewardcoin(res.doubleValue());
        user.setModifytime(DataTypeUtils.formatCurDateTime());
        int upRes = userMapper.updateByPrimaryKey(user);
        if (upRes < 1) {
            return new ResultMsg(false, 403, "体现失败");
        }

        // 扣款记录
        MoneyLog moneyLog = new MoneyLog();
        moneyLog.setBalance(0.00);
        moneyLog.setCashback(0.00);
        moneyLog.setMonetary(0.00);
        moneyLog.setUserid(userId);
        moneyLog.setType("4");
        moneyLog.setWithdraw(amount);
        moneyLog.setRealname(real_name);
        moneyLog.setCreatetime(DataTypeUtils.formatCurDateTime());
        CommonUtil.buildBaseInfo(moneyLog);
        int inRes = moneyLogMapper.insert(moneyLog);
        if (inRes < 1) {
            logger.warn("moneyLogMapper insert is error real_name:" + real_name + ", amount:" + amount + ", userId:" + userId);
        }
        return new ResultMsg("returnCashOK", "");
    }

    public List<MoneyLog> getMoneyLogs(Integer userId){
        List<MoneyLog> moneyLogList = moneyLogMapper.getMoneyLogByUserId(userId);
        return moneyLogList;
    }

    public Template getTemplateById(Integer templateId){
        return templateMapper.selectByPrimaryKey(templateId);
    }

    public List<Introduction> getIntroduction(Integer templateId){
        return introductionMapper.getIntroductionsByTemplateId(templateId);
    }

    public List<Story> getStroysByTemplateId(Integer templateId){
        return storyMapper.getStroysByTemplateId(templateId);
    }

    public List<Comment> getCommentByTemplateId(Integer templateId){
        return commentMapper.selectByTemplateId(templateId);
    }

    public List<Comment> getCommentByStoryId(Integer storyId){
        return commentMapper.getCommentByStoryId(storyId);
    }

    public User getUserInfo(Integer userId){
        return userMapper.selectByPrimaryKey(userId);
    }

    public String getLikedId(Integer id){
        return userMapper.getLikeId(id);
    };

    public Story getStroyById(Integer id){
        return storyMapper.selectByPrimaryKey(id);
    };

    public int updateLike(Integer userId,String likeId){
       return userMapper.updateLike(userId,likeId);
    }

    public ResultMsg getBuyTemplate(Integer userId){
        User user = userMapper.selectByPrimaryKey(userId);
        String[] buyIds = user.getBuytemplateid().split(",");
        List<Template> storyTempList = new ArrayList<Template>();
        List<Template> wkList = new ArrayList<Template>();

        for (String buyId : buyIds) {
            if (StringUtils.isNotBlank(buyId)) {
                Template template = templateMapper.selectByPrimaryKey(Integer.parseInt(buyId));
                if ("4".equals(template.getType())) {
                    wkList.add(template);
                } else {
                    storyTempList.add(template);
                }
            }
        }

        Map<String, Object> res = new HashMap<String, Object>();
        res.put("storytemps", storyTempList);
        res.put("storytemps_count", storyTempList.size());
        res.put("wks", wkList);
        res.put("wks_count", wkList.size());
        return new ResultMsg("getBuyTemplateOK", res);
    }

    public Map<String, List<Story>> getLikeStory(Integer userId){
        User user = userMapper.selectByPrimaryKey(userId);
        Map<String, List<Story>> map = new HashMap<String, List<Story>>();
        List<Story> stories = new ArrayList<Story>();
        List<Story> wks = new ArrayList<Story>();
        if (StringUtils.isNotBlank(user.getLikeid())) {
            String[] likeIds = user.getLikeid().split(",");
            for (String likeId : likeIds) {
                if (StringUtils.isBlank(likeId)) {
                    continue;
                }
                Story story = storyMapper.getStroysByTempIdAndStoryId(Integer.parseInt(likeId.split("#")[0]), Integer.parseInt(likeId.split("#")[1]));
                Template template = templateMapper.selectByPrimaryKey(Integer.parseInt(likeId.split("#")[0]));
                if ("4".equals(template.getType())) {
                    wks.add(story);
                } else {
                    stories.add(story);
                }
            }
        }
        map.put("stories", stories);
        map.put("wks", wks);
        return map;
    }

    public List<CommentBean> getCommentInfo(Integer templateId,Integer storyId){
        return commentMapper.getCommentInfo(templateId,storyId);
    }

    public int insertComment(Comment comment){
        return commentMapper.insert(comment);
    }

}
