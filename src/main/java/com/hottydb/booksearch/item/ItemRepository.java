package com.hottydb.booksearch.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

interface ItemRepository extends JpaRepository<ItemEntity, Integer> {
    @Query("SELECT x FROM ItemEntity x WHERE " +
            "x.salesYear >= :minSalesYear AND " +
            "x.reviewCount >= :minReviewCount AND " +
            "x.reviewAverage >= 3.0 AND " +
            "x.itemPrice >= 3000 " +
            "ORDER BY x.reviewCount DESC, x.reviewAverage DESC, x.itemPrice DESC")
    List<ItemEntity> trendItems(@Param("minSalesYear") int minSalesYear, @Param("minReviewCount") int minReviewCount);

    @Query(value = "SELECT * FROM SEARCH(item, search_text, :q) ORDER BY _similarity DESC", nativeQuery = true)
    List<ItemEntity> search(@Param("q") String q);

    @Query(value = "SELECT * FROM SEARCH(item, search_text, :seedTitle, OR) ORDER BY _similarity DESC", nativeQuery = true)
    List<ItemEntity> similarItems(@Param("seedTitle") String seedTitle);
}