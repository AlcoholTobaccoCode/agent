package com.liang.agent.dto;

/**
 * @program: agent
 * @ClassName Detail
 * @description:
 * @author: liangliang
 * @create: 2024-08-05 16:25
 * @Version 1.0
 **/
public class Detail {

    private long id;

    private String uuid;

    private String applyTo; //来源表

    private String refNum; //关联表示

    private String name; //附件名称

    private String url; //附件路径
    private String fileType;  //附件类型
    private String fileExt; //附件后缀名称
    private long fileSize; //附件大小

}
