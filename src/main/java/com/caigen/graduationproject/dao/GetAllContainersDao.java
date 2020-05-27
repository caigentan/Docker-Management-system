package com.caigen.graduationproject.dao;

import com.caigen.graduationproject.comhandle.ComHandle;
import com.caigen.graduationproject.impls.DockerInit;
import com.caigen.graduationproject.model.ContainerBean;
import com.github.dockerjava.api.model.Container;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yasin
 * @version 1.0
 * @date 2020-02-17 20:56
 * @description 获得所有状态容器的信息：up，etited
 */
@Service
public class GetAllContainersDao extends ComHandle implements DockerInit {
    public List<ContainerBean> getAllContainers(){
        List<ContainerBean> allContainersInfo = new ArrayList<>();

        List<Container> allContainers = dockerClient
                .listContainersCmd()
                .withShowAll(true)
                .exec();
        for (Container ac : allContainers){
            ContainerBean acb = new ContainerBean();

            acb.setContainerId(ac.getId().replace("sha256",""));
            acb.setContainerName(
                    strarrayToString(ac.getNames()).replace("/","")
            );
            if(ac.getImage().length()>11) {
                acb.setImageName(ac.getImage().replace("sha256:", "").substring(0, 10));
            } else {
                acb.setImageName(ac.getImage());
            }
            acb.setContainerPort(
                    ac.getPorts().length>0?
                    (Integer.toString(
                            ac.getPorts()[0]
                            .getPublicPort()
                            )
                    ):"未指定"
            );
            acb.setStatus(ac.getStatus());
            acb.setCreateTimel(sdf.format(ac.getCreated()*1000L));
            allContainersInfo.add(acb);
        }
        return allContainersInfo;
    }
}
