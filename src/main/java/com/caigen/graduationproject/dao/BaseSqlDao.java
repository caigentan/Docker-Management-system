package com.caigen.graduationproject.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yasin on 2020-02-18 14:42
 */

@Repository
public class BaseSqlDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public static final String NEWUSER_SQL = "INSERT INTO system_user(user_id,user_name,ENCRYTED_PASSWORD,enabled) " +
            "values(?,?,?,?)";
    public static final String NEWUSER_INFO = "INSERT INTO USER_INFO(USER_ID,IMG,ROLE) VALUES(?,?,?)";
    public static final String USER_ROLE = "INSERT INTO USER_ROLE(USER_ID,ROLE_ID) VALUES(?,?)";

    public static final String COMMIT_SUGGEST = "INSERT INTO writeboard(title,message,user_name,create_time) " +
            "VALUES(?,?,?,?)";
    public static final String DEL_MESSAGE_BY_ID = "DELETE FROM writeboard where id=?";

    public static final String COMMON_IMAGES_INFO = "SELECT id,image_name,image_info from common_images";
    public void initLogin(){
        // TODO : 接口，留存，后期可能用到
        //在此提前获取数据库中与登录有关的信息
    }

    public Map<String, Object> getImageinfo(String name){
        String sql = "SELECT * FROM imageinfo where imagename =?";

        Object[] params = new Object[]{name};
        Map<String, Object> imageInfo = new HashMap<>();
        //imageInfo = jdbcTemplate.queryForMap(sql,params);

        try{
            imageInfo = jdbcTemplate.queryForMap(sql,params);
        }catch (EmptyResultDataAccessException e){
            String insertSql = "INSERT INTO imageinfo(imagename,imageinfo,imagefrom) values(?,?,?)";
            Object[] iParams =  new Object[]{name,"<h1>最新拉取，尚未注入\"灵魂\"！","HubDocker"};
            jdbcTemplate.update(insertSql,iParams);
            imageInfo = jdbcTemplate.queryForMap(sql,params);
        }
        return imageInfo;
    }

    public void updateImageInfo(String imagename,String newInfo){
        String sql = "update imageinfo set imageinfo='"+newInfo+"' where imagename='"+imagename+"'";
        jdbcTemplate.execute(sql);
    }

    public Map<String, Object> getNotice(){
        String sql = "SELECT * FROM notice";
        Map<String,Object> notice = new HashMap<>();

        try {
            notice = jdbcTemplate.queryForMap(sql);

        }catch (Exception e){
            e.printStackTrace();
        }

        return notice;
    }

    public void updateNotice(String id,String content,String date){
        String sql = "UPDATE notice set content=?,date=? where id=?";
        Object[] params = new Object[]{content,date,id};
        jdbcTemplate.update(sql,params);
    }
}
