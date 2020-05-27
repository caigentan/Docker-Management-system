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
 * @date 2020-04-28 13:12
 * @description
 */
public class ShowContainerLogDao {

    public List<String> getContainerLog(String id){
        LogContainerCmd logContainerCmd = DockerInit.dockerClient
                .logContainerCmd(id)
                .withFollowStream(true);
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
