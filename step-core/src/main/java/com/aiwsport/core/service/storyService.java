package com.aiwsport.core.service;

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
public class StoryService {

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

}
