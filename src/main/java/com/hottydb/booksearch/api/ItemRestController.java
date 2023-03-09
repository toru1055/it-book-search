package com.hottydb.booksearch.api;

import com.hottydb.booksearch.item.ItemEntity;
import com.hottydb.booksearch.item.ItemService;
import com.hottydb.booksearch.item.SearchItemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ItemRestController {
    @Autowired
    private ItemService itemService;

    @GetMapping("/api/items/trends")
    @ResponseBody
    public List<ItemEntity> trends() {
        return itemService.trends();
    }

    @GetMapping("/api/items/search")
    @ResponseBody
    public List<SearchItemEntity> search(@RequestParam("q") String query) {
        return itemService.search(query);
    }

    @GetMapping("/api/items/search/click")
    @ResponseBody
    public boolean click(@RequestParam("request_id") int requestId, @RequestParam("key_id") int keyId) {
        itemService.train(requestId, keyId);
        return true;
    }

    @GetMapping("/api/items/similar")
    @ResponseBody
    public List<ItemEntity> similar(@RequestParam("seed") int seedId) {
        return itemService.similarItems(seedId);
    }

    @GetMapping("/api/items/list")
    @ResponseBody
    public List<ItemEntity> list(@RequestParam("ids") String ids) {
        if (ids == null || ids.length() == 0) return List.of();
        List<Integer> idList = Arrays.asList(ids.split(",")).stream().map(id -> Integer.parseInt(id)).collect(Collectors.toList());
        return itemService.in(idList);
    }
}
