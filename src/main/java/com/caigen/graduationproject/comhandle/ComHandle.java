package com.caigen.graduationproject.comhandle;

import java.text.SimpleDateFormat;

/**
 * Created by Yasin on 2020-02-25 20:36
 */
public class ComHandle {
    public SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    //字符串数组转字符串
    public String strarrayToString(String str[]){
        String result = "";
        for(String i:str){
            result+=i;
        }
        return result;
    }


}
