package com.aiwsport.core.service;

import com.aiwsport.core.constant.ResultMsg;
import com.aiwsport.core.entity.Template;
import com.aiwsport.core.entity.User;
import com.aiwsport.core.mapper.TemplateMapper;
import com.aiwsport.core.mapper.UserMapper;
import com.aiwsport.core.utils.CommonUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    private static Logger logger = LogManager.getLogger();

    public JSONObject decrypt(String encryptedData, String iv, String sessionKey){
        return CommonUtil.decrypt(encryptedData, iv, sessionKey);
    }

    public User login(String openid, String province,
                      String avatarUrl, String nickName, String country, String city, String gender) {
        User user = userMapper.getByOpenId(openid);
        if (user == null) {
            // 创建用户
            user = new User();
            user.setOpenid(openid);
            user.setCoinnum(0.00);
            user.setAvatarurl(avatarUrl);
            user.setCity(city);
            user.setGender(gender);
            user.setCountry(country);
            user.setNickname(nickName);
            user.setProvince(province);
            user.setLikeid("");
            user.setBuytemplateid("");
            CommonUtil.buildBaseInfo(user);
            userMapper.insert(user);
        } else {
            if (!avatarUrl.equals(user.getAvatarurl())) {
                user.setAvatarurl(avatarUrl);
            }
            if (!nickName.equals(user.getNickname())) {
                user.setNickname(nickName);
            }
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

    public ResultMsg updateChildInfo(Integer userId, String name, String sex, String brithday){
        User user = userMapper.selectByPrimaryKey(userId);
        if (user == null) {
            logger.error("updateChildInfo is error userId " + userId);
            return new ResultMsg(false, 403, "用户不存在");
        }

        user.setChildname(name);
        user.setChildsex(sex);
        user.setChildbirthday(brithday);
        int flag = userMapper.updateByPrimaryKey(user);
        if (flag == 0) {
            logger.error("updateChildInfo is error name:" + name + ",sex:" + sex + ",brithday:" + brithday);
            return new ResultMsg(false, 403, "更新失败");
        }

        return new ResultMsg("updateChildInfo", true);
    }

    public User getUserInfo(Integer userId){
        return userMapper.selectByPrimaryKey(userId);
    }

}
