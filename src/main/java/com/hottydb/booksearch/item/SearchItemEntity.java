package com.hottydb.booksearch.item;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "dummy_item") // ダミーではあるが、テーブルは作成される.
@Data
public class SearchItemEntity {
    /* ==ItemEntityにもあるフィールド== */
    @Id Integer id;
    String title;
    String subTitle;
    String seriesName;
    String author;
    String publisherName;
    @Lob String itemCaption;
    @JsonFormat(pattern="yyyy年") Date salesDate;
    int itemPrice;
    String itemUrl;
    String affiliateUrl;
    String largeImageUrl;
    int reviewCount;
    double reviewAverage;
    int salesYear;
    @Lob String searchText;

    /* ==追加フィールド== */
    @Column(name = "_similarity") Double similarity;
    @Column(name = "_request_id") Integer requestId;
    @Column(name = "_key_id") Integer keyId;
    @Column(name = "_score") Double score;


    /* ==表示用メソッド. ItemEntityにもある== */
    @JsonProperty("reviewRating")
    public String reviewRating() {
        return StringUtils.reviewRating(reviewAverage);
    }

    @JsonProperty("keywords")
    public List<String> keywords() {
        return StringUtils.extractKeywords(title + " " + subTitle);
    }
}