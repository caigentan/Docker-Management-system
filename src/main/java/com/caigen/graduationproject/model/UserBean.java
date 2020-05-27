package com.caigen.graduationproject.model;

/**
 * @author Yasin
 * @version 1.0
 * @date 2020-05-21 08:50
 * @description
 */
public class UserBean {
    private int id;
    private String userName;
    private String email;
    private String role;
    private String enabled;

    public UserBean(int id,String userName, String email,String role,String enabled){
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.role = role;
        this.enabled = enabled;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

}
