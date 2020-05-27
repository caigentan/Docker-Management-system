package com.caigen.graduationproject.utils;

import com.caigen.graduationproject.impls.DockerInit;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.model.Statistics;
import com.github.dockerjava.core.InvocationBuilder;

import java.io.IOException;

/**
 * @author Yasin
 * @version 1.0
 * @date 2020-04-10 13:09
 * @description
 */
public class GetDcockerInfoUtils implements DockerInit {

    public static void getContainerStats(){
        InvocationBuilder.AsyncResultCallback<Statistics> callback = new InvocationBuilder.AsyncResultCallback<>();
        dockerClient.statsCmd("a2da86047bd4").exec(callback);
        Statistics stats = null;
        try {
            stats = callback.awaitResult();
            callback.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(stats);
    }

    public static void getInfo(){
        InspectContainerResponse response = dockerClient.inspectContainerCmd("a2da86047bd4").exec();
        System.out.println(response+"\"}");
    }
    public static void main(String[] args) {
        //getContainerStats();
        System.out.println("1212");
        getInfo();
    }
}
