package com.liang.agent.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @program: agent
 * @ClassName InputMsg
 * @description:
 * @author: liangliang
 * @create: 2024-08-05 16:08
 * @Version 1.0
 **/
@Data
public class Input4LLM {

    private String content; //内容描述
    private String address;
    private String longitude;   //经度
    private String latitude;   //纬度

}
