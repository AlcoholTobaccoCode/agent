package com.liang.agent.repository;

import com.liang.agent.entity.BigCategory;
import com.liang.agent.entity.Person;
import com.liang.agent.entity.SmallCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@EnableNeo4jRepositories
public interface BigCategoryRep extends Neo4jRepository<BigCategory,Long> {

    Page<BigCategory> findByNameLike(@Param("name") String name, Pageable pageable);


    @Query("MATCH (bc:bigcategory)-[r:`子类`]->(sc:smallcategory) " +
            "WHERE id(bc) = $bigCategoryId AND id(sc) = $smallCategoryId DELETE r")
    void deleteSmallCategoryFromBigCategory(Long bigCategoryId, Long smallCategoryId);

    @Query(value = "MATCH (n:bigcategory) WHERE n.name CONTAINS $name RETURN n",
            countQuery = "MATCH (n:bigcategory) WHERE n.name CONTAINS $name RETURN count(n)")
    Page<BigCategory> findByNameContaining(String name, Pageable pageable);


    @Query(value = "MATCH (n) RETURN n",
            countQuery = "MATCH (n) RETURN count(n)")
    Page<AllNodes> findAllType(@Param("name") String name, Pageable pageable);


    public class AllNodes{
        public BigCategory bigCategory;
        public  SmallCategory smallCategory;
    }
}
