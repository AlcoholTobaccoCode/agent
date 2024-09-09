package com.liang.agent.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

import java.time.LocalDate;


/**
 * @program: agent
 * @ClassName Event
 * @description:
 * @author: liangliang
 * @create: 2024-08-16 11:09
 * @Version 1.0
 **/

@Data
@Node(labels = "event")
public class Event {

    @Id
    @GeneratedValue
    private Long Id;

    @Property("<elementId>")
    private String elementId; // 保持原样，根据业务需求决定是否需要处理或存储

    @Property("id")
    private String businessId; // 使用"businessId"以避免与Neo4j内部ID混淆

    @Property("address")
    private String address;

    @Property("category_small_sym")
    private String categorySmallSym; // 修改为非ID形式，根据实际含义可能需要关联实体

    @Property("created_at")
    private LocalDate created_at; // 根据实际内容确定是否转换为日期时间类型

    @Property("issue")
    private String issue;

    @Property("status")
    private String status;

    @Property("urgency_sym")
    private String urgencySym;

   // @JsonIgnoreProperties("smCategories")  // 忽略 SmallCategory 中的 bigCategories 字段
    @Relationship(type = "ASSOCIATED_TO",direction = Relationship.Direction.OUTGOING)
    private SmallCategory smallCategory;

}
