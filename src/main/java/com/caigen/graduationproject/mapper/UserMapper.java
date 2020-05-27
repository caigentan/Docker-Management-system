package com.caigen.graduationproject.mapper;

import com.caigen.graduationproject.model.UserBean;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Yasin
 * @version 1.0
 * @date 2020-05-21 08:55
 * @description
 */

public class UserMapper implements RowMapper<UserBean> {
    public static final String SQL = "select ui.user_id,user_name,email,role,enabled from user_info ui,system_user su where ui.user_id = su.user_id";

    @Override
    public UserBean mapRow(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("user_id");
        String userName = rs.getString("user_name");
        String email = rs.getString("email");
        String role = rs.getString("role");
        String enabled = rs.getString("enabled");
        return new UserBean(id,userName,email,role,enabled);
    }
}
