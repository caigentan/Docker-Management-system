package com.caigen.graduationproject.dao;

import com.caigen.graduationproject.mapper.UserInfoMapper;
import com.caigen.graduationproject.model.UserInfoBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

/**
 * @author Yasin
 * @version 1.0
 * @date 2020-04-21 18:36
 * @description
 */
@Repository
@Transactional
public class UserInfoDao extends JdbcDaoSupport{
    Logger LOG = LoggerFactory.getLogger(UserInfoDao.class);
    @Autowired
    public UserInfoDao(DataSource dataSource){
        this.setDataSource(dataSource);
    }

    public UserInfoBean getUserinfo(String userName) {
        String sql = "select u.user_id,u.img,u.profile,u.email from user_info u ,system_user s " +
                "where u.user_id = s.user_id and s.user_name=?";
        Object[] params = new Object[]{userName};
        UserInfoMapper mapper = new UserInfoMapper();
        UserInfoBean userInfo = null;
        try {
            userInfo = this.getJdbcTemplate().queryForObject(sql,params,mapper);
        }catch (EmptyResultDataAccessException e){
            LOG.info("error,Reason:EmptyResultDataAccessException");
        }
        return userInfo;
    }

    public void setUserinfo(String userName,String img,String profile,String email){
        String sql = "UPDATE user_info SET img=?,profile=?,email=? WHERE user_id=" +
                "(SELECT USER_ID FROM SYSTEM_USER WHERE USER_NAME=?)";
        Object[] params = new Object[]{img,profile,email,userName};
        this.getJdbcTemplate().update(sql,params);
    }
}
