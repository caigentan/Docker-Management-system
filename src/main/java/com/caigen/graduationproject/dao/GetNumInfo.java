package com.caigen.graduationproject.dao;

import com.caigen.graduationproject.comhandle.ComHandle;
import com.caigen.graduationproject.impls.DockerInit;
import org.springframework.stereotype.Component;

import java.util.*;
/**
 * @author  yasin
 * @date  2020-02-17 20:56
 * @version 1.0
 */

@Component
public class GetNumInfo extends ComHandle implements DockerInit{

    //当docker未运行时会报错，需要处理

    private int imagesNum = dockerClient
            .listImagesCmd()
            .withShowAll(true)
            .exec()
            .size();
    private int containersNum = info.getContainers();
    private int stoppedContainers = info.getContainersStopped();
    private int runningContainerNum = dockerClient
            .listContainersCmd()
            .withShowAll(true)
                .withStatusFilter(Collections.singleton("running")).exec().size();
    /*private int runningContainerNum = info.getContainersRunning();*/

    //获取镜像数
    public int GetImagesNum(){
        return imagesNum;
    }

    //获取容器数量
    public int getContainerNum(){
        return containersNum;
    }

    //获取暂停的容器数
    public int getPausedContainerNum(){
        return stoppedContainers;
    }

    //获取运行中的容器数
    public int getRunningContainerNum(){
        return runningContainerNum;
    }

    public Map<String,Object> getAllWithMap(){
        Map<String,Object> numMap = new HashMap<>();
        numMap.put("imagesNum",imagesNum);
        numMap.put("containersNum",containersNum);
        numMap.put("stoppedContainers", stoppedContainers);
        numMap.put("runningContainerNum",runningContainerNum);
        return numMap;
    }

}
