package com.liang.agent.dto;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Property;

import java.time.LocalDate;
import java.util.Date;

/**
 * @program: agent
 * @ClassName EvnetInput
 * @description:
 * @author: liangliang
 * @create: 2024-08-16 11:43
 * @Version 1.0
 **/
@Data
public class EvnetInput {

    private Long id;

    private String uuid;

    private String task_no;

    private String source;

    private String category_type;

    private int category_big_sym;

    private String category_big_name;

    private int category_small_sym;

    private String category_small_name;

    private String area_code;

    private String area_name;

    private String county_code;

    private String county_name;

    private String town_code;

    private String town_name;

    private String village_code;

    private String village_name;

    private String grid_code;

    private String grid_name;

    private String address;

    private String longitude;

    private String latitude;

    private String issue;

    private String reporter;

    private Date report_time;

    private String descr;

    private String status;

    private String urgency_name;


    private String urgency_sym;

    private String urgency_color;

    private String config_id;

    private String config_uuid;

    private String register_condition;

    private String close_condition;

    private String duplication;

    private String duplication_task_no;

    private Date limit_time;

    private String limit_at;

    private Date warn_time;

    private String apply_type;

    private String warn_at;

    private String disposal_deadline;

    private String check_deadline;

    private Date disposal_time;

    private Date check_time;

    private Date close_time;

    private LocalDate created_at;

    private String created_by_user_id;

    private String created_by_user_username;

    private String created_by_person_id;

    private String created_by_person_name;

    private Date updated_at;

    private String updated_by_user_id;

    private String updated_by_user_username;

    private String updated_by_person_id;

    private String updated_by_person_name;

    private String client_ip;

    ////-------------------------------------
    private String content;

    private boolean isRepeat;

}
