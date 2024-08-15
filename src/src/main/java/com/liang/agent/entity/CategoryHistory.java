package com.liang.agent.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("category_history")
public class CategoryHistory {
  @TableId(value = "id")
  private Long id;
  private String uuid;
  private String taskNo;
  private String source;
  private String categoryType;
  private String categoryBigSym;
  private String categoryBigName;
  private String categorySmallSym;
  private String categorySmallName;
  private String areaCode;
  private String areaName;
  private String countyCode;
  private String countyName;
  private String townCode;
  private String townName;
  private String villageCode;
  private String villageName;
  private String gridCode;
  private String gridName;
  private String address;
  private String longitude;
  private String latitude;
  private String issue;
  private String reporter;
  private Date reportTime;
  private String descr;
  private String status;
  private String urgencyName;
  private String urgencySym;
  private String urgencyColor;
  private String configId;
  private String configUuid;
  private String registerCondition;
  private String closeCondition;
  private String duplication;
  private String duplicationTaskNo;
  private Date limitTime;
  private String limitAt;
  private Date warnTime;
  private String applyType;
  private String warnAt;
  private String disposalDeadline;
  private String checkDeadline;
  private Date disposalTime;
  private Date checkTime;
  private Date closeTime;
  private Date createdAt;
  private String createdByUserId;
  private String createdByUserUsername;
  private String createdByPersonId;
  private String createdByPersonName;
  private Date updatedAt;
  private String updatedByUserId;
  private String updatedByUserUsername;
  private String updatedByPersonId;
  private String updatedByPersonName;
  private String clientIp;



}
