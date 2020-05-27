package com.caigen.graduationproject.dao;

import com.caigen.graduationproject.impls.DockerInit;

/**
 * @author Yasin
 * @version 1.0
 * @date 2020-04-12 22:57
 * @description
 */
public class RenameContainerDao implements DockerInit {
    /**
     *
     * @param contaienrId
     * @return boolean
     */
    public boolean renameContainer(String contaienrId){
        try {
            dockerClient.renameContainerCmd(contaienrId);
        }catch (Exception e){
            //TODO LOG
            return false;
        }
        return true;
    }
}
