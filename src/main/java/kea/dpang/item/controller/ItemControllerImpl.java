package kea.dpang.item.controller;

import io.swagger.v3.oas.annotations.Operation;
import kea.dpang.item.dto.*;
import kea.dpang.item.service.ItemServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
@Slf4j
public class ItemControllerImpl implements ItemController {

    private final ItemServiceImpl itemService;

    @Override
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

    // pageable : 페이지 번호, 페이지 크기, 정렬 방법
    @Override
    @GetMapping
    @Operation(summary = "상품 리스트 조회 (프론트엔드)", description = "페이지 정보에 따라 상품 리스트를 조회합니다.")
    public ResponseEntity<List<ItemSimpleFrontendDto>> getItemListForFrontend(Pageable pageable) {
        List<ItemSimpleFrontendDto> items = itemService.getItemListForFrontend(pageable);
        return ResponseEntity.ok(items);
    }

    @Override
    @GetMapping("/{itemId}")
    @Operation(summary = "상품 리스트 조회 (백엔드)", description = "지정된 상품 ID 리스트에 대한 상품 정보를 조회합니다.")
    public ResponseEntity<List<ItemSimpleBackendDto>> getItemListForBackend(@RequestBody List<Long> itemId) {
        List<ItemSimpleBackendDto> items = itemService.getItemListForBackend();
        return ResponseEntity.ok(items);
    }

    @Override
    @GetMapping("/{itemId}")
    @Operation(summary = "상품 상세 정보 조회", description = "상품 ID를 통해 상세한 상품 정보를 조회합니다.")
    public ResponseEntity<ItemResponseDto> getItem(@PathVariable Long itemId) {
        ItemResponseDto item = itemService.getItem(itemId);
        log.info("상품 상세 정보 조회 완료. 상품 ID: {}", item.getItemId());

        return ResponseEntity.ok(item);
    }

    @Override
    @GetMapping("/popular")
    @Operation(summary = "인기 상품 조회", description = "지정된 상품 ID 리스트에 대한 인기 상품 정보를 페이지 정보에 따라 조회합니다.")
    public ResponseEntity<List<PopularItemDto>> getPopularItems(@RequestBody List<Long> itemIdList, Pageable pageable) {
        List<PopularItemDto> popularItems = itemService.getPopularItems();
        log.info("인기 상품 목록 조회 완료. 조회된 인기 상품 수: {}", popularItems.size());

        return ResponseEntity.ok(popularItems);
    }

    @Override
    @PutMapping("/{itemId}")
    @Operation(summary = "상품 수정", description = "상품 ID에 해당하는 상품 정보를 수정합니다.")
    public ResponseEntity<ItemResponseDto> updateItem(@PathVariable Long itemId, @RequestBody ItemUpdateDto itemUpdateDto) {
        ItemResponseDto item = itemService.updateItem(itemId, itemUpdateDto);
        log.info("상품 정보 업데이트 완료. 상품 ID: {}", item.getItemId());

        return ResponseEntity.ok(item);
    }

    @Override
    @DeleteMapping("/{itemId}")
    @Operation(summary = "상품 삭제", description = "상품 ID에 해당하는 상품 정보를 삭제합니다.")
    public ResponseEntity<Void> deleteItem(@PathVariable Long itemId) {
        itemService.deleteItem(itemId);
        log.info("상품 정보 삭제 완료. 상품 ID: {}", itemId);

        return ResponseEntity.noContent().build();
    }
}
