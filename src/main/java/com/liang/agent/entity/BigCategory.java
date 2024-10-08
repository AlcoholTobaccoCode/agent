package com.liang.agent.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.liang.agent.dto.GraphNode;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

import java.util.List;

/**
 * @program: agent
 * @ClassName Category
 * @description:
 * @author: liangliang
 * @create: 2024-08-01 15:44
 * @Version 1.0
 **/

@Node(labels = "bigcategory")
@Data
public class BigCategory implements GraphNode {

    @Id
    @GeneratedValue
    private Long id;

    @Property("name")
    private String name;

    @JsonIgnoreProperties("bigCategory")  // 忽略 SmallCategory 中的 bigCategories 字段
    @Relationship(type = "子类", direction = Relationship.Direction.OUTGOING)
    private List<SmallCategory> smCategories;

    @Override
    public String getType() {
        return "bigcategory";
    }
}
