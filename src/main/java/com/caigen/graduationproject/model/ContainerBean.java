package com.caigen.graduationproject.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

/**
 * Created by Yasin on 2020-03-13 15:09
 */
public class ContainerBean {

    /**
     * @Descriptions:
     * imagename:原始启动的镜像名
     * containerId:容器ID
     * containerName:容器名，自己定义的
     * containerPort:容器所映射的端口
     */

    @JsonProperty("image_name")
    private String imageName;

    @JsonProperty("container_id")
    private String containerId;

    @JsonProperty("container_name")
    private String containerName;

    @JsonProperty("container_port")
    private String containerPort;

    @JsonProperty("create_time")
    private String createTimel;

    @Nullable
    @JsonProperty("status")
    private String status;

    public String getContainerPort() {
        return containerPort;
    }

    public void setContainerPort(String containerPort) {
        this.containerPort = containerPort;
    }

    public String getCreateTimel() {
        return createTimel;
    }

    public void setCreateTimel(String createTimel) {
        this.createTimel = createTimel;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

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

    @Nullable
    public String getStatus() {
        return status;
    }

    public void setStatus(@Nullable String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ContainerBean{" +
                "imageName='" + imageName + '\'' +
                ", containerId='" + containerId + '\'' +
                ", containerName='" + containerName + '\'' +
                ", containerPort='" + containerPort + '\'' +
                ", createTimel='" + createTimel + '\'' +
                '}';
    }

}
