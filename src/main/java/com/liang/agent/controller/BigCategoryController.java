package com.liang.agent.controller;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.liang.agent.annotation.ApiResponse;
import com.liang.agent.dto.BigCategoryDTO;
import com.liang.agent.entity.BigCategory;
import com.liang.agent.entity.SmallCategory;
import com.liang.agent.repository.BigCategoryRep;
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
@RequestMapping("/bigcategory")
@Slf4j
public class BigCategoryController {

    @Resource
    BigCategoryRep bigCategoryRep;

    @PostMapping(value = "/add")
    public ApiResponse<Object> add(@RequestBody BigCategoryDTO bigCategoryDTO) {
        log.info("传入的对象-------{}", JSON.toJSONString(bigCategoryDTO));

        BigCategory bigCategory = new BigCategory();
        log.info("新生成的bigCategory对象-------{}", JSON.toJSONString(bigCategory));
        BeanUtil.copyProperties(bigCategoryDTO, bigCategory);

        log.info("存入数据库的bigCategory对象-------{}", bigCategory);

        BigCategory saved = bigCategoryRep.save(bigCategory);
        return ApiResponse.success(saved);

    }

    @PostMapping(value = "/del")
    public ApiResponse<Object> del(@RequestBody BigCategoryDTO bigCategoryDTO) {

        bigCategoryRep.deleteById(bigCategoryDTO.getBigId());
        return ApiResponse.success(true);

    }

    @PostMapping(value = "/update")
    public ApiResponse<Object> update(@RequestBody BigCategoryDTO bigCategoryDTO) {
        Optional<BigCategory> bigCategory = bigCategoryRep.findById(bigCategoryDTO.getBigId());
        if (bigCategory.isPresent()) {
            BigCategory bigCategory1 = bigCategory.get();
            bigCategory1.setName(bigCategoryDTO.getName());

            bigCategoryRep.save(bigCategory1);
            return ApiResponse.success(null);

        }
        return null;
    }


    @PostMapping(value = "/findbyname")
    public ApiResponse<Object> findbyname(@RequestBody BigCategoryDTO bigCategoryDTO) {

        log.info("传入的对象-------{}", JSON.toJSONString(bigCategoryDTO));
        PageRequest pageRequest = PageRequest.of(bigCategoryDTO.getPageNum(), bigCategoryDTO.getPageSize());

        Page<SmallCategory> plist = bigCategoryRep.findByNameContaining(bigCategoryDTO.getName(), pageRequest);
        return ApiResponse.success(plist);
    }

//    @GetMapping(value = "/findall")
//    public ApiResponse<Object> findall(@RequestParam String name) throws Exception {
//
////        List<Person> all = neo4jRepository.findAll();
////        log.info("根据用户名查询到的用户列表-----{}", JSON.toJSONString(all));
////        return ApiResponse.success(all);
//        return null;
//    }


}
