package com.caigen.graduationproject.mapper;

import com.caigen.graduationproject.model.SystemUserBean;
import com.caigen.graduationproject.model.UserInfoBean;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Yasin
 * @version 1.0
 * @date 2020-04-21 20:14
 * @description
 */
public class UserInfoMapper implements RowMapper<UserInfoBean>  {

    @Override
    public UserInfoBean mapRow(ResultSet resultSet, int i) throws SQLException {
        Long userId = resultSet.getLong("User_Id");
        String img = resultSet.getString("img");
        String proFile = resultSet.getString("profile");
        String email = resultSet.getString("email");

        return new UserInfoBean(userId,img,proFile,email);
    }
}
