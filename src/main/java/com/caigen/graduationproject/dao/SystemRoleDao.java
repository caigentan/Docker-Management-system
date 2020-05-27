package com.caigen.graduationproject.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author Yasin
 * @version 1.0
 * @date 2020-04-09 16:58
 * @description 系统角色获取验证处理
 * @Repository -- 该注解的作用不只是将类识别为Bean，
 *                同时它还能将所标注的类中抛出的数据访问异常封装为 Spring 的数据访问异常类型。
 *                将次数据访问层标注为bean
 * @Transactional -- 根据你的配置，设置是否自动开启事务
 *                   自动提交事务或者遇到异常自动回滚
 */
@Repository
@Transactional
public class SystemRoleDao extends JdbcDaoSupport {
    @Autowired
    public  SystemRoleDao(DataSource dataSource){
        this.setDataSource(dataSource);
    }

    public List<String> getRoleNmaes(Long userId){
        String sql = "select r.Role_Name from User_Role ur,system_role r " +
                "where ur.Role_Id = r.Role_Id and ur.User_Id = ? ";
        Object[] params = new Object[]{userId};

        List<String> roles = this.getJdbcTemplate().queryForList(sql,params,String.class);
        return roles;
    }
}
