package com.liang.agent.entity;

import com.liang.agent.dto.GraphNode;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

import java.util.List;

/**
 * @program: agent
 * @ClassName SmallCategory
 * @description:
 * @author: liangliang
 * @create: 2024-08-01 15:51
 * @Version 1.0
 **/
@Data
@Node(labels = "smallcategory")

public class SmallCategory implements GraphNode {
    @Id
    @GeneratedValue
    private Long id;

    @Property("name")
    private String name;

    @Property("content")
    private String content;

    @Relationship(type = "子类",direction = Relationship.Direction.INCOMING)
    private BigCategory bigCategory;

    @Override
    public String getType() {
        return "smallcategory";
    }

}
