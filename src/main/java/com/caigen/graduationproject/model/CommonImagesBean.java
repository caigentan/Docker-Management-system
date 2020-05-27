package com.caigen.graduationproject.model;

/**
 * @author Yasin
 * @version 1.0
 * @date 2020-04-27 21:46
 * @description 常见镜像处理
 */
public class CommonImagesBean {
    private int id;
    private String imageName;
    private String imageInfo;

    public CommonImagesBean(int id,String imageName, String imageInfo) {
        this.id = id;
        this.imageName = imageName;
        this.imageInfo = imageInfo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageInfo() {
        return imageInfo;
    }

    public void setImageInfo(String imageInfo) {
        this.imageInfo = imageInfo;
    }
}
