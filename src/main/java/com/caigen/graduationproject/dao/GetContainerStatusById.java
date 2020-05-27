package com.caigen.graduationproject.dao;

import com.caigen.graduationproject.comhandle.ComHandle;
import com.caigen.graduationproject.impls.DockerInit;
import com.github.dockerjava.api.command.InspectContainerResponse;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Yasin
 * @version 1.0
 * @date 2020-04-13 21:38
 * @description
 */
public class GetContainerStatusById extends ComHandle {
    public Map<String,Map<String,Object>> getContainerStatus(String containerId){
        Map<String,Object> status = new HashMap<>(16);
        Map<String,Object> details = new HashMap<>(16);
        Map<String,Map<String,Object>> stusAndDets = new HashMap<>();
        System.out.println(containerId);
        InspectContainerResponse inspectContainer = DockerInit.dockerClient
                .inspectContainerCmd(containerId)
                .exec();

        String containerName = inspectContainer.getName();
        String containerStatus = inspectContainer.getState()
                .getStatus();
        String containerCreated = inspectContainer.getCreated();
        String containerImage = inspectContainer.getImageId();
        status.put("id",containerId);
        status.put("name",containerName.replace("/",""));
        status.put("stus",containerStatus);
        status.put("created",containerCreated.substring(0,19));
        status.put("image",containerImage.replace("sha256:",""));
        String[] cmd = inspectContainer.getConfig().getCmd();
        String[] entrypoint = inspectContainer.getConfig()
                .getEntrypoint();
        String path = inspectContainer.getConfig()
                .getEnv()[0];
        details.put("path",path);
        details.put("cmd",cmd==null? "":new ArrayList<String>(Arrays.asList(cmd)));
        details.put("entrypoint",entrypoint==null?"":new ArrayList<String>(Arrays.asList(entrypoint)));
        stusAndDets.put("status",status);
        stusAndDets.put("details",details);
        return stusAndDets;
    }
}
