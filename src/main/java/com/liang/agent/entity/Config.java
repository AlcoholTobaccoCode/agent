package com.liang.agent.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: ai_agent
 * @ClassName Config
 * @description:
 * @author: liangliang
 * @create: 2024-07-29 10:13
 * @Version 1.0
 **/
@Data
@TableName("config")
public class Config implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id")
    private Integer id;
    private String serverId;
    private String serverName;
    private boolean status;

}
