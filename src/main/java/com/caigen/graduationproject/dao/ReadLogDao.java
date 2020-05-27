package com.caigen.graduationproject.dao;

import org.springframework.stereotype.Service;

import java.io.*;

/**
 * @author Yasin
 * @version 1.0
 * @date 2020-04-25 14:57
 * @description 用于从日志文件查看日志
 */
@Service
public class ReadLogDao {
    private static final String path = "/logs/main.log";

    public String getLog(){
        File file =  new File(path);
        StringBuilder result = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String s = null;
            int i=0;
            while((s=br.readLine())!=null){
                result.append("<p id=\"log"+i+"\">"+s+"</p>");
                ++i;
            }
            br.close();
            return result.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
