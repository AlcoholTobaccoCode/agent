package com.liang.agent.controller;

import com.alibaba.fastjson.JSON;
import com.liang.agent.annotation.ApiResponse;
import com.liang.agent.entity.Person;
import com.liang.agent.repository.PersonRepository;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
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
@RequestMapping("/neo4j")
@Slf4j
public class Neo4JController {

    @Resource
    PersonRepository neo4jRepository;

    @GetMapping(value = "/add")
    public void add(String prompt) throws Exception {
        Person person = new Person();
        person.setName("王天来");
        Person girlfriend = new Person();
        girlfriend.setName("李秋歌");
        person.setFistGirlfriend(Arrays.asList(girlfriend));
        Person p = neo4jRepository.save(person);
        System.out.println(p.getId() + ":" + p.getName());


    }

    @GetMapping(value = "/del")
    public void del(@RequestParam long id) throws Exception {
        Person person = new Person();
        person.setId(id);

        neo4jRepository.delete(person);
    }

    @GetMapping(value = "/update")
    public void update(String prompt) throws Exception {
        Person person = new Person();
        person.setId(131L);
        person.setName("王天来");
        Person father = new Person();
        father.setId(115L);
        father.setName("王大拿");
        person.setFathers(Arrays.asList(father));
        Person gf = new Person();
        gf.setId(123L);
        gf.setName("李秋歌");
        person.setFistGirlfriend(Arrays.asList(gf));
        Person p = neo4jRepository.save(person);
        System.out.println(p.getId() + ":" + p.getName());

    }


    @GetMapping(value = "/findbyid")
    public ApiResponse findById(String name) throws Exception {

        Optional<Person> byId = neo4jRepository.findById(1L);
        log.info("controller层响应的返回对象----{}", JSON.toJSONString(byId));
        return ApiResponse.success(byId);
    }

    @GetMapping(value = "/findbyname")
    public ApiResponse findbyname(@RequestParam String name) {


        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Person> plist = neo4jRepository.findByNameLike(name, pageRequest);
        return ApiResponse.success(plist);
    }

    @GetMapping(value = "/findall")
    public ApiResponse findall(@RequestParam String name) throws Exception {

        List<Person> all = neo4jRepository.findAll();
        log.info("根据用户名查询到的用户列表-----{}", JSON.toJSONString(all));
        return ApiResponse.success(all);

    }





}
