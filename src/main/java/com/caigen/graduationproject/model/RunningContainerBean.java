package com.caigen.graduationproject.model;

/**
 * Created by Yasin on 2020-03-09 23:03
 */
public class RunningContainerBean {
    private String containerId;
    private String containerName;
    private String createdTime;
    private String imageName;
    private String containerPort;

    public String getContainerPort() {
        return containerPort;
    }

    public void setContainerPort(String containerPort) {
        this.containerPort = containerPort;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    private String imageId;

    public String getContainerId() {
        return containerId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }

    public String getContainerName() {
        return containerName;
    }

    public void setContainerName(String containerName) {
        this.containerName = containerName;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    //不输出原镜像ID，名
    @Override
    public String toString() {
        return "RunningContainerBean{" +
                "containerId='" + containerId + '\'' +
                ", containerName='" + containerName + '\'' +
                ", createdTime='" + createdTime + '\'' +
                '}';
    }
}
