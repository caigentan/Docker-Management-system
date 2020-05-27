package com.caigen.graduationproject.utils;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @author Yasin
 * @version 1.0
 * @date 2020-04-09 18:50
 * @description
 */
public class WebUtils {

    public static String toString(User user) {
        StringBuilder sb = new StringBuilder();
        sb.append("UserName:").append(user.getUsername());
        Collection<GrantedAuthority> authorities = user.getAuthorities();
        if(authorities != null && !authorities.isEmpty()){
            sb.append("(");
            boolean first = true;
            for(GrantedAuthority authority:authorities){
                if (first){
                    sb.append(authority.getAuthority());
                }else{
                    sb.append(",").append(authority.getAuthority());
                }
            }
            sb.append(")");
        }
        return sb.toString();
    }

}
