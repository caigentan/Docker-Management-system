package com.caigen.graduationproject.mapper;

import com.caigen.graduationproject.model.CommonImagesBean;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Yasin
 * @version 1.0
 * @date 2020-04-27 21:48
 * @description
 */
public class CommonImagesMapper implements RowMapper<CommonImagesBean> {
    @Override
    public CommonImagesBean mapRow(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("id");
        String imageName = rs.getString("image_name");
        String imageInfo = rs.getString("image_info");

        return new CommonImagesBean(id,imageName,imageInfo);
    }
}
