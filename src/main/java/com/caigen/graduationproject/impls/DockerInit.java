package com.caigen.graduationproject.impls;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Info;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Yasin on 2020-03-09 23:00
 */
public interface DockerInit {

    DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder().withDockerTlsVerify(true)
            .withDockerCertPath("F:/Work/ca/").withDockerHost("tcp://192.168.134.128:2376")
            .withDockerConfig("F:/Work/ca/").withApiVersion("1.40").withRegistryUrl("https://index.docker.io/v1.40/")
            .withRegistryUsername("username").withRegistryPassword("password")
            .withRegistryEmail("email").build();
    DockerClient dockerClient = DockerClientBuilder.getInstance(config).build();
    Info info = dockerClient.infoCmd().exec();

}
