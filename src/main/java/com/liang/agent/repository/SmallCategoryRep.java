package com.liang.agent.repository;

import com.liang.agent.entity.BigCategory;
import com.liang.agent.entity.SmallCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@EnableNeo4jRepositories
public interface SmallCategoryRep extends Neo4jRepository<SmallCategory,Long> {

    Page<SmallCategory> findByNameLike(@Param("name") String name, Pageable pageable);


    @Query(value = "MATCH (n:smallcategory) WHERE n.name CONTAINS $name RETURN n",
            countQuery = "MATCH (n:smallcategory) WHERE n.name CONTAINS $name RETURN count(n)")
    Page<SmallCategory> findByNameContaining(String name, Pageable pageable);



    Page<SmallCategory> findByName(@Param("name") String name, Pageable pageable);

}
