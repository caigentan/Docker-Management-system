package com.caigen.graduationproject.model;

/**
 * @author Yasin
 * @version 1.0
 * @date 2020-04-26 21:52
 * @description
 */
public class WriteBoardBean {
    private int id;
    private String title;
    private String message;
    private String userName;
    private String createTime;

    public WriteBoardBean(int id,String title, String message, String userName, String createTime) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.userName = userName;
        this.createTime = createTime;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
