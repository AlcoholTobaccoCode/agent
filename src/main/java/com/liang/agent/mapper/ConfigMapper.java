package com.liang.agent.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liang.agent.entity.Config;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ConfigMapper extends BaseMapper<Config> {
    List<Config> findAll();

}