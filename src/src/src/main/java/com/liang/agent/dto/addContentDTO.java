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

    private List<String> repeatTaskNo;

    private String responsibleUnitId;

    private String responsibleUnitName;
    private String deptName;
    private List<String> updatekeys;
}
