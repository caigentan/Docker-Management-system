package com.caigen.graduationproject.dao;

import com.caigen.graduationproject.impls.DockerInit;

/**
 * @author Yasin
 * @version 1.0
 * @date 2020-04-28 13:50
 * @description
 */
public class StartContainerByIdDao implements DockerInit {
    public StartContainerByIdDao(String id){
        dockerClient.startContainerCmd(id)
                .exec();
    }
}
