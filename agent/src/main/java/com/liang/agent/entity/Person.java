package com.liang.agent.entity;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

import java.util.List;

/**
 * @program: ai_agent
 * @ClassName Person
 * @description:
 * @author: liangliang
 * @create: 2024-07-19 14:08
 * @Version 1.0
 **/
@Node(labels = "loveperson")
@Data
public class Person {
    @Id
    @GeneratedValue
    private Long id;
    @Property("name")
    private String name;
    @Relationship(type = "夫妻", direction = Relationship.Direction.OUTGOING)
    private List<Person> partners;
    @Relationship(type = "父子", direction = Relationship.Direction.OUTGOING)
    private List<Person> fathers;
    @Relationship(type = "暗恋", direction = Relationship.Direction.OUTGOING)
    private List<Person> girlfriend;
    @Relationship(type = "老丈人", direction = Relationship.Direction.OUTGOING)
    private List<Person> outFather;
    @Relationship(type = "初恋", direction = Relationship.Direction.OUTGOING)
    private List<Person> fistGirlfriend;
    @Relationship(type = "老弟", direction = Relationship.Direction.OUTGOING)
    private List<Person> brother;
}

