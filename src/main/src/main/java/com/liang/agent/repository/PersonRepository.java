package com.liang.agent.repository;

import com.liang.agent.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

@EnableNeo4jRepositories
public interface PersonRepository extends Neo4jRepository<Person,Long> {

   // @Query("match (n:loveperson) where n.name = $name return n")
    //List<Person> findByNameLike(@Param("name") String name);

    Page<Person> findByNameLike(@Param("name") String name, Pageable pageable);


  //  Optional<Person> findById( @Param("id") Long id);

}
