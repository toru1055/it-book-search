package com.hottydb.booksearch.rakuten.book;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BooksBook {
    @JsonProperty("Items")
    List<BookItem> items;
    int count;
    int first;
    int hits;
    int last;
    int page;
    int pageCount;
}
