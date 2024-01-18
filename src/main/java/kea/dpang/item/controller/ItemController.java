package kea.dpang.item.controller;

import io.swagger.v3.oas.annotations.Operation;
import kea.dpang.item.dto.CreateItemDto;
import kea.dpang.item.dto.ItemDetailDto;
import kea.dpang.item.dto.PopularItemDto;
import kea.dpang.item.dto.UpdateItemDto;
import kea.dpang.item.entity.Item;
import kea.dpang.item.service.ItemServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

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

        // ServletUriComponentsBuilder를 이용해 생성된 직원의 URI를 구성
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{itemId}") // 생성된 직원의 id를 경로에 추가
                .buildAndExpand(item.getItemId()) // 경로에 변수를 추가하고 URI를 빌드
                .toUri();

        // 생성된 직원의 URI를 'Location' 헤더에 추가하고, 상태 코드 201을 반환.
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

    @PostMapping("/{id}/view")
    @Operation(summary = "조회수 증가", description = "조회 시 조회수가 증가되게 합니다.")
    public ResponseEntity<Void> incrementItemView(@PathVariable Long id) {
        itemService.incrementItemViewCount(id);
        return ResponseEntity.ok().build();
    }
}

