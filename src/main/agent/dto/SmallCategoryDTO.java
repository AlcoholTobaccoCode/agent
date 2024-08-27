package com.liang.agent.dto;

import com.liang.agent.entity.BigCategory;
import com.liang.agent.entity.SmallCategory;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

/**
 * @program: agent
 * @ClassName SmallCategoryDTO
 * @description:
 * @author: liangliang
 * @create: 2024-08-01 16:00
 * @Version 1.0
 **/

@Data
public class SmallCategoryDTO {

    private Long id;

    private String name;

    private String content;

    private BigCategoryDTO bigCategoryDTO;

    private long smallId;

    private long bigId;

    private long oldBigId;

    private int pageNum;

    private int pageSize;
}
