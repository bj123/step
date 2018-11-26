package com.aiwsport.core.utils;

import com.aiwsport.core.constant.stepConstant;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.reflect.MethodUtils;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.security.InvalidAlgorithmParameterException;

/**
 * 公用工具类
 *
 * @author yangjian9
 */
public class CommonUtil {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(CommonUtil.class);

    /**
     * 构建实体基础属性
     *
     * @param obj
     * @return
     */
    public static void buildBaseInfo(Object obj) {
        try {
            MethodUtils.invokeMethod(obj, "setOpId", 9276L);
            MethodUtils.invokeMethod(obj, "setOpName", "admin");
            MethodUtils.invokeMethod(obj, "setVaildState", stepConstant.ValidState.YES);
            MethodUtils.invokeMethod(obj, "setModifyTime", DataTypeUtils.formatCurDateTime());
        } catch (NoSuchMethodException e) {
            logger.warn("build baseinfo method is not exist");
        } catch (Exception e) {
            logger.error("build baseinfo is error :", e);
        }
    }

    public static void writeFile(String path, String content) {
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            File file = new File(path);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
            fw = new FileWriter(file, true);
            bw = new BufferedWriter(fw);
            bw.write(content);
            bw.flush();
        } catch (Exception e) {
            logger.error("writeFile is error: " + e);
        } finally {
            try {
                bw.close();
                fw.close();
            } catch (IOException e) {
                logger.error("writeFile close is error: " + e);
            }
        }
    }

    public static int decrypt(String encryptedData, String iv, String sessionKey, String token,Integer days){
        JSONObject flagObj = null;
        try {
            byte[] resultByte  = AES.decrypt(Base64.decodeBase64(encryptedData), Base64.decodeBase64(sessionKey),
                    Base64.decodeBase64(iv));
            if(null != resultByte && resultByte.length > 0){
                JSONObject stepJson = JSONObject.parseObject(new String(resultByte, "UTF-8"));
                JSONArray stepInfoList =  (JSONArray) stepJson.get("stepInfoList");

                if(stepInfoList.size()>0){
                    flagObj = (JSONObject)stepInfoList.get(0);
                    for(int i=0;i<stepInfoList.size();i++){
                        // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                        JSONObject job = stepInfoList.getJSONObject(i);
                        if ((Integer)job.get("timestamp") > (Integer)flagObj.get("timestamp")) {
                            flagObj = job;
                        }
                    }
                }
            }else{
                logger.info("解密失败");
            }
        }catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (flagObj == null) {
            return -1;
        }
        int toDayStep = (int)flagObj.get("step");
        return toDayStep;
    }

}
