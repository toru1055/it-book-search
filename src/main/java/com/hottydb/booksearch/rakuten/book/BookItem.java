package com.hottydb.booksearch.rakuten.book;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hottydb.booksearch.item.ItemEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookItem {
    @JsonProperty("Item")
    ItemEntity item;
}
