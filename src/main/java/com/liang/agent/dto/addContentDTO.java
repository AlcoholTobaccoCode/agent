package com.liang.agent.dto;

import lombok.Data;

import java.util.List;

/**
 * @program: agent
 * @ClassName addContentDTO
 * @description:
 * @author: liangliang
 * @create: 2024-08-06 16:33
 * @Version 1.0
 **/
@Data
public class addContentDTO {

    private boolean isRepeat;
    private String  address;
    private String categorySmallName;
    private String categoryBigName;
    private String responsibleUnit;
    private String responsibleDepartment;
    private String reportTime;

}
