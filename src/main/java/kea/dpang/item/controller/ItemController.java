package kea.dpang.item.controller;

import kea.dpang.item.entity.Item;
import kea.dpang.item.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<?> getItemById(@PathVariable Long id) {
        return itemService.getItemById(id)
                .map(product -> ResponseEntity.ok().body(product))
                .orElse(ResponseEntity.status(404).body((Item) Map.of(
                        "status", 404,
                        "message", "상품을 찾을 수 없습니다."
                )));
    }
}

