package com.hottydb.booksearch.rakuten.book;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class BooksBookService {
    @Value("${rakuten.api.applicationId}")
    private String applicationId;
    @Value("${rakuten.api.affiliateId:#{null}}")
    private String affiliateId;

    public BooksBook get(String publisherName, String booksGenreId, String sort, Integer page) {
        try {
            String url = "https://app.rakuten.co.jp/services/api/BooksBook/Search/20170404";
            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
            if (publisherName != null) builder.queryParam("publisherName", publisherName);
            if (booksGenreId != null) builder.queryParam("booksGenreId", booksGenreId);
            if (sort != null) builder.queryParam("sort", sort);
            if (page != null) builder.queryParam("page", String.valueOf(page));
            builder.queryParam("format", "json");
            builder.queryParam("applicationId", applicationId);
            if (affiliateId != null) builder.queryParam("affiliateId", affiliateId);
            String uri = builder.toUriString().replace("+", "%2B");
            RequestEntity<Void> requestEntity = RequestEntity.get(new URI(uri)).build();
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<BooksBook> response = restTemplate.exchange(requestEntity, BooksBook.class);
            return response.getBody();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (HttpClientErrorException e) {
            throw e;
        }
    }
}
