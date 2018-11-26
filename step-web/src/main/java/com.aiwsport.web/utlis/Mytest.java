package com.aiwsport.web.utlis;

import com.alibaba.fastjson.JSONObject;

import java.io.*;

/**
 * Created by yangjian9 on 2018/8/13.
 */
public class Mytest {



    public static void main(String[] args){
        try {
            FileInputStreamDemo("/Users/yangjian9/Downloads/tao_uid_bind_data/signin.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public static void FileInputStreamDemo(String path) throws IOException {
        String url = "http://api.weibo.com/taccount/v2/get.json?source=3439264077&uid=";
        File file = new File(path);
        BufferedReader reader = null;
        String temp = null;
        int line = 1;
        FileWriter fw=null;
        BufferedWriter bw = null;
        try {
            fw=new FileWriter(new File("/Users/yangjian9/Downloads/tao_uid_bind_data/signin_ali_id.txt"));
            //写入中文字符时会出现乱码
            bw=new BufferedWriter(fw);

            reader = new BufferedReader(new FileReader(file));
            while ((temp = reader.readLine()) != null) {
                System.out.println("line" + line + ":" + temp);
                String ali_id = "";
                String res = ParseUrl.getDataFromUrl(url + temp);
                JSONObject jsonObject = JSONObject.parseObject(res);
                if (jsonObject != null && "true".equals(jsonObject.get("result").toString())) {
                    JSONObject alipayObj = (JSONObject)jsonObject.get("alipay");
                    if (alipayObj != null) {

                        Object obj = alipayObj.get("ali_id");
                        if (obj != null) {
                            ali_id = obj.toString();

                        }

                    }
                }

                String writeInfo = temp + "       ali_id= " + ali_id;
                bw.write(writeInfo+"\t\n");
                line++;
            }

            bw.close();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            bw.close();
            fw.close();
        }
    }

}
