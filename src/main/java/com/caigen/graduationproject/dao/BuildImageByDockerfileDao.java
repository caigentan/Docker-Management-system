package com.caigen.graduationproject.dao;

import com.caigen.graduationproject.impls.DockerInit;
import com.caigen.graduationproject.model.ItemsBean;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.BuildResponseItem;
import com.github.dockerjava.core.command.BuildImageResultCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Yasin
 * @version 1.0
 * @date 2020-04-18 22:21
 * @description 通过DockerFile创建
 */
public class BuildImageByDockerfileDao implements DockerInit {
    public static final Logger LOG = LoggerFactory.getLogger(BuildImageByDockerfileDao.class);
    private final String GET_DOCKER_PATH = "src/main/resources/static/upload/Dockerfile";
    public String BuildImageByDockerfileDao(
            @Nullable String tag
    ){
        File dockerFile = new File(GET_DOCKER_PATH);
        ItemsBean itemsBean = new ItemsBean();
        BuildImageResultCallback callback = new BuildImageResultCallback(){
            @Override
            public void onNext(BuildResponseItem item) {
                itemsBean.addItem(item.toString());
                itemsBean.addItem("\n");
                super.onNext(item);
            }
        };
        dockerClient.buildImageCmd(dockerFile)
                .withTag(tag)
                .exec(callback)
                .awaitImageId();
        LOG.info("BuildImageByDockerfile Success,ID:{}",callback.awaitImageId());
        return itemsBean.getItems();
    }
}
