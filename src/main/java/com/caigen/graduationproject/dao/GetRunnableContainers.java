package com.caigen.graduationproject.dao;

import com.caigen.graduationproject.model.RunningContainerBean;
import com.caigen.graduationproject.comhandle.ComHandle;
import com.caigen.graduationproject.impls.DockerInit;
import com.github.dockerjava.api.model.Container;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yasin on 2020-03-25 19:22
 */
@Service
public class GetRunnableContainers extends ComHandle implements DockerInit {
    public List<RunningContainerBean> getImagContainerInfo(){
        List<RunningContainerBean> containerInfo = new ArrayList<>();
        List<Container> containerList = dockerClient.listContainersCmd().exec();
        for(Container container : containerList){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            RunningContainerBean cb = new RunningContainerBean();
            String containerId = container.getId();
            String imageName = container.getImage();
            String imageId = container.getImageId();
        }

        return null;
    }
}
