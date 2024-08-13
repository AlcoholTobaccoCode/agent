package com.liang.agent.entity;

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
public class BigCategory {

    @Id
    @GeneratedValue
    private Long id;

    @Property("name")
    private String name;

    @Relationship(type = "子类", direction = Relationship.Direction.OUTGOING)
    private List<SmallCategory> smCategories;
}
