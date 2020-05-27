package com.caigen.graduationproject.dao;

import com.caigen.graduationproject.mapper.UserMapper;
import com.caigen.graduationproject.model.UserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author Yasin
 * @version 1.0
 * @date 2020-05-21 08:54
 * @description
 */
@Repository
@Transactional
public class UserDao extends JdbcDaoSupport {
    @Autowired
    public UserDao(DataSource dataSource){
        this.setDataSource(dataSource);
    }

    public List<UserBean> findAllMessages(){
        String sql = UserMapper.SQL;
        UserMapper mapper = new UserMapper();
        List<UserBean> userList =  this.getJdbcTemplate().query(sql,mapper);
        return userList;
    }
}
