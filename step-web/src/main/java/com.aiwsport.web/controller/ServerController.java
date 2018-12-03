package com.aiwsport.web.controller;

import com.aiwsport.core.constant.ResultMsg;
import com.aiwsport.core.entity.Share;
import com.aiwsport.core.entity.Template;
import com.aiwsport.core.entity.User;
import com.aiwsport.core.service.StorysService;
import com.aiwsport.core.service.SysInfoService;
import com.aiwsport.core.showmodel.CourseObj;
import com.aiwsport.core.showmodel.InitObj;
import com.aiwsport.core.showmodel.ShareInfoObj;
import com.aiwsport.core.utils.CommonUtil;
import com.aiwsport.web.utlis.ParseUrl;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
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

    private static final String URL1 = "https://api.weixin.qq.com/sns/jscode2session?appid=wx2a3f4b2e7fe9c09b&secret=3a499d875c3b6cabecf1f8f6e9608f92&js_code=";
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
        try {
            return storysService.createShare(invitedUserId, beInvitedUserId);
        } catch (Exception e) {
            logger.error("createShare is error " + e.getMessage(), e);
            return new ResultMsg(false, 403, "创建邀请关系失败");
        }
    }

    @RequestMapping(value = "/story/getShareInfo.json")
    public ResultMsg getShareInfo(Integer userId) {
        ShareInfoObj shareInfoObj = new ShareInfoObj();
        try {
            // 获取邀请的人集合
            List<Share> shares = storysService.getShareByInvitedUserId(userId);
            List<User> shareUserList = new ArrayList<User>();
            for (Share share : shares) {
                User user = storysService.getUserInfo(share.getBeinviteduserid());
                shareUserList.add(user);
            }
            shareInfoObj.setShareList(shareUserList);

            // 获取邀请获得的奖励金额
            User user = storysService.getUserInfo(userId);
            shareInfoObj.setMyUser(user);
        } catch (Exception e) {
            logger.error("getShareInfo is error " + e.getMessage(), e);
            return new ResultMsg(false, 403, "获取邀请关系信息");
        }
        return new ResultMsg("getShareInfoOK", shareInfoObj);
    }

    @RequestMapping("/test.json")
    public ResultMsg test() throws Exception{
        // 此方法仅适用于JdK1.6及以上版本
        Desktop.getDesktop().browse(new URL("https://blog.csdn.net/xu_san_duo/article/details/78084582").toURI());
        Robot robot = new Robot();
        robot.delay(10000);
        Dimension d = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
        int width = (int) d.getWidth();
        int height = (int) d.getHeight();
        // 最大化浏览器
        robot.keyRelease(KeyEvent.VK_F11);
        robot.delay(2000);
        Image image = robot.createScreenCapture(new Rectangle(0, 0, width,
                height));
        BufferedImage bi = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.createGraphics();
        g.drawImage(image, 0, 0, width, height, null);
        // 保存图片
        ImageIO.write(bi, "jpg", new File("/home/vsftpd/test/google"+System.currentTimeMillis()+".jpg"));
        return new ResultMsg("服务启动成功", 9276);
    }

}
