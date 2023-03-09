package com.hottydb.booksearch.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SearchItemRepository extends JpaRepository<SearchItemEntity, Integer> {
    @Query(value = "SELECT * FROM MLR(mm1, :q)(30, 0)", nativeQuery = true)
    List<SearchItemEntity> search(@Param("q") String q);

    @Modifying
    @Query(value = "INSERT MLR_POSITIVE(mm1, :request_id, :key_id)", nativeQuery = true)
    void train(@Param("request_id") int requestId, @Param("key_id") int keyId);
}