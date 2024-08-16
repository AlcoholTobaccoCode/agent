package com.liang.agent.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

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
    private Long id;

    @Property("uuid")
    private String uuid;

    @Property("name")
    private String name;

    @Property("task_no")
    private String task_no;

    @Property("source")
    private String source;

    @Property("category_type")
    private String category_type;

    @Property("category_big_sym")
    private String category_big_sym;

    @Property("category_big_name")
    private String category_big_name;

    @Property("category_small_sym")
    private String category_small_sym;

    @Property("category_small_name")
    private String category_small_name;

    @Property("area_code")
    private String area_code;

    @Property("area_name")
    private String area_name;

    @Property("county_code")
    private String county_code;

    @Property("county_name")
    private String county_name;

    @Property("town_code")
    private String town_code;

    @Property("town_name")
    private String town_name;

    @Property("village_code")
    private String village_code;

    @Property("village_name")
    private String village_name;

    @Property("grid_code")
    private String grid_code;

    @Property("grid_name")
    private String grid_name;

    @Property("address")
    private String address;

    @Property("longitude")
    private String longitude;

    @Property("latitude")
    private String latitude;

    @Property("issue")
    private String issue;

    @Property("reporter")
    private String reporter;

    @Property("report_time")
    private LocalDate report_time;

    @Property("descr")
    private String descr;

    @Property("status")
    private String status;

    @Property("urgency_name")
    private String urgency_name;


    @Property("urgency_sym")
    private String urgency_sym;

    @Property("urgency_color")
    private String urgency_color;

    @Property("config_id")
    private String config_id;

    @Property("config_uuid")
    private String config_uuid;

    @Property("register_condition")
    private String register_condition;

    @Property("close_condition")
    private String close_condition;

    @Property("duplication")
    private String duplication;

    @Property("duplication_task_no")
    private String duplication_task_no;

    @Property("limit_time")
    private LocalDate  limit_time;

    @Property("limit_at")
    private String limit_at;

    @Property("warn_time")
    private LocalDate  warn_time;

    @Property("apply_type")
    private String apply_type;

    @Property("warn_at")
    private String warn_at;

    @Property("disposal_deadline")
    private String disposal_deadline;

    @Property("check_deadline")
    private String check_deadline;

    @Property("disposal_time")
    private LocalDate  disposal_time;

    @Property("check_time")
    private LocalDate  check_time;

    @Property("close_time")
    private LocalDate  close_time;

    @Property("created_at")
    private LocalDate  created_at;

    @Property("created_by_user_id")
    private String created_by_user_id;

    @Property("created_by_user_username")
    private String created_by_user_username;

    @Property("created_by_person_id")
    private String created_by_person_id;

    @Property("created_by_person_name")
    private String created_by_person_name;

    @Property("updated_at")
    private LocalDate  updated_at;

    @Property("updated_by_user_id")
    private String updated_by_user_id;

    @Property("updated_by_user_username")
    private String updated_by_user_username;

    @Property("updated_by_person_id")
    private String updated_by_person_id;

    @Property("updated_by_person_name")
    private String updated_by_person_name;

    @Property("client_ip")
    private String client_ip;
}
