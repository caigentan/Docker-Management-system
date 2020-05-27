package com.caigen.graduationproject.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author Yasin
 * @version 1.0
 * @date 2020-04-09 18:43
 * @description
 */
public class EncrytedPasswordUtils {
    public static String encrytePassword(String password){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    public static void main(String[] args) {
        String password = "123";
        String encrtedPassword = encrytePassword(password);

        System.out.println("Encryted Password:"+encrtedPassword);
    }
}
