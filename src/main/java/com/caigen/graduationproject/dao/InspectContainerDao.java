package com.caigen.graduationproject.dao;

import com.caigen.graduationproject.impls.DockerInit;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dockerjava.api.command.InspectContainerResponse;

/**
 * @author Yasin
 * @version 1.0
 * @date 2020-04-11 17:53
 * @description 获取容器的具体信息，根据id
 */
public class InspectContainerDao implements DockerInit {
    public String inspectContainer(String id){
        String cinfo = "{}";
        try {
            InspectContainerResponse containerResponse = dockerClient.inspectContainerCmd(id).exec();
            cinfo = new ObjectMapper().writeValueAsString(containerResponse);
        } catch (JsonProcessingException e) {
            // TODO 写入日志
            e.printStackTrace();
        } catch (Exception v1){
            v1.printStackTrace();
        }
        return cinfo;
    }
}
