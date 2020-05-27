package com.caigen.graduationproject;

import com.caigen.graduationproject.dao.BaseSqlDao;
import com.caigen.graduationproject.dao.UserInfoDao;
import com.caigen.graduationproject.dao.WriteBoardDao;
import com.caigen.graduationproject.impls.DockerInit;
import com.caigen.graduationproject.model.UserInfoBean;
import com.caigen.graduationproject.model.WriteBoardBean;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dockerjava.api.command.LogContainerCmd;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.command.LogContainerResultCallback;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CountDownLatch;

@SpringBootTest
class GraduationprojectApplicationTests implements DockerInit {

    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private WriteBoardDao writeBoardDao;
    @Autowired
    private BaseSqlDao baseSqlDao;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Test
    void contextLoads() {
        /*String sql = "select * from user_tab";
        List<Map<String,Object>> lm = jdbcTemplate.queryForList(sql);

        System.out.println(lm.get(0).get("id"));

        DockerClient dockerClient = DockerClientBuilder.getInstance("tcp://localhost:2375").build();
        Info info = dockerClient.infoCmd().exec();

        System.out.println(info);
        System.out.println("镜像数："+info.getImages());
        System.out.println("容器数："+info.getContainers());

        //tGetImages.setImageStats();
        String sql = "SELECT * FROM imageinfo where imagename ='mysql:latest'";
        Map<String, Object> imageInfo = jdbcTemplate.queryForMap(sql);
        System.out.println(imageInfo);*/
        // getYmlLinuxInfoBean = new GetYmlLinuxInfoBean();

        //System.out.println(linuxName);
       /* System.out.println(getYmlLinuxInfoBean.getLinuxName());
        System.out.println(getYmlLinuxInfoBean.getLinuxPassword());*/

        UserInfoBean userinfo = userInfoDao.getUserinfo("user1");

        try {
            System.out.println(new ObjectMapper().writeValueAsString(userinfo));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void getLog(){
        final String path = "/logs/main.log";
        File file =  new File(path);
        StringBuilder result = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String s = null;
            while((s=br.readLine())!=null){
                result.append(s+"\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(result.toString());
    }

    @Test
    public void writeBoardList(){
        List<WriteBoardBean> writeBoardBeanList =writeBoardDao.findAllMessages();
        System.out.println(writeBoardBeanList.get(0).getTitle());
        System.out.println(writeBoardBeanList.get(1).getUserName());
    }

    @Test
    public void LogContainer(){

        LogContainerCmd logContainerCmd = DockerInit.dockerClient
                .logContainerCmd("a2da86047bd4e129b6024512e3ffdac2df726ddc6874e1964b2b49f8d1f1e5ed")
                ;
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
        for (String s: logList){
        System.out.println(s);
        }
    }

    @Test
    public void startContaienr(){
        dockerClient.startContainerCmd("a2da86047bd4e129b6024512e3ffdac2df726ddc6874e1964b2b49f8d1f1e5ed")
                .exec();
    }

    @Test
    public void containerNum(){
        int i = DockerInit.info.getContainersRunning();
        int j = info.getContainersStopped();
        System.out.println(i+" "+j);
        System.out.println(dockerClient.listContainersCmd().withShowAll(true).exec());
        System.out.println(dockerClient.listContainersCmd()
                .withShowAll(true)
                .withStatusFilter(Collections.singleton("running")).exec().size());
        System.out.println(dockerClient.listImagesCmd().withShowAll(true).exec().size());
    }

    @Test
    public void querySql(){
        String sql = "select count from container_count";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        List<Object> containersDayCount = new ArrayList<>();
        for(Map<String,Object> map : maps){
            containersDayCount.add(map.get("count"));
            System.out.println(map.get("count"));
        }
        System.out.println(containersDayCount);
        //System.out.println(maps);
    }

    @Test
    public void allContainer(){
        List<Container> allContainers = dockerClient
                .listContainersCmd()
                .withShowAll(true)
                .exec();
        System.out.println(allContainers.get(0).getPorts()[0].getPublicPort());
    }
}
