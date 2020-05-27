package com.caigen.graduationproject.dao;

import com.caigen.graduationproject.model.ContainerBean;
import com.caigen.graduationproject.comhandle.ComHandle;
import com.caigen.graduationproject.impls.DockerInit;
import com.github.dockerjava.api.model.Container;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Yasin on 2020-03-25 19:24
 */
@Service
public class GetExitedContainersDao extends ComHandle implements DockerInit {
    public List<ContainerBean> getExitedContainers(){
        List<ContainerBean> exitedContainerInfo = new ArrayList<>();
        
        List<Container> containerList = dockerClient.listContainersCmd()
                .withShowAll(true)
                .withStatusFilter(Collections.singleton("exited"))
                .exec();
        for(Container ec:containerList){
            ContainerBean ecb = new ContainerBean();
            ecb.setContainerId(ec.getId().replace("sha256:",""));
            ecb.setContainerName(
                    strarrayToString(ec.getNames())
                            .replace("/","")
            );
            if(ec.getImage().length()>11) {
                ecb.setImageName(ec.getImage().replace("sha256:", "").substring(0, 10));
            } else {
                ecb.setImageName(ec.getImage());
            }
            ecb.setContainerPort(
                    (ec.getPorts().length>0?
                            (Integer.toString(ec.getPorts()[0]
                                    .getPrivatePort())):"未指定")
            );
            ecb.setCreateTimel(sdf.format(ec.getCreated()*1000L));
            exitedContainerInfo.add(ecb);
        }
        return exitedContainerInfo;
    }
}
