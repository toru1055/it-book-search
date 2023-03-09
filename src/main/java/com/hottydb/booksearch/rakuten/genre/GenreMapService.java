package com.hottydb.booksearch.rakuten.genre;

import java.util.*;

public class GenreMapService {
    final BooksGenreService booksGenreService;
    final Map<String, List<String>> genreMap;
    private boolean isMade = false;

    public GenreMapService(BooksGenreService booksGenreService) {
        this.booksGenreService = booksGenreService;
        this.genreMap = new HashMap<>();
    }

    public synchronized void makeGenreMap(String genreId) {
        if (!isMade) {
            genreScan(genreId);
            isMade = true;
        } else throw new RuntimeException("genreMap has been already made");
    }

    public synchronized Map<String, List<String>> getGenreMap() {
        if (!isMade) throw new RuntimeException("genreMap has not been made");
        else return Collections.unmodifiableMap(genreMap);
    }

    public List<String> getLeaves() {
        List<String> leaves = new ArrayList<>();
        for (String genreId : genreMap.keySet()) {
            if (genreMap.get(genreId).size() == 0) leaves.add(genreId);
        }
        return leaves;
    }

    private void genreScan(String genreId) {
        List<String> children = new ArrayList<>();
        BooksGenre booksGenre = booksGenreService.get(genreId);
        for (GenreChild genreChild : booksGenre.children) {
            children.add(genreChild.child.booksGenreId);
        }
        genreMap.put(genreId, children);
        for (String child : children) genreScan(child);
    }
}
