package com.hottydb.booksearch.rakuten.genre;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BooksGenreService {
    @Value("${rakuten.api.applicationId}")
    private String applicationId;

    public BooksGenre get(String genreId) {
        try {
            String urlFormat = "https://app.rakuten.co.jp/services/api/BooksGenre/Search/20121128?format=json&booksGenreId=%s&applicationId=%s";
            String url = String.format(urlFormat, genreId, applicationId);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<BooksGenre> response = restTemplate.exchange(url, HttpMethod.GET, null, BooksGenre.class);
            Thread.sleep(1000);
            return response.getBody();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
