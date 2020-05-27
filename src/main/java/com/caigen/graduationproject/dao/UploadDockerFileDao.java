package com.caigen.graduationproject.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpSession;
import java.io.*;

/**
 * @author Yasin
 * @version 1.0
 * @date 2020-04-18 22:12
 * @description 将上传的文件写入Dockerfile
 */
public class UploadDockerFileDao {
    public static final Logger LOG = LoggerFactory.getLogger(UploadDockerFileDao.class);
    private final String DOCKERFILE_UPLOAD_PATH = "src/main/resources/static/upload/Dockerfile";
    public UploadDockerFileDao(String content){
        try {
            LOG.info("User {} input a new DockerFile success", SecurityContextHolder.getContext().getAuthentication().getName());
            File writeName = new File(DOCKERFILE_UPLOAD_PATH);
            writeName.createNewFile();
            BufferedWriter out = new BufferedWriter(new FileWriter(writeName));
            out.write(content);
            out.flush();
            out.close();
        } catch (FileNotFoundException e1){
            LOG.error("User {} input new DockerFile failed,Reason:FileNotFoundException", SecurityContextHolder.getContext().getAuthentication().getName());
        } catch (IOException e2) {
            LOG.error("User {} input new DockerFile failed,Reason:Exception", SecurityContextHolder.getContext().getAuthentication().getName());
        }
    }
}
