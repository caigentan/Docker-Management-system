package com.caigen.graduationproject.dao;

import com.caigen.graduationproject.impls.DockerInit;
import com.github.dockerjava.api.command.LogContainerCmd;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.core.command.LogContainerResultCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yasin
 * @version 1.0
 * @date 2020-04-12 22:57
 * @description 根据容器id获取容器的日志
 */
public class ContainerLogDao implements DockerInit {
    public List<String> getContaienrLog(String containerId){
        LogContainerCmd logContainerCmd = dockerClient.logContainerCmd(containerId);
        logContainerCmd.withStdOut(true).withStdErr(true).withTailAll();
        List<String> logList = new ArrayList<>();
        try {
            logContainerCmd.exec(new LogContainerResultCallback() {
                @Override
                public void onNext(Frame item) {
                    logList.add(item.toString());
                }
            }).awaitCompletion();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return logList;
    }

}
