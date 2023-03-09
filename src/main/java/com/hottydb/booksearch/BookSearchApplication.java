package com.hottydb.booksearch;

import com.hottydb.booksearch.item.ItemService;
import com.hottydb.booksearch.rakuten.LoadRakutenItemService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class BookSearchApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(BookSearchApplication.class, args);
		ItemService itemService = ctx.getBean(ItemService.class);
		itemService.initialize();
		LoadRakutenItemService loadRakutenItemService = ctx.getBean(LoadRakutenItemService.class);
		loadRakutenItemService.initialize();
		loadRakutenItemService.run();
	}

}
