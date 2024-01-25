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

    @PostMapping
    @Operation(summary = "상품 등록", description = "상품 정보를 시스템에 추가합니다.")
    public ResponseEntity<ItemResponseDto> createItem(@RequestBody ItemCreateDto itemCreateDto) {
        ItemResponseDto item = itemService.createItem(itemCreateDto);
        log.info("새로운 상품 등록 완료. 상품 ID: {}", item.getItemId());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{itemId}")
                .buildAndExpand(item.getItemId())
                .toUri();

        return ResponseEntity.created(location).body(item);
    }

    @GetMapping("/{itemId}")
    @Operation(summary = "상품 상세 정보 조회", description = "상품의 상세 정보를 조회합니다.")
    public ResponseEntity<ItemResponseDto> getItem(@PathVariable Long itemId) {
        ItemResponseDto item = itemService.getItem(itemId);
        log.info("상품 상세 정보 조회 완료. 상품 ID: {}", item.getItemId());

        return ResponseEntity.ok(item);
    }

    @GetMapping("/items")
    @Operation(summary = "상품 리스트 조회", description = "상품의 썸네일 정보를 조회합니다.")
    public ResponseEntity<List<ItemThumbnailDto>> getItemList() {
        List<ItemThumbnailDto> items = itemService.getItemList();
        return ResponseEntity.ok(items);
    }


    @GetMapping("/{itemId}/popular")
    @Operation(summary = "인기 상품 조회", description = "인기 상품을 조회합니다.")
    public ResponseEntity<List<PopularItemDto>> getPopularItems() {
        List<PopularItemDto> popularItems = itemService.getPopularItems();
        log.info("인기 상품 목록 조회 완료. 조회된 인기 상품 수: {}", popularItems.size());

        return ResponseEntity.ok(popularItems);
    }

    @PutMapping("/{itemId}")
    @Operation(summary = "상품 수정", description = "기존 상품 정보를 업데이트합니다.")
    public ResponseEntity<ItemResponseDto> updateItem(@PathVariable Long itemId, @RequestBody ItemUpdateDto itemUpdateDto) {
        ItemResponseDto item = itemService.updateItem(itemId, itemUpdateDto);
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
    public ResponseEntity<Void> incrementItemView(@PathVariable Long itemId) {
        itemService.incrementItemViewCount(itemId);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/cart")
    @Operation(summary = "장바구니 조회", description = "장바구니의 상품 정보 목록을 조회 합니다.")
    public ResponseEntity<List<ReadCartItemDto>> getCartItemInfo(@RequestParam("itemIds") List<Long> itemIds) {

        // 상품 ID 목록에 해당하는 상품을 찾습니다.
        List<Item> items = itemService.getCartItems(itemIds);

        // 찾은 상품을 ReadCartItemDto로 변환합니다.
        List<ReadCartItemDto> itemInfoDtos = items.stream()
                .map(item -> new ReadCartItemDto(item))
                .collect(Collectors.toList());

        // 변환된 ReadCartItemDto의 리스트를 응답 본문에 담아 반환합니다.
        return ResponseEntity.ok(itemInfoDtos);
    }

    @GetMapping("/cart/{itemId}")
    @Operation(summary = "장바구니 상품 조회", description = "장바구니의 상품 별 상세 정보를 조회 합니다.")
    public ResponseEntity<ReadCartItemDto> getCartItemInfo(@PathVariable("itemId") Long itemId) {

        // item
        Item item = itemService.getCartItem(itemId);

        ReadCartItemDto itemInfo = new ReadCartItemDto(item);

        return ResponseEntity.ok(itemInfo);
    }

    @GetMapping("/wishlist")
    @Operation(summary = "위시리스트 조회", description = "위시리스트의 상품 정보 목록을 조회합니다.")
    public ResponseEntity<List<ReadWishlistItemDto>> getWishlistItemInfo(@RequestParam("itemIds") List<Long> itemIds) {

        // 상품 ID 목록에 해당하는 상품을 찾습니다.
        List<Item> items = itemService.getWishlistItems(itemIds);

        // 찾은 상품을 ReadWishlistItemDto의 리스트로 변환합니다.
        List<ReadWishlistItemDto> itemInfoDtos = items.stream()
                .map(ReadWishlistItemDto::new)
                .toList();

        // 변환된 ReadWishlistItemDto의 리스트를 응답 본문에 담아 반환합니다.
        return ResponseEntity.ok(itemInfoDtos);
    }

    @GetMapping("/wishlist/{itemId}")
    @Operation(summary = "위시리스트 상품 조회", description = "위시리스트의 상품 별 상세 정보를 조회합니다.")
    public ResponseEntity<ReadWishlistItemDto> getWishlistItemInfo(@PathVariable("itemId") Long itemId) {

        // 상품 ID 목록에 해당하는 상품을 찾습니다.
        Item item = itemService.getWishlistItem(itemId);

        // 찾은 상품을 ReadWishlistItemDto로 변환합니다.
        ReadWishlistItemDto itemInfo = new ReadWishlistItemDto(item);

        // 변환된 ReadWishlistItemDto를 응답 본문에 담아 반환합니다.
        return ResponseEntity.ok(itemInfo);
    }
}
