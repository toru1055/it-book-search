package com.hottydb.booksearch.item;

import com.hottydb.booksearch.rakuten.book.BookItem;
import com.hottydb.booksearch.rakuten.book.BooksBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;
    @Value("${ItemService.trends.minReviewCount}")
    private int trendsMinReviewCount;

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void initialize() {
        entityManager.createNativeQuery("CREATE SEARCH INDEX IF NOT EXISTS s1 ON item (search_text)").executeUpdate();
    }

    public List<ItemEntity> trends() {
        int minYear = Calendar.getInstance().get(Calendar.YEAR) - 1;
        List<ItemEntity> itemEntityList = itemRepository.trendItems(minYear, trendsMinReviewCount);
        int to = Math.min(100, itemEntityList.size());
        return itemEntityList.subList(0, to);
    }

    public List<ItemEntity> search(String query) {
        String normalizedQuery = StringUtils.normalize(query);
        List<ItemEntity> itemDtoList = itemRepository.search(normalizedQuery);
        int to = Math.min(30, itemDtoList.size());
        return itemDtoList.subList(0, to);
    }

    public List<ItemEntity> similarItems(int seedId) {
        Optional<ItemEntity> seedItem = itemRepository.findById(seedId);
        String normalizedQuery = StringUtils.normalize(seedItem.get().getTitle());
        List<ItemEntity> itemEntityList = itemRepository.similarItems(normalizedQuery);
        itemEntityList.removeIf(x -> x.getId() == seedId);
        int to = Math.min(30, itemEntityList.size());
        return itemEntityList.subList(0, to);
    }

    public ItemEntity find(int itemId) {
        Optional<ItemEntity> itemDto = itemRepository.findById(itemId);
        return itemDto.get();
    }

    public List<ItemEntity> in(List<Integer> ids) {
        List<ItemEntity> itemEntityList = itemRepository.findAllById(ids);
        ItemEntity[] originalOrder = new ItemEntity[ids.size()];
        for (ItemEntity itemEntity : itemEntityList) {
            int k = ids.indexOf(itemEntity.getId());
            originalOrder[k] = itemEntity;
        }
        return Arrays.asList(originalOrder);
    }

    public void save(BooksBook booksBook) {
        for (BookItem bookItem : booksBook.getItems()) {
            ItemEntity itemEntity = bookItem.getItem();
            itemEntity.setId();
            itemEntity.setSalesYear();
            itemEntity.setSearchText();
            itemRepository.save(itemEntity);
        }
    }

    public long count() {
        return itemRepository.count();
    }
}
