package com.hottydb.booksearch.rakuten;

import com.hottydb.booksearch.item.ItemService;
import com.hottydb.booksearch.params.ParamsEntity;
import com.hottydb.booksearch.params.ParamsService;
import com.hottydb.booksearch.rakuten.book.BooksBook;
import com.hottydb.booksearch.rakuten.book.BooksBookService;
import com.hottydb.booksearch.rakuten.genre.BooksGenreService;
import com.hottydb.booksearch.rakuten.genre.GenreMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoadRakutenItemService {
    @Autowired
    ParamsService paramsService;
    @Autowired
    ItemService itemService;
    @Autowired
    BooksBookService booksBookService;
    @Autowired
    BooksGenreService booksGenreService;

    @Value("${rakuten.api.rootGenreId}")
    private String rootGenreId;

    public void initialize() {
        if (paramsService.count() != 0) return;
        System.out.println("楽天データ抽出プロセスの初期化をしています...");
        GenreMapService genreMapService = new GenreMapService(booksGenreService);
        genreMapService.makeGenreMap(rootGenreId);
        List<String> leafGenres = genreMapService.getLeaves();
        for (String leafGenreId : leafGenres) {
            paramsService.save(ParamsEntity.newRecord(leafGenreId, null));
        }
    }

    @Async
    public void run() {
        while (true) {
            try {
                System.out.println("楽天データ抽出プロセスを開始しています...");
                executeOne();
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println(e.getMessage());
            }
        }
    }

    private void executeOne() throws InterruptedException {
        List<ParamsEntity> paramsList = paramsService.findAll();
        int minLaps = getMinLaps(paramsList);
        int apiIntervalMills = minLaps == 1 ? 1000 : 60000;
        if (minLaps > 1) {
            System.out.println("【1周目の楽天データ抽出は完了しています。2周目以降はゆっくり実行します。】");
        }
        for (ParamsEntity params : paramsList) {
            if (params.getLaps() != minLaps) continue;
            Thread.sleep(apiIntervalMills);
            System.out.println("楽天書籍APIをコールしています..." + params);
            BooksBook booksBook = booksBookService.get(null, params.getBooksGenreId(), params.getSort(), params.getPage());
            itemService.save(booksBook);
            params.update(booksBook);
            if (booksBook.getCount() > 3000 && params.getSort() == null) {
                params.setSort("standard");
                for (ParamsEntity newParams : params.makeAllSortPatterns()) paramsService.save(newParams);
            }
            paramsService.save(params);
            System.out.println("現在のDB内の書籍数 = " + itemService.count());
        }
    }

    private int getMinLaps(List<ParamsEntity> paramsList) {
        int minLaps = Integer.MAX_VALUE;
        for (ParamsEntity paramsEntity : paramsList)
            if (minLaps > paramsEntity.getLaps()) minLaps = paramsEntity.getLaps();
        return minLaps;
    }
}
