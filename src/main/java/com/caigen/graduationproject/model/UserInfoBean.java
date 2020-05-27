package com.caigen.graduationproject.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Yasin
 * @version 1.0
 * @date 2020-04-21 18:17
 * @description
 */
public class UserInfoBean {
    @JsonProperty("id")
    private Long user_id;

    @JsonProperty("img")
    private String img;

    private String profile;

    private String email;

    public UserInfoBean(Long user_id,String img,String profile,String email){
        this.user_id = user_id;
        this.img = img;
        this.profile = profile;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
