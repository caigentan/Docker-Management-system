package com.caigen.graduationproject.service;

import com.caigen.graduationproject.dao.SystemRoleDao;
import com.caigen.graduationproject.dao.SystemUserDao;
import com.caigen.graduationproject.model.SystemUserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotAuthorizedException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yasin
 * @version 1.0
 * @date 2020-04-09 17:58
 * @description
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SystemUserDao systemUserDao;

    @Autowired
    private SystemRoleDao systemRoleDao;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException{
        SystemUserBean systemUser = this.systemUserDao.findUserAccount(userName);

        if (systemUser == null){
            System.out.println("SystemUser not found:"+userName);
            throw new UsernameNotFoundException("User "+userName+" was not found in the database");
        }
        if (!"1".equals(systemUser.getEnabled())){
            System.out.println("SystemUser not allow:"+userName);
            throw new UsernameNotFoundException("User "+userName+" was not allow login");
        }

        System.out.println("Found User:"+systemUser);

        //角色 ROLE_ADMIN,ROLE_USER...
        List<String> roleNames = this.systemRoleDao.getRoleNmaes(systemUser.getUserId());

        List<GrantedAuthority> grantList = new ArrayList<>();

        if (roleNames != null){
            for(String role : roleNames){
                GrantedAuthority authority = new SimpleGrantedAuthority(role);
                grantList.add(authority);
            }
        }
        UserDetails userDetails = (UserDetails) new User(systemUser.getUserName(),
                systemUser.getEncryedPassword(),grantList);

        return userDetails;
    }
}
