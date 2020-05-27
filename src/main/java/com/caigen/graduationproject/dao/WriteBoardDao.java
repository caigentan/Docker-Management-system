package com.caigen.graduationproject.dao;

import com.caigen.graduationproject.mapper.WriteBoardMapper;
import com.caigen.graduationproject.model.SystemUserBean;
import com.caigen.graduationproject.model.WriteBoardBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Yasin
 * @version 1.0
 * @date 2020-04-26 21:58
 * @description 留言板处理
 */
@Repository
@Transactional
public class WriteBoardDao extends JdbcDaoSupport {
    @Autowired
    public WriteBoardDao(DataSource dataSource){
        this.setDataSource(dataSource);
    }

    public List<WriteBoardBean> findAllMessages(){
        String sql = WriteBoardMapper.WRITEBOARD_SQL;
        WriteBoardMapper mapper = new WriteBoardMapper();
        List<WriteBoardBean> wirteBoardList =  this.getJdbcTemplate().query(sql,mapper);
        return wirteBoardList;
    }
}
