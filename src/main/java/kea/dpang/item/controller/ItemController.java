package kea.dpang.item.controller;

import io.swagger.v3.oas.annotations.Operation;
import kea.dpang.item.dto.*;
import kea.dpang.item.entity.Item;
import kea.dpang.item.service.ItemServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
@Slf4j
public class ItemController {

    private final ItemServiceImpl itemService;

    @GetMapping("/{itemId}")
    @Operation(summary = "상품 상세 정보 조회", description = "상품의 상세 정보를 조회합니다.")
    public ResponseEntity<ItemDetailDto> getItem(@PathVariable Long itemId) {
        ItemDetailDto item = itemService.getItem(itemId);
        log.info("상품 상세 정보 조회 완료. 상품 ID: {}", item.getItemId());

        return ResponseEntity.ok(item);
    }

    @GetMapping("/{itemId}/popular")
    @Operation(summary = "인기 상품 조회", description = "인기 상품을 조회합니다.")
    public ResponseEntity<ItemDetailDto> getPopularItems(@PathVariable Long itemId, Double score) {
        ItemDetailDto item = itemService.getPopularItems(itemId, score);
        log.info("인기 상품 정보 조회 완료. 상 ID: {}", item.getItemId());
        return ResponseEntity.ok(item);
    }


    @PostMapping
    @Operation(summary = "상품 등록", description = "상품 정보를 시스템에 추가합니다.")
    public ResponseEntity<ItemDetailDto> createItem(@RequestBody CreateItemDto createItemDto) {
        ItemDetailDto item = itemService.createItem(createItemDto);
        log.info("새로운 상품 등록 완료. 상품 ID: {}", item.getItemId());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{itemId}")
                .buildAndExpand(item.getItemId())
                .toUri();

        return ResponseEntity.created(location).body(item);
    }

    @PutMapping("/{itemId}")
    @Operation(summary = "상품 수정", description = "기존 상품 정보를 업데이트합니다.")
    public ResponseEntity<ItemDetailDto> updateItem(@PathVariable Long itemId, @RequestBody UpdateItemDto updateItemDto) {
        ItemDetailDto item = itemService.updateItem(itemId, updateItemDto);
        log.info("상품 정보 업데이트 완료. 상품 ID: {}", item.getItemId());

        return ResponseEntity.ok(item);
    }

    @DeleteMapping("/{itemId}")
    @Operation(summary = "상품 삭제", description = "상품 정보를 시스템에서 제거합니다.")
    public ResponseEntity<Void> deleteItem(@PathVariable Long itemId) {
        itemService.deleteItem(itemId);
        log.info("상품 정보 삭제 완료. 상품 ID: {}", itemId);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{itemId}/view")
    @Operation(summary = "조회수 증가", description = "조회 시 조회수가 증가되게 합니다.")
    public ResponseEntity<Void> incrementItemView(@PathVariable Long id) {
        itemService.incrementItemViewCount(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/cart")
    public ResponseEntity<List<ReadCartItemDto>> getCartItem(@RequestParam("itemIds") List<Long> itemIds) {

        // 상품 ID 목록에 해당하는 상품을 찾습니다.
        List<Item> items = itemService.getCartItem(itemIds);

        // 찾은 상품을 ReadCartItemDto로 변환합니다.
        List<ReadCartItemDto> itemInfoDtos = items.stream()
                .map(item -> new ReadCartItemDto(item))
                .collect(Collectors.toList());

        // 변환된 ReadCartItemDto의 리스트를 응답 본문에 담아 반환합니다.
        return ResponseEntity.ok(itemInfoDtos);
    }

}
