package com.caigen.graduationproject.mapper;

import com.caigen.graduationproject.model.WriteBoardBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Yasin
 * @version 1.0
 * @date 2020-04-26 21:59
 * @description
 */
public class WriteBoardMapper implements RowMapper<WriteBoardBean> {
    public static final String WRITEBOARD_SQL = "SELECT id,title,message,user_name,create_time FROM writeboard";

    @Override
    public WriteBoardBean mapRow(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("id");
        String title = rs.getString("title");
        String message = rs.getString("message");
        String userName = rs.getString("user_name");
        String createTime = rs.getString("create_time");

        return new WriteBoardBean(id,title,message,userName,createTime);
    }
}
