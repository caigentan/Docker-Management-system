package com.caigen.graduationproject.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Yasin on 2020-02-23 20:32
 */
public class ImageBean {

    @JsonProperty("image_id")
    private String imageId;

    @JsonProperty("image_name")
    private String imageName;

    @JsonProperty("image_size")
    private String imageSize;

    @JsonProperty("create_time")
    private String createTime;

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageSize() {
        return imageSize;
    }

    public void setImageSize(String imageSize) {
        this.imageSize = imageSize;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
