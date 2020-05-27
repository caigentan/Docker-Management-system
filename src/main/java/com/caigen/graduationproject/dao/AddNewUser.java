package com.caigen.graduationproject.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

/**
 * @author Yasin
 * @version 1.0
 * @date 2020-04-26 15:14
 * @description
 */
@Repository
public class AddNewUser extends JdbcDaoSupport {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    public AddNewUser(DataSource dataSource){
        this.setDataSource(dataSource);
    }

    public int addNewUser( String sql,Object[] params){
        return jdbcTemplate.update(sql,params);
    }

    public int userRole(String sql,Object[] params){
        return jdbcTemplate.update(sql,params);
    }

    public int addUserInfo(String sql,Object[] params){
        return jdbcTemplate.update(sql,params);
    }
}
