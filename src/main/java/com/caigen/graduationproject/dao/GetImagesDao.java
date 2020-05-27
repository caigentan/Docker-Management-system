package com.caigen.graduationproject.dao;

import com.caigen.graduationproject.model.ImageBean;
import com.caigen.graduationproject.comhandle.ComHandle;
import com.caigen.graduationproject.impls.DockerInit;
import com.github.dockerjava.api.model.Image;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Yasin on 2020-03-25 18:55
 */
@Service
public class GetImagesDao extends ComHandle implements DockerInit {
    public List<ImageBean> getImageImfo(){
        List<ImageBean> imagesImfo = new ArrayList<>();
        List<Image> imageLists = dockerClient.listImagesCmd().exec();
        for(Image image : imageLists){
            ImageBean ib = new ImageBean();
            String imageID = image.getId().replaceAll("sha256:","");
            String imageName = strarrayToString(image.getRepoTags());
            String imageSize = Math.round(image.getSize()/1024/1000.0)+"";
            Long created = image.getCreated();
            //将获取的时间戳转换为北京时间
            String createTime = sdf.format(new Date(created*1000L));
            ib.setImageId(imageID);
            ib.setImageName(imageName);
            ib.setImageSize(imageSize);
            ib.setCreateTime(createTime);
            imagesImfo.add(ib);
        }
        return imagesImfo;
    }
}
