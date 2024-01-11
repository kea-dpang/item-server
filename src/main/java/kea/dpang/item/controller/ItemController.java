package kea.dpang.item.controller;

import kea.dpang.item.entity.Item;
import kea.dpang.item.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<?> getItemById(@PathVariable Long id) {
        return itemService.getItemById(id)
                .map(item -> ResponseEntity.ok().body(item))
                .orElse(ResponseEntity.status(404).body((Item) Map.of(
                        "status", 404,
                        "message", "상품을 찾을 수 없습니다."
                )));
    }

//    @GetMapping("/search")
//    public ResponseEntity<Map<String, Object>> searchItems(@RequestParam String keyword) {
//        List<Item> items = itemService.searchitems(keyword);
//        List<Map<String, Object>> items = items.stream().map(item -> {
//            Map<String, Object> item = new HashMap<>();
//            item.put("itemId", item.getItemId());
//            item.put("itemName", item.getItemName());
//            item.put("brand", item.getBrand());
//            item.put("itemPrice", item.getItemPrice());
//            item.put("discountPrice", item.getDiscountPrice());
//            item.put("wishlistNot", item.getWishlistNot());
//            item.put("itemImage", item.getItemImage());
//            item.put("numberReview", item.getNumberReview());
//            return item;
//        }).collect(Collectors.toList());
//
//        Map<String, Object> response = new HashMap<>();
//        response.put("status", 200);
//        response.put("message", "상품 검색이 완료되었습니다.");
//        response.put("data", Map.of("items", items));
//
//        return ResponseEntity.ok(response);
    }
}

