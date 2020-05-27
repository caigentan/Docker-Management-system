package com.caigen.graduationproject.dao;

import com.caigen.graduationproject.impls.DockerInit;

/**
 * @author Yasin
 * @version 1.0
 * @date 2020-04-12 22:56
 * @description
 */
public class UpdateContaierDao {
    public void updateContainr(String containerId){
        DockerInit.dockerClient
                .updateContainerCmd(containerId)
                .withBlkioWeight(300) //设置资源类的权重
                .withCpuShares(512)
                .withCpuPeriod(100000)
                .withCpuQuota(50000)
//                .withCpusetCpus("0") // depends on env
                .withCpusetMems("0")
//                .withMemory(209715200L + 2L)
//                .withMemorySwap(514288000L) Your kernel does not support swap limit capabilities, memory limited without swap.
//                .withMemoryReservation(209715200L)
//                .withKernelMemory(52428800) Can not update kernel memory to a running container, please stop it first.
                .exec();


    }
}
