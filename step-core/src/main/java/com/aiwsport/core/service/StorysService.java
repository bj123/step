package com.aiwsport.core.service;

import com.aiwsport.core.constant.ResultMsg;
import com.aiwsport.core.entity.MoneyLog;
import com.aiwsport.core.entity.Share;
import com.aiwsport.core.entity.Template;
import com.aiwsport.core.entity.User;
import com.aiwsport.core.mapper.MoneyLogMapper;
import com.aiwsport.core.mapper.ShareMapper;
import com.aiwsport.core.mapper.TemplateMapper;
import com.aiwsport.core.mapper.UserMapper;
import com.aiwsport.core.utils.CommonUtil;
import com.aiwsport.core.utils.DataTypeUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    public ResultMsg returnCash(Integer userId, String real_name, double amount)throws Exception {
        // 查询用户
        User user = userMapper.selectByPrimaryKey(userId);
        double rewardCoin = user.getRewardcoin();
        if (amount < rewardCoin) {
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
        CommonUtil.buildBaseInfo(moneyLog);
        int inRes = moneyLogMapper.insert(moneyLog);
        if (inRes < 1) {
            logger.warn("moneyLogMapper insert is error real_name:" + real_name + ", amount:" + amount + ", userId:" + userId);
        }
        return new ResultMsg("returnCashOK", "");
    }


    public User getUserInfo(Integer userId){
        return userMapper.selectByPrimaryKey(userId);
    }


}
