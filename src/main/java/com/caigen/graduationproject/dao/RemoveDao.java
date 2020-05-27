package com.caigen.graduationproject.dao;


import com.caigen.graduationproject.impls.DockerInit;
import jdk.internal.org.objectweb.asm.tree.TryCatchBlockNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.Cache;

import javax.swing.text.TableView;
import java.util.List;

/**
 * @author Yasin
 * @version 1.0
 * @date 2020-04-14 10:28
 * @description
 */
public class RemoveDao {
    public static final Logger LOG = LoggerFactory.getLogger(RemoveDao.class);
    /**
     * receive param (type list) from ui,and delete image where imageid in param
     * @param id
     * @return boolean
     */
    public boolean removeImages(String id){
        try {
            DockerInit.dockerClient.removeImageCmd(id).exec();
            LOG.info("remove {} success!",id);
        } catch (Exception e){
            LOG.error("remove {} failed",id);
        }
        return true;
    }

    /**
     *
     * @param id
     * @return boolean
     */
    public boolean removeContainers(String id){
        try {

            DockerInit.dockerClient.removeContainerCmd(id).exec();

        } catch (Exception e) {
            //TODO ---- LOG
            return false;
        }

        return true;
    }
}
