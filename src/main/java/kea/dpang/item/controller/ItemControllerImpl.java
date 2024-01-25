package kea.dpang.item.controller;

import io.swagger.v3.oas.annotations.Operation;
import kea.dpang.item.dto.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemControllerImpl implements ItemController {
    @Override
    @PostMapping
    @Operation(summary = "상품 등록", description = "상품 정보를 시스템에 추가합니다.")
    public ResponseEntity<ItemResponseDto> createItem(@RequestBody ItemCreateDto itemCreateDto) {
        return null;
    }

    @Override
    @GetMapping
    @Operation(summary = "상품 리스트 조회 (프론트엔드)", description = "페이지 정보에 따라 상품 리스트를 조회합니다.")
    public ResponseEntity<List<ItemThumbnailDto>> getItemListForFrontend(Pageable pageable) {
        return null;
    }

    @Override
    @GetMapping("/backend")
    @Operation(summary = "상품 리스트 조회 (백엔드)", description = "지정된 상품 ID 리스트에 대한 상품 정보를 조회합니다.")
    public ResponseEntity<List<ItemThumbnailDto>> getItemListForBackend(@RequestBody List<Long> itemIdList) {
        return null;
    }

    @Override
    @GetMapping("/{itemId}")
    @Operation(summary = "상품 상세 정보 조회", description = "상품 ID를 통해 상세한 상품 정보를 조회합니다.")
    public ResponseEntity<ItemResponseDto> getItem(@PathVariable Long itemId) {
        return null;
    }

    @Override
    @GetMapping("/popular")
    @Operation(summary = "인기 상품 조회", description = "지정된 상품 ID 리스트에 대한 인기 상품 정보를 페이지 정보에 따라 조회합니다.")
    public ResponseEntity<List<PopularItemDto>> getPopularItems(@RequestBody List<Long> itemIdList, Pageable pageable) {
        return null;
    }

    @Override
    @PutMapping("/{itemId}")
    @Operation(summary = "상품 수정", description = "상품 ID에 해당하는 상품 정보를 수정합니다.")
    public ResponseEntity<ItemResponseDto> updateItem(@PathVariable Long itemId, @RequestBody ItemUpdateDto itemUpdateDto) {
        return null;
    }

    @Override
    @DeleteMapping("/{itemId}")
    @Operation(summary = "상품 삭제", description = "상품 ID에 해당하는 상품 정보를 삭제합니다.")
    public ResponseEntity<Void> deleteItem(@PathVariable Long itemId) {
        return null;
    }
}
