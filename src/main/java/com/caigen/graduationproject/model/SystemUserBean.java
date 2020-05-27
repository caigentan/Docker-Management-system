package com.caigen.graduationproject.model;

/**
 * @author Yasin
 * @version 1.0
 * @date 2020-04-09 17:39
 * @description
 */
public class SystemUserBean {
    private Long userId;
    private String userName;
    private String encryedPassword;
    private String enabled;

    public  SystemUserBean(){}

    public SystemUserBean(Long userId,String userName,String encryedPassword,String enabled){
        this.userId = userId;
        this.userName = userName;
        this.encryedPassword = encryedPassword;
        this.enabled = enabled;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEncryedPassword() {
        return encryedPassword;
    }

    public void setEncryedPassword(String encryedPassword) {
        this.encryedPassword = encryedPassword;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return this.userName+"/"+this.encryedPassword;
    }
}
