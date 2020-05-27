package com.caigen.graduationproject.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

/**
 * @author Yasin
 * @version 1.0
 * @date 2020-04-27 15:37
 * @description
 */
@Repository
public class SuggestDao extends JdbcDaoSupport {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public SuggestDao(DataSource dataSource){
        this.setDataSource(dataSource);
    }

    public int Commit(String sql,Object[] params){
        return this.jdbcTemplate.update(sql,params);
    }

    public int Delete(String sql,Object[] params){
        return this.jdbcTemplate.update(sql,params);
    }
}
