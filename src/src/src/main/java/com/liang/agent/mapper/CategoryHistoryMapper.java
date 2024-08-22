package com.liang.agent.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liang.agent.entity.CategoryHistory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryHistoryMapper extends BaseMapper<CategoryHistory> {
    List<CategoryHistory> findAll();

}