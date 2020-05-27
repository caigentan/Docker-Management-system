package com.caigen.graduationproject.dao;

import com.caigen.graduationproject.impls.DockerInit;
import com.github.dockerjava.api.model.SearchItem;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Yasin on 2020-03-18 22:27
 */
@Service
public class SearchImages implements DockerInit {
    /**
     *
     * @param sImageName
     * @return List<SearchItm>
     */
    public List<SearchItem> getResult(String sImageName){
        List<SearchItem> searchResult = dockerClient
                .searchImagesCmd(sImageName)
                .exec();
        return searchResult;
    }
}
