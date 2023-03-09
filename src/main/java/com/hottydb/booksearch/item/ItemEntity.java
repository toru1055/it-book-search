package com.hottydb.booksearch.item;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Entity
@DynamicUpdate
@Table(name = "item")
@Data
public class ItemEntity {
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

    void setId() {
        String s = itemUrl.replaceFirst("https://books.rakuten.co.jp/rb/", "");
        s = s.replaceAll("/", "");
        id = Integer.parseInt(s);
    }

    void setSalesYear() {
        if (salesDate == null) salesYear = 1950;
        else salesYear = salesDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getYear();
    }

    void setSearchText() {
        String s = String.format("%s %s %s", title, subTitle, itemCaption);
        searchText = StringUtils.normalize(s);
    }


    @JsonProperty("reviewRating")
    public String reviewRating() {
        return StringUtils.reviewRating(reviewAverage);
    }

    @JsonProperty("keywords")
    public List<String> keywords() {
        return StringUtils.extractKeywords(title + " " + subTitle);
    }

    public String affiliateUrlOrItemUrl() {
        if (affiliateUrl != null && !"".equals(affiliateUrl)) return affiliateUrl;
        else return itemUrl;
    }

    public String amazonUrl() {
        String q = title + " " + subTitle + " " + publisherName;
        return "https://www.amazon.co.jp/s?k=" + q;
    }

    public String commaPrice() {
        return String.format("%,d", itemPrice);
    }

    public String salesYM() {
        SimpleDateFormat f = new SimpleDateFormat("yyyy年");
        return f.format(salesDate);
    }
}