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
public class InputMsgDTO {
    private long id;
    private String uuid;
    private Date createdAt;
    private String createdByUserUsername;  //创建用户姓名
    private long createdByPersonId; //创建人员id
    private String createdByPersonName;//创建人员姓名

    private Date updatedAt; //更新时间
    private long updatedByUserId;
    private String updatedByUserUsername;
    private long updatedByPersonId;
    private String updatedByPersonName;

    private String clientIp;

    private String taskNo;//事件编号

    private String urgencyName;

    private String source;

    private String categoryType;//事件类型

    private String categoryBigSym; //问题大类代码

    private String categoryBigName; //问题大类名称

    private String categorySmallSym;   //问题小类代码

    private String categorySmallName;   //问题小类名称

    private String issue;   //问题描述
    private String areaCode;   //区域编码
    private String areaName;   //区域名称
    private String townCode;   //街道/乡镇编码
    private String townName;   //街道/乡镇名称
    private String villageCode;   //社区/村编码
    private String villageName;   //社区/村名称
    private String gridName;   //网格所属编号
    private String address;   //案件发生地址
    private String longitude;   //经度
    private String latitude;   //纬度
    private String reporter;   //上报人
    private Date reportTime;   //上报时间
    private String descr;   //备注
    private String status;   //案件状态
    private String urgencySym;   //紧急程度代码
    private String urgencyColor;   //紧急程度色值
    private String configUuid;   //事件分类配置uuid
    private String registerCondition;   //立案条件
    private String closeCondition;   //结案条件

    private String content; //内容描述


///////////////////////////新增处理部分//////////////////////////////////////////
    private boolean isRepeat;
    private List<String> repeatTaskNo;
    private String responsibleUnitId;
    private String responsibleUnotName;
    private String deptName;
    private List<String> updatekeys;

   // private addContentDTO addContent;


/////////////////////////////////新增处理部分//////////////////////////////////////

    private List<DetailDTO> details;







}
