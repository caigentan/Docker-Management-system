package com.caigen.graduationproject.dao;

import com.caigen.graduationproject.mapper.SystemUserMapper;
import com.caigen.graduationproject.model.SystemUserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

/**
 * @author Yasin
 * @version 1.0
 * @date 2020-04-09 17:34
 * @description
 */
@Repository
@Transactional
public class SystemUserDao extends JdbcDaoSupport {
    @Autowired
    public SystemUserDao(DataSource dataSource){
        this.setDataSource(dataSource);
    }

    public SystemUserBean findUserAccount(String userName){
        String sql = SystemUserMapper.BASE_SQL+" where u.User_Name = ?";

        Object[] params = new Object[]{userName};
        SystemUserMapper mapper = new SystemUserMapper();
        try{
            SystemUserBean userInfo = this.getJdbcTemplate().queryForObject(sql,params,mapper);
            return userInfo;
        } catch (EmptyResultDataAccessException e){
            return null;//日志待记录
        }
    }
}
