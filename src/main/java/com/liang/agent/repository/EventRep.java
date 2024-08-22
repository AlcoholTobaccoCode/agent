package com.liang.agent.repository;

import com.liang.agent.entity.Event;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventRep extends Neo4jRepository<Event,Long> {

//    @Query("MATCH (bc:event) " +
//            "WHERE ($name IS NULL OR bc.name = $name) " +
//            "AND ($address IS NULL OR bc.address = $address) " +
//            "AND ($createTime IS NULL OR bc.createTime = $createTime) " +
//            "RETURN bc")
//    List<Event> findByProperties(@Param("name") String name,
//                                 @Param("address") String address);

    @Query("MATCH (bc:event) " +
            "WHERE ($category_big_sym IS NULL OR bc.category_big_sym = $category_big_sym) " +
            "AND ($category_small_sym IS NULL OR bc.category_small_sym = $category_small_sym) " +
            "AND ($address IS NULL OR bc.address = $address) " +
            "RETURN bc")
    List<Event> findByProperties(@Param("category_big_sym") String category_big_sym,
                                 @Param("category_small_sym") String category_small_sym,
                                 @Param("address") String address);



    @Query("MATCH (e:event)-[:ASSOCIATED_TO]->(s:smallcategory)-[:BELONGS_TO]->(b:bigcategory)" +
            "WHERE b.id = 101 AND s.id = 3 AND e.address = $address" +
            "RETURN e")
    List<Event> find2(@Param("category_big_sym") String category_big_sym,
                      @Param("category_small_sym") String category_small_sym,
                      @Param("address") String address);

    @Query("MATCH (e:event)-[:ASSOCIATED_TO]->(s:smallcategory)-[:BELONGS_TO]->(b:bigcategory) "
            + "WHERE b.name = $bname AND s.name = $sname AND e.address = $address "
            + "RETURN e")
    List<Event> findEventsByBidSidAndAddress(@Param("bname") String bname, @Param("sname") String sname, @Param("address") String address);

//    @Query("MATCH (e:event)-[:ASSOCIATED_TO]->(s:smallcategory)-[:BELONGS_TO]->(b:bigcategory) "
//            + "WHERE b.id = $bid AND s.id = $sid AND e.address = $address "
//            + "RETURN e")
//    List<Event> findEventsByBidSidAndAddress2(@Param("bid") int bid, @Param("sid") int sid, @Param("address") String address);

}
