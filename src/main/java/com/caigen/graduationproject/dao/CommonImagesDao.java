package com.caigen.graduationproject.dao;

import com.caigen.graduationproject.mapper.CommonImagesMapper;
import com.caigen.graduationproject.model.CommonImagesBean;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.util.List;

/**
 * @author Yasin
 * @version 1.0
 * @date 2020-04-27 21:48
 * @description 公共镜像处理类
 */
@Repository
@Transactional
public class CommonImagesDao extends JdbcDaoSupport {

    public CommonImagesDao(DataSource dataSource){
        this.setDataSource(dataSource);
    }

    public List<CommonImagesBean> findAllCommonImages(){
        String sql = BaseSqlDao.COMMON_IMAGES_INFO;
        CommonImagesMapper mapper = new CommonImagesMapper();
        List<CommonImagesBean> commonImagesBeanList = this.getJdbcTemplate().query(sql,mapper);
        return commonImagesBeanList;
    }
}
