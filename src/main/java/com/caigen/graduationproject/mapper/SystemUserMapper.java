package com.caigen.graduationproject.mapper;

import com.caigen.graduationproject.model.SystemUserBean;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Yasin
 * @version 1.0
 * @date 2020-04-09 17:38
 * @description
 */
public class SystemUserMapper implements RowMapper<SystemUserBean> {
    public static final String BASE_SQL="SELECT u.User_Id,u.User_Name,u.Encryted_Password,u.enabled from system_user u";

    @Override
    public SystemUserBean mapRow(ResultSet resultSet, int i) throws SQLException {
        Long userId = resultSet.getLong("User_Id");
        String userName = resultSet.getString("User_Name");
        String encrytedPassword = resultSet.getString("Encryted_Password");
        String enabled = resultSet.getString("enabled");
        return new SystemUserBean(userId,userName,encrytedPassword,enabled);
    }
}
