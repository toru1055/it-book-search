package com.hottydb.booksearch.rakuten.genre;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
class BooksGenre {
    List<GenreChild> children;
    Genre current;
}
