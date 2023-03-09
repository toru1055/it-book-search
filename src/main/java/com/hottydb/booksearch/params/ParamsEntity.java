package com.hottydb.booksearch.params;

import com.hottydb.booksearch.rakuten.book.BooksBook;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "params")
@Data
@NoArgsConstructor
public class ParamsEntity {
    @Id
    @GeneratedValue
    Integer id;
    String booksGenreId;
    String sort;
    Integer page;
    Integer pageCount;
    Integer laps;

    public void update(BooksBook booksBook) {
        pageCount = booksBook.getPageCount();
        page += 1;
        if (page > pageCount) {
            page = 1;
            laps += 1;
        }
    }

    public List<ParamsEntity> makeAllSortPatterns() {
        List<ParamsEntity> list = new ArrayList<>();
        list.add(newRecord(this.booksGenreId, "+releaseDate"));
        list.add(newRecord(this.booksGenreId, "-releaseDate"));
        return list;
    }

    public static ParamsEntity newRecord(String booksGenreId, String sort) {
        ParamsEntity p = new ParamsEntity();
        p.booksGenreId = booksGenreId;
        p.sort = sort;
        p.page = 1;
        p.laps = 1;
        return p;
    }
}
