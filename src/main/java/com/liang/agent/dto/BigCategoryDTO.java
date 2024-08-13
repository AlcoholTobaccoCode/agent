package com.liang.agent.dto;

import com.liang.agent.entity.SmallCategory;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;

import java.util.List;

/**
 * @program: agent
 * @ClassName CategoryDTO
 * @description:
 * @author: liangliang
 * @create: 2024-08-01 15:20
 * @Version 1.0
 **/
@Data
public class BigCategoryDTO {

    private  Long id;

    private long bigId;
    private int pageNum;
    private  int pageSize;
    private String name;
    List<SmallCategoryDTO> smCategories;

}
