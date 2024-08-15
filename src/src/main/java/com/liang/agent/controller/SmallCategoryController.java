package com.liang.agent.controller;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.liang.agent.annotation.ApiResponse;
import com.liang.agent.dto.SmallCategoryDTO;
import com.liang.agent.entity.BigCategory;
import com.liang.agent.entity.SmallCategory;
import com.liang.agent.repository.BigCategoryRep;
import com.liang.agent.repository.SmallCategoryRep;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @program: ai_agent
 * @ClassName Neo4JController
 * @description:
 * @author: liangliang
 * @create: 2024-07-19 11:37
 * @Version 1.0
 **/

@RestController
@RequestMapping("/smcategory")
@Slf4j
public class SmallCategoryController {

    @Resource
    SmallCategoryRep smallCategoryRep;
    @Resource
    BigCategoryRep bigCategoryRep;

    @PostMapping(value = "/add")
    public ApiResponse add(@RequestBody SmallCategoryDTO smCategoryDTO) {
        log.info("传入的对象-------{}", JSON.toJSONString(smCategoryDTO));

        SmallCategory smallCategory = new SmallCategory();
        log.info("新生成的smallCategory对象-------{}", JSON.toJSONString(smallCategory));

        BeanUtil.copyProperties(smCategoryDTO, smallCategory);

        if (0 != smCategoryDTO.getBigId()) {
            Optional<BigCategory> byId = bigCategoryRep.findById(smCategoryDTO.getBigId());
            BigCategory bigCategory = byId.get();
        }

        log.info("存入数据库的smallCategory对象-------{}", JSON.toJSONString(smallCategory));
        SmallCategory saved = smallCategoryRep.save(smallCategory);
        return ApiResponse.success(saved);
    }


    /**
     * 本方法 主要用来关联大类和小类，也可对小类的属性进行修改
     *
     * @param smCategoryDTO
     * @throws Exception
     */
    @PostMapping(value = "/update")
    public ApiResponse update(@RequestBody SmallCategoryDTO smCategoryDTO) throws Exception {


        Optional<SmallCategory> byId = smallCategoryRep.findById(smCategoryDTO.getSmallId());
        if (byId.isPresent()) {
            SmallCategory smallCategory = byId.get();

            if (null != smCategoryDTO.getName()) {
                smallCategory.setName(smCategoryDTO.getName());
            }
            if (null != smCategoryDTO.getContent()) {
                smallCategory.setContent(smCategoryDTO.getContent());
            }

            //删除老关系
            //bigCategoryRep.deleteSmallCategoryFromBigCategory(smCategoryDTO.getOldBigId(), smCategoryDTO.getSmallId());
            //关联上新关系
            if (0 != smCategoryDTO.getBigId()) {
                Optional<BigCategory> big = bigCategoryRep.findById(smCategoryDTO.getBigId());
                smallCategory.setBigCategory(big.get());
            }
            smallCategoryRep.save(smallCategory);

        }
        return ApiResponse.success(true);

    }


    @PostMapping(value = "/del")
    public ApiResponse del(@RequestBody SmallCategoryDTO smallCategoryDTO) {

        smallCategoryRep.deleteById(smallCategoryDTO.getSmallId());
        return ApiResponse.success(true);

    }


    @PostMapping(value = "/findbyname")
    public ApiResponse findbyname(@RequestBody SmallCategoryDTO smallCategory) {
        log.info("传入的对象-------{}", JSON.toJSONString(smallCategory));
        PageRequest pageRequest = PageRequest.of(smallCategory.getPageNum(), smallCategory.getPageSize());

        Page<SmallCategory> plist = smallCategoryRep.findByName(smallCategory.getName(), pageRequest);
        return ApiResponse.success(plist);
    }

    @GetMapping(value = "/findall")
    public ApiResponse findall(@RequestParam String name) throws Exception {
//        List<Person> all = neo4jRepository.findAll();
//        log.info("根据用户名查询到的用户列表-----{}", JSON.toJSONString(all));
//        return ApiResponse.success(all);
        return null;
    }


}
