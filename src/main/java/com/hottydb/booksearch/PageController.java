package com.hottydb.booksearch;

import com.hottydb.booksearch.item.ItemEntity;
import com.hottydb.booksearch.item.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController {
    @Autowired
    private ItemService itemService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("title", "Hotty技術書検索 〜 HottyDBを使った技術書の検索サービス");
        model.addAttribute("description", "Hotty技術書検索は、技術書の検索とレコメンデーションのサービスです。HottyDBという次世代型RDBMSを利用して作られており、その機能を紹介するために作られたサイトです。");
        return "index";
    }

    @GetMapping("/item/{itemId}")
    public String item(@PathVariable("itemId") int itemId, Model model) {
        ItemEntity item = itemService.find(itemId);
        model.addAttribute("item", item);
        model.addAttribute("title", String.format("「%s」の関連書籍を探す - Hotty技術書検索", item.getTitle()));
        model.addAttribute("description", item.getItemCaption());
        return "item";
    }

    @GetMapping("/search")
    public String search(@RequestParam("q") String query, Model model) {
        model.addAttribute("query", query);
        model.addAttribute("title", String.format("「%s」の関連書籍を探す - Hotty技術書検索", query));
        model.addAttribute("description", String.format("HottyDBを用いた「%s」の検索結果を表示します。", query));
        return "search";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "Hotty技術書検索とは？ - Hotty技術書検索");
        model.addAttribute("description", "Hotty技術書検索は、技術書の検索とレコメンデーションのサービスです。HottyDBという次世代型RDBMSを利用して作られており、その機能を紹介するために作られたサイトです。");
        return "about";
    }
}
